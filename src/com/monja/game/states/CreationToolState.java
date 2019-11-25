package com.monja.game.states;

import com.monja.game.Game;
import com.monja.game.InputHandler;
import com.monja.game.entities.Actor;
import com.monja.game.entities.Creator;
import com.monja.game.gfx.Screen;
import com.monja.game.level.Level;
import com.monja.game.maze.MazeBuilder;
import com.monja.game.maze.MazeHistory;
import com.monja.game.sound.SoundPlayer;
import com.monja.game.sound.SoundsEnumeration;

public class CreationToolState extends RenderedState {

    private MazeHistory mazeHistory;
    private int currentIteration;
    private Actor actor;

    public CreationToolState(Game game) {
        super(game);
    }

    public void initializeMazeHistory(int mazeWidth, int mazeHeight) {
        mazeHistory = MazeBuilder.getMazeWithHistory(mazeWidth, mazeHeight);
        currentIteration = 0;

        Level level = new Level(game.getLocationStrategy(), mazeHistory.getMazeWidth(), mazeHistory.getMazeHeight(),
                mazeHistory.getCellsFromHistory(0));
        actor = new Creator(level, 20, 20, game.getInput());
        level.addEntity(actor);
        game.setupLevelAndActor(level, actor);
    }

    public void prepareLevelAndPlayer(int index) {
        Level level = new Level(game.getLocationStrategy(), mazeHistory.getMazeWidth(), mazeHistory.getMazeHeight(),
                mazeHistory.getCellsFromHistory(index));
        level.addEntity(actor);
        game.setupLevelAndActor(level, actor);
    }

    @Override
    void renderState() {
        Screen screen = game.getScreen();

        Actor actor = game.getActor();
        int xOffset = actor.x - (screen.width / 2);
        int yOffset = actor.y - (screen.height / 2);

        Level level = game.getLevel();
        level.renderTiles(screen, xOffset, yOffset);
        level.renderEntities(screen);
    }

    @Override
    public void tick() {
        InputHandler input = game.getInput();
        game.tickCount++;
        game.getLevel().tick();

        if (input.q.isPressed()) {
            if (currentIteration - 1 >= 0) {
                prepareLevelAndPlayer(--currentIteration);
                SoundPlayer.getInstance().playSound(SoundsEnumeration.MAZE_DESTROYING);
            }

            input.q.toggle(false);
        }

        if (input.e.isPressed()) {
            if (currentIteration + 1 < mazeHistory.getHistorySize()) {
                prepareLevelAndPlayer(++currentIteration);
                SoundPlayer.getInstance().playSound(SoundsEnumeration.MAZE_BUILDING);
            }

            input.e.toggle(false);
        }

        if (input.r.isPressed()) {
            initializeMazeHistory(mazeHistory.getMazeWidth(), mazeHistory.getMazeHeight());
            SoundPlayer.getInstance().playSound(SoundsEnumeration.MAZE_REBUILDING);

            input.r.toggle(false);
        }

        if (input.escape.isPressed()) {
            game.getLocationStrategy().pauseBackground();
            SoundPlayer.getInstance().playSound(SoundsEnumeration.PAUSE_ACTIVATION);
            game.changeState(StateClient.getState(StatesEnumeration.CREATION_PAUSE));

            input.escape.toggle(false);
        }
    }
}
