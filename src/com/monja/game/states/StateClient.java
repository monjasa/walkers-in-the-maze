package com.monja.game.states;

import com.monja.game.Game;

import java.util.HashMap;

public class StateClient {

    private static HashMap<StatesEnumeration, State> states;

    public static void initializeStates(Game game) {
        states = new HashMap<StatesEnumeration, State>();
        states.put(StatesEnumeration.MENU, new MenuState(game));
        states.put(StatesEnumeration.GAME_CREATION_MENU, new GameCreationMenuState(game));
        states.put(StatesEnumeration.CREATION_TOOL_MENU, new CreationToolMenuState(game));
        states.put(StatesEnumeration.GAME, new GameState(game));
        states.put(StatesEnumeration.CREATION_TOOL, new CreationToolState(game));
        states.put(StatesEnumeration.PAUSE, new PauseState(game));
        states.put(StatesEnumeration.CREATION_PAUSE, new CreationPauseState(game));
        states.put(StatesEnumeration.SETTINGS, new SettingsState(game));
        states.put(StatesEnumeration.CREDITS, new CreditsState(game));
        states.put(StatesEnumeration.FINISH, new FinishState(game));
    }

    public static State getState(StatesEnumeration state) {
        return states.get(state);
    }
}
