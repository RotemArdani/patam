package test;
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

    void initSpecialSquars(){ //init all the special sqares by using determinSquareType
        int specialSquare[][] = new int[][] {
                {0,0}, {0,7}, {0,14},{7,0} {7,14}, {14,0}, {14,7}, {14,14}, {7,7}
                {0,3}, {0,11}, {2,6}, {2,8}, {3,0}, {3,7}, {3,14}, {6,2}, {6,6}, {6,8}, {6,12}, {7,3}, {7,11}, {8,2}, {8,6}, {8,8}, {8,12}, {11,0}, {11,7}, {11,14}, {12,6}, {12,8}, {14,3}, {14,11},
                {1,5}, {1,9}, {5,1}, {5,5}, {5,9}, {5,13}, {9,1}, {9,5}, {9,9}, {9,13}, {13,5}, {13,9},
                {1,1}, {1,13}, {2,2}, {2,12}, {3,3}, {3,11} , {4,4}, {4,10}, {10,4}, {10,10}, {11,3}, {11,11}, {12,2}, {12,12}, {13,1}, {13,13}
        };
        for (square i : specialSquare){
            int row = square[0];
            int col = square[1];
            String position = row + col;
            specialMap.put(position, determinSquareType(row,col));
        }
    }

    Type determinSquareType(int row, int col){
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
    }

    public static Board getBoard(){
        if (single_instance == null)
            single_instance = new Board();

        return single_instance;
    }

    public boolean dictionaryLegal(){
        return true;
    }

    public int getScore(Word word){
        boolean vertical = word.isVertical();
        int len = word.getTiles().length;
        Tile[] tiles = word.getTiles();
        int row = word.getRow();
        int col = word.getCol();
        int i = 0, sum = 0;
        if (vertical){
            while (i < len){
                int tileRow = row + i ;
                String coedinate = tileRow + col;
                Type t = specialMap.get(coedinate);
                switch (t) {
                    case DOUBLEWORD:
                        word;
                        break;
                    case FRIDAY:
                        System.out.println("Fridays are better.");
                        break;
                    case SATURDAY:
                    case SUNDAY:
                        System.out.println("Weekends are best.");
                        break;
                    default:
                        System.out.println("Midweek days are so-so.");
                        break;
                }
                }
                STAR,
                        DOUBLEWORD,
                        DOUBLETTER,
                        TRIPPLEWORD,
                        TRIPPLELETTER;

            }
        }

    }

    public ArrayList<Word> getWords(Word word){

    }

    public Tile[][] getTiles(){
        return gameBoard;
    }

    public boolean boardLegal(Word word){
        boolean vertical = word.isVertical();
        int len = word.getTiles().length;
        int row = word.getRow();
        int col = word.getCol();
        boolean a1, a2, a3;
        a1= (vertical && row+len < gameBoard.length) || (!vertical && col+len < gameBoard.length);


    }


}
