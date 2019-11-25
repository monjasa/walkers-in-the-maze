package com.monja.game.states;

import com.monja.game.Game;
import com.monja.game.InputHandler;
import com.monja.game.gfx.Colours;
import com.monja.game.gfx.Font;
import com.monja.game.gfx.Screen;
import com.monja.game.sound.SoundPlayer;
import com.monja.game.sound.SoundsEnumeration;

import java.util.Arrays;
import java.util.List;

public class SettingsState extends RenderedState {

    private static String volume = "Volume: ";
    private static String apply = "Apply";
    private static String settingsOptions[] = {volume, apply};
    private static List<String> options = Arrays.asList(settingsOptions);

    public enum SettingsOptionsEnumeration {
        VOLUME,
        APPLY
    }

    private static final int VOLUME_OPTIONS = 10;

    private int currentVolumeOption;

    public SettingsState(Game game) {
        super(game);
        currentVolumeOption = VOLUME_OPTIONS;
    }

    @Override
    void renderState() {
        Screen screen = game.getScreen();

        game.getLevel().renderTiles(screen, 0, 25);

        StringBuilder volumeIndicator = new StringBuilder();
        for (int i = 0; i < currentVolumeOption; i++) volumeIndicator.append("_");
        for (int j = 0; j < VOLUME_OPTIONS - currentVolumeOption; j++) volumeIndicator.append("'");

        for (int i = 0; i < settingsOptions.length; i++) {
            if (i != selectedOption) {
                if (i == options.indexOf(volume))
                    Font.render(settingsOptions[i] + volumeIndicator.toString(), screen,
                            screen.xOffset + screen.width / 2 - ((settingsOptions[i].length() + volumeIndicator.length()) * 8 / 2),
                            screen.yOffset + (screen.height - 16 * settingsOptions.length) / 2 + 16 * i, Colours.get(-1, 000, -1, 555), 1);
                else if (i == options.indexOf(apply))
                    Font.render(settingsOptions[i], screen, screen.xOffset + screen.width / 2 - ((settingsOptions[i].length() * 8) / 2),
                            screen.yOffset + 20 + (screen.height - 16 * settingsOptions.length) / 2 + 16 * i, Colours.get(-1, 000, -1, 555), 1);
                else
                    Font.render(settingsOptions[i], screen, screen.xOffset + screen.width / 2 - ((settingsOptions[i].length() * 8) / 2),
                            screen.yOffset + (screen.height - 16 * settingsOptions.length) / 2 + 16 * i, Colours.get(-1, 000, -1, 555), 1);
            }
        }

        StringBuilder selectedString = new StringBuilder();
        selectedString.append("> ");
        selectedString.append(settingsOptions[selectedOption]);
        if (selectedOption == options.indexOf(volume)) selectedString.append(volumeIndicator);
        selectedString.append(" <");

        int yApplyOffset = selectedOption == options.indexOf(apply) ? 20 : 0;

        Font.render(selectedString.toString(), screen, screen.xOffset + screen.width / 2 - ((selectedString.length() * 8) / 2),
                screen.yOffset + yApplyOffset + (screen.height - 16 * settingsOptions.length) / 2 + 16 * selectedOption, Colours.get(-1, 555, -1, 000), 1);
    }

    @Override
    public void tick() {
        InputHandler input = game.getInput();
        game.tickCount++;
        game.getLevel().tick();

        if (input.up.isPressed()) {
            if (selectedOption - 1 >= 0) {
                selectedOption--;
                SoundPlayer.getInstance().playSound(SoundsEnumeration.MENU_NAVIGATION);
            }

            input.up.toggle(false);
        }

        if (input.down.isPressed()) {
            if (selectedOption + 1 < settingsOptions.length) {
                selectedOption++;
                SoundPlayer.getInstance().playSound(SoundsEnumeration.MENU_NAVIGATION);
            }

            input.down.toggle(false);
        }

        if (input.left.isPressed()) {
            switch (selectedOption) {
                case 0:
                    if (currentVolumeOption  - 1 >= 0) {
                        currentVolumeOption--;
                        SoundPlayer.getInstance().decreaseVolume();
                        SoundPlayer.getInstance().playSound(SoundsEnumeration.MENU_NAVIGATION);
                    }
                    break;
                case 1:
                    break;
            }

            input.left.toggle(false);
        }

        if (input.right.isPressed()) {

            switch (selectedOption) {
                case 0:
                    if (currentVolumeOption < VOLUME_OPTIONS) {
                        currentVolumeOption++;
                        SoundPlayer.getInstance().increaseVolume();
                        SoundPlayer.getInstance().playSound(SoundsEnumeration.MENU_NAVIGATION);
                    }
                    break;
                case 1:
                    break;
            }

            input.right.toggle(false);
        }

        if (input.enter.isPressed()) {

            switch (selectedOption) {
                case 0:
                    SoundPlayer.getInstance().playSound(SoundsEnumeration.MENU_NAVIGATION);
                    if (currentVolumeOption < VOLUME_OPTIONS) {
                        currentVolumeOption++;
                        SoundPlayer.getInstance().increaseVolume();
                        SoundPlayer.getInstance().playSound(SoundsEnumeration.MENU_NAVIGATION);
                    }
                    break;
                case 1:
                    SoundPlayer.getInstance().playSound(SoundsEnumeration.MENU_SELECTION);
                    game.changeState(StateClient.getState(StatesEnumeration.MENU));
                    break;
            }

            selectedOption = 0;
            input.enter.toggle(false);
        }
    }
}
