package com.monja.game.level.locations;

import com.monja.game.level.Level;
import com.monja.game.level.tiles.Tile;
import com.monja.game.maze.Maze;

public class OceanLocation implements LocationStrategy {

    private static Tile[] wallTiles = new Tile[4];
    private static Tile[] pathTiles = new Tile[4];
    private static Tile[] exitTiles = new Tile[4];

    @Override
    public void loadLevelFromMaze(Level level, int mazeWidth, int mazeHeight) {

    }

    @Override
    public void loadLevelFromCells(Level level, int mazeWidth, int mazeHeight, Maze.Cell[] cells) {

    }

    @Override
    public void loadLevelBackground(Level level) {

    }

    @Override
    public void playBackground() {

    }

    @Override
    public void stopBackground() {

    }

    @Override
    public void pauseBackground() {

    }

    @Override
    public void resumeBackground() {

    }

    @Override
    public Tile getWallTile(int quarter) {
        return wallTiles[quarter];
    }

    @Override
    public Tile getPathTile(int quarter) {
        return pathTiles[quarter];
    }

    @Override
    public Tile getExitTile(int quarter) {
        return exitTiles[quarter];
    }
}
