package leonardo.java;

import leonardo.java.controller.TicTacToeController;
import leonardo.java.model.TicTacToeModel;
import leonardo.java.view.TicTacToeView;
import javax.swing.UIManager;
import com.formdev.flatlaf.FlatDarculaLaf;

public class TicTacToeApp {
    
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        TicTacToeModel model = new TicTacToeModel();
        TicTacToeView view = new TicTacToeView();
        TicTacToeController controller = new TicTacToeController(model, view);
    }
}
