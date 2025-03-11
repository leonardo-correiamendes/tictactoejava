package leonardo.java.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

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
            view.choixModeJeu();
            view.getJeu().revalidate();
            view.getJeu().repaint();

            ajouterListenersChoixMode();
        });

        view.addBtnResetActionListener(e -> resetJeu());
    }

    private void ajouterListenersChoixMode() {
        view.addBtnJouerVsJoueurActionListener(e -> {
            lancer1vs1();
        });

        view.addBtnJouerVsOrdiEasyActionListener(e -> {
            lancer1vsOrdiEasy();
        });

        // Autre choix
    }
    
    private void lancer1vs1() {
        view.setChoixModeJeu("Mode 1vs1 sélectionné !");
        Timer timer = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            view.getJeu().getContentPane().removeAll();
            view.jeuFrame();
            view.getJeu().revalidate();
            view.getJeu().repaint();
            ajouterListeners();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void lancer1vsOrdiEasy() {
        view.setChoixModeJeu("Mode contre l'ordinateur (facile) sélectionné !");
        model.setOrdiEasy(true);
        Timer timer = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            view.getJeu().getContentPane().removeAll();
            view.jeuFrame();
            view.getJeu().revalidate();
            view.getJeu().repaint();
            ajouterListeners();
            }
        });
        timer.setRepeats(false);
        timer.start();
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
                if (model.getJoueur() == 'O' && model.isOrdiEasy()) {
                    System.out.println("L'ordinateur réfléchit...");
                    int[] ordiEasyCoup = model.jouerOrdiEasy();
                    if (ordiEasyCoup != null) {
                        view.addBtnCelluleAnim(ordiEasyCoup[0], ordiEasyCoup[1], "O");
                        if (model.isGameOver()) {
                            view.setStatusLabel("L'ordinateur a gagné");
                            view.highlightWinningButtons(model.getWinningCells());
                        } else {
                            view.setStatusLabel("Au tour de X");
                        }
                    }
                } else {
                    view.setStatusLabel("Au tour de " + model.getJoueur());
                }
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
