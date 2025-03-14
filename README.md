# Projet Tic Tac Toe (Java Swing)

Ce projet implémente un jeu de Tic Tac Toe avec une interface graphique développée en Java, en utilisant la bibliothèque Swing. L'architecture suit le modèle MVC (Model-View-Controller).

## Fonctionnalités

- Jeu en mode **Joueur contre Joueur**.
- Jeu en mode **Joueur contre Ordinateur** avec deux niveaux :
  - **Facile** : l'ordinateur choisit une case au hasard.
  - **Difficile** : IA utilisant l'algorithme Minimax.

## Structure du projet

- `TicTacToeModel.java` : Gère la logique et l'état du jeu.
- `TicTacToeView.java` : Gère l'affichage graphique et les interactions visuelles.
- `TicTacToeController.java` : Assure la communication entre la vue et le modèle, et gère les interactions utilisateur.
- `TicTacToeApp.java` : Classe principale lançant l'application.

## Prérequis

- Java JDK (version 8 ou supérieure recommandée)
- Maven pour la gestion des dépendances

## Dépendances

- Bibliothèque graphique : **Swing** (inclus dans Java)
- Thème graphique : **FlatDarculaLaf**

## Installation et lancement

### Cloner le dépôt

```bash
git clone https://github.com/leonardo-correiamendes/tictactoejava.git
cd tictactoejava
```

### Compiler et lancer avec Maven

Pour compiler et exécuter le programme depuis un terminal :

```bash
cd tictactoe
mvn clean install
cd target
java -jar ./tictactoe-3.jar
```

### Exécution depuis un IDE (Eclipse, IntelliJ IDEA, etc.)

- Importer le projet comme un projet Maven.
- Lancer la classe principale : `TicTacToeApp.java`

## Auteur

- *CORREIA MENDES Leonardo*

---
