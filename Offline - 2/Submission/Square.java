import java.util.ArrayList;

public class Square {
    private int row, col;
    //private int domainSize;
    Square(int row, int col) {
        this.row = row;
        this.col = col;
    }
    public boolean equals(Object o) {
        if (o instanceof Square) {
            Square c = (Square) o;
            return (row == c.row && col == c.col);
        } else {
            return false;
        }
    }
    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }

    //tostring
    public String toString() {
        return "(" + row + "," + col + ")";
    }

}
