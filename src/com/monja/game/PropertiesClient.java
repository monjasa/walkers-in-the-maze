package com.monja.game;

import com.monja.game.level.DifficultiesEnumeration;
import com.monja.game.level.locations.LocationsEnumeration;

import java.util.HashMap;

public class PropertiesClient {

    private static String easy = "Easy";
    private static String normal = "Normal";
    private static String hard = "Hard";
    private static String insane = "Insane";
    private static String difficulties[] = {easy, normal, hard, insane};

    private static String glades = "Glades";
    private static String darkForest = "The Dark Forest";
    private static String locations[] = {glades, darkForest};

    private static HashMap<String, LocationsEnumeration> locationsMap;
    private static HashMap<String, DifficultiesEnumeration> difficultiesMap;

    static {
        difficultiesMap = new HashMap<>();
        difficultiesMap.put(easy, DifficultiesEnumeration.EASY);
        difficultiesMap.put(normal, DifficultiesEnumeration.NORMAL);
        difficultiesMap.put(hard, DifficultiesEnumeration.HARD);
        difficultiesMap.put(insane, DifficultiesEnumeration.INSANE);

        locationsMap = new HashMap<>();
        locationsMap.put(glades, LocationsEnumeration.GLADES);
        locationsMap.put(darkForest, LocationsEnumeration.DARK_FOREST);
    }

    public static LocationsEnumeration getLocation(int selectedLocation) {
        return locationsMap.get(locations[selectedLocation]);
    }

    public static DifficultiesEnumeration getDifficulty(int selectedDifficulty) {
        return difficultiesMap.get(difficulties[selectedDifficulty]);
    }
}
