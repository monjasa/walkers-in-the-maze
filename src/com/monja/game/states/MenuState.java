package com.monja.game.states;

import com.monja.game.Game;
import com.monja.game.InputHandler;
import com.monja.game.gfx.Colours;
import com.monja.game.gfx.Font;
import com.monja.game.gfx.Screen;
import com.monja.game.sound.SoundPlayer;
import com.monja.game.sound.SoundsEnumeration;

public class MenuState extends RenderedState {

    private static String play = "Play";
    private static String creationTool = "Creation Tool";
    private static String settings = "Settings";
    private static String credits = "Credits";
    private static String exit = "Exit";
    private static String menuOptions[] = {play, creationTool, settings, credits, exit};

    public MenuState(Game game) {
        super(game);
    }

    @Override
    void renderState() {
        Screen screen = game.getScreen();
        int selectedOption = game.getSelectedOption();

        game.getLevel().renderTiles(screen, 0, 25);

        for (int i = 0; i < menuOptions.length; i++) {
            if (i != selectedOption) {
                Font.render(menuOptions[i], screen, screen.xOffset + screen.width / 2 - ((menuOptions[i].length() * 8) / 2),
                        screen.yOffset + 20 + (screen.height - 16 * menuOptions.length) / 2 + 16 * i, Colours.get(-1, 000, -1, 555), 1);
            }
        }

        StringBuilder selectedString = new StringBuilder();
        selectedString.append("> ");
        selectedString.append(menuOptions[selectedOption]);
        selectedString.append(" <");

        Font.render(selectedString.toString(), screen, screen.xOffset + screen.width / 2 - ((selectedString.length() * 8) / 2),
                screen.yOffset + 20 + (screen.height - 16 * menuOptions.length) / 2 + 16 * selectedOption, Colours.get(-1, 555, -1, 000), 1);
    }

    @Override
    public void tick() {
        InputHandler input = game.getInput();
        game.tickCount++;
        game.getLevel().tick();

        int selectedOption = game.getSelectedOption();

        if (input.up.isPressed()) {
            if (selectedOption - 1 >= 0) {
                game.setSelectedOption(--selectedOption);
                SoundPlayer.getInstance().playSound(SoundsEnumeration.MENU_NAVIGATION);
            }

            input.up.toggle(false);
        }

        if (input.down.isPressed()) {
            if (selectedOption + 1 < menuOptions.length) {
                game.setSelectedOption(++selectedOption);
                SoundPlayer.getInstance().playSound(SoundsEnumeration.MENU_NAVIGATION);
            }

            input.down.toggle(false);
        }

        if (input.enter.isPressed()) {
            switch (selectedOption) {
                case 0:
                    SoundPlayer.getInstance().playSound(SoundsEnumeration.MENU_SELECTION);
                    game.changeState(StateClient.getState(StatesEnumeration.GAME_CREATION_MENU));
                    break;
                case 1:
                    SoundPlayer.getInstance().playSound(SoundsEnumeration.MENU_SELECTION);
                    game.changeState(StateClient.getState(StatesEnumeration.CREATION_TOOL_MENU));
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    game.isRunning = false;
                    game.closeApplication();
                    break;
            }

            input.enter.toggle(false);
            game.setSelectedOption(0);
        }
    }
}
