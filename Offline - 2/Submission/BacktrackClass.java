import java.util.ArrayList;

import static java.util.Collections.swap;

public class BacktrackClass {
    private int nodeNum;
    private int backtrackNum;
    private  VariableOrderHeuristics vah;
    private  ValueOrderingHeuristics valueH;

    public BacktrackClass(VariableOrderHeuristics vah, ValueOrderingHeuristics valueH){
        this.vah = vah;
        this.valueH = valueH;
    }


    public void backTrack(Board board){
        nodeNum = 0;
        backtrackNum = 0;
        ArrayList<Square> emptySquares = board.getEmptySquares();
        long start = System.currentTimeMillis();
        if(backTrackRecursive(board, emptySquares) == true){
            System.out.println("Solution found!");
            board.printBoard();
        }
        else{
            nodeNum = -1;
            backtrackNum = -1;
            System.out.println("No solution found!");
        }
        long end = System.currentTimeMillis();
        System.out.println("Number of nodes: " + nodeNum);
        System.out.println("Number of backtracks: " + backtrackNum);
        System.out.println("Time taken: " + (end - start) + " ms");
    }

    public boolean backTrackRecursive(Board board, ArrayList<Square> emptySquares){
        nodeNum++;
        if(board.isLegal() == false){
            backtrackNum++;
            return false;
        }
        if(emptySquares.isEmpty()){
            return true;
        }
        //VariableOrderHeuristics vah = new VariableOrderHeuristics("SmallestDomainFirst");
        int nextIndex = vah.getNextVariable(board, emptySquares);
        //System.out.println("nextindex: " + nextIndex);
        Square nextSquare = emptySquares.get(nextIndex);
        emptySquares.remove(nextIndex);

        ArrayList<Integer> valuess = valueH.getValueOrder(board, nextSquare);
        for(int i=0; i<valuess.size(); i++){
            board.setSquareValue(nextSquare, valuess.get(i));
            if(backTrackRecursive(board, emptySquares) == true){
                return true;
            }
            board.setSquareValue(nextSquare, 0);
        }

        emptySquares.add(nextIndex, nextSquare);

        return false;
    }
}
