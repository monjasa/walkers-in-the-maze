package com.monja.game.entities;

import com.monja.game.InputHandler;
import com.monja.game.gfx.Colours;
import com.monja.game.level.Level;

public abstract class Actor extends Mob {

    protected InputHandler input;
    protected int scale = 1;

    public Actor(Level level, String name, int x, int y, InputHandler input) {
        super(level, name, x, y, 1);
        this.input = input;
    }
}
