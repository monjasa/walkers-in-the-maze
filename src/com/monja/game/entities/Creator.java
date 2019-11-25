package com.monja.game.entities;

import com.monja.game.InputHandler;
import com.monja.game.gfx.Colours;
import com.monja.game.gfx.Screen;
import com.monja.game.level.Level;

public class Creator extends Actor {

    private int colour = Colours.get(-1, 501, 541, 554);

    private int[] ySpriteArray = {20, 22};
    private int currentAnimationIndex;
    private long lastIterationTime;
    private int animationSwitchDelay;

    public Creator(Level level, int x, int y, InputHandler input) {
        super(level, "Creator", x, y, input);
        this.speed = 2;
        this.currentAnimationIndex = 0;
        this.animationSwitchDelay = 750;

        this.xSprite = 0;
        this.ySprite = ySpriteArray[currentAnimationIndex];
    }

    @Override
    public void tick() {
        int xa = 0;
        int ya = 0;

        if ((System.currentTimeMillis() - lastIterationTime) >= animationSwitchDelay) {
            lastIterationTime = System.currentTimeMillis();
            currentAnimationIndex = (currentAnimationIndex + 1) % ySpriteArray.length;
            ySprite = (ySpriteArray[currentAnimationIndex]);
        }

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

        screen.render(xOffset + (modifier * flipTop), yOffset, xTile + yTile * 32,
                colour, flipTop, scale);
        screen.render(xOffset + modifier - (modifier * flipTop), yOffset, (xTile + 1) + yTile * 32,
                colour, flipTop, scale);

        screen.render(xOffset + (modifier * flipBottom), yOffset + modifier, xTile + (yTile + 1) * 32,
                colour, flipBottom, scale);
        screen.render(xOffset + modifier - (modifier * flipBottom), yOffset + modifier, (xTile + 1) + (yTile + 1) * 32,
                colour, flipBottom, scale);
    }

    @Override
    public boolean hasCollided(int xa, int ya) {
        int xMin = 0;
        int xMax = 7;
        int yMin = 3;
        int yMax = 7;

        for (int x = xMin; x < xMax; x++) {
            if (isOnBorderTile(xa, ya, x, yMin)) return true;
        }

        for (int x = xMin; x < xMax; x++) {
            if (isOnBorderTile(xa, ya, x, yMax)) return true;
        }

        for (int y = yMin; y < yMax; y++) {
            if (isOnBorderTile(xa, ya, xMin, y)) return true;
        }

        for (int y = yMin; y < yMax; y++) {
            if (isOnBorderTile(xa, ya, xMax, y)) return true;
        }

        return false;
    }
}
