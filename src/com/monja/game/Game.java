package com.monja.game;

import com.monja.game.entities.Actor;
import com.monja.game.entities.Player;
import com.monja.game.gfx.Screen;
import com.monja.game.gfx.SpriteSheet;
import com.monja.game.level.Level;
import com.monja.game.level.locations.LocationStrategy;
import com.monja.game.sound.SoundPlayer;
import com.monja.game.sound.SoundsEnumeration;
import com.monja.game.states.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Game extends Canvas implements Runnable {

    private static final double RESOLUTION =
            Toolkit.getDefaultToolkit().getScreenSize().getWidth() / Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    private static final int WIDTH = 400;
    private static final int HEIGHT = (int) (WIDTH / RESOLUTION);
    private static final int SCALE = 1;
    private static final String NAME = "Walkers In The Maze";
    public static Game game;

    private JFrame frame;

    private static Thread gameThread;
    public int tickCount = 0;

    public boolean isRunning = false;

    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    private int[] colours = new int[6 * 6 * 6];

    public long startTime = 0;
    public long endTime = 0;
    public long pauseStartTime = 0;
    public long pauseEndTime = 0;

    private State state;
    private int selectedOption;
    private int selectedDifficulty;
    private int selectedLocation;

    private LocationStrategy locationStrategy;

    private Screen screen;
    private InputHandler input;
    private SoundPlayer soundPlayer;
    private Level level;
    private Actor actor;

    public Game() {
        setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

        soundPlayer = SoundPlayer.getInstance();

        frame = new JFrame(NAME);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setLayout(new BorderLayout());

        frame.add(this, BorderLayout.CENTER);
        frame.pack();

        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        game = new Game();
        game.start();

        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void start() {
        isRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public synchronized void stop() {
        endTime = System.currentTimeMillis();
        locationStrategy.pauseBackground();
        soundPlayer.playSound(SoundsEnumeration.FINISH);

        changeState(StateClient.getState(StatesEnumeration.FINISH));
    }

    public void setupColours() {
        int index = 0;
        for (int r = 0; r < 6; r++) {
            for (int g = 0; g < 6; g++) {
                for (int b = 0; b < 6; b++) {
                    int rr = (r * 255 / 5);
                    int gg = (g * 255 / 5);
                    int bb = (b * 255 / 5);

                    colours[index++] = rr << 16 | gg << 8 | bb;
                }
            }
        }
    }

    public void initializeLevel() {
        setupLevelAndActor();
        startTime = System.currentTimeMillis();
        pauseStartTime = 0;
        pauseEndTime = 0;
    }

    public void setupLevelAndActor() {
        int mazeSize = GameCreationMenuState.getDifficulty(selectedDifficulty).getSize();
        level = new Level(locationStrategy, mazeSize * 2 + 1, mazeSize);
        actor = new Player(level, 20, 20, input);
        level.addEntity(actor);
    }

    public void setupLevelAndActor(Level level, Actor actor) {
        this.level = level;
        this.actor = actor;
    }

    public void setupCreationTool() {
        int mazeSize = CreationToolMenuState.getDifficulty(selectedDifficulty).getSize();
        ((CreationToolState) StateClient.getState(StatesEnumeration.CREATION_TOOL)).initializeMazeHistory(mazeSize * 2 + 1, mazeSize);
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D / 60D;

        int ticks = 0;
        int frames = 0;

        long lastTimer = System.currentTimeMillis();
        double delta = 0;

        screen = new Screen(WIDTH, HEIGHT, new SpriteSheet("res/sprite_sheet.png"));
        setupColours();
        input = new InputHandler(this);

        selectedOption = 0;
        selectedDifficulty = 1;
        selectedLocation = 0;

        locationStrategy = GameCreationMenuState.getLocation(selectedLocation).getLocationStrategy();
        locationStrategy.playBackground();

        StateClient.initializeStates(this);
        state = StateClient.getState(StatesEnumeration.MENU);
        level = new Level(locationStrategy);

        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            boolean shouldRender = true;

            while (delta >= 1) {
                ticks++;
                state.tick();
                delta -= 1;
                shouldRender = true;
            }

            try {
                Thread.sleep(2);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }

            if (shouldRender) {
                frames++;
                state.render();
            }

            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;
                System.out.println(state.getClass().getSimpleName());
                System.out.println(ticks + " ticks, " + frames + " frames");
                frames = 0;
                ticks = 0;
            }
        }
    }

    public BufferStrategy prepareBufferStrategy() {
        BufferStrategy strategy = getBufferStrategy();
        if (strategy == null) {
            createBufferStrategy(3);
            return getBufferStrategy();
        }

        return strategy;
    }

    public void displayGraphic() {
        BufferStrategy strategy = prepareBufferStrategy();

        for (int y = 0; y < screen.height; y++) {
            for (int x = 0; x < screen.width; x++) {
                int colourCode = screen.pixels[x + y * screen.width];
                if (colourCode < 255) pixels[x + y * WIDTH] = colours[colourCode];
            }
        }

        Graphics g = strategy.getDrawGraphics();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

        g.dispose();
        strategy.show();
    }

    public void changeLocationStrategy(LocationStrategy locationStrategy) {
        this.locationStrategy.stopBackground();
        this.locationStrategy = locationStrategy;
        this.locationStrategy.playBackground();

        level = new Level(locationStrategy);
    }

    public void changeState(State state) {
        this.state = state;
    }

    public void closeApplication() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    public InputHandler getInput() {
        return input;
    }

    public Actor getActor() {
        return actor;
    }

    public Screen getScreen() {
        return screen;
    }

    public int[] getColours() {
        return colours;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Level getLevel() {
        return level;
    }

    public int getSelectedLocation() {
        return selectedLocation;
    }

    public void setSelectedLocation(int selectedLocation) {
        this.selectedLocation = selectedLocation;
    }

    public int getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(int selectedOption) {
        this.selectedOption = selectedOption;
    }

    public int getSelectedDifficulty() {
        return selectedDifficulty;
    }

    public void setSelectedDifficulty(int selectedDifficulty) {
        this.selectedDifficulty = selectedDifficulty;
    }

    public LocationStrategy getLocationStrategy() {
        return locationStrategy;
    }
}
