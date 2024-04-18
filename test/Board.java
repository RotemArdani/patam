package test;
import java.util.ArrayList;
import java.util.HashMap;


public class Board {
    enum Type {
        STAR,
        DOUBLEWORD,
        DOUBLETTER,
        TRIPPLEWORD,
        TRIPPLELETTER;
    }
    private static Board single_instance= null;
    public Tile[][] gameBoard;
    HashMap<String,Type> specialMap;

    private Board(){
        gameBoard= new Tile[15][15];
        specialMap= new HashMap<>(70);
        initSpecialSquars();
    }

    private void initSpecialSquars(){ //init all the special sqares by using determinSquareType
        int[][] specialSquare = new int[][] {
                {0,0}, {0,7}, {0,14},{7,0}, {7,14}, {14,0}, {14,7}, {14,14}, {7,7},
                {0,3}, {0,11}, {2,6}, {2,8}, {3,0}, {3,7}, {3,14}, {6,2}, {6,6}, {6,8}, {6,12}, {7,3}, {7,11}, {8,2}, {8,6}, {8,8}, {8,12}, {11,0}, {11,7}, {11,14}, {12,6}, {12,8}, {14,3}, {14,11},
                {1,5}, {1,9}, {5,1}, {5,5}, {5,9}, {5,13}, {9,1}, {9,5}, {9,9}, {9,13}, {13,5}, {13,9},
                {1,1}, {1,13}, {2,2}, {2,12}, {3,3}, {3,11} , {4,4}, {4,10}, {10,4}, {10,10}, {11,3}, {11,11}, {12,2}, {12,12}, {13,1}, {13,13},
        };
        
        for (int[] square : specialSquare){
            int row = square[0];
            int col = square[1];
            int position = row + col;
            String stringPosition = Integer.toString(position);
            specialMap.put(stringPosition, determinSquareType(row,col));
        }
    }

    public Type determinSquareType (int row, int col){
        if (row == 7 && col == 7)
            return Type.STAR;
        if ((row == 0 && (col== 0 || col == 7 || col == 14)) || (row ==7 && (col == 0 || col == 14)) || (row ==14 && (col == 0 || col == 7 || col == 14))) //red
            return Type.TRIPPLEWORD;
        if ((row == 0 && (col == 11 || col == 3)) || (row == 2 && (col == 6 || col == 8)) || (row == 3 && (col == 0 || col == 7 || col == 14)) || (row == 6 && (col == 2 || col == 6 || col == 8 || col == 12)) || (row == 7 && (col == 11 || col == 3)) ||
                (row == 8 && (col == 2 || col == 6 || col == 8 || col == 12)) || (row == 11 && (col == 0 || col == 7 || col == 14)) || (row == 12 && (col == 6 || col == 8)) || (row == 14 && (col == 11 || col == 3))) //light blue
            return Type.DOUBLETTER;
        if ((row == 1 && (col== 5 || col == 9)) || (row == 5 && (col == 1 || col == 5 || col == 9 || col == 13)) || (row ==9 && (col == 1 || col == 5 || col == 9 || col == 13)) ||
                (row == 13 && (col== 5 || col == 9))) //dark blue
            return Type.TRIPPLELETTER;
        if ((row == 1 && (col == 1 || col == 13)) || (row == 2 && (col == 2 || col == 12)) || (row == 3 && (col == 3 || col == 11))  || (row == 4 && (col == 4 || col == 10)) || (row == 10 && (col == 4 || col == 10))
                || (row == 11 && (col == 3 || col == 11)) || (row == 12 && (col == 2 || col == 12)) || (row == 13 && (col == 13 || col == 1))) //yellow
            return Type.DOUBLEWORD;
        return null;
    }

    public static Board getBoard(){
        if (single_instance == null)
            single_instance = new Board();

        return single_instance;
    }

    public boolean dictionaryLegal(){
        return true;
    }

    public int getScore(Word word) {
        boolean vertical = word.isVertical();
        int len = word.getTiles().length;
        Tile[] tiles = word.getTiles();
        int row = word.getRow();
        int col = word.getCol();
        int result = 0, sumword = 0;
        for (Tile tile : tiles) {
            if (tile != null)
                sumword += tile.score;
        }
        result = calculateScore(vertical, len, tiles, sumword, row, col);
        return result;
    }
    
    private int calculateScore(boolean vertical, int len, Tile[] tiles, int sumword, int row, int col) {
        int i = 0, result = 0;
        while (i < len) {
            int position = vertical ? (row + i) + col : row + (col + i);
            String stringPosition = Integer.toString(position);
            Type t = specialMap.get(stringPosition);
            switch (t) {
                case DOUBLEWORD:
                    result = sumword * 2;
                    break;
                case DOUBLETTER:
                    if (tiles[i] != null)
                        result = sumword - tiles[i].score + (tiles[i].score * 2);
                    else
                        result = sumword + (gameBoard[vertical ? row + i : row][vertical ? col : col + i].score * 3);
                    break;
                case TRIPPLEWORD:
                    result = sumword * 3;
                    break;
                case TRIPPLELETTER:
                    if (tiles[i] != null)
                        result = sumword - tiles[i].score + (tiles[i].score * 3);
                    else
                        result = sumword + (gameBoard[vertical ? row + i : row][vertical ? col : col + i].score * 3);
                    break;
                default:
                    System.out.println("not a special position");
                    break;
            }
            i++;
        }
        return result;
    }
    
    public boolean boardLegal(Word word){
        boolean vertical = word.isVertical();
        int len = word.getTiles().length;
        int row = word.getRow();
        int col = word.getCol();
        boolean isInBound = (vertical && row+len < gameBoard.length && col<gameBoard.length) || (!vertical && col+len < gameBoard.length && row<gameBoard.length);
        if(!isInBound)
            return false;
        else { //word inbounds
            if (gameBoard[7][7] == null){
                if (row == 7 || col == 7)
                    return true;
                
                return false; //first turn must be on [7][7], star
                }
            else { //not first turn + word inbounds
                if (vertical){ //up to down
                    for(int i=0;i<len;i++){
                        if (gameBoard[row+i][col] != null)
                        return true;
                    }
                }
                else { //left to right
                    for(int i=0;i<len;i++){
                        if (gameBoard[row][col+i] != null)
                        return true;
                    }
                }
            }
        }
        return false;
    }

    

    public ArrayList<Word> getWords(Word word){

    }

    public Tile[][] getTiles(){
        return gameBoard;
    }


}
