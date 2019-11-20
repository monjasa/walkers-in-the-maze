package com.monja.game.level.tiles;

import com.monja.game.gfx.Screen;
import com.monja.game.level.Level;

public class BasicTile extends Tile {

    protected int tileId;
    protected int tileColour;

    public BasicTile(int id, int x, int y, int tileColour, int levelColour, boolean isSolid) {
        super(id, false, false, levelColour);
        this.tileId = x + y * 32;
        this.tileColour = tileColour;
        this.solid = isSolid;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Screen screen, Level level, int x, int y) {
        screen.render(x, y, tileId, tileColour, 0x00, 1);
    }
}
