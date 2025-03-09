package leonardo.java.controller;

import java.awt.Color;

import leonardo.java.model.TicTacToeModel;
import leonardo.java.view.TicTacToeView;

public class TicTacToeController {
    
    // Attributs
    private TicTacToeModel model;
    private TicTacToeView view;

    // Constructeur
    public TicTacToeController(TicTacToeModel model, TicTacToeView view) {
        this.model = model;
        this.view = view;

        view.addBtnJouerActionListener(e -> {
            view.getJeu().getContentPane().removeAll();
            view.jeuFrame();
            view.getJeu().revalidate();
            view.getJeu().repaint();

            ajouterListeners();
        });

        view.addBtnResetActionListener(e -> resetJeu());
    }

    // Méthodes

    private void ajouterListeners() {
        System.out.println("Ajout des écouteurs...");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final int ligne = i, colonne = j;
                view.addBtnCelluleActionListener(e -> {
                    System.out.println("Clic détecté sur [" + ligne + "," + colonne + "]");
                    gererCoup(ligne, colonne);
                }, ligne, colonne);
            }    
        }
    }
    

    public void gererCoup(int ligne, int colonne) {
        System.out.println("gererCoup() appelé pour [" + ligne + "][" + colonne + "]");
        if (!model.isGameOver() && model.getCellule(ligne, colonne) == ' ') {
            char joueurActuel = model.getJoueur();
            model.jouerCoup(ligne, colonne);

            view.addBtnCelluleAnim(ligne, colonne, String.valueOf(joueurActuel));

            if (model.isGameOver()) {
                if (model.partieGagnee(ligne, colonne)) {
                    view.setStatusLabel("Le joueur " + model.getJoueur() + " a gagné !");
                    view.highlightWinningButtons(model.getWinningCells());
                } else {
                    view.setStatusLabel("Match nul !");
                }
            } else {
                view.setStatusLabel("Au tour de " + model.getJoueur());
            }
        }
    }

    public void resetJeu() {
        view.stopHighlightAnimation();

        model.resetGrille();
        view.resetGrille();
        view.setStatusLabel("Joueur X commence");

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                view.getBtnCellule(i, j).setBackground(new Color(70, 70, 70));;
                view.getBtnCellule(i, j).setText(" ");
            }
        }
    }
}
