package com.monja.game.level.tiles;

import com.monja.game.gfx.Colours;
import com.monja.game.gfx.Screen;
import com.monja.game.level.Level;

public abstract class Tile {

    public static final Tile[] tiles = new Tile[256];

    public static final Tile VOID = new BasicTile(0, 0, 0,
            Colours.get(000, -1, -1, -1), 0xFF000000, true);
    public static final Tile STONE = new BasicTile(1, 1, 0,
            Colours.get(-1, 333, 222, -1), -1, true);
    public static final Tile GRASS = new AnimatedBasicTile(2, new int[][] {{0, 4}, {1, 4}},
            Colours.get(-1, 131, 141, -1), -1, 750, false);
    public static final Tile END_TILE = new ActiveTile(3, 3, 0,
            Colours.get(-1, 540, 555, -1), -1);
    public static final Tile WATER = new AnimatedBasicTile(4, new int[][] {{0, 5}, {1, 5}, {2, 5}, {1, 5}},
            Colours.get(-1, 004, 115, -1), 0xFF0000FF, 750, false);

    protected byte id;
    protected boolean solid;
    protected boolean emitter;
    private int levelColour;

    public Tile(int id, boolean isSolid, boolean isEmitter, int levelColour) {
        this.id = (byte) id;
        if (tiles[id] != null) throw new RuntimeException("Duplicate the id on " + id);
        this.solid = isSolid;
        this.emitter = isEmitter;
        this.levelColour = levelColour;

        tiles[id] = this;
    }

    public byte getId() {
        return id;
    }

    public int getLevelColour() {
        return levelColour;
    }

    public boolean isSolid() {
        return solid;
    }

    public boolean isEmitter() {
        return emitter;
    }

    public abstract void tick();

    public abstract void render(Screen screen, Level level, int x, int y);
}
