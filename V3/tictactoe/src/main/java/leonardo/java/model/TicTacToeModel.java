package leonardo.java.model;

import java.util.Random;

public class TicTacToeModel {
    
    // Attributs
    private char[][] grille;
    private char joueur;
    private boolean gameOver;
    private boolean victoire = false;
    private int nbCoups;
    private boolean ordiEasy = false;
    private boolean ordiHard = false;

    // Constructeur
    public TicTacToeModel() {
        grille = new char[3][3];
        resetGrille();
    }

    // Méthodes
    // Initialise la grille avec des espaces
    public void initialiserGrille() {
        for (int i = 0; i < grille.length; i++) {
            for (int j = 0; j < grille[i].length; j++) {
                grille[i][j] = ' ';
            }
        }
    }

    // Réinitialise la grille et les attributs
    public void resetGrille() {
        initialiserGrille();
        joueur = 'X';
        gameOver = false;
        victoire = false;
        nbCoups = 0;
    }

    // Jouer un coup
    public void jouerCoup(int ligne, int colonne) {
        if (grille[ligne][colonne] == ' ' && !gameOver) { // Vérification de la case vide et de la fin de la partie
            grille[ligne][colonne] = joueur; // Jouer le coup
            nbCoups++;
            if (nbCoups > 4 && partieGagnee(ligne, colonne)) { // Vérification de la victoire à partir du 4ème coup
                gameOver = true;
                victoire = true;
            } else if (nbCoups == 9) { // Vérification de l'égalité
                gameOver = true;
            } else { // Changement de joueur
                joueur = (joueur == 'X') ? 'O' : 'X';
            }
        }

        System.out.println("[" + grille[0][0] + "][" + grille[0][1] + "][" + grille[0][2] + "]");
        System.out.println("[" + grille[1][0] + "][" + grille[1][1] + "][" + grille[1][2] + "]");
        System.out.println("[" + grille[2][0] + "][" + grille[2][1] + "][" + grille[2][2] + "]");
        System.out.println("etat victoire : " + victoire);

    }

    // Vérifie si la partie est gagnée
    public boolean partieGagnee(int ligne, int colonne) {
        return (grille[ligne][0] == joueur && grille[ligne][1] == joueur && grille[ligne][2] == joueur)// Ligne
                || (grille[0][colonne] == joueur && grille[1][colonne] == joueur && grille[2][colonne] == joueur) // Colonne
                || (ligne == colonne && grille[0][0] == joueur && grille[1][1] == joueur && grille[2][2] == joueur) // Diagonale 
                || (ligne + colonne == 2 && grille[0][2] == joueur && grille[1][1] == joueur && grille[2][0] == joueur); // Diagonale
    }

    public int[][] winningCells() {
        if (!victoire) return null; // Si pas de victoire, retourne null
    
        // Vérification des lignes
        for (int i = 0; i < 3; i++) {
            if (grille[i][0] == joueur && grille[i][1] == joueur && grille[i][2] == joueur) {
                return new int[][] { {i, 0}, {i, 1}, {i, 2} };
            }
        }
    
        // Vérification des colonnes
        for (int j = 0; j < 3; j++) {
            if (grille[0][j] == joueur && grille[1][j] == joueur && grille[2][j] == joueur) {
                return new int[][] { {0, j}, {1, j}, {2, j} };
            }
        }
    
        // Vérification diagonale principale
        if (grille[0][0] == joueur && grille[1][1] == joueur && grille[2][2] == joueur) {
            return new int[][] { {0, 0}, {1, 1}, {2, 2} };
        }
    
        // Vérification diagonale secondaire
        if (grille[0][2] == joueur && grille[1][1] == joueur && grille[2][0] == joueur) {
            return new int[][] { {0, 2}, {1, 1}, {2, 0} };
        }
    
        return null; // Si aucune victoire détectée
    }

    public int[] jouerOrdiEasy() {
        if(!ordiEasy || gameOver) return null;

        Random random = new Random();

        int ligne, colonne;

        // Trouver un coup
        do {
            ligne = random.nextInt(3);
            colonne = random.nextInt(3);
        } while (grille[ligne][colonne] != ' ');

        jouerCoup(ligne, colonne);

        return new int[]{ligne, colonne};
    }

    public int[] jouerOrdiHard() {
        if(!ordiHard || gameOver) return null;

        int bestVal = Integer.MIN_VALUE;
        int bestMove[] = new int[]{-1, -1};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grille[i][j] == ' ') {
                    grille[i][j] = 'O';
                    int moveVal = minimax(grille, 0, false);
                    grille[i][j] = ' ';

                    if (moveVal > bestVal) {
                        bestMove[0] = i;
                        bestMove[1] = j;
                        bestVal = moveVal;
                    }
                }
            }
        }

        if (bestMove[0] == -1) {
            // Aucun coup trouvé, le plateau est plein ou erreur
            return null;
        }

        System.out.println("i : " + bestMove[0] + " j : " + bestMove[1]);
        jouerCoup(bestMove[0], bestMove[1]);
        return bestMove;
    }

    private int minimax(char[][] board, int depth, boolean isMaximizing) {
        int score = evaluate(board);
        // Si l'état est terminal, on retourne le score
        if (score == 10 || score == -10)
            return score;
        if (isBoardFull(board))
            return 0;
        
        if (isMaximizing) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = 'O';
                        best = Math.max(best, minimax(board, depth + 1, false));
                        board[i][j] = ' ';
                    }
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = 'X';
                        best = Math.min(best, minimax(board, depth + 1, true));
                        board[i][j] = ' ';
                    }
                }
            }
            return best;
        }
    }

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
        return joueur;
    }

    public char getCellule(int ligne, int colonne) {
        return grille[ligne][colonne];
    }

    public boolean isOrdiEasy() {
        return this.ordiEasy;
    }

    public boolean isOrdiHard() {
        return this.ordiHard;
    }    

    public boolean isGameOver() {
        return gameOver;
    }

    // Setters
    public void setOrdiEasy(boolean choix) {
        this.ordiEasy = choix;
    }

    public void setOrdiHard(boolean choix) {
        this.ordiHard = choix;
    }
}
