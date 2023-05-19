import java.util.ArrayList;

public class ValueOrderingHeuristics {
    String ValueOrderingType; //Sequential

    public ValueOrderingHeuristics(String ValueOrderingType){
        this.ValueOrderingType = ValueOrderingType;
    }

    public ArrayList<Integer> getValueOrder(Board board, Square square){
        ArrayList<Integer> valueOrder = new ArrayList<>();
        if(ValueOrderingType.equalsIgnoreCase("Sequential")){
            for(int i = 1; i <= board.getDimension(); i++){
                valueOrder.add(i);
            }
        }
        else if(ValueOrderingType.equalsIgnoreCase("LCV")){
            ArrayList<Square> emptySquares = board.getEmptySquares();
            ArrayList<ArrayList<Integer>> domainsOfEmptySquares = board.getDomains(emptySquares);
            ArrayList<Integer> conflicts = new ArrayList<>();
            for(int i = 1; i <= board.getDimension(); i++){
                valueOrder.add(i);
            }
            for(int i = 0; i < valueOrder.size(); i++){
                int value = valueOrder.get(i);
                int conf = 0;
                for (int j = 0; j < emptySquares.size(); j++) {
                    if (emptySquares.get(j).getRow() == square.getRow() || emptySquares.get(j).getCol() == square.getCol()) {
                        if (domainsOfEmptySquares.get(j).contains(value)) {
                            conf++;
                        }
                    }
                }
                conflicts.add(conf);
            }

            //sort valueOrder based on conflicts
            for(int i = 0; i < valueOrder.size(); i++){
                for(int j = i+1; j < valueOrder.size(); j++){
                    if(conflicts.get(i) > conflicts.get(j)){
                        int temp = valueOrder.get(i);
                        valueOrder.set(i, valueOrder.get(j));
                        valueOrder.set(j, temp);
                        temp = conflicts.get(i);
                        conflicts.set(i, conflicts.get(j));
                        conflicts.set(j, temp);
                    }
                }
            }


        }
        return valueOrder;
    }

    public ArrayList<Integer> getValueOrderFC(Board board, Square square, ArrayList<Integer> domain){
        ArrayList<Integer> valueOrder = new ArrayList<>();
        if(ValueOrderingType.equalsIgnoreCase("Sequential")){
            for(int i = 1; i <= board.getDimension(); i++){
                if(domain.contains(i)){
                    valueOrder.add(i);
                }
            }
        }
        else if(ValueOrderingType.equalsIgnoreCase("LCV")){
            ArrayList<Square> emptySquares = board.getEmptySquares();
            ArrayList<ArrayList<Integer>> domainsOfEmptySquares = board.getDomains(emptySquares);
            ArrayList<Integer> conflicts = new ArrayList<>();
            for(int i = 1; i <= board.getDimension(); i++){
                if(domain.contains(i)){
                    valueOrder.add(i);
                }
            }
            for(int i = 0; i < valueOrder.size(); i++){
                int value = valueOrder.get(i);
                int conf = 0;
                for (int j = 0; j < emptySquares.size(); j++) {
                    if (emptySquares.get(j).getRow() == square.getRow() || emptySquares.get(j).getCol() == square.getCol()) {
                        if (domainsOfEmptySquares.get(j).contains(value)) {
                            conf++;
                        }
                    }
                }
                conflicts.add(conf);
            }

            //sort valueOrder based on conflicts
            for(int i = 0; i < valueOrder.size(); i++){
                for(int j = i+1; j < valueOrder.size(); j++){
                    if(conflicts.get(i) > conflicts.get(j)){
                        int temp = valueOrder.get(i);
                        valueOrder.set(i, valueOrder.get(j));
                        valueOrder.set(j, temp);
                        temp = conflicts.get(i);
                        conflicts.set(i, conflicts.get(j));
                        conflicts.set(j, temp);
                    }
                }
            }

        }
        return valueOrder;
    }
}
