package leonardo.java.model;

public class TicTacToeModel {
    
    // Attributs
    private char[][] grille;
    private char joueur;
    private boolean gameOver;
    private boolean victoire = false;
    private int nbCoups;

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

    // Getters
    public char getJoueur() {
        return joueur;
    }

    public char getCellule(int ligne, int colonne) {
        return grille[ligne][colonne];
    }

    public int[][] getWinningCells() {
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

    public boolean isGameOver() {
        return gameOver;
    }
}
