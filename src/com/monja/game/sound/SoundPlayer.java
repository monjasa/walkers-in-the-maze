package com.monja.game.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import java.io.BufferedInputStream;
import java.util.HashMap;
import java.util.Map;


public class SoundPlayer {

    private static SoundPlayer instance;
    private static float volume;

    private HashMap<SoundsEnumeration, Clip> clips = new HashMap<SoundsEnumeration, Clip>();
    private HashMap<SoundsEnumeration, Integer> pausedClips = new HashMap<SoundsEnumeration, Integer>();

    private SoundPlayer() {
        init();
    }

    public static SoundPlayer getInstance() {
        if (instance == null) {
            instance = new SoundPlayer();
            volume = 0.0f;
        }

        return instance;
    }

    private void init() {
        loadSound(SoundsEnumeration.GLADES_BACKGROUND, "/sounds/glades_soundtrack.wav");
        loadSound(SoundsEnumeration.FOREST_BACKGROUND, "/sounds/forest_soundtrack.wav");
        loadSound(SoundsEnumeration.MENU_NAVIGATION, "/sounds/menu_navigate.wav");
        loadSound(SoundsEnumeration.MENU_SELECTION, "/sounds/menu_select.wav");
        loadSound(SoundsEnumeration.PAUSE_ACTIVATION, "/sounds/pause_activate.wav");
        loadSound(SoundsEnumeration.PAUSE_DEACTIVATION, "/sounds/pause_deactivate.wav");
        loadSound(SoundsEnumeration.MAZE_BUILDING, "/sounds/maze_building.wav");
        loadSound(SoundsEnumeration.MAZE_DESTROYING, "/sounds/maze_destroying.wav");
        loadSound(SoundsEnumeration.MAZE_REBUILDING, "/sounds/maze_rebuilding.wav");
        loadSound(SoundsEnumeration.FINISH, "/sounds/end_sound.wav");
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

    public void increaseVolume() {
        for (Map.Entry<SoundsEnumeration, Clip> entry : clips.entrySet()) {
            FloatControl gainControl = (FloatControl) entry.getValue().getControl(FloatControl.Type.MASTER_GAIN);
            volume = volume + 0.75f > gainControl.getMaximum() ? gainControl.getMaximum() : volume + 0.75f;
            gainControl.setValue(volume);
        }
    }

    public void decreaseVolume() {
        for (Map.Entry<SoundsEnumeration, Clip> entry : clips.entrySet()) {
            FloatControl gainControl = (FloatControl) entry.getValue().getControl(FloatControl.Type.MASTER_GAIN);
            volume = volume - 0.75f < gainControl.getMinimum() ? gainControl.getMinimum() : volume - 0.75f;
            gainControl.setValue(volume);
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

    public boolean playSoundContinuously(SoundsEnumeration name) {
        if (clips.containsKey(name)) {
            clips.get(name).setFramePosition(0);
            clips.get(name).start();
            clips.get(name).loop(Clip.LOOP_CONTINUOUSLY);
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
