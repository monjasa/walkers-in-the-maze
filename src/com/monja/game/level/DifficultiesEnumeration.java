package com.monja.game.level;

public enum DifficultiesEnumeration {

    EASY(21),
    NORMAL(37),
    HARD(59),
    INSANE(75);

    private int size;

    DifficultiesEnumeration(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
