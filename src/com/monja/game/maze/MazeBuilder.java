package com.monja.game.maze;

import com.monja.game.level.tiles.Tile;

import java.util.Random;

import static com.monja.game.maze.Maze.Cell.PATH;
import static com.monja.game.maze.Maze.Cell.WALL;

public class MazeBuilder {

    public static Maze getMazeInTiles(int width, int height) {
        Maze maze = new Maze(width, height);
        recursiveDivision(maze, width, height, 0, 0);
        burrowExit(maze, width, height);
        return maze;
    }

    private static void recursiveDivision(Maze maze, int width, int height, int xOffset, int yOffset) {

        Random random = new Random();
        boolean isHorizontal;

        if (width < height) isHorizontal = true;
        else if (width > height) isHorizontal = false;
        else isHorizontal = random.nextBoolean();

        int nextWidth, nextHeight;
        int xOffsetPair, yOffsetPair;
        int nextWidthPair, nextHeightPair;

        if (isHorizontal) {
            if (height < 5) return;

            int indexWall = yOffset + getRandomElement(2, height - 3) / 2 * 2;      // the wall is on an even coordinate
            int indexPath = xOffset + getRandomElement(1, width - 2) / 2 * 2 + 1;   // the hole is on an odd coordinate
            buildWall(maze, indexWall, width, xOffset, true);
            buildPath(maze, indexPath, indexWall, true);

            nextWidth = width;
            nextHeight = indexWall - yOffset + 1;

            xOffsetPair = xOffset;
            yOffsetPair = indexWall;
            nextWidthPair = width;
            nextHeightPair = height + yOffset - indexWall;

        } else {
            if (width < 5) return;

            int indexWall = xOffset + getRandomElement(2, width - 3) / 2 * 2;
            int indexPath = yOffset + getRandomElement(1, height - 2) / 2 * 2 + 1;
            buildWall(maze, indexWall, height, yOffset, false);
            buildPath(maze, indexPath, indexWall, false);

            nextWidth = indexWall - xOffset + 1;
            nextHeight = height;

            xOffsetPair = indexWall;
            yOffsetPair = yOffset;
            nextWidthPair = width + xOffset - indexWall;
            nextHeightPair = height;
        }

        recursiveDivision(maze, nextWidth, nextHeight, xOffset, yOffset);
        recursiveDivision(maze, nextWidthPair, nextHeightPair, xOffsetPair, yOffsetPair);
    }

    private static void buildWall(Maze maze, int indexWall, int length, int offset, boolean isHorizontal) {
        if (isHorizontal) {
            for (int x = offset; x < (length + offset - 1); x++)
                maze.setCell(x, indexWall, Maze.Cell.WALL);
        } else {
            for (int y = offset; y < (length + offset - 1); y++)
                maze.setCell(indexWall, y, Maze.Cell.WALL);
        }
    }

    private static void buildPath(Maze maze, int indexPath, int indexWall, boolean isHorizontal) {
        if (isHorizontal) {
            maze.setCell(indexPath, indexWall, Maze.Cell.PATH);
        } else {
            maze.setCell(indexWall, indexPath, Maze.Cell.PATH);
        }
    }

    private static void burrowExit(Maze maze, int width, int height) {
        Random random = new Random();

        while (true) {
            boolean isOnHorizontalWall = random.nextBoolean();
            int pathIndex = isOnHorizontalWall ?
                    getRandomElement(1, width - 2) : getRandomElement(1, height - 2);
            int wallIndex = isOnHorizontalWall ? height - 1 : width - 1;

            buildPath(maze, pathIndex, wallIndex, isOnHorizontalWall);

            if (isOnHorizontalWall) {
                if (maze.getCell(pathIndex, wallIndex - 1).equals(Maze.Cell.PATH)) {
                    maze.setExit(pathIndex, wallIndex);
                    break;
                } else {
                    maze.setCell(pathIndex, wallIndex, Maze.Cell.WALL);
                }
            } else {
                if (maze.getCell(wallIndex - 1, pathIndex).equals(Maze.Cell.PATH)) {
                    maze.setExit(wallIndex, pathIndex);
                    break;
                } else {
                    maze.setCell(wallIndex, pathIndex, Maze.Cell.WALL);
                }
            }
        }
    }

    private static int getRandomElement(int leftInclusive, int rightInclusive) {
        Random random = new Random();
        return random.nextInt(rightInclusive - leftInclusive + 1) + leftInclusive;
    }
}
