package grant.guo.ideas.log.parser

import scala.collection.mutable.ListBuffer

/**
 * this class is used to parse log string which is composed of key/value pairs, separated with space into Map[String, String]
 *
 * parser uses a state machine - LogParserStateMachine
 */
class LogParser {
  def parse(line: String): (List[(String, String)], List[(String, Boolean)]) = {
    val machine = new LogParserStateMachine
    for(c <- line) {
      machine.accept(c)
    }
    machine.results()
  }
}

class LogParserStateMachine {


  var status = LogParserStatus.KEY // the initial state of the machine is KEY
  val keyBuffer = ListBuffer.empty[Char]
  val valueBuffer = ListBuffer.empty[Char]
  val outputBuffer = ListBuffer.empty[(String, String)]
  val fieldQuoted = ListBuffer.empty[(String, Boolean)] // if the value of a field is quoted

  def accept(input: Char): Unit = {
    status match {
      case LogParserStatus.KEY => {
        input match {
          case '=' => status = LogParserStatus.KEY_END
          case cc if(input.isDigit || input.isLetter || input.equals('_')) => keyBuffer.append(cc)
          case _ => throw new Exception(s"invalid character in key '$input'")
        }
      }
      case LogParserStatus.KEY_END => {
        input match {
          case '"' => status = LogParserStatus.QUOTEDVALUE
          case _ => {
            status = LogParserStatus.NORMALVALUE
            valueBuffer.append(input)
          }
        }
      }
      case LogParserStatus.NORMALVALUE => {
        input match {
          case ' ' => {
            status = LogParserStatus.KV_END
            outputBuffer.append((keyBuffer.mkString(""), valueBuffer.mkString("")))
            fieldQuoted.append((keyBuffer.mkString(""), false))
            keyBuffer.clear()
            valueBuffer.clear()
          }
          case _ => valueBuffer.append(input)
        }
      }
      case LogParserStatus.QUOTEDVALUE => {
        input match {
          case '"' => {
            status = LogParserStatus.KV_END
            outputBuffer.append((keyBuffer.mkString(""), valueBuffer.mkString("")))
            fieldQuoted.append((keyBuffer.mkString(""), true))
            keyBuffer.clear()
            valueBuffer.clear()
          }
          case '\\' => {
            status = LogParserStatus.ESCAPING_DETECTED
          }
          case _ => {
            valueBuffer.append(input)
          }
        }
      }
      case LogParserStatus.KV_END => {
        input match {
          case ' ' =>
          case _ => {
            status = LogParserStatus.KEY
            keyBuffer.append(input)
          }
        }
      }
      case LogParserStatus.ESCAPING_DETECTED => {
        status = LogParserStatus.QUOTEDVALUE
        valueBuffer.append(input)
      }
    }
  }

  def results(): (List[(String, String)], List[(String, Boolean)]) = {
    (outputBuffer.toList, fieldQuoted.toList)
  }
}

object LogParserStatus extends Enumeration {
  type LineParseStatus = Value
  val KEY, // processing KEY
  KEY_END, // meet "=", which means key processing is done
  NORMALVALUE, // value without quotes
  QUOTEDVALUE, // value with quotes, which means there might be "\" in the value string to escape charater
  KV_END, // done with key/value pair processing
  ESCAPING_DETECTED = Value // a "\" detected
}