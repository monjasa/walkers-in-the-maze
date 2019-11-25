package com.monja.game.entities;

import com.monja.game.gfx.Screen;
import com.monja.game.level.Level;

public abstract class Entity {

    public int x, y;
    protected int xSprite;
    protected int ySprite;
    protected Level level;

    public Entity(Level level) {
        init(level);
    }

    public final void init (Level level) {
        this.level = level;
    }

    public abstract void tick();

    public abstract void render(Screen screen);
}
