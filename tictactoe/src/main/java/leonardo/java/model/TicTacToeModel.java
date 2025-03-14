package leonardo.java.model;

import java.util.Random;

public class TicTacToeModel {
    
    // Attributs
    private char[][] grille;
    private char joueur;
    private boolean gameOver;
    private boolean victoire;
    private int nbCoups;
    private boolean ordiEasy = false;
    private boolean ordiHard = false;

    // Constructeur
    public TicTacToeModel() {
        this.resetGrille();
    }

    // Méthodes

    // Réinitialise la grille et les attributs
    public void resetGrille() {
        this.initialiserGrille();
        this.joueur = 'X';
        this.gameOver = false;
        this.victoire = false;
        this.nbCoups = 0;
    }

    // Initialise la grille avec des espaces
    public void initialiserGrille() {
        this.grille = new char[3][3];
        for (int i = 0; i < this.grille.length; i++) {
            for (int j = 0; j < this.grille[i].length; j++) {
                this.grille[i][j] = ' ';
            }
        }
    }

    // Jouer un coup
    public void jouerCoup(int ligne, int colonne) {
        if (this.grille[ligne][colonne] == ' ' && !this.gameOver) { 
            this.grille[ligne][colonne] = this.joueur; 
            this.nbCoups++;
            if (this.nbCoups > 4 && partieGagnee(ligne, colonne)) { // Vérification de la victoire à partir du 5ème coup seulement
                this.gameOver = true;
                this.victoire = true;
            } else if (nbCoups == 9) { 
                this.gameOver = true;
            } else { // Changement de joueur
                this.joueur = (this.joueur == 'X') ? 'O' : 'X';
            }
        }
    }

    // Vérifie si la partie est gagnée
    public boolean partieGagnee(int ligne, int colonne) {
        return (this.grille[ligne][0] == this.joueur && this.grille[ligne][1] == this.joueur && this.grille[ligne][2] == this.joueur) // Ligne
                || (this.grille[0][colonne] == this.joueur && this.grille[1][colonne] == this.joueur && this.grille[2][colonne] == this.joueur) // Colonne
                || (ligne == colonne && this.grille[0][0] == this.joueur && this.grille[1][1] == this.joueur && this.grille[2][2] == this.joueur) // Diagonale 
                || (ligne + colonne == 2 && this.grille[0][2] == this.joueur && this.grille[1][1] == this.joueur && this.grille[2][0] == this.joueur); // Diagonale
    }

    public int[][] winningCells() {
        if (!this.victoire) return null; // Si pas de victoire, retourne null
    
        // Vérification des lignes
        for (int i = 0; i < 3; i++) {
            if (this.grille[i][0] == this.joueur && this.grille[i][1] == this.joueur && this.grille[i][2] == this.joueur) {
                return new int[][] { {i, 0}, {i, 1}, {i, 2} };
            }
        }
    
        // Vérification des colonnes
        for (int j = 0; j < 3; j++) {
            if (this.grille[0][j] == this.joueur && this.grille[1][j] == this.joueur && this.grille[2][j] == this.joueur) {
                return new int[][] { {0, j}, {1, j}, {2, j} };
            }
        }
    
        // Vérification diagonale principale
        if (this.grille[0][0] == this.joueur && this.grille[1][1] == this.joueur && this.grille[2][2] == this.joueur) {
            return new int[][] { {0, 0}, {1, 1}, {2, 2} };
        }
    
        // Vérification diagonale secondaire
        if (this.grille[0][2] == this.joueur && this.grille[1][1] == this.joueur && this.grille[2][0] == this.joueur) {
            return new int[][] { {0, 2}, {1, 1}, {2, 0} };
        }
    
        return null; // Si aucune victoire détectée
    }

    // Permet à l'ordi en mode facile de jouer
    public int[] jouerOrdiEasy() {
        if(!this.ordiEasy || this.gameOver) return null;

        Random random = new Random();

        int ligne, colonne;

        // Trouver un coup
        do {
            ligne = random.nextInt(3);
            colonne = random.nextInt(3);
        } while (this.grille[ligne][colonne] != ' ');

        this.jouerCoup(ligne, colonne);

        return new int[]{ligne, colonne};
    }

    // Permet à l'ordi en mode difficile de jouer (IA)
    public int[] jouerOrdiHard() {
        if(!this.ordiHard || this.gameOver) return null;

        int meilleurVal = Integer.MIN_VALUE;
        int meilleurCoup[] = new int[]{-1, -1};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.grille[i][j] == ' ') {
                    this.grille[i][j] = 'O';
                    int valCoup = this.minimax(this.grille, 0, false);
                    this.grille[i][j] = ' ';

                    if (valCoup > meilleurVal) {
                        meilleurCoup[0] = i;
                        meilleurCoup[1] = j;
                        meilleurVal = valCoup;
                    }
                }
            }
        }

        if (meilleurCoup[0] == -1) {
            // Aucun coup trouvé, le plateau est plein ou erreur
            return null;
        }

        this.jouerCoup(meilleurCoup[0], meilleurCoup[1]);

        return meilleurCoup;
    }

    // Algorithme Minimax
    private int minimax(char[][] board, int depth, boolean isMaximizing) {
        int score = this.evaluate(board);
        // Si l'état est terminal, on retourne le score
        if (score == 10 || score == -10) return score;
        if (this.isBoardFull(board)) return 0;
        
        // Si c'est le tour de l'ordinateur
        if (isMaximizing) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = 'O';
                        best = Math.max(best, this.minimax(board, depth + 1, false));
                        board[i][j] = ' ';
                    }
                }
            }

            return best;
        } else { // Si c'est le tour du joueur
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = 'X';
                        best = Math.min(best, this.minimax(board, depth + 1, true));
                        board[i][j] = ' ';
                    }
                }
            }

            return best;
        }
    }

    // Vérifie si le plateau est plein
    private boolean isBoardFull(char[][] board) {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] != ' ') {
                    count++;
                }
            }
        }
        return (count == 9);
    }    

    // Évaluation de l'état du plateau pour l'IA
    public int evaluate(char[][] board) {
        // Vérification des lignes
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != ' ' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return (board[i][0] == 'O') ? 10 : -10;
            }
        }
        // Vérification des colonnes
        for (int j = 0; j < 3; j++) {
            if (board[0][j] != ' ' && board[0][j] == board[1][j] && board[1][j] == board[2][j]) {
                return (board[0][j] == 'O') ? 10 : -10;
            }
        }
        // Vérification des diagonales
        if (board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2])
            return (board[0][0] == 'O') ? 10 : -10;
        if (board[0][2] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0])
            return (board[0][2] == 'O') ? 10 : -10;
        
        return 0;
    }    
    
    // Getters
    public char getJoueur() {
        return this.joueur;
    }

    public char getCellule(int ligne, int colonne) {
        return this.grille[ligne][colonne];
    }

    public boolean isOrdiEasy() {
        return this.ordiEasy;
    }

    public boolean isOrdiHard() {
        return this.ordiHard;
    }    

    public boolean isGameOver() {
        return this.gameOver;
    }

    // Setters
    public void setOrdiEasy(boolean choix) {
        this.ordiEasy = choix;
    }

    public void setOrdiHard(boolean choix) {
        this.ordiHard = choix;
    }
}