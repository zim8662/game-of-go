package main.game;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

import static main.game.Color.*;

class Board {
    private Stone[][] boardMatrix;

    Board(int boardSize) {
        this.boardMatrix = new Stone[boardSize][boardSize];
        initializeBoard(boardMatrix);
    }

    boolean placeStone(Stone stone) {
        try {
            if (boardMatrix[stone.getY()][stone.getX()].getColor() == EMPTY) {
                boardMatrix[stone.getY()][stone.getX()] = stone;
                checkGrouping(stone);
                removeDeadConnections();
                return true;
            }
            return false;
        } catch (ArrayIndexOutOfBoundsException ex) {
            return false;
        }
    }

    Stone[][] getBoardMatrix() {
        return boardMatrix;
    }

    String getBoardMatrixVisual() {
        StringBuilder sb = new StringBuilder();

        buildTopHeader(sb);
        buildRows(sb);

        return sb.toString();
    }

    private void checkGrouping(Stone stone) {
        Stone[] neighbours = new Stone[4];
        getNeighbours(neighbours, stone);

        Group group = new Group();
        for (Stone neighbor : neighbours) {
            if (isStoneInSameColorGroup(stone, neighbor)) {
                group.joinGroup(neighbor.getGroup());
            }
        }

        group.addStone(stone);
    }

    private boolean isStoneInSameColorGroup(Stone stone, Stone neighbor) {
        return neighbor != null && neighbor.getGroup() != null && neighbor.getColor() == stone.getColor();
    }

    private void getNeighbours(Stone[] neighbours, Stone stone) {
        neighbours[0] = getStoneAt(stone.getX(), stone.getY() - 1);
        neighbours[1] = getStoneAt(stone.getX() + 1, stone.getY());
        neighbours[2] = getStoneAt(stone.getX(), stone.getY() + 1);
        neighbours[3] = getStoneAt(stone.getX() - 1, stone.getY());
    }

    private Stone getStoneAt(int x, int y) {
        try {
            return boardMatrix[y][x];
        } catch (ArrayIndexOutOfBoundsException ex) {
            return null;
        }
    }

    private void initializeBoard(Stone[][] boardMatrix) {
        for (int i = 0; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                Stone stone = new Stone(EMPTY, j, i);
                boardMatrix[i][j] = stone;
            }
        }

        calculateLiveConnections();
    }

    private void calculateLiveConnections() {
        for (Stone[] row : boardMatrix) {
            for (Stone stone : row) {
                calculateLiveConnections(stone);
            }
        }
    }

    private void calculateLiveConnections(Stone stone) {
        Stone[] neighbours = new Stone[4];
        getNeighbours(neighbours, stone);

        int liveConnections = 4;
        for (Stone s : neighbours) {
            if (s == null || s.getColor() != EMPTY) {
                liveConnections--;
            }
        }

        stone.setLiveConnections(liveConnections);
    }

    private void removeDeadConnections() {
        calculateLiveConnections();
        Set<Group> groupsForRemoval = new HashSet<>();

        for (Stone[] row : boardMatrix) {
            for (Stone stone : row) {
                if (stone.getGroup() != null && stone.getGroup().getLiveConnections() <= 0) {
                    groupsForRemoval.add(stone.getGroup());
                }
            }
        }

        groupsForRemoval.forEach(group -> group.getStones().forEach(stone -> stone.setColor(EMPTY)));
        calculateLiveConnections();
    }

    private void buildTopHeader(StringBuilder sb) {
        sb.append("    ");
        IntStream.range(0, boardMatrix.length).forEach(i -> {
            sb.append(i);
            if (i > 10) {
                sb.append("  ");
            } else {
                sb.append("   ");
            }
        });
        sb.append("\n");
    }

    private void buildRows(StringBuilder sb) {
        IntStream.range(0, boardMatrix.length).forEach(i -> {
            if (i < 10) sb.append(" ");
            sb.append(i).append("-");
            buildEntries(sb, boardMatrix[i]);
            sb.append("\n");
        });
    }

    private void buildEntries(StringBuilder sb, Stone[] boardMatrix) {
        for (Stone stone : boardMatrix) {
            if (stone.getColor() == WHITE) {
                sb.append(" o ");
            } else if (stone.getColor() == BLACK) {
                sb.append(" x ");
            } else {
                sb.append("   ");
            }
            sb.append("-");
        }
    }
}
