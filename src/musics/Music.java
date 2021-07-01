package musics;

import java.nio.file.Paths;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Music {
    private Clip clip;
    private String path;
    private double volume = 0.3;

    public Music(String path) {
        this.path = path;
        setClip();
    }

    private void setClip() {
        try {
            this.clip = AudioSystem.getClip();
            this.clip.open(AudioSystem.getAudioInputStream(Paths.get(this.path).toFile()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setVolume(this.volume);
    }

    public void play() {
        this.setClip();
        this.clip.start();
    }

    public void playAfterFinish() {
        if (this.clip.isRunning())
            return;
        this.setClip();
        this.clip.start();
    }

    public void playBackground() {
        this.setClip();
        this.clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void setVolume(double volume) {
        this.volume = volume;
        FloatControl gainControl = (FloatControl) this.clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20 * (float) Math.log(this.volume));
    }

    public void mute() {
        FloatControl gainControl = (FloatControl) this.clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20 * (float) Math.log(0));
    }

    public void unmute() {
        FloatControl gainControl = (FloatControl) this.clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20 * (float) Math.log(this.volume));
    }
}