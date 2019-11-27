package com.monja.game.level;

import com.monja.game.entities.Entity;
import com.monja.game.gfx.Screen;
import com.monja.game.level.locations.LocationStrategy;
import com.monja.game.level.tiles.Tile;
import com.monja.game.maze.Maze;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Level {

    private byte[] tiles;
    private int width;
    private int height;
    private List<Entity> entities = new ArrayList<Entity>();
    //private String imagePath;
    private BufferedImage image;

    public Level(LocationStrategy locationStrategy) {
        locationStrategy.loadLevelBackground(this);
    }

    public Level(LocationStrategy locationStrategy, int mazeWidth, int mazeHeight) {
        locationStrategy.loadLevelFromMaze(this, mazeWidth, mazeHeight);
    }

    public Level(LocationStrategy locationStrategy, int mazeWidth, int mazeHeight, Maze.Cell[] cells) {
        locationStrategy.loadLevelFromCells(this, mazeWidth, mazeHeight, cells);
    }

    /*private void saveLevelToFile() {
        try {
            ImageIO.write(image, "png", new File(Level.class.getResource(this.imagePath).getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public void tick() {
        for (Entity e : entities) {
            e.tick();
        }

        for (Tile t : Tile.tiles) {
            if (t == null) {
                break;
            }

            t.tick();
        }
    }

    public void renderTiles(Screen screen, int xOffset, int yOffset) {
        if (xOffset < 0) xOffset = 0;
        if (xOffset > ((width << 3) - screen.width)) xOffset = ((width << 3) - screen.width);

        if (yOffset < 0) yOffset = 0;
        if (yOffset > ((height << 3) - screen.height)) yOffset = ((height << 3) - screen.height);

        if(screen.width > width * 8)
            xOffset = (screen.width - (width * 8)) / 2 * -1;
        if(screen.height > height * 8)
            yOffset = (screen.height - (height * 8)) / 2 * -1;
        screen.setOffset(xOffset, yOffset);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                getTile(x, y).render(screen, this, x << 3, y << 3);
            }
        }
    }

    public void renderEntities(Screen screen) {
        for (Entity e : entities) {
            e.render(screen);
        }
    }

    public Tile getTile(int x, int y) {
        if (0 > x || x >= width || 0 > y || y >= height) return Tile.VOID;
        return Tile.tiles[tiles[x + y * width]];
    }

    public void addEntity(Entity entity) {
        this.entities.add(entity);
    }

    public byte[] getTiles() {
        return tiles;
    }

    public void setTiles(byte[] tiles) {
        this.tiles = tiles;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
