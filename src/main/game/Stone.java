package main.game;

class Stone {
    private Color color;
    private int x; //column
    private int y; //row
    private int liveConnections;
    private Group group;

    Stone(Color color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.liveConnections = 4;
    }

    Color getColor() {
        return color;
    }

    void setColor(Color color) {
        this.color = color;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    int getLiveConnections() {
        return liveConnections;
    }

    void setLiveConnections(int liveConnections) {
        this.liveConnections = liveConnections;
    }

    Group getGroup() {
        return group;
    }

    void setGroup(Group group) {
        this.group = group;
    }
}
