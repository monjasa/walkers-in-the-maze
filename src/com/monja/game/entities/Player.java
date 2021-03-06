package com.monja.game.entities;

import com.monja.game.InputHandler;
import com.monja.game.gfx.Colours;
import com.monja.game.gfx.Screen;
import com.monja.game.level.Level;
import com.monja.game.level.tiles.Active;
import com.monja.game.level.tiles.Tile;

import static com.monja.game.Game.game;

public class Player extends Actor {

    private int colour = Colours.get(-1, 101, 401, 554);

    private int tickCount = 0;
    protected boolean isOnExit = false;
    protected boolean isSwimming = false;

    public Player(Level level, int x, int y, InputHandler input) {
        super(level, "Player", x, y, input);
        this.xSprite = 0;
        this.ySprite = 26;
    }

    @Override
    public void tick() {
        int xa = 0;
        int ya = 0;

        if (input.up.isPressed()) {
            ya--;
        }

        if (input.down.isPressed()) {
            ya++;
        }

        if (input.left.isPressed()) {
            xa--;
        }

        if (input.right.isPressed()) {
            xa++;
        }

        if (xa != 0 || ya != 0) {
            move(xa, ya);
            isMoving = true;
        } else {
            isMoving = false;
        }

        if (level.getTile(this.x >> 3, this.y >> 3) instanceof Active) {
            isOnExit = true;
            game.stop();
        }

        if (level.getTile(this.x >> 3, this.y >> 3).getId() == Tile.WATER.getId()) {
            isSwimming = true;
        }

        if (isSwimming && level.getTile(this.x >> 3, this.y >> 3).getId() != Tile.WATER.getId()) {
            isSwimming = false;
        }

        tickCount++;
    }

    @Override
    public void render(Screen screen) {
        int xTile = xSprite;
        int yTile = ySprite;
        int walkingSpeed = 4;

        int flipTop = (numSteps >> walkingSpeed) & 1;
        int flipBottom = (numSteps >> walkingSpeed) & 1;

        if (movingDir == 1) {
            xTile += 2;
        } else if (movingDir > 1) {
            xTile += 4 + ((numSteps >> walkingSpeed) & 1) * 2;
            flipTop = (movingDir - 1) % 2;
            flipBottom = (movingDir - 1) % 2;
        }

        int modifier = 8 * scale;
        int xOffset = x - modifier / 2;
        int yOffset = y - modifier / 2 - 4;
        if (isSwimming) {
            int waterColour = 0;
            yOffset += 4;
            if (tickCount % 60 < 15) {
                waterColour = Colours.get(-1, -1, 225, -1);
            } else if (15 <= tickCount % 60 && tickCount % 60 < 30) {
                yOffset -= 1;
                waterColour = Colours.get(-1, 225, 115, -1);
            } else if (30 <= tickCount % 60 && tickCount % 60 < 45) {
                waterColour = Colours.get(-1, 115, -1, 225);
            } else {
                yOffset -= 1;
                waterColour = Colours.get(-1, 225, 115, -1);
            }

            screen.render(xOffset, yOffset + 3, 0 + 25 * 32, waterColour, 0x00, 1);
            screen.render(xOffset + 8, yOffset + 3, 0 + 25 * 32, waterColour, 0x01, 1);
        }

        screen.render(xOffset + (modifier * flipTop), yOffset, xTile + yTile * 32,
                colour, flipTop, scale);
        screen.render(xOffset + modifier - (modifier * flipTop), yOffset, (xTile + 1) + yTile * 32,
                colour, flipTop, scale);

        if (!isSwimming) {
            screen.render(xOffset + (modifier * flipBottom), yOffset + modifier, xTile + (yTile + 1) * 32,
                    colour, flipBottom, scale);
            screen.render(xOffset + modifier - (modifier * flipBottom), yOffset + modifier, (xTile + 1) + (yTile + 1) * 32,
                    colour, flipBottom, scale);
        }
    }

    @Override
    public boolean hasCollided(int xa, int ya) {
        int xMin = 0;
        int xMax = 7;
        int yMin = 3;
        int yMax = 7;

        for (int x = xMin; x < xMax; x++) {
            if (isOnSolidTile(xa, ya, x, yMin)) return true;
        }

        for (int x = xMin; x < xMax; x++) {
            if (isOnSolidTile(xa, ya, x, yMax)) return true;
        }

        for (int y = yMin; y < yMax; y++) {
            if (isOnSolidTile(xa, ya, xMin, y)) return true;
        }

        for (int y = yMin; y < yMax; y++) {
            if (isOnSolidTile(xa, ya, xMax, y)) return true;
        }

        return false;
    }
}
