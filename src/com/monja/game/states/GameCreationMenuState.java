package com.monja.game.states;

import com.monja.game.Game;
import com.monja.game.InputHandler;
import com.monja.game.PropertiesClient;
import com.monja.game.gfx.Colours;
import com.monja.game.gfx.Font;
import com.monja.game.gfx.Screen;
import com.monja.game.level.DifficultiesEnumeration;
import com.monja.game.level.locations.LocationsEnumeration;
import com.monja.game.sound.SoundPlayer;
import com.monja.game.sound.SoundsEnumeration;

public class GameCreationMenuState extends RenderedState {

    private static String locations[] = {
        LocationsEnumeration.GLADES.toString(),
        LocationsEnumeration.DARK_FOREST.toString()
    };

    private static String difficulties[] = {
        DifficultiesEnumeration.EASY.toString(),
        DifficultiesEnumeration.NORMAL.toString(),
        DifficultiesEnumeration.HARD.toString(),
        DifficultiesEnumeration.INSANE.toString()
    };

    private static String start = "Start";
    private static String backToMenu = "Back to menu";
    private static String gameCreationMenuOptions[] = {null, null, start, backToMenu};

    public GameCreationMenuState(Game game) {
        super(game);
    }

    @Override
    void renderState() {
        Screen screen = game.getScreen();
        int selectedDifficulty = game.getSelectedDifficulty();
        int selectedLocation = game.getSelectedLocation();

        game.getLevel().renderTiles(screen, 0, 25);

        gameCreationMenuOptions[0] = difficulties[selectedDifficulty];
        gameCreationMenuOptions[1] = locations[selectedLocation];

        int heightUnit = screen.height / 15;
        int heightBlocks[] = {4, 6, 9, 11, 12, 13, 14};

        String selectDifficulty = "Select Difficulty:";
        Font.render(selectDifficulty, screen, screen.xOffset + screen.width / 2 - (selectDifficulty.length() * 8) / 2,
                heightBlocks[0] * heightUnit, Colours.get(-1, 000, -1, 555), 1);

        String selectLocation = "Select Location:";
        Font.render(selectLocation, screen, screen.xOffset + screen.width / 2 - (selectLocation.length() * 8) / 2,
                 heightBlocks[2] * heightUnit, Colours.get(-1, 000, -1, 555), 1);

        for (int i = 0; i < gameCreationMenuOptions.length; i++) {
            if (i != selectedOption) {
                int block =  i == gameCreationMenuOptions.length - 1 ? 2 * i : 2 * i + 1;
                System.out.println(i);
                Font.render(gameCreationMenuOptions[i], screen, screen.xOffset + screen.width / 2 - ((gameCreationMenuOptions[i].length() * 8) / 2),
                        heightBlocks[block] * heightUnit, Colours.get(-1, 000, -1, 555), 1);
            }
        }

        int block = selectedOption == gameCreationMenuOptions.length - 1 ? 2 * selectedOption : 2 * selectedOption + 1;

        StringBuilder selectedString = new StringBuilder();
        selectedString.append("> ");
        selectedString.append(gameCreationMenuOptions[selectedOption]);
        selectedString.append(" <");

        Font.render(selectedString.toString(), screen, screen.xOffset + screen.width / 2 - ((selectedString.length() * 8) / 2),
                heightBlocks[block] * heightUnit, Colours.get(-1, 555, -1, 000), 1);
    }

    @Override
    public void tick() {
        InputHandler input = game.getInput();
        game.tickCount++;
        game.getLevel().tick();
        int selectedDifficulty = game.getSelectedDifficulty();
        int selectedLocation = game.getSelectedLocation();

        if (input.up.isPressed()) {
            if (selectedOption - 1 >= 0) {
                selectedOption--;
                SoundPlayer.getInstance().playSound(SoundsEnumeration.MENU_NAVIGATION);
            }

            input.up.toggle(false);
        }

        if (input.down.isPressed()) {
            if (selectedOption + 1 < gameCreationMenuOptions.length) {
                selectedOption++;
                SoundPlayer.getInstance().playSound(SoundsEnumeration.MENU_NAVIGATION);
            }

            input.down.toggle(false);
        }

        if (input.left.isPressed()) {
            switch (selectedOption) {
                case 0:
                    if (selectedDifficulty  - 1 >= 0)
                        game.setSelectedDifficulty(--selectedDifficulty);
                    else
                        game.setSelectedDifficulty(difficulties.length - 1);

                    SoundPlayer.getInstance().playSound(SoundsEnumeration.MENU_NAVIGATION);
                    break;
                case 1:
                    if (selectedLocation - 1 >= 0)
                        game.setSelectedLocation(--selectedLocation);
                    else
                        game.setSelectedLocation(locations.length - 1);

                    game.changeLocationStrategy(PropertiesClient.getLocation(game.getSelectedLocation()).getLocationStrategy());
                    SoundPlayer.getInstance().playSound(SoundsEnumeration.MENU_NAVIGATION);
                    break;
            }

            input.left.toggle(false);
        }

        if (input.right.isPressed()) {
            switch (selectedOption) {
                case 0:
                    if (selectedDifficulty + 1 < difficulties.length)
                        game.setSelectedDifficulty(++selectedDifficulty);
                    else
                        game.setSelectedDifficulty(0);

                    SoundPlayer.getInstance().playSound(SoundsEnumeration.MENU_NAVIGATION);
                    break;
                case 1:
                    if (selectedLocation + 1 < locations.length)
                        game.setSelectedLocation(++selectedLocation);
                    else
                        game.setSelectedLocation(0);

                    game.changeLocationStrategy(PropertiesClient.getLocation(game.getSelectedLocation()).getLocationStrategy());
                    SoundPlayer.getInstance().playSound(SoundsEnumeration.MENU_NAVIGATION);
                    break;
            }

            input.right.toggle(false);
        }

        if (input.enter.isPressed()) {
            switch (selectedOption) {
                case 0:
                    SoundPlayer.getInstance().playSound(SoundsEnumeration.MENU_NAVIGATION);
                    if (selectedDifficulty + 1 < difficulties.length)
                        game.setSelectedDifficulty(++selectedDifficulty);
                    else
                        game.setSelectedDifficulty(0);
                    break;
                case 1:
                    SoundPlayer.getInstance().playSound(SoundsEnumeration.MENU_NAVIGATION);
                    if (selectedLocation + 1 < locations.length)
                        game.setSelectedLocation(++selectedLocation);
                    else
                        game.setSelectedLocation(0);

                    game.changeLocationStrategy(PropertiesClient.getLocation(game.getSelectedLocation()).getLocationStrategy());
                    break;
                case 2:
                    SoundPlayer.getInstance().playSound(SoundsEnumeration.MENU_SELECTION);
                    game.changeState(StateClient.getState(StatesEnumeration.GAME));
                    game.initializeLevel();
                    break;
                case 3:
                    SoundPlayer.getInstance().playSound(SoundsEnumeration.PAUSE_DEACTIVATION);
                    game.changeState(StateClient.getState(StatesEnumeration.MENU));
                    break;
            }

            selectedOption = 0;
            input.enter.toggle(false);
        }
    }
}
