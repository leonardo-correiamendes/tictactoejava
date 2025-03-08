package leonardo.java.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeView extends JFrame {

    // Attributs
    private JFrame jeu;
    private JButton btnJouer;
    private JButton[][] btnGrille = new JButton[3][3];
    private JLabel statusLabel;
    private JButton btnReset = new JButton("Rejouer");
    private Timer effetVictoire;
    private int[][] winningCells;
    
    // Constructeur
    public TicTacToeView() {

        // Configuration de la JFrame
        jeu = new JFrame();
        jeu.setTitle("Tic Tac Toe");
        jeu.setSize(400, 400);
        jeu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jeu.setLocationRelativeTo(null);
        jeu.setResizable(false);

        // Affichage de l'accueil
        accueilFrame();
        
        // Affichage de la JFrame
        jeu.setVisible(true);
    }

    // Méthodes

    // Affichage de l'accueil
    public void accueilFrame() {
        // Configuration du panel
        JPanel accueilPanel = new JPanel();
        accueilPanel.setLayout(new BorderLayout());

        // Configuration du Label
        JLabel titleLabel = new JLabel("Tic Tac Toe", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        accueilPanel.add(titleLabel, BorderLayout.NORTH);

        // Image 
        JLabel iconLabel = new JLabel(new ImageIcon("tictactoe\\src\\img\\tictactoeIcon.png"), SwingConstants.CENTER);
        accueilPanel.add(iconLabel, BorderLayout.CENTER);

        // Bouton Jouer
        btnJouer = new JButton("Jouer");
        btnJouer.setFont(new Font("Arial", Font.BOLD, 20));
        btnJouer.setForeground(Color.WHITE);
        btnJouer.setFocusPainted(false);
        btnJouer.setBorderPainted(false);

        // Panel pour centrer le bouton
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnJouer);

        // Ajout du bouton au panel
        accueilPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Ajout du panel à la JFrame
        jeu.add(accueilPanel);
    }

    // Affichage du jeu
    public void jeuFrame() {

        // Configuration du panel du jeu
        JPanel jeuPanel = new JPanel();
        jeuPanel.setLayout(new BorderLayout());

        // Configuration du panel de la grille
        JPanel grillePanel = new JPanel(new GridLayout(3, 3));

        // Configuration des boutons
        for (int i = 0; i < btnGrille.length; i++) {
            for (int j = 0; j < btnGrille[i].length; j++) {
                btnGrille[i][j] = new JButton(" ");
                btnGrille[i][j].setFont(new Font("Arial", Font.BOLD, 32));
                btnGrille[i][j].setForeground(Color.WHITE);
                btnGrille[i][j].setFocusPainted(false);
                btnGrille[i][j].setBorderPainted(false);
                grillePanel.add(btnGrille[i][j]);
                System.out.println("Bouton [" + i + "][" + j + "] créé");
            }
        }

        statusLabel = new JLabel("Joueur X commence", SwingConstants.CENTER);

        // Ajout du panel à la JFrame
        jeuPanel.add(grillePanel, BorderLayout.CENTER);
        jeuPanel.add(statusLabel, BorderLayout.NORTH);
        jeuPanel.add(btnReset, BorderLayout.SOUTH);

        // Ajout du panel à la JFrame
        jeu.add(jeuPanel);
    }

    public void resetGrille() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                btnGrille[i][j].setText(" ");
            }
        }
    }

    // Getters
    public JFrame getJeu() {
        return jeu;
    }

    public JButton getBtnCellule(int ligne, int colonne) {
        if (btnGrille[ligne][colonne] == null) {
            System.out.println("ERREUR : Accès à un bouton non initialisé [" + ligne + "][" + colonne + "]");
        }
        return btnGrille[ligne][colonne];
    }    

    // Setters
    public void setStatusLabel(String text) {
        statusLabel.setText(text);
    }

    // Actions

    // Démarre le jeu
    public void addBtnJouerActionListener(ActionListener listener) {
        btnJouer.addActionListener(listener);
    }

    // Jouer un coup
    public void addBtnCelluleActionListener(ActionListener listener, int ligne, int colonne) {
        if (btnGrille[ligne][colonne] == null) {
            System.out.println("ERREUR : Le bouton [" + ligne + "][" + colonne + "] est NULL !");
        } else {
            System.out.println("Ajout d'un écouteur sur le bouton [" + ligne + "][" + colonne + "]");
            btnGrille[ligne][colonne].addActionListener(listener);
        }
    }

    // Rejouer
    public void addBtnResetActionListener(ActionListener listener) {
        btnReset.addActionListener(listener);
    }

    // Animation des cellules
    public void addBtnCelluleAnim(int ligne, int colonne, String symbole) {
        JButton btn = btnGrille[ligne][colonne];
        btn.setText(symbole);
        btn.setForeground(new Color(255, 255, 255, 0)); // Transparence au début
    
        Timer timer = new Timer(30, new ActionListener() {
            private int alpha = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                alpha += 20;
                if (alpha >= 255) {
                    alpha = 255;
                    ((Timer) e.getSource()).stop();
                }
                btn.setForeground(new Color(255, 255, 255, alpha));
            }
        });
        timer.start();
    }

    // Animation des cellules lors de la victoire
    public void highlightWinningButtons(int[][] winningCells) {
        stopHighlightAnimation();

        this.winningCells = winningCells;

        effetVictoire = new Timer(300, new ActionListener() {
            private boolean toggle = false;
    
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int[] cell : winningCells) {
                    int i = cell[0], j = cell[1];
                    btnGrille[i][j].setBackground(toggle ? Color.YELLOW : new Color(70, 70, 70));
                }
                toggle = !toggle;
            }
        });
        effetVictoire.start();
    }

    // Desactive l'animation des cellules lors de la victoire
    public void stopHighlightAnimation() {
        if (effetVictoire != null) {
            effetVictoire.stop();
            effetVictoire = null;
        }

        if (winningCells != null) {
            for (int[] cell : winningCells) {
                int i = cell[0], j = cell[1];
                btnGrille[i][j].setBackground(new Color(70, 70, 70));
            }
        }
        winningCells = null;
    }
}
