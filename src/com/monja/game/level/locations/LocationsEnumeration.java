package com.monja.game.level.locations;

public enum LocationsEnumeration {

    GLADES(new GladesLocation()),
    DARK_FOREST(new DarkForestLocation());

    private LocationStrategy location;

    LocationsEnumeration(LocationStrategy location) {
        this.location = location;
    }

    public LocationStrategy getLocationStrategy() {
        return location;
    }


    @Override
    public String toString() {
        String name = name();
        StringBuilder fullName = new StringBuilder();
        fullName.append(name.charAt(0));
        fullName.append(name.substring(1).toLowerCase());

        return fullName.toString().replaceAll("_", " ");
    }
}
