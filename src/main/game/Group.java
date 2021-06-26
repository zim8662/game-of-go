package main.game;

import java.util.ArrayList;
import java.util.List;

class Group {
    private List<Stone> stones;

    Group() {
        this.stones = new ArrayList<>();
    }

    int getLiveConnections() {
        int liveConnections = 0;
        for (Stone stone : stones) {
            liveConnections += stone.getLiveConnections();
        }
        return liveConnections;
    }

    void addStone(Stone stone) {
        stone.setGroup(this);
        this.stones.add(stone);
    }

    void joinGroup(Group group) {
        for (Stone stone : group.getStones()) {
            addStone(stone);
        }
    }

    List<Stone> getStones() {
        return stones;
    }
}
