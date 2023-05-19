import java.io.*;
import java.util.ArrayList;

public class Board {
    private ArrayList<ArrayList<Integer>> board;
    private File file;
    private int dimension;
    Board(File file) {
        this.file = file;
        board = new ArrayList<>();
    }
    public void loadBoard() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        line = br.readLine();
        dimension = Integer.parseInt(line.substring(2, line.length() - 1));
        for (int i = 0; i < dimension; i++) {
            board.add(new ArrayList<Integer>());
            for (int j = 0; j < dimension; j++) {
                board.get(i).add(0);
            }
        }
        //read 2lines of garbage
        br.readLine();
        br.readLine();

        int row = 0;
        while ((line = br.readLine()) != null) {
            if (row == dimension - 1) {
                line = line.substring(0, line.length() - 4);
            } else {
                line = line.substring(0, line.length() - 2);
            }
            String[] values = line.split(", ");
            for (int i = 0; i < values.length; i++) {
                board.get(row).set(i, Integer.parseInt(values[i]));
            }
            row++;
        }

        br.close();

    }

    public void printBoard() {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                System.out.print(board.get(i).get(j));
                if(board.get(i).get(j) < 10){
                    System.out.print("  ");
                }else{
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    public ArrayList<Square> getEmptySquares() {
        ArrayList<Square> emptySquares = new ArrayList<>();
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (board.get(i).get(j) == 0) {
                    emptySquares.add(new Square(i, j));
                }
            }
        }
        return emptySquares;
    }

    public int getDimension() {
        return dimension;
    }

    public boolean isLegal(){
        ArrayList<Integer> row = new ArrayList<>();
        ArrayList<Integer> column = new ArrayList<>();
        for(int i=0; i<dimension; i++){
            for(int j=0; j<dimension; j++){
                if(board.get(i).get(j)!=0){
                    if(row.contains(board.get(i).get(j))){
                        /*System.out.println(i+ " " + j);
                        System.out.println(board.get(i).get(j));
                        System.out.println(row);
                        System.out.println(column);*/
                        return false;
                    }
                    row.add(board.get(i).get(j));
                }
                if(board.get(j).get(i)!=0){
                    if(column.contains(board.get(j).get(i))){
                        /*System.out.println(i+ " " + j);
                        System.out.println(board.get(j).get(i));
                        System.out.println(row);
                        System.out.println(column);*/
                        return false;
                    }
                    column.add(board.get(j).get(i));
                }
            }
            row.clear();
            column.clear();
        }
        return true;
    }

    public int getDomainSize(Square square){
        int row = square.getRow();
        int column = square.getCol();
        ArrayList<Integer> domain = new ArrayList<>();
        for(int i=1; i<=dimension; i++){
            domain.add(i);
        }
        for(int i=0; i<dimension; i++){
            if(domain.contains(board.get(row).get(i))){
                domain.remove(board.get(row).get(i));
            }
            if(domain.contains(board.get(i).get(column))){
                domain.remove(board.get(i).get(column));
            }
        }
        return domain.size();
    }

    public void setSquareValue(Square square, int value){
        board.get(square.getRow()).set(square.getCol(), value);
    }

    public int getDegree(Square square){
        int degree = 0;
        int row = square.getRow();
        int column = square.getCol();
        for(int i=0; i<dimension; i++){
            if(board.get(row).get(i)==0){
                degree++;
            }
            if(board.get(i).get(column)==0){
                degree++;
            }
        }
        return degree-2;
    }

    public ArrayList<ArrayList<Integer>> getDomains(ArrayList<Square> emptySquares){
        ArrayList<ArrayList<Integer>> domains = new ArrayList<>();
        for(int i=0; i<emptySquares.size(); i++){
            domains.add(new ArrayList<>());
            for(int j=1; j<=dimension; j++){
                domains.get(i).add(j);
            }
        }
        for(int i=0; i<emptySquares.size(); i++){
            int row = emptySquares.get(i).getRow();
            int column = emptySquares.get(i).getCol();
            for(int j=0; j<dimension; j++){
                if(domains.get(i).contains(board.get(row).get(j))){
                    domains.get(i).remove(board.get(row).get(j));
                }
                if(domains.get(i).contains(board.get(j).get(column))){
                    domains.get(i).remove(board.get(j).get(column));
                }
            }
        }
        return domains;
    }

    public ArrayList<Integer> getSingleDomain(Square square){
        ArrayList<Integer> domain = new ArrayList<>();
        for(int i=1; i<=dimension; i++){
            domain.add(i);
        }
        int row = square.getRow();
        int column = square.getCol();
        for(int i=0; i<dimension; i++){
            if(domain.contains(board.get(row).get(i))){
                domain.remove(board.get(row).get(i));
            }
            if(domain.contains(board.get(i).get(column))){
                domain.remove(board.get(i).get(column));
            }
        }
        return domain;
    }
}
