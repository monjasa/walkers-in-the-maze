package com.monja.game.maze;

public class Maze {

    public int width;
    public int height;
    private int xExit;
    private int yExit;
    private Cell[] cells;

    public enum Cell {
        WALL,
        PATH
    }

    public Maze(int width, int height) {
        this.width = width;
        this.height = height;
        cells = new Cell[width * height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                cells[x + y * width] = Cell.PATH;
            }
        }

        for (int y = 0; y < height; y++) {
            cells[y * width] = Cell.WALL;
            cells[(width - 1) + y * width] = Cell.WALL;
        }

        for (int x = 1; x < width - 1; x++) {
            cells[x] = Cell.WALL;
            cells[x + (height - 1) * width] = Cell.WALL;
        }
    }

    public Cell[] getCellsArray() {
        return cells;
    }

    public String cellsArrayToString() {
        StringBuilder builder = new StringBuilder();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                switch (getCell(x, y)) {
                    case WALL:
                        builder.append("1");
                        break;
                    case PATH:
                        builder.append("0");
                        break;
                }
            }

            builder.append('\n');
        }

        builder.append('\n');
        return builder.toString();
    }

    public Cell getCell(int x, int y) {
        return cells[x + y * width];
    }

    public void setCell(int x, int y, Cell type) {
        cells[x + y * width] = type;
    }

    public void setExit(int xExit, int yExit) {
        this.xExit = xExit;
        this.yExit = yExit;
    }

    public int getExitX() {
        return xExit;
    }

    public int getExitY() {
        return yExit;
    }
}
