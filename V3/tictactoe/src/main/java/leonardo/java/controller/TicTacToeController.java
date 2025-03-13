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
    @SuppressWarnings("unused")
    public TicTacToeController(TicTacToeModel model, TicTacToeView view) {
        this.model = model;
        this.view = view;

        view.addBtnJouerActionListener(e -> retourSelection());

        view.addBtnRejouerActionListener(e -> resetJeu());
        view.addBtnRetourActionListener(e -> retourSelection());
    }

    // Méthodes

    // Retourner à la sélection du mode de jeu
    public void retourSelection() {
        view.getJeu().getContentPane().removeAll();
        view.choixModeJeu();
        view.getJeu().revalidate();
        view.getJeu().repaint();

        ajouterListenersChoixMode();
    }

    // Ajouter les écouteurs pour les boutons de sélection du mode de jeu
    @SuppressWarnings("unused")
    private void ajouterListenersChoixMode() {

        view.addBtnJouerVsOrdiEasyActionListener(e -> {
            lancer1vsOrdiEasy();
        });

        view.addBtnJouerVsOrdiHardActionListener(e -> {
            lancer1vsOrdiHard();
        });

        view.addBtnJouerVsJoueurActionListener(e -> {
            lancer1vs1();
        });

        view.addBtnRetourAccueilActionListener(e -> {
            view.getJeu().getContentPane().removeAll();
            view.accueilFrame();
            view.getJeu().revalidate();
            view.getJeu().repaint();

            view.addBtnJouerActionListener(ev -> retourSelection());
        });
    }

    // Lancer le jeu en mode 1vsOrdi (facile)
    private void lancer1vsOrdiEasy() {
        view.setChoixModeJeuColor(Color.ORANGE);
        view.setChoixModeJeu("Mode contre l'ordinateur (facile) sélectionné !");
        model.setOrdiEasy(true);
        model.setOrdiHard(false);
        Timer timer = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            view.getJeu().getContentPane().removeAll();
            view.jeuFrame();
            view.getJeu().revalidate();
            view.getJeu().repaint();
            resetJeu();
            ajouterListeners();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    // Lancer le jeu en mode 1vsOrdi (difficile)    
    private void lancer1vsOrdiHard() {
        view.setChoixModeJeuColor(Color.RED);
        view.setChoixModeJeu("Mode contre l'ordinateur (difficile) sélectionné !");
        model.setOrdiHard(true);
        model.setOrdiEasy(false);
        Timer timer = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            view.getJeu().getContentPane().removeAll();
            view.jeuFrame();
            view.getJeu().revalidate();
            view.getJeu().repaint();
            resetJeu();
            ajouterListeners();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    // Lancer le jeu en mode 1vs1
    private void lancer1vs1() {
        view.setChoixModeJeuColor(Color.GREEN);
        view.setChoixModeJeu("Mode 1vs1 sélectionné !");
        model.setOrdiEasy(false);
        model.setOrdiHard(false);
        Timer timer = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            view.getJeu().getContentPane().removeAll();
            view.jeuFrame();
            view.getJeu().revalidate();
            view.getJeu().repaint();
            resetJeu();
            ajouterListeners();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    // Réinitialiser le jeu
    public void resetJeu() {
        view.stopHighlightAnimation();
        model.resetGrille();
        view.resetGrille();
    }

    // Ajouter les écouteurs pour les boutons de la grille de jeu
    @SuppressWarnings("unused")
    private void ajouterListeners() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final int ligne = i, colonne = j;
                view.addBtnCelluleActionListener(e -> {
                    gererCoup(ligne, colonne);
                }, ligne, colonne);
            }    
        }
    }
    

    // Gérer le coup joué par un joueur ou l'ordinateur
    public void gererCoup(int ligne, int colonne) {
        if (!model.isGameOver() && model.getCellule(ligne, colonne) == ' ') {
            char joueurActuel = model.getJoueur();
            model.jouerCoup(ligne, colonne);

            view.addBtnCelluleAnim(ligne, colonne, String.valueOf(joueurActuel));

            if (model.isGameOver()) {
                // Gérer la fin de partie (victoire ou match nul)
                if (model.partieGagnee(ligne, colonne)) {
                    view.setStatutLabelColor(Color.GREEN);
                    view.setStatutLabel("Le joueur " + model.getJoueur() + " a gagné !");
                    view.highlightWinningButtons(model.winningCells());
                } else {
                    view.setStatutLabelColor(Color.ORANGE);
                    view.setStatutLabel("Match nul !");
                }
            } else {
                // Si c'est le tour de l'ordinateur, on déclenche le Timer pour le coup
                if (model.getJoueur() == 'O' && (model.isOrdiEasy() || model.isOrdiHard())) {
                    view.setInputBlocked(true);
                    if (model.isOrdiEasy()) {
                        new Timer(1000, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int[] ordiEasyCoup = model.jouerOrdiEasy();
                                if (ordiEasyCoup != null) {
                                    view.addBtnCelluleAnim(ordiEasyCoup[0], ordiEasyCoup[1], "O");
                                    if (model.isGameOver()) {
                                        view.setStatutLabelColor(Color.RED);
                                        view.setStatutLabel("L'ordinateur a gagné");
                                        view.highlightWinningButtons(model.winningCells());
                                    } else {
                                        view.setStatutLabel("Au tour de X");
                                    }
                                }
                                view.setInputBlocked(false);
                                ((Timer) e.getSource()).stop();
                            }
                        }).start();
                    } else if (model.isOrdiHard()) {
                        new Timer(1000, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int[] ordiHardCoup = model.jouerOrdiHard();
                                if (ordiHardCoup != null) {
                                    view.addBtnCelluleAnim(ordiHardCoup[0], ordiHardCoup[1], "O");
                                    if (model.isGameOver()) {
                                        view.setStatutLabelColor(Color.RED);
                                        view.setStatutLabel("L'ordinateur a gagné");
                                        view.highlightWinningButtons(model.winningCells());
                                    } else {
                                        view.setStatutLabel("Au tour de X");
                                    }
                                }
                                view.setInputBlocked(false);
                                ((Timer) e.getSource()).stop();
                            }
                        }).start();
                    }
                } else {
                    // Mode 1vs1 : mise à jour du statut pour indiquer le tour du joueur
                    view.setStatutLabel("Au tour de " + model.getJoueur());
                }
            }
        }
    }
}