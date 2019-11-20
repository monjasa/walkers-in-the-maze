package com.monja.game.states;

import com.monja.game.Game;
import com.monja.game.InputHandler;
import com.monja.game.entities.Player;
import com.monja.game.gfx.Screen;
import com.monja.game.level.Level;
import com.monja.game.maze.Maze;
import com.monja.game.maze.MazeHistory;
import com.monja.game.sound.SoundPlayer;
import com.monja.game.sound.SoundsEnumeration;

import java.util.LinkedList;

public class CreationToolState extends RenderedState {

    private MazeHistory mazeHistory;

    public CreationToolState(Game game) {
        super(game);
    }

    @Override
    void renderState() {
        Screen screen = game.getScreen();

        Player player = game.getPlayer();
        int xOffset = player.x - (screen.width / 2);
        int yOffset = player.y - (screen.height / 2);

        Level level = game.getLevel();
        level.renderTiles(screen, xOffset, yOffset);
        level.renderEntities(screen);
    }

    @Override
    public void tick() {
        InputHandler input = game.getInput();
        game.tickCount++;
        game.getLevel().tick();

        if (input.escape.isPressed()) {
            /*game.getLocationStrategy().pauseBackground();
            SoundPlayer.getInstance().playSound(SoundsEnumeration.PAUSE_ACTIVATION);

            game.pauseStartTime = System.currentTimeMillis();
            game.changeState(StateClient.getState(StatesEnumeration.PAUSE));*/

            game.setLevel(new Level(game.getLocationStrategy(), mazeHistory.getMazeWidth(), mazeHistory.getMazeHeight(),
                    mazeHistory.getCellsFromHistory(0)));
        }

        input.escape.toggle(false);
    }
}
