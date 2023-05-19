import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        //File file = new File("d-10-01.txt");
        //File file = new File("d-10-06.txt");
        //File file = new File("d-10-07.txt");
        //File file = new File("d-10-08.txt");
        //File file = new File("d-10-09.txt");
        File file = new File("d-15-01.txt");
        //File file = new File(("test.txt"));

        VariableOrderHeuristics vah = new VariableOrderHeuristics("VAH4");
        ValueOrderingHeuristics valueH = new ValueOrderingHeuristics("LCV");
        //Sequential
        //LCV

        Board board = new Board(file);
        board.loadBoard();
        board.printBoard();


        /*BacktrackClass backtrackClass = new BacktrackClass(vah, valueH);
        backtrackClass.backTrack(board);*/


        ForwardCheckClass forwardCheckClass = new ForwardCheckClass(vah, valueH);
        forwardCheckClass.forwardCheck(board);



        /*ForwardCheckingClass forwardCheckingClass = new ForwardCheckingClass(vah, valueH);
        forwardCheckingClass.ForwardCheck(board);*/
    }
}