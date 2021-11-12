package grant.guo.ideas.log.parser

import grant.guo.ideas.log.parser.Log.QUOTED
import grant.guo.ideas.log.parser.LogParserState.{ESCAPING, KEY, KEY_START, KV_END, LogParserState, QUOTED_VALUE, START, TAILING_SPACE_IN_KEY, VALUE, VALUE_START}

import scala.collection.mutable.ListBuffer
import java.nio.charset.StandardCharsets

/**
 * this class is used to parse log string which is composed of key/value pairs, separated with space
 *
 * parser uses a state machine - LogParserStateMachine
 */

case class Log(kv: List[(String, String, QUOTED)])
object Log {
  type QUOTED = Boolean
}

class LogParser {
  val stateMachine = new LogParserStateMachine
  def parse(raw: Array[Byte]): Log = {
    parse(new String(raw, StandardCharsets.UTF_8))
  }

  def parse(raw: String): Log = {
    for(c <- raw) {
      stateMachine.accept(c)
    }
    stateMachine.done()
  }
}

class LogParserStateMachine {
  var state: LogParserState = START
  var context: Context = Context.newContext
  def accept(c: Char): Unit = {
    state match {
      case START =>
        c match {
          case ' ' =>
          case '"' => throw new LogFormatErrorException("Double quotes can't be in KEY")
          case _ => state = KEY_START
        }
      case KEY_START =>
        c match {
          case ' ' =>
          case cc if (cc.isLetter || cc.equals('_')) => {
            context.key.append(cc)
            state = KEY
          }
          case _ => {
            println(s"${context.cache.toList}")
            println(s"new key starts with '$c'")
            throw new LogFormatErrorException("KEY only can start with letters or '_' ")
          }
        }
      case KEY =>
        c match {
          case ' ' => state = TAILING_SPACE_IN_KEY
          case '=' => state = VALUE_START
          case _ => context.key.append(c)
        }
      case VALUE_START =>
        c match {
          case ' ' =>
          case '"' => state = QUOTED_VALUE
          case _ => {
            context.value.append(c)
            state = VALUE
          }
        }
      case VALUE =>
        c match {
          case ' ' => {
            context.cache.append((context.key.toString, context.value.toString, false))
            state = KV_END
          }
          case _ => context.value.append(c)
        }
      case KV_END => {
        context.key.setLength(0)
        context.value.setLength(0)
        c match {
          case ' ' | '\t' =>
          case _ => {
            state = KEY_START
            accept(c)
          }
        }
      }
      case QUOTED_VALUE =>
        c match {
          case '"' => {
            context.cache.append((context.key.toString, context.value.toString, true))
            state = KV_END
          }
          case '\\' => state = ESCAPING
          case _ => context.value.append(c)
        }
      case ESCAPING => {
        context.value.append('\\')
        context.value.append(c)
        state = QUOTED_VALUE
      }
    }//end of state match
  }// end of accept

  def done(): Log = {
    val ret = Log(context.cache.toList)
    context.cache.clear()
    ret
  }

  case class Context(key: StringBuilder, value: StringBuilder, cache: ListBuffer[(String, String, QUOTED)])
  object Context {
    def newContext: Context = Context(new StringBuilder(), new StringBuilder(), ListBuffer.empty)
  }
}

object LogParserState extends Enumeration {
  type LogParserState = Value
  val START, KEY_START, KEY, TAILING_SPACE_IN_KEY, VALUE_START, VALUE, KV_END, QUOTED_VALUE, ESCAPING = Value
}

class LogFormatErrorException(msg: String) extends Exception(msg)