package com.monja.game.maze;

import java.util.ArrayList;

public class MazeHistory {

    private int mazeWidth;
    private int mazeHeight;

    private ArrayList<Maze.Cell[]> mazeHistory;

    public MazeHistory(int mazeWidth, int mazeHeight) {
        this.mazeWidth = mazeWidth;
        this.mazeHeight = mazeHeight;
        mazeHistory = new ArrayList<>();
    }

    public void pushCellsIntoHistory(Maze.Cell[] cells) {
        mazeHistory.add(cells);
    }

    public Maze.Cell[] getCellsFromHistory(int iteration) throws ArrayIndexOutOfBoundsException {
        return mazeHistory.get(iteration);
    }

    public int getHistorySize() {
        return mazeHistory.size();
    }

    public int getMazeWidth() {
        return mazeWidth;
    }

    public int getMazeHeight() {
        return mazeHeight;
    }
}
