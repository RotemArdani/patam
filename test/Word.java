package test;


public class Word {
    private Tile[] tiles;
    public final int row;
    public final int col;
    public final boolean vertical; //T if its up to down, F if its right to left

    public Word(Tile[] t, int row, int col, boolean vertical){
        this.tiles= Tile[t.length];
        for(int i=0;i<t.length;t++) {
            tiles[i]=new Tile(t[i].letter, t[i].score);
        }
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
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
