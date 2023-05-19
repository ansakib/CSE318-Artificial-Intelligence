import java.util.ArrayList;

public class ForwardCheckClass {
    private int nodeNum;
    private int backTrackNum;

    private VariableOrderHeuristics vah;
    private ValueOrderingHeuristics valueH;

    public ForwardCheckClass(VariableOrderHeuristics vah, ValueOrderingHeuristics valueH){
        this.vah = vah;
        this.valueH = valueH;
    }

    private boolean isArc(Square x, Square y){
        if(x==y) return false;
        if(x.getRow() == y.getRow() || x.getCol() == y.getCol()) return true;
        return false;
    }

    public void forwardCheck(Board board){
        nodeNum = 0;
        backTrackNum = 0;
        ArrayList<Square> emptySquares = board.getEmptySquares();
        ArrayList<ArrayList<Integer>> domainsOfEmptySquares = board.getDomains(emptySquares);

        long start = System.currentTimeMillis();
        if(forwardCheckRecursive(board, emptySquares, domainsOfEmptySquares) == true){
            System.out.println("Solution found!");
            board.printBoard();
        }
        else{
            nodeNum = -1;
            backTrackNum = -1;
            System.out.println("No solution found!");
        }
        long end = System.currentTimeMillis();
        System.out.println("Number of nodes: " + nodeNum);
        System.out.println("Number of backtracks: " + backTrackNum);
        System.out.println("Time taken: " + (end - start) + " ms");
    }

    public boolean forwardCheckRecursive(Board board, ArrayList<Square> emptySquares, ArrayList<ArrayList<Integer>> domainsOfEmptySquares){
        nodeNum++;

        //if domains of empty squares is empty, return false
        for(int i=0; i<domainsOfEmptySquares.size(); i++){
            if(domainsOfEmptySquares.get(i).isEmpty()){
                //System.out.println("Empty domain");
                backTrackNum++;
                return false;
            }
        }

        if(emptySquares.isEmpty()){
            return true;
        }

        int nextIndex = vah.getNextVariable(board, emptySquares);
        Square nextSquare = emptySquares.get(nextIndex);
        ArrayList<Integer> nextSquareDomain = domainsOfEmptySquares.get(nextIndex);
        emptySquares.remove(nextIndex);
        domainsOfEmptySquares.remove(nextIndex);
        ArrayList<Integer> values = valueH.getValueOrderFC(board, nextSquare, nextSquareDomain);

        for(int i=0; i<values.size(); i++){
            board.setSquareValue(nextSquare, values.get(i));
            ArrayList<ArrayList<Integer>> newDomainsOfEmptySquares = new ArrayList<>();
            for(int j=0; j<domainsOfEmptySquares.size(); j++){
                newDomainsOfEmptySquares.add(new ArrayList<>(domainsOfEmptySquares.get(j)));
            }
            for(int j=0; j<emptySquares.size(); j++){
                if(isArc(nextSquare, emptySquares.get(j))){
                    if(newDomainsOfEmptySquares.get(j).contains(values.get(i))){
                        newDomainsOfEmptySquares.get(j).remove(values.get(i));

                    }
                }
            }
            if(forwardCheckRecursive(board, emptySquares, newDomainsOfEmptySquares)){
                return true;
            }
            board.setSquareValue(nextSquare, 0);

        }
        emptySquares.add(nextIndex, nextSquare);
        domainsOfEmptySquares.add(nextIndex, nextSquareDomain);
        return false;
    }
}
