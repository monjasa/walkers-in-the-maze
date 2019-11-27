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
import java.util.Random;

public class DarkForestLocation implements LocationStrategy {

    private static Tile[] wallTiles = new Tile[4];
    private static Tile[] pathTiles = new Tile[4];
    private static Tile[] decoratedPathTiles = new Tile[4];
    private static Tile[] exitTiles = new Tile[4];

    private static final Tile FOREST_TREE_LU = new AnimatedBasicTile(14, new int[][] {{0, 6}, {2, 6}, {0, 6}, {4, 6}, {0, 6}},
            Colours.get(021, 000, 210, 013), 0xFF403020, 750, true);
    private static final Tile FOREST_TREE_RU = new AnimatedBasicTile(15, new int[][] {{1, 6}, {3, 6}, {1, 6}, {5, 6}, {1, 6}},
            Colours.get(021, 000, 210, 013), -1, 750, true);
    private static final Tile FOREST_TREE_LD = new AnimatedBasicTile(16, new int[][] {{0, 7}, {2, 7}, {0, 7}, {4, 7}, {0, 7}},
            Colours.get(021, 000, 210, 013), -1, 750, true);
    private static final Tile FOREST_TREE_RD = new AnimatedBasicTile(17, new int[][] {{1, 7}, {3, 7}, {1, 7}, {5, 7}, {1, 7}},
            Colours.get(021, 000, 210, 013), -1, 750, true);

    private static final Tile FOREST_GRASS_LU = new AnimatedBasicTile(18, new int[][] {{6, 6}, {8, 6}, {10, 6}, {6, 6}},
            Colours.get(013, 000, 121, -1), 0xFF002030, 750, false);
    private static final Tile FOREST_GRASS_RU = new AnimatedBasicTile(19, new int[][] {{7, 6}, {9, 6}, {11, 6}, {7, 6}},
            Colours.get(013, 000, 121, -1), -1, 750, false);
    private static final Tile FOREST_GRASS_LD = new AnimatedBasicTile(20, new int[][] {{6, 7}, {8, 7}, {10, 7}, {6, 7}},
            Colours.get(013, 000, 121, -1), -1, 750, false);
    private static final Tile FOREST_GRASS_RD = new AnimatedBasicTile(21, new int[][] {{7, 7}, {9, 7}, {11, 7}, {7, 7}},
            Colours.get(013, 000, 121, -1), -1, 750, false);

    private static final Tile FOREST_FIREFLY_LU = new AnimatedBasicTile(22, new int[][] {{6, 8}, {8, 8}, {10, 8}, {6, 8}},
            Colours.get(013, 000, 121, 043), 0xFF002090, 750, false);
    private static final Tile FOREST_FIREFLY_RU = new AnimatedBasicTile(23, new int[][] {{7, 8}, {9, 8}, {11, 8}, {7, 8}},
            Colours.get(013, 000, 121, 043), -1, 750, false);
    private static final Tile FOREST_FIREFLY_LD = new AnimatedBasicTile(24, new int[][] {{6, 9}, {8, 9}, {10, 9}, {6, 9}},
            Colours.get(013, 000, 121, 043), -1, 750, false);
    private static final Tile FOREST_FIREFLY_RD = new AnimatedBasicTile(25, new int[][] {{7, 9}, {9, 9}, {11, 9}, {7, 9}},
            Colours.get(013, 000, 121, 043), -1, 750, false);

    private static final Tile FOREST_EXIT_LU = new AnimatedActiveTile(26, new int[][] {{12, 6}, {14, 6}, {16, 6}},
            Colours.get(431, 000, 013, 043), 0xFF00FFFF, 750, false);
    private static final Tile FOREST_EXIT_RU = new AnimatedActiveTile(27, new int[][] {{13, 6}, {15, 6}, {17, 6}},
            Colours.get(431, 000, 013, 043), -1, 750, false);
    private static final Tile FOREST_EXIT_LD = new AnimatedActiveTile(28, new int[][] {{12, 7}, {14, 7}, {16, 7}},
            Colours.get(431, 000, 013, 043), -1, 750, false);
    private static final Tile FOREST_EXIT_RD = new AnimatedActiveTile(29, new int[][] {{13, 7}, {15, 7}, {17, 7}},
            Colours.get(431, 000, 013, 043), -1, 750, false);

    static {
        wallTiles[0] = FOREST_TREE_LU;
        wallTiles[1] = FOREST_TREE_RU;
        wallTiles[2] = FOREST_TREE_LD;
        wallTiles[3] = FOREST_TREE_RD;

        pathTiles[0] = FOREST_GRASS_LU;
        pathTiles[1] = FOREST_GRASS_RU;
        pathTiles[2] = FOREST_GRASS_LD;
        pathTiles[3] = FOREST_GRASS_RD;

        decoratedPathTiles[0] = FOREST_FIREFLY_LU;
        decoratedPathTiles[1] = FOREST_FIREFLY_RU;
        decoratedPathTiles[2] = FOREST_FIREFLY_LD;
        decoratedPathTiles[3] = FOREST_FIREFLY_RD;

        exitTiles[0] = FOREST_EXIT_LU;
        exitTiles[1] = FOREST_EXIT_RU;
        exitTiles[2] = FOREST_EXIT_LD;
        exitTiles[3] = FOREST_EXIT_RD;
    }

    @Override
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
                    Random random = new Random();
                    if (random.nextInt(8) == 0) {
                        tiles[(2 * x) + (2 * y) * width] = getDecoratedPathTile(0).getId();
                        tiles[(2 * x + 1) + (2 * y) * width] = getDecoratedPathTile(1).getId();
                        tiles[(2 * x) + (2 * y + 1) * width] = getDecoratedPathTile(2).getId();
                        tiles[(2 * x + 1) + (2 * y + 1) * width] = getDecoratedPathTile(3).getId();
                    } else {
                        tiles[(2 * x) + (2 * y) * width] = getPathTile(0).getId();
                        tiles[(2 * x + 1) + (2 * y) * width] = getPathTile(1).getId();
                        tiles[(2 * x) + (2 * y + 1) * width] = getPathTile(2).getId();
                        tiles[(2 * x + 1) + (2 * y + 1) * width] = getPathTile(3).getId();
                    }
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
            BufferedImage image = ImageIO.read(Level.class.getResource("/levels/level_forest.png"));
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
        SoundPlayer.getInstance().playSoundContinuously(SoundsEnumeration.FOREST_BACKGROUND);
    }

    @Override
    public void stopBackground() {
        SoundPlayer.getInstance().stopSound(SoundsEnumeration.FOREST_BACKGROUND);
    }

    @Override
    public void pauseBackground() {
        SoundPlayer.getInstance().pauseSound(SoundsEnumeration.FOREST_BACKGROUND);
    }

    @Override
    public void resumeBackground() {
        SoundPlayer.getInstance().resumeSound(SoundsEnumeration.FOREST_BACKGROUND);
    }

    @Override
    public Tile getWallTile(int quarter) {
        return wallTiles[quarter];
    }

    @Override
    public Tile getPathTile(int quarter) {
        return pathTiles[quarter];
    }

    public Tile getDecoratedPathTile(int quarter) {
        return decoratedPathTiles[quarter];
    }

    @Override
    public Tile getExitTile(int quarter) {
        return exitTiles[quarter];
    }
}
