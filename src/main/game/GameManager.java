package main.game;

import java.util.Scanner;

import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.lang.System.exit;
import static main.game.Color.BLACK;
import static main.game.Color.WHITE;

public class GameManager {

    private static final String EXIT_CMD = "exit";
    private static final Scanner scanner = new Scanner(System.in);

    @SuppressWarnings("InfiniteRecursion")
    public void startGame() {
        Board playingBoard = new Board(20);
        Color playingColor = WHITE;
        displayNewGameText();

        while (true) {
            displayBoard(playingBoard, playingColor);

            Integer inputX = null;
            Integer inputY = null;

            inputX = askForInput(inputX, format("Place %s stone on column: ", playingColor));
            inputY = askForInput(inputY, "and place on row: ");

            boolean playedSuccessfully = playingBoard.placeStone(new Stone(playingColor, inputX, inputY));

            if (playedSuccessfully) {
                if (isWin(playingBoard)) {
                    break;
                } else {
                    playingColor = playingColor.getOppositeColor();
                }
            } else {
                System.out.println("Cannot play on that field!");
            }
        }
        startGame();
    }

    private boolean isWin(Board playingBoard) {
        Color winner = getWinner(playingBoard.getBoardMatrix());
        if (winner != null) {
            System.out.println(format("Congratulations! %s won!!!", winner));
            return true;
        }
        return false;
    }

    private Integer askForInput(Integer inputReference, String text) {
        String input = null;
        while (inputReference == null) {
            System.out.println(text);
            try {
                input = scanner.nextLine();
                inputReference = parseInt(input);
            } catch (NumberFormatException ex) {
                if (EXIT_CMD.equals(input)) exit(0);
                System.out.println("Type in a number please!");
            }
        }
        return inputReference;
    }

    //Sorry I don't know the actual rules of this game so conquering over 50% of the board is what I came up with :)
    private Color getWinner(Stone[][] boardMatrix) {
        int numberOfFields = boardMatrix.length * boardMatrix.length;
        float numberToWin = (numberOfFields >> 1) + 1f;

        int blackCount = calculateCount(boardMatrix, BLACK);
        int whiteCount = calculateCount(boardMatrix, WHITE);

        if (blackCount >= numberToWin) {
            return BLACK;
        } else if (whiteCount >= numberToWin) {
            return WHITE;
        }

        return null;
    }

    private int calculateCount(Stone[][] boardMatrix, Color color) {
        int count = 0;
        for (Stone[] row : boardMatrix) {
            for (Stone stone : row) {
                if (stone != null && stone.getColor() == color) count++;
            }
        }
        return count;
    }

    private void displayNewGameText() {
        System.out.println("New game!");
        System.out.println("(Type \"exit\" to exit, and \"surrender\" to end the game)");
    }

    private void displayBoard(Board playingBoard, Color playingColor) {
        System.out.println();
        System.out.println(playingColor + " is playing this turn...");
        System.out.println();
        System.out.println(playingBoard.getBoardMatrixVisual());
    }
}
