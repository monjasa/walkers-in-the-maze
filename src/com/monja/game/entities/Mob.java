package com.monja.game.entities;

import com.monja.game.level.Level;
import com.monja.game.level.tiles.Tile;

import java.util.Arrays;

public abstract class Mob extends Entity {

    protected String name;
    protected int speed;
    protected int numSteps = 0;
    protected boolean isMoving;
    protected int movingDir = 1;
    protected int scale = 1;

    public Mob(Level level, String name, int x, int y, int speed) {
        super(level);

        this.name = name;
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public void move(int xa, int ya) {
        if (xa != 0 && ya != 0) {
            move(xa, 0);
            move(0, ya);

            numSteps--;
            return;
        }

        numSteps++;
        if (!hasCollided(xa, ya)) {
            if (ya < 0) movingDir = 0;
            if (ya > 0) movingDir = 1;
            if (xa < 0) movingDir = 2;
            if (xa > 0) movingDir = 3;

            x += xa * speed;
            y += ya * speed;
        }
    }

    public abstract boolean hasCollided(int xa, int ya);

    protected boolean isOnSolidTile(int xa, int ya, int x, int y) {
        if (level == null) {
            return false;
        }

        Tile lastTile = level.getTile((this.x + x) >> 3, (this.y + y) >> 3);
        Tile newTile = level.getTile((this.x + x + xa) >> 3, (this.y + y + ya) >> 3);

        if (!lastTile.equals(newTile) && newTile.isSolid()) {
            return true;
        }

        return false;
    }

    protected boolean isOnBorderTile(int xa, int ya, int x, int y) {
        if (level == null)
            return false;

        int xLastTile = (this.x + x) >> 3;
        int yLastTile = (this.y + y) >> 3;
        int xNewTile = (this.x + x + xa) >> 3;
        int yNewTile = (this.y + y + ya) >> 3;

        Integer[] xBorderTiles = {0, 1, level.getWidth() - 2, level.getWidth() - 1};
        Integer[] yBorderTiles = {0, 1, level.getHeight() - 2, level.getHeight() - 1};

        if (xLastTile != xNewTile && Arrays.asList(xBorderTiles).contains(xNewTile))
            return true;
        if (yLastTile != yNewTile && Arrays.asList(yBorderTiles).contains(yNewTile))
            return true;

        return false;
    }

    public String getName() {
        return name;
    }
}