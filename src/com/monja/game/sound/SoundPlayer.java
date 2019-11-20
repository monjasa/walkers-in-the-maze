package com.monja.game.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.io.BufferedInputStream;
import java.util.HashMap;


public class SoundPlayer {

    private static SoundPlayer instance;

    private HashMap<SoundsEnumeration, Clip> clips = new HashMap<>();
    private HashMap<SoundsEnumeration, Integer> pausedClips = new HashMap<>();

    private SoundPlayer() {
        init();
    }

    public static SoundPlayer getInstance() {
        if (instance == null) {
            instance = new SoundPlayer();
        }

        return instance;
    }

    private void init() {
        loadSound(SoundsEnumeration.GLADES_BACKGROUND, "/sounds/glades_soundtrack.wav");
        loadSound(SoundsEnumeration.FOREST_BACKGROUND, "/sounds/forest_soundtrack.wav");
        loadSound(SoundsEnumeration.FINISH, "/sounds/finish.wav");
        loadSound(SoundsEnumeration.MENU_NAVIGATION, "/sounds/menu_navigate.wav");
        loadSound(SoundsEnumeration.MENU_SELECTION, "/sounds/menu_select.wav");
        loadSound(SoundsEnumeration.PAUSE_ACTIVATION, "/sounds/pause_activate.wav");
        loadSound(SoundsEnumeration.PAUSE_DEACTIVATION, "/sounds/pause_deactivate.wav");
    }

    private void loadSound(SoundsEnumeration name, String resourcePath) {
        try {
            BufferedInputStream inputStream = new BufferedInputStream(SoundPlayer.class.getResourceAsStream(resourcePath));
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputStream);

            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.setLoopPoints(0, -1);
            clips.put(name, clip);

            audioInputStream.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean playSound(SoundsEnumeration name) {
        if (clips.containsKey(name)) {
            clips.get(name).setFramePosition(0);
            clips.get(name).start();

            return true;
        }

        return false;
    }

    public boolean stopSound(SoundsEnumeration name) {
        if (clips.containsKey(name) && clips.get(name).isActive()) {
            clips.get(name).stop();
            clips.get(name).setFramePosition(0);
            return true;
        }

        return false;
    }

    public boolean pauseSound(SoundsEnumeration name) {
        if (clips.containsKey(name) && clips.get(name).isActive()) {
            int framePosition = clips.get(name).getFramePosition();
            pausedClips.put(name, framePosition);
            clips.get(name).stop();
            return true;
        }

        return false;
    }

    public boolean resumeSound(SoundsEnumeration name) {
        if (pausedClips.containsKey(name) && !clips.get(name).isActive()) {
            int framePosition = pausedClips.get(name);
            clips.get(name).setFramePosition(framePosition);
            clips.get(name).start();

            return true;
        }

        return false;
    }

    public boolean isPlaying(SoundsEnumeration name) {
        if (clips.containsKey(name) && clips.get(name).isRunning()) {
            return true;
        }

        return false;
    }

    public long getPosition(SoundsEnumeration name) {
        if (clips.containsKey(name) && clips.get(name).isRunning()) {
            return clips.get(name).getLongFramePosition();
        }

        return -1;
    }
}
