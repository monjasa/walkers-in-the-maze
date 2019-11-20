package com.monja.game.level.locations;

public enum LocationsEnumeration {

    GLADES(new GladesLocation()),
    DARK_FOREST(new DarkForestLocation()),
    OCEAN(new OceanLocation());

    private LocationStrategy location;

    LocationsEnumeration(LocationStrategy location) {
        this.location = location;
    }

    public LocationStrategy getLocationStrategy() {
        return location;
    }

}
