package com.monja.game.states;

import com.monja.game.Game;

public abstract class RenderedState implements State {

    protected Game game;

    public RenderedState(Game game) {
        this.game = game;
    }

    abstract void renderState();

    @Override
    public void render() {
        game.prepareBufferStrategy();
        renderState();
        game.displayGraphic();
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
