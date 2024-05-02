package test;

import java.util.Arrays;

public class Word {
    private Tile[] tiles;
    public final int row;
    public final int col;
    public final boolean vertical; //T if its up to down, F if its right to left

    public Word(Tile[] t, int row, int col, boolean vertical){
        this.tiles= new Tile [t.length];
        for(int i=0;i<t.length;i++) 
            tiles[i]= t[i];
        this.row=row;
        this.col=col;
        this.vertical=vertical;
    }
    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public boolean isVertical() {
        return vertical;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(tiles);
        result = prime * result + row;
        result = prime * result + col;
        result = prime * result + (vertical ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Word other = (Word) obj;
        if (!Arrays.equals(tiles, other.tiles))
            return false;
        if (row != other.row)
            return false;
        if (col != other.col)
            return false;
        if (vertical != other.vertical)
            return false;
        return true;
    }
 
}
