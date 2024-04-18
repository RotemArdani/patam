package test;

import java.util.Objects;
import java.util.Random;

public class Tile {
    public final char letter;
    public final int score;

    private Tile(char letter, int score){
        this.letter=letter;
        this.score=score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; //same reference
        if (!(o instanceof Tile tile)) return false;
        Tile t = (Tile) o;
        return this.letter == t.letter && this.score == t.score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(letter, score);
    }


    public static class Bag {
        private static Bag single_instance = null;
        private final int size;
        private int bagCount;
        private int[] LetterQuantity; //every letter, and how much we have from it
        private final int[] TempLetterQuantity;
        private Tile[] tiles;

        private Bag() {
            size = 26;
            bagCount = 98;
            LetterQuantity = new int[size];
            TempLetterQuantity = new int[size];
            tiles = new Tile[size];
            LetterQuantity = new int[]{9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};
            TempLetterQuantity = (int[]) LetterQuantity.clone();
            int[] score = new int[]{1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};
            char temp = 'A';
            for (int i = 0; i < size; i++) {
                this.tiles[i] = new Tile(temp, score[i]);
                temp++;
            }
        }

        public Tile getRand() {
            if (bagCount == 0)
                return null;
            Random rand = new Random();
            while (true) {
                int Index = rand.nextInt(tiles.length);
                if (LetterQuantity[Index] > -1 ) {
                    LetterQuantity[Index]--;
                    bagCount--;
                    return tiles[Index];
                }
            }
        }

        public Tile getTile(char ch) {
            int i = ch - 65;
            if ((i<LetterQuantity.length) && (i > -1) && (LetterQuantity[i] > 0) {
                LetterQuantity[i]--;
                bagCount--;
                return tiles[i];
            }
            else
                return null;
        }


        public void put(Tile t){
            int i = t.letter - 65;
            if (LetterQuantity[i] +1 <= TempLetterQuantity[i]) {
                LetterQuantity[i]++;
                bagCount++;
            }
        }

       public int size(){
            return bagCount;
       }

       public int[] getQuantities(){ //the temporary one or the one we work on?
            int[] copy=(int[]) LetterQuantity.clone();
            return copy;
        }

       public static Bag getBag(){
           if (single_instance == null)
               single_instance = new Bag();

           return single_instance;
       }


    }
}

