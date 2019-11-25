package com.monja.game.level;

public enum DifficultiesEnumeration {

    EASY(23),
    NORMAL(41),
    HARD(51),
    INSANE(61);

    private int size;

    DifficultiesEnumeration(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }


    @Override
    public String toString() {
        String name = name();
        StringBuilder fullName = new StringBuilder();
        fullName.append(name.charAt(0));
        fullName.append(name.substring(1).toLowerCase());

        return fullName.toString();
    }
}
