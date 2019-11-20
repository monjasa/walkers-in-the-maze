package com.monja.game.states;

import com.monja.game.Game;
import com.monja.game.gfx.Colours;
import com.monja.game.gfx.Font;
import com.monja.game.gfx.Screen;
import com.monja.game.sound.SoundPlayer;
import com.monja.game.sound.SoundsEnumeration;

public class FinishState extends RenderedState {

    private static String winMessage = "You have won!";
    private static String pressEnterMessage = "Press ENTER to continue...";
    private static String credits = "Monja (c)";
    private static String[] messages = {winMessage, pressEnterMessage, credits};

    public FinishState(Game game) {
        super(game);
    }

    @Override
    void renderState() {
        Screen screen = game.getScreen();
        int timeSpentInSeconds = (int) (game.endTime - game.startTime) / 1000;
        int timeSpentInMinutes = timeSpentInSeconds / 60;
        timeSpentInSeconds = timeSpentInSeconds % 60;

        StringBuilder timeString = new StringBuilder();
        if (timeSpentInMinutes > 1) timeString.append(timeSpentInMinutes + " minutes ");
        else if (timeSpentInMinutes == 1) timeString.append("1 minute ");
        timeString.append(timeSpentInSeconds + " seconds");

        Font.render(messages[0], screen, screen.xOffset + screen.width / 2 - ((messages[0].length() * 8) / 2),
                screen.yOffset - 20 + screen.height / 2, Colours.get(-1, 000, -1, 555), 1);
        Font.render(timeString.toString(), screen, screen.xOffset + screen.width / 2 - ((timeString.length() * 8) / 2),
                screen.yOffset - 20 + screen.height / 2 + 16, Colours.get(-1, 000, -1, 555), 1);
        Font.render(messages[1], screen, screen.xOffset + screen.width / 2 - ((messages[1].length() * 8) / 2),
                screen.yOffset + 25 + screen.height / 2 + 16, Colours.get(-1, 000, -1, 555), 1);
        Font.render(messages[2], screen, screen.xOffset + screen.width / 2 - ((messages[2].length() * 8) / 2),
                screen.yOffset + screen.height - 12, Colours.get(-1, 222, -1, 555), 1);
    }

    @Override
    public void tick() {
        if (game.getInput().enter.isPressed()) {
            SoundPlayer.getInstance().playSound(SoundsEnumeration.MENU_SELECTION);
            game.initializeLevel();
            game.getLocationStrategy().resumeBackground();
            game.changeState(StateClient.getState(StatesEnumeration.GAME));


            game.getInput().enter.toggle(false);
        }
    }
}
