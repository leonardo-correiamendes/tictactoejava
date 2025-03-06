package leonardo.java;

import leonardo.java.controller.TicTacToeController;
import leonardo.java.model.TicTacToeModel;
import leonardo.java.view.TicTacToeView;

public class TicTacToeApp {
    
    public static void main(String[] args) {
        TicTacToeModel model = new TicTacToeModel();
        TicTacToeView view = new TicTacToeView();
        TicTacToeController controller = new TicTacToeController(model, view);
    }
}
