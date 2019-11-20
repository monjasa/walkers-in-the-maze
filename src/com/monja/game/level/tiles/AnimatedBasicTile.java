package com.monja.game.level.tiles;

public class AnimatedBasicTile extends BasicTile implements Animated {

    private int[][] animationTileCoords;
    private int currentAnimationIndex;
    private long lastIterationTime;
    private int animationSwitchDelay;

    public AnimatedBasicTile(int id, int[][] animationCoords, int tileColour, int levelColour, int animationSwitchDelay, boolean isSolid) {
        super(id, animationCoords[0][0], animationCoords[0][1], tileColour, levelColour, isSolid);

        this.animationTileCoords = animationCoords;
        this.currentAnimationIndex = 0;
        this.lastIterationTime = System.currentTimeMillis();
        this.animationSwitchDelay = animationSwitchDelay;
    }

    @Override
    public void animatedTick() {
        if ((System.currentTimeMillis() - lastIterationTime) >= animationSwitchDelay) {
            lastIterationTime = System.currentTimeMillis();
            currentAnimationIndex = (currentAnimationIndex + 1) % animationTileCoords.length;
            tileId = (animationTileCoords[currentAnimationIndex][0] + (animationTileCoords[currentAnimationIndex][1] * 32));
        }
    }

    @Override
    public void tick() {
        animatedTick();
    }
}
