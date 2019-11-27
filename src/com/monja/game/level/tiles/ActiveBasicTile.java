package com.monja.game.level.tiles;

public class ActiveBasicTile extends BasicTile implements Active {

    public ActiveBasicTile(int id, int x, int y, int tileColour, int levelColour) {
        super(id, x, y, tileColour, levelColour, false);
    }
}
