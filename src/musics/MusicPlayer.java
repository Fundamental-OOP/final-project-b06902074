package musics;

import java.util.HashMap;
import java.util.Map;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class MusicPlayer {
    private static Map<String, Music> musics = new HashMap<String, Music>();

    private static Boolean isMuted = false;

    public static void play(String musicName) {
        if (!isMuted) {
            Music music = musics.get(musicName);
            music.play();
        }
    }

    public static void playAfterFinish(String musicName) {
        if (!isMuted) {
            Music music = musics.get(musicName);
            music.playAfterFinish();
        }
    }

    public static void playBackground(String musicName) {
        Music music = musics.get(musicName);
        music.playBackground();
    }

    public static void addMusic(String musicName, Music music) {
        musics.put(musicName, music);
    }

    public static void setMusicVolume(String musicName, double volume) {
        MusicPlayer.musics.get(musicName).setVolume(volume);
    }

    public static void mute() {
        musics.values().forEach(music -> {
            if (isMuted) {
                music.unmute();
            } else {
                music.mute();
            }
        });
        isMuted = !isMuted;
    }

    public static void addMusicFromDirectory(String dirName) throws IOException {
        Files.list(new File(dirName).toPath()).forEach(path -> {
            try {
                String fileName = path.getFileName().toString();
                if (fileName.endsWith(".wav")) {
                    fileName = fileName.substring(0, fileName.lastIndexOf("."));
                    Music music = new Music(path.toString());
                    musics.put(fileName, music);
                }
            } catch (Exception err) {
            }
        });
    }
}
