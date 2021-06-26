package main;

import main.game.GameManager;

public class Main {
    private static final GameManager gameManager = new GameManager();

    public static void main(String[] args) {
        gameManager.startGame();
    }
}
