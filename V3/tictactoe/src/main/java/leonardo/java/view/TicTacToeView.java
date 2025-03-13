package leonardo.java.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeView extends JFrame {

    // Attributs
    private JFrame jeu;
    private JLabel statutLabel;
    private JLabel choixModeJeuLabel;
    private JButton btnJouer;
    private JButton btnJouerVsJoueur;
    private JButton btnJouerVsOrdiEasy;
    private JButton btnJouerVsOrdiHard;
    private JButton[][] btnGrille;
    private JButton btnRejouer = new JButton("Rejouer");
    private JButton btnRetour = new JButton("Retour à la sélection du mode de jeu");
    private JButton btnRetourAccueil;
    private Timer effetVictoire;
    private int[][] winningCells;
    
    // Constructeur
    public TicTacToeView() {
        // Configuration de la JFrame
        this.configJFrame();
        // Affichage de l'accueil
        this.accueilFrame();
        // Affichage de la JFrame
        this.jeu.setVisible(true);
    }

    // Méthodes

    // Configuration de la JFrame
    public void configJFrame() {
        // Configuration de la JFrame
        this.jeu = new JFrame();
        this.jeu.setTitle("Tic Tac Toe");
        this.jeu.setSize(400, 400);
        this.jeu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jeu.setLocationRelativeTo(null);
        this.jeu.setResizable(false);

        this.setGlassPane();
    }

    // Configuration du Glass Pane (permet de ne pas pouvoir jouer à la place de l'ordi pendant qu'il réfléchit)
    public void setGlassPane() {
        // Glass Pane
        JPanel glassPane = new JPanel();
        glassPane.setOpaque(false);
        glassPane.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                e.consume();
            }
        });

        this.jeu.setGlassPane(glassPane);
    }

    // Affichage de l'accueil
    public void accueilFrame() {
        // Configuration du panel
        JPanel accueilPanel = new JPanel();
        accueilPanel.setLayout(new BorderLayout());

        // Configuration du Label
        JLabel accueilLabel = new JLabel("Tic Tac Toe", SwingConstants.CENTER);
        accueilLabel.setFont(new Font("Arial", Font.BOLD, 34));
        accueilLabel.setForeground(Color.WHITE);
        accueilPanel.add(accueilLabel, BorderLayout.NORTH);

        // Image 
        ImageIcon imgAccueil = new ImageIcon(getClass().getResource("/img/tictactoeIcon.png"));
        Image imgAccueilResized = imgAccueil.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon iconAccueil = new ImageIcon(imgAccueilResized);
        JLabel iconAccueilLabel = new JLabel(iconAccueil, SwingConstants.CENTER);
        accueilPanel.add(iconAccueilLabel, BorderLayout.CENTER);

        // Bouton Jouer
        this.btnJouer = new JButton("Jouer");
        this.btnJouer.setFont(new Font("Arial", Font.BOLD, 25));
        this.btnJouer.setForeground(Color.WHITE);
        this.btnJouer.setFocusPainted(false);
        this.btnJouer.setBorderPainted(false);

        // Panel pour centrer le bouton
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(this.btnJouer);

        // Ajout du bouton au panel
        accueilPanel.add(buttonsPanel, BorderLayout.SOUTH);

        // Ajout du panel à la JFrame
        this.jeu.add(accueilPanel);
    }

    // Affichage du choix du mode de jeu
    public void choixModeJeu() {
        // Configuration du panel
        JPanel choixPanel = new JPanel();
        choixPanel.setLayout(new BorderLayout());

        // Configuration du Label
        choixModeJeuLabel = new JLabel("Choix du mode de jeu", SwingConstants.CENTER);
        choixModeJeuLabel.setFont(new Font("Arial", Font.BOLD, 32));
        choixModeJeuLabel.setForeground(Color.WHITE);
        choixModeJeuLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        choixPanel.add(choixModeJeuLabel, BorderLayout.NORTH);

        // Bouton Jouer contre l'ordinateur (facile)
        this.btnJouerVsOrdiEasy = new JButton("Jouer contre l'ordinateur (facile)");
        configBtnChoix(this.btnJouerVsOrdiEasy);

        // Bouton Jouer contre l'ordinateur (difficile)
        btnJouerVsOrdiHard = new JButton("Jouer contre l'ordinateur (difficile)");
        configBtnChoix(this.btnJouerVsOrdiHard);

        // Bouton Jouer contre un autre joueur
        btnJouerVsJoueur = new JButton("Jouer contre un autre joueur");
        configBtnChoix(this.btnJouerVsJoueur);

        // Bouton Retour à l'accueil
        btnRetourAccueil = new JButton("Retour à l'accueil");
        configBtnChoix(this.btnRetourAccueil);

        // Panel pour centrer les boutons
        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0); // Marge entre les boutons

        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonsPanel.add(this.btnJouerVsOrdiEasy, gbc);

        gbc.gridy = 1;
        buttonsPanel.add(this.btnJouerVsOrdiHard, gbc);

        gbc.gridy = 2;
        buttonsPanel.add(this.btnJouerVsJoueur, gbc);

        gbc.gridy = 3;
        buttonsPanel.add(this.btnRetourAccueil, gbc);

        // Ajout du panel au panel principal
        choixPanel.add(buttonsPanel, BorderLayout.CENTER);

        this.jeu.add(choixPanel);
    }

    // Configuration des boutons de choix
    public void configBtnChoix(JButton btn) {
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
    }

    // Affichage du jeu
    public void jeuFrame() {

        // Configuration du panel du jeu
        JPanel jeuPanel = new JPanel();
        jeuPanel.setLayout(new BorderLayout());

        // Configuration du panel de la grille
        JPanel grillePanel = new JPanel(new GridLayout(3, 3));

        // Configuration des boutons
        this.btnGrille = new JButton[3][3];
        for (int i = 0; i < this.btnGrille.length; i++) {
            for (int j = 0; j < this.btnGrille[i].length; j++) {
                this.btnGrille[i][j] = new JButton(" ");
                this.btnGrille[i][j].setFont(new Font("Arial", Font.BOLD, 32));
                this.btnGrille[i][j].setForeground(Color.WHITE);
                this.btnGrille[i][j].setFocusPainted(false);
                this.btnGrille[i][j].setBorderPainted(false);
                grillePanel.add(btnGrille[i][j]);
            }
        }

        this.statutLabel = new JLabel("Joueur X commence", SwingConstants.CENTER);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.add(this.btnRejouer);
        buttonsPanel.add(this.btnRetour);

        // Ajout du panel à la JFrame
        jeuPanel.add(grillePanel, BorderLayout.CENTER);
        jeuPanel.add(this.statutLabel, BorderLayout.NORTH);
        jeuPanel.add(buttonsPanel, BorderLayout.SOUTH);

        // Ajout du panel à la JFrame
        this.jeu.add(jeuPanel);
    }

    // Réinitialisation de la grille
    public void resetGrille() {
        this.statutLabel.setForeground(Color.WHITE);
        this.setStatutLabel("Joueur X commence");
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                this.btnGrille[i][j].setText(" ");
                this.btnGrille[i][j].setBackground(UIManager.getColor("Button.background"));
            }
        }
    }

    // Getters

    // Retourne la JFrame
    public JFrame getJeu() {
        return this.jeu;
    }

    // Retourne le bouton de la grille
    public JButton getBtnCellule(int ligne, int colonne) {
        return this.btnGrille[ligne][colonne];
    }    

    // Setters

    // Change le statut
    public void setStatutLabel(String text) {
        this.statutLabel.setText(text);
    }

    // Change la couleur du statut
    public void setStatutLabelColor(Color color) {
        this.statutLabel.setForeground(color);
    }

    // Change le choix du mode de jeu
    public void setChoixModeJeu(String text) {
        this.choixModeJeuLabel.setText("<html><div style='text-align: center; width:300px;'>" + text + "</div></html>");
    }

    public void setChoixModeJeuColor(Color color) {
        this.choixModeJeuLabel.setForeground(color);
    }

    // Bloque ou débloque l'input
    public void setInputBlocked(boolean blocked) {
        this.jeu.getGlassPane().setVisible(blocked);
    }
    

    // Listeners

    // Fais apparaître le choix du mode de jeu
    public void addBtnJouerActionListener(ActionListener listener) {
        this.btnJouer.addActionListener(listener);
    }

    // Jouer contre l'ordinateur (facile)
    public void addBtnJouerVsOrdiEasyActionListener(ActionListener listener) {
        this.btnJouerVsOrdiEasy.addActionListener(listener);
    }

    // Jouer contre l'ordinateur (difficile)
    public void addBtnJouerVsOrdiHardActionListener(ActionListener listener) {
        this.btnJouerVsOrdiHard.addActionListener(listener);
    }

    // Jouer contre joueur
    public void addBtnJouerVsJoueurActionListener(ActionListener listener) {
        this.btnJouerVsJoueur.addActionListener(listener);
    }

    // Retour à l'accueil
    public void addBtnRetourAccueilActionListener(ActionListener listener) {
        this.btnRetourAccueil.addActionListener(listener);
    }

    // Jouer un coup
    public void addBtnCelluleActionListener(ActionListener listener, int ligne, int colonne) {
        this.btnGrille[ligne][colonne].addActionListener(listener);
    }

    // Rejouer
    public void addBtnRejouerActionListener(ActionListener listener) {
        this.btnRejouer.addActionListener(listener);
    }

    // Retour à la sélection du mode de jeu
    public void addBtnRetourActionListener(ActionListener listener) {
        this.btnRetour.addActionListener(listener);
    }

    // Animation des cellules lors d'un coup
    public void addBtnCelluleAnim(int ligne, int colonne, String symbole) {
        JButton btn = this.btnGrille[ligne][colonne];
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

        this.effetVictoire = new Timer(300, new ActionListener() {
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
        this.effetVictoire.start();
    }

    // Desactive l'animation des cellules gagantes
    public void stopHighlightAnimation() {
        if (this.effetVictoire != null) {
            this.effetVictoire.stop();
            this.effetVictoire = null;
        }

        if (this.winningCells != null) {
            for (int[] cell : this.winningCells) {
                int i = cell[0], j = cell[1];
                this.btnGrille[i][j].setBackground(new Color(70, 70, 70));
            }
        }
        this.winningCells = null;
    }
}