package com.monja.game.level.locations;

import com.monja.game.level.Level;
import com.monja.game.level.tiles.Tile;
import com.monja.game.maze.Maze;

public interface LocationStrategy {

    Tile getWallTile(int quarter);

    Tile getPathTile(int quarter);

    Tile getExitTile(int quarter);

    void loadLevelFromCells(Level level, int mazeWidth, int mazeHeight, Maze.Cell[] cells);

    void loadLevelFromMaze(Level level, int mazeWidth, int mazeHeight);

    void loadLevelBackground(Level level);

    void playBackground();

    void stopBackground();

    void pauseBackground();

    void resumeBackground();

}
