package pages;

import javax.swing.JPanel;

import images.ImageHandler;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import musics.MusicPlayer;
import object.Button;

public class Setting extends ShowPage {
    public Setting(int w, int h) {
        super(w, h);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel labels = new JPanel(new GridBagLayout());
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;

        Icon settingLabel = new ImageIcon(ImageHandler.getImage("game_setting"));
        JLabel label = new JLabel(settingLabel);
        labels.add(label);

        gbc.insets = new Insets(40, 0, 10, 0);
        gbc.ipadx = 100;
        labels.setBackground(null);
        labels.setOpaque(false);
        this.add(labels, gbc);

        GridBagConstraints gbcButton = new GridBagConstraints();
        gbcButton.gridwidth = GridBagConstraints.REMAINDER;
        gbcButton.anchor = GridBagConstraints.CENTER;
        gbcButton.insets.bottom = 15;

        JPanel buttons = new JPanel(new GridBagLayout());

        Icon backIcon = new ImageIcon(ImageHandler.getImage("button_back"));
        Button btnGame = new Button(backIcon, e -> this.back());

        Icon restartIcon = new ImageIcon(ImageHandler.getImage("button_restart"));
        Button btnRestart = new Button(restartIcon, e -> this.restart());

        Icon menuIcon = new ImageIcon(ImageHandler.getImage("button_menu"));
        Button btnMain = new Button(menuIcon, e -> this.backToMainPage());

        Icon muteIcon = new ImageIcon(ImageHandler.getImage("button_mute"));
        Button btnMute = new Button(muteIcon, e -> {
            this.mute();
            this.requestFocus();
        });

        Icon helpIcon = new ImageIcon(ImageHandler.getImage("button_help"));
        Button btnHelp = new Button(helpIcon, e -> this.help());

        buttons.add(btnGame, gbcButton);
        buttons.add(btnRestart, gbcButton);
        buttons.add(btnMain, gbcButton);
        buttons.add(btnMute, gbcButton);
        buttons.add(btnHelp, gbcButton);

        buttons.setBackground(null);
        buttons.setOpaque(false);
        gbcButton.gridy = 1;
        gbcButton.insets = new Insets(20, 0, 30, 0);

        this.add(buttons, gbcButton);

        addKeyListener(this);
        this.setVisible(false);
    }

    public void restart() {
        MusicPlayer.play("click");
        BufferedImage background = ImageHandler.getImage("background");
        Game game = new Game(panelW, panelH, background);
        PageController.addPanel("game", game);
        PageController.switchPanel("game");

    }

    public void backToMainPage() {
        MusicPlayer.play("click");
        PageController.removePanel("game");
        PageController.switchPanel("main");
    }

    public void back() {
        MusicPlayer.play("click");
        PageController.switchPanel("game");
    }

    public void mute() {
        MusicPlayer.play("click");
        MusicPlayer.mute();
    }

    public void help() {
        MusicPlayer.play("click");
        PageController.showPanel("help");
    }

    public void sound(MusicPlayer musicPlayer) {
        // TODO

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_B:
                this.back();
                break;
            case KeyEvent.VK_Q:
                this.backToMainPage();
                break;
            case KeyEvent.VK_R:
                this.restart();
                break;
            case KeyEvent.VK_M:
                this.mute();
                break;
            case KeyEvent.VK_H:
                this.help();
                break;
        }
    }
}
