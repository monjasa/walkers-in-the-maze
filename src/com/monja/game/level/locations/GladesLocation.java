package com.monja.game.level.locations;

import com.monja.game.gfx.Colours;
import com.monja.game.level.Level;
import com.monja.game.level.tiles.AnimatedActiveTile;
import com.monja.game.level.tiles.AnimatedBasicTile;
import com.monja.game.level.tiles.Tile;
import com.monja.game.maze.Maze;
import com.monja.game.maze.MazeBuilder;
import com.monja.game.sound.SoundPlayer;
import com.monja.game.sound.SoundsEnumeration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GladesLocation implements LocationStrategy {

    private static Tile[] wallTiles = new Tile[4];
    private static Tile[] pathTiles = new Tile[4];
    private static Tile[] exitTiles = new Tile[4];

    private static final Tile GLADES_STONE_LU = new AnimatedBasicTile(5, new int[][] {{0, 2}, {2, 2}, {4, 2}, {0, 2}},
            Colours.get(-1, 333, 222, 444), 0xFF555555, 750, true);
    private static final Tile GLADES_STONE_RU = new AnimatedBasicTile(6, new int[][] {{1, 2}, {3, 2}, {5, 2}, {1, 2}},
            Colours.get(-1, 333, 222, 444), -1, 750, true);
    private static final Tile GLADES_STONE_LD = new AnimatedBasicTile(7, new int[][] {{0, 3}, {2, 3}, {4, 3}, {0, 3}},
            Colours.get(-1, 333, 222, 444), -1, 750, true);
    private static final Tile GLADES_STONE_RD = new AnimatedBasicTile(8, new int[][] {{1, 3}, {3, 3}, {5, 3}, {1, 3}},
            Colours.get(-1, 333, 222, 444), -1, 750, true);

    private static final Tile GLADES_GRASS_LU = new AnimatedBasicTile(9, new int[][] {{6, 2}, {8, 2}, {6, 2}, {10, 2}, {8, 2}},
            Colours.get(-1, 131, 141, 512), 0xFF00FF00, 750, false);
    private static final Tile GLADES_GRASS_RU = new AnimatedBasicTile(10, new int[][] {{7, 2}, {9, 2}, {7, 2}, {11, 2}, {9, 2}},
            Colours.get(-1, 131, 141, 553), -1, 750, false);
    private static final Tile GLADES_GRASS_LD = new AnimatedBasicTile(11, new int[][] {{6, 3}, {8, 3}, {6, 3}, {10, 3}, {8, 3}},
            Colours.get(-1, 131, 141, 553), -1, 750, false);
    private static final Tile GLADES_GRASS_RD = new AnimatedBasicTile(12, new int[][] {{7, 3}, {9, 3}, {9, 3}, {11, 3}, {9, 3}},
            Colours.get(-1, 131, 141, 512), -1, 750, false);

    private static final Tile GLADES_EXIT_LU = new AnimatedActiveTile(13, new int[][] {{12, 2}, {14, 2}, {16, 2}},
            Colours.get(000, 131, 512, 554), 0xFFFFFF00, 750, false);
    private static final Tile GLADES_EXIT_RU = new AnimatedActiveTile(14, new int[][] {{13, 2}, {15, 2}, {17, 2}},
            Colours.get(000, 131, 512, 554), -1, 750, false);
    private static final Tile GLADES_EXIT_LD = new AnimatedActiveTile(15, new int[][] {{12, 3}, {14, 3}, {16, 3}},
            Colours.get(000, 131, 512, 554), -1, 750, false);
    private static final Tile GLADES_EXIT_RD = new AnimatedActiveTile(16, new int[][] {{13, 3}, {15, 3}, {17, 3}},
            Colours.get(000, 131, 512, 554), -1, 750, false);

    static {
        wallTiles[0] = GLADES_STONE_LU;
        wallTiles[1] = GLADES_STONE_RU;
        wallTiles[2] = GLADES_STONE_LD;
        wallTiles[3] = GLADES_STONE_RD;

        pathTiles[0] = GLADES_GRASS_LU;
        pathTiles[1] = GLADES_GRASS_RU;
        pathTiles[2] = GLADES_GRASS_LD;
        pathTiles[3] = GLADES_GRASS_RD;

        exitTiles[0] = GLADES_EXIT_LU;
        exitTiles[1] = GLADES_EXIT_RU;
        exitTiles[2] = GLADES_EXIT_LD;
        exitTiles[3] = GLADES_EXIT_RD;
    }

    public void loadLevelFromMaze(Level level, int mazeWidth, int mazeHeight) {
        int width = 2 * mazeWidth;
        int height = 2 * mazeHeight;

        level.setWidth(width);
        level.setHeight(height);
        byte[] tiles = new byte[width * height];

        Maze maze = MazeBuilder.getMazeInTiles(width / 2, height / 2);
        Maze.Cell[] cells = maze.getCellsArray();

        for (int y = 0; y < height / 2; y++) {
            for (int x = 0; x < width / 2; x++) {
                Maze.Cell cell = cells[x + y * width / 2];

                if (cell.equals(Maze.Cell.WALL)) {
                    tiles[(2 * x) + (2 * y) * width] = getWallTile(0).getId();
                    tiles[(2 * x + 1) + (2 * y) * width] = getWallTile(1).getId();
                    tiles[(2 * x) + (2 * y + 1) * width] = getWallTile(2).getId();
                    tiles[(2 * x + 1) + (2 * y + 1) * width] = getWallTile(3).getId();
                } else if (cell.equals(Maze.Cell.PATH)) {
                    tiles[(2 * x) + (2 * y) * width] = getPathTile(0).getId();
                    tiles[(2 * x + 1) + (2 * y) * width] = getPathTile(1).getId();
                    tiles[(2 * x) + (2 * y + 1) * width] = getPathTile(2).getId();
                    tiles[(2 * x + 1) + (2 * y + 1) * width] = getPathTile(3).getId();
                }
            }
        }

        int xExit = maze.getExitX();
        int yExit = maze.getExitY();
        tiles[(2 * xExit) + (2 * yExit) * width] = getExitTile(0).getId();
        tiles[(2 * xExit + 1) + (2 * yExit) * width] = getExitTile(1).getId();
        tiles[(2 * xExit) + (2 * yExit + 1) * width] = getExitTile(2).getId();
        tiles[(2 * xExit + 1) + (2 * yExit + 1) * width] = getExitTile(3).getId();

        level.setTiles(tiles);
    }

    @Override
    public void loadLevelFromCells(Level level, int mazeWidth, int mazeHeight, Maze.Cell[] cells) {
        int width = 2 * mazeWidth;
        int height = 2 * mazeHeight;

        level.setWidth(width);
        level.setHeight(height);
        byte[] tiles = new byte[width * height];

        for (int y = 0; y < mazeHeight; y++) {
            for (int x = 0; x < mazeWidth; x++) {
                Maze.Cell cell = cells[x + y * mazeWidth];

                if (cell.equals(Maze.Cell.WALL)) {
                    tiles[(2 * x) + (2 * y) * width] = getWallTile(0).getId();
                    tiles[(2 * x + 1) + (2 * y) * width] = getWallTile(1).getId();
                    tiles[(2 * x) + (2 * y + 1) * width] = getWallTile(2).getId();
                    tiles[(2 * x + 1) + (2 * y + 1) * width] = getWallTile(3).getId();
                } else if (cell.equals(Maze.Cell.PATH)) {
                    tiles[(2 * x) + (2 * y) * width] = getPathTile(0).getId();
                    tiles[(2 * x + 1) + (2 * y) * width] = getPathTile(1).getId();
                    tiles[(2 * x) + (2 * y + 1) * width] = getPathTile(2).getId();
                    tiles[(2 * x + 1) + (2 * y + 1) * width] = getPathTile(3).getId();
                }
            }
        }

        int xExit = 0;
        int yExit = 0;

        for (int i = 0; i < mazeWidth; i++) {
            if (cells[i + 0 * mazeWidth].equals(Maze.Cell.PATH)) {
                xExit = i;
                yExit = 0;
                break;
            } else if (cells[i + (mazeHeight - 1) * mazeWidth].equals(Maze.Cell.PATH)) {
                xExit = i;
                yExit = mazeHeight - 1;
                break;
            }
        }

        if (xExit == 0 && yExit == 0)
            for (int j = 1; j < mazeHeight - 1; j++) {
                if (cells[0 + j * mazeWidth].equals(Maze.Cell.PATH)) {
                    xExit = 0;
                    yExit = j;
                    break;
                } else if (cells[(mazeWidth - 1) + j * mazeWidth].equals(Maze.Cell.PATH)) {
                    xExit = mazeWidth - 1;
                    yExit = j;
                    break;
                }
            }

        if (xExit != 0 && yExit != 0) {
            tiles[(2 * xExit) + (2 * yExit) * width] = getExitTile(0).getId();
            tiles[(2 * xExit + 1) + (2 * yExit) * width] = getExitTile(1).getId();
            tiles[(2 * xExit) + (2 * yExit + 1) * width] = getExitTile(2).getId();
            tiles[(2 * xExit + 1) + (2 * yExit + 1) * width] = getExitTile(3).getId();
        }

        level.setTiles(tiles);
    }

    public void loadLevelBackground(Level level) {
        try {
            BufferedImage image = ImageIO.read(Level.class.getResource("/levels/level_glades.png"));
            level.setImage(image);

            int width = image.getWidth() * 2;
            int height = image.getHeight() * 2;

            level.setWidth(width);
            level.setHeight(height);

            level.setTiles(new byte[width * height]);

            int[] tileColours = level.getImage().getRGB(0, 0, width / 2, height / 2, null, 0, width / 2);
            for (int y = 0; y < height / 2; y++) {
                for (int x = 0; x < width / 2; x++) {
                    tileCheck: for (Tile tile : Tile.tiles) {
                        if (tile != null && tile.getLevelColour() == tileColours[x + y * width / 2]) {

                            level.getTiles()[(2 * x) + (2 * y) * width] = tile.getId();
                            level.getTiles()[(2 * x + 1) + (2 * y) * width] = (byte) (tile.getId() + 1);
                            level.getTiles()[(2 * x) + (2 * y + 1) * width] = (byte) (tile.getId() + 2);
                            level.getTiles()[(2 * x + 1) + (2 * y + 1) * width] = (byte) (tile.getId() + 3);

                            break tileCheck;
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void playBackground() {
        SoundPlayer.getInstance().playSound(SoundsEnumeration.GLADES_BACKGROUND);
    }

    @Override
    public void stopBackground() {
        SoundPlayer.getInstance().stopSound(SoundsEnumeration.GLADES_BACKGROUND);
    }

    @Override
    public void pauseBackground() {
        SoundPlayer.getInstance().pauseSound(SoundsEnumeration.GLADES_BACKGROUND);
    }

    @Override
    public void resumeBackground() {
        SoundPlayer.getInstance().resumeSound(SoundsEnumeration.GLADES_BACKGROUND);
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
