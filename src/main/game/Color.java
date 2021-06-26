package main.game;

public enum Color {
    BLACK(1), WHITE(2), EMPTY(0);

    private int colorId;

    Color(int colorId) {
        this.colorId = colorId;
    }

    public Color getOppositeColor() {
        return colorId == 2 ? BLACK : WHITE;
    }
}
