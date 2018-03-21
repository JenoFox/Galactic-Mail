package galacticmail;

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sounds {

    private Clip clip;
    // Gets sound from the system directory
    public Sounds(String Filename) {
        File file = new File(Filename);
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(file));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    // Plays the sound continuously 
    public void play() {
        if (clip == null) {
            return;
        }
        stop();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    // Plays the sound once
    public void playonce() {
        if (clip == null) {
            return;
        }
        stop();
        clip.start();
    }
    // Stops the sound being played
    public void stop() {
        if (clip.isRunning() && clip != null) {
            clip.stop();
        }
    }
    // Closes the sound file
    public void close() {
        stop();
        clip.close();
    }
}
