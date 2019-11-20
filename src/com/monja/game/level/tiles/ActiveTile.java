package com.monja.game.level.tiles;

public class ActiveTile extends BasicTile implements Active {

    public ActiveTile(int id, int x, int y, int tileColour, int levelColour) {
        super(id, x, y, tileColour, levelColour, false);
    }
}
