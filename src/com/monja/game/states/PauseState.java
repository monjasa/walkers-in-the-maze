package com.monja.game.states;

import com.monja.game.Game;
import com.monja.game.InputHandler;
import com.monja.game.entities.Player;
import com.monja.game.gfx.Colours;
import com.monja.game.gfx.Font;
import com.monja.game.gfx.Screen;
import com.monja.game.level.Level;
import com.monja.game.sound.SoundPlayer;
import com.monja.game.sound.SoundsEnumeration;

public class PauseState extends RenderedState {

    private static String resume = "Resume";
    private static String backToMenu = "Back to menu";
    private static String exit = "Exit";
    private static String pauseMenuOptions[] = {resume, backToMenu, exit};

    public PauseState(Game game) {
        super(game);
    }

    @Override
    void renderState() {
        Player player = game.getPlayer();
        Screen screen = game.getScreen();
        Level level = game.getLevel();

        int xOffset = player.x - (screen.width / 2);
        int yOffset = player.y - (screen.height / 2);

        level.renderTiles(screen, xOffset, yOffset);
        level.renderEntities(screen);

        String paused = "PAUSED";
        Font.render(paused, screen, screen.xOffset + screen.width / 2 - (paused.length() * 8) / 2,
                screen.yOffset + 60, Colours.get(-1, 000, -1, 555), 1);

        int selectedOption = game.getSelectedOption();

        for (int i = 0; i < pauseMenuOptions.length; i++) {
            if (i != selectedOption) {
                Font.render(pauseMenuOptions[i], screen, screen.xOffset + screen.width / 2 - ((pauseMenuOptions[i].length() * 8) / 2),
                        screen.yOffset + 30 + (screen.height - 16 * pauseMenuOptions.length) / 2 + 16 * i, Colours.get(-1, 000, -1, 555), 1);
            }
        }

        StringBuilder selectedString = new StringBuilder();
        selectedString.append("> ");
        selectedString.append(pauseMenuOptions[selectedOption]);
        selectedString.append(" <");

        Font.render(selectedString.toString(), screen, screen.xOffset + screen.width / 2 - ((selectedString.length() * 8) / 2),
                screen.yOffset + 30 + (screen.height - 16 * pauseMenuOptions.length) / 2 + 16 * selectedOption, Colours.get(-1, 555, -1, 000), 1);
    }

    @Override
    public void tick() {
        InputHandler input = game.getInput();
        SoundPlayer soundPlayer = SoundPlayer.getInstance();
        game.tickCount++;

        int selectedOption = game.getSelectedOption();

        if (input.up.isPressed()) {
            if (selectedOption - 1 >= 0) {
                game.setSelectedOption(--selectedOption);
                soundPlayer.playSound(SoundsEnumeration.MENU_NAVIGATION);
            }
            input.up.toggle(false);
        }

        if (input.down.isPressed()) {
            if (selectedOption + 1 < pauseMenuOptions.length) {
                game.setSelectedOption(++selectedOption);
                soundPlayer.playSound(SoundsEnumeration.MENU_NAVIGATION);
            }
            input.down.toggle(false);
        }

        if (input.enter.isPressed()) {
            switch (selectedOption) {
                case 0:
                    soundPlayer.playSound(SoundsEnumeration.PAUSE_DEACTIVATION);
                    game.getLocationStrategy().resumeBackground();

                    game.pauseEndTime = System.currentTimeMillis();
                    game.startTime = game.startTime + (game.pauseEndTime - game.pauseStartTime);
                    game.changeState(StateClient.getState(StatesEnumeration.GAME));
                    break;
                case 1:
                    soundPlayer.playSound(SoundsEnumeration.PAUSE_DEACTIVATION);
                    game.getLocationStrategy().resumeBackground();

                    game.setLevel(new Level(game.getLocationStrategy()));
                    game.changeState(StateClient.getState(StatesEnumeration.MENU));
                    break;
                case 2:
                    game.closeApplication();
                    break;
            }

            input.enter.toggle(false);
            game.setSelectedOption(0);
        }
    }
}
