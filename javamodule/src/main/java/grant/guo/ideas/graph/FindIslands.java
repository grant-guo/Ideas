package grant.guo.ideas.graph;

/**
 You are given a 2D matrix integers. A group of connected 1’s represents an island.

 Connected is defined as an adjacent block that is neighbouring horizontally, vertically or diagonally.
 Example:
 int[][] map = new int[][] {
 {1, 1, 0, 0, 0},
 {0, 1, 0, 0, 1},
 {1, 0, 0, 1, 1},
 {0, 0, 0, 0, 0},
 {1, 0, 1, 0, 1}
 };

 Your function should return a count of 5.

 **/

public class FindIslands {

    public static void main(String[] args) {
        int[][] map = new int[][]{
            {1, 1, 0, 0, 0},
            {0, 1, 0, 0, 1},
            {1, 0, 0, 1, 1},
            {1, 0, 0, 0, 0},
            {1, 0, 1, 0, 1}
        };

        System.out.println(map.length);
        System.out.println(map[0].length);

        FindIslands inst = new FindIslands();

        System.out.println("There are " + inst.findIslands1(map) + " islands.");
        System.out.println("There are " + inst.findIslands2(map) + " islands.");
    }

    private int findIslands1(int[][] map) {

        boolean[][] visited = new boolean[][]{
                {false, false, false, false, false,},
                {false, false, false, false, false,},
                {false, false, false, false, false,},
                {false, false, false, false, false,},
                {false, false, false, false, false,}

        };

        int count = 0;
        for(int i = 0;i < 5;i++){
            for(int j = 0;j < 5;j++) {
                if(map[i][j] == 1 && visited[i][j] == false) {
                    dfs(map, i, j, visited);
                    count++;
                }
            }
        }

        return count;
    }

    private void dfs(int[][] map, int row, int col, boolean[][] visited) {
        visited[row][col] = true;
        int rowNbr[] = new int[] {-1, -1, -1,  0, 0,  1, 1, 1};
        int colNbr[] = new int[] {-1,  0,  1, -1, 1, -1, 0, 1};

        for(int k=0;k<8;k++) {
            if(isValidNeighbour(map, row+rowNbr[k], col+colNbr[k], visited) == true){
                dfs(map, row+rowNbr[k], col+colNbr[k], visited);
            }
        }

    }

    private boolean isValidNeighbour(int[][] map, int row, int col, boolean[][] visited) {
        return (row >= 0) && (row < 5) && (col >=0 ) && (col < 5) &&
                (map[row][col] == 1) && (visited[row][col] == false);
    }

    private int findIslands2(int[][] map) {
        return 0;
    }
}
