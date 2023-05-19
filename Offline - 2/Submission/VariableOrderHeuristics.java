import java.util.ArrayList;

public class VariableOrderHeuristics {
    String VariableOrderingType;
    public VariableOrderHeuristics(String VariableOrderingType){
        this.VariableOrderingType = VariableOrderingType;
    }

    public int getNextVariable(Board board, ArrayList<Square> emptySquares){
        if(VariableOrderingType.equalsIgnoreCase("VAH1")){
            int smallestDomain = board.getDimension() + 1;
            int smallestDomainIndex = -1;
            for(int i = 0; i < emptySquares.size(); i++){
                int domainSize = board.getDomainSize(emptySquares.get(i));
                //System.out.println(emptySquares.get(i).getRow() +" " + emptySquares.get(i).getCol() +" --domainSize: " + domainSize);
                if(domainSize < smallestDomain){
                    smallestDomain = domainSize;
                    smallestDomainIndex = i;
                }
            }
            return smallestDomainIndex;
        }
        else if(VariableOrderingType.equalsIgnoreCase("VAH2")){
            int maxDegree = -1;
            int maxDegreeIndex = -1;
            for(int i = 0; i < emptySquares.size(); i++){
                int degree = board.getDegree(emptySquares.get(i));
                //System.out.println(emptySquares.get(i).getRow() +" " + emptySquares.get(i).getCol() +" --degree: " + degree);
                if(degree > maxDegree){
                    maxDegree = degree;
                    maxDegreeIndex = i;
                }
            }
            return maxDegreeIndex;

        }
        else if(VariableOrderingType.equalsIgnoreCase("VAH3")){
            VariableOrderHeuristics temp = new VariableOrderHeuristics("VAH1");
            int smallestDomainIndex = temp.getNextVariable(board, emptySquares);
            int smallestDomain = board.getDomainSize(emptySquares.get(smallestDomainIndex));
            int maxDegree = 0;
            int maxDegreeIndex = smallestDomainIndex;
            for(int i = 0; i < emptySquares.size(); i++){
                int sz = board.getDomainSize(emptySquares.get(i));
                if(sz == smallestDomain){
                    int degree = board.getDegree(emptySquares.get(i));
                    if(degree > maxDegree){
                        maxDegree = degree;
                        maxDegreeIndex = i;
                    }
                }
            }
            return maxDegreeIndex;
        }
        else if(VariableOrderingType.equalsIgnoreCase("VAH4")){
            double min = board.getDimension() + 2;
            int index = -1;
            for(int i = 0; i < emptySquares.size(); i++){
                int sz = board.getDomainSize(emptySquares.get(i));
                int degree = board.getDegree(emptySquares.get(i));
                double val ;
                if(degree== 0){
                    val = board.getDimension()+1;
                }
                else{
                    val = (double)sz/degree;
                }

                if(val < min){
                    min = val;
                    index = i;

                }
            }
            //System.out.println(index);
            return index;
        }
        else if(VariableOrderingType.equalsIgnoreCase("VAH5")){
            //choose a random number within range of emptySquares.size()
            int randomIndex = (int)(Math.random() * emptySquares.size()-1);
            return randomIndex;
        }
        else{
            return -1;
        }
    }
}
