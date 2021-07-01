import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Dimension;

import musics.MusicPlayer;
import pages.*;
import config.Config;
import images.ImageHandler;

public class Main {
    public static void main(String[] args) {
        try {
            MusicPlayer.addMusicFromDirectory(Config.musicDirectory);
            ImageHandler.addImageFromDirectory(Config.imageDirectory);
            ;
        } catch (Exception err) {
        }
        MusicPlayer.playBackground("theme");

        JFrame frame = setFrame();
        MainPage mainPage = new MainPage(Config.pageWidth, Config.pageHeight);
        Setting setting = new Setting(Config.pageWidth, Config.pageHeight);
        Help help = new Help(Config.pageWidth, Config.pageHeight);
        ChangeBall changeBall = new ChangeBall(Config.pageWidth, Config.pageHeight);
        HashMap<String, ShowPage> pages = new HashMap<String, ShowPage>() {
            {
                put("main", mainPage);
                put("setting", setting);
                put("help", help);
                put("changeBall", changeBall);
            }
        };
        PageController.init(frame, pages);
        PageController.switchPanel("main");
    }

    private static JFrame setFrame() {
        String title = "LH-TAN";
        JFrame frame = new JFrame(title);

        Dimension actualsize = getActualSize(title, new Dimension(Config.pageWidth, Config.pageHeight));
        frame.setSize(actualsize);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        return frame;
    }

    private static Dimension getActualSize(String title, Dimension d) {
        JFrame tempFrame = new JFrame(title);
        JPanel tempPanel = new JPanel();
        tempPanel.setSize(d);
        tempFrame.setSize(d);
        tempFrame.add(tempPanel);

        tempFrame.setVisible(true);
        Dimension actualSize = tempFrame.getContentPane().getSize();
        tempFrame.setVisible(false);
        tempFrame.dispose();
        int extraWidth = d.width - actualSize.width;
        int extraHeight = d.height - actualSize.height;
        System.out.printf("extra: %d %d\n", extraWidth, extraHeight);

        return new Dimension(d.width + extraWidth, d.height + extraHeight);
    }
}