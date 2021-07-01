package pages;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import images.ImageHandler;
import musics.MusicPlayer;
import object.Button;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class MainPage extends ShowPage {
    public MainPage(int w, int h) {
        super(w, h);

        new Thread(() -> repaint()).start();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel labels = new JPanel(new GridBagLayout());
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;

        Icon title = new ImageIcon(ImageHandler.getImage("game_title"));
        JLabel label = new JLabel(title);
        labels.add(label, gbc);

        gbc.insets = new Insets(25, 0, 55, 0);
        gbc.ipadx = 100;

        labels.setBackground(null);
        labels.setOpaque(false);
        this.add(labels, gbc);

        GridBagConstraints gbcButton = new GridBagConstraints();

        gbcButton.gridwidth = GridBagConstraints.REMAINDER;
        gbcButton.anchor = GridBagConstraints.CENTER;
        gbcButton.fill = GridBagConstraints.HORIZONTAL;
        gbcButton.insets.bottom = 15;

        JPanel buttons = new JPanel(new GridBagLayout());

        Icon muteIcon = new ImageIcon(ImageHandler.getImage("button_mute"));
        Button btnMute = new Button(muteIcon, e -> {
            this.mute();
            this.requestFocus();
        });
        buttons.add(btnMute, gbcButton);

        Icon startIcon = new ImageIcon(ImageHandler.getImage("button_start"));
        Button btnStart = new Button(startIcon, e -> this.startGame());
        buttons.add(btnStart, gbcButton);

        Icon exitIcon = new ImageIcon(ImageHandler.getImage("button_exit"));
        Button btnExit = new Button(exitIcon, e -> this.exit());
        buttons.add(btnExit, gbcButton);

        Icon changeBallIcon = new ImageIcon(ImageHandler.getImage("button_change-ball"));
        Button btnChangeBall = new Button(changeBallIcon, e -> this.changeBall());
        buttons.add(btnChangeBall, gbcButton);

        Icon helpIcon = new ImageIcon(ImageHandler.getImage("button_help"));
        Button btnHelp = new Button(helpIcon, e -> this.help());
        buttons.add(btnHelp, gbcButton);

        buttons.setBackground(null);
        buttons.setOpaque(false);
        this.add(buttons, gbcButton);

        addKeyListener(this);
    }

    public void startGame() {
        MusicPlayer.play("click");
        BufferedImage background = ImageHandler.getImage("background");
        Game game = new Game(panelW, panelH, background);
        PageController.addPanel("game", game);
        PageController.switchPanel("game");
    }

    private void changeBall() {
        MusicPlayer.play("click");
        PageController.switchPanel("changeBall");
    }

    public void mute() {
        MusicPlayer.play("click");
        MusicPlayer.mute();
    }

    public void exit() {
        MusicPlayer.play("click");
        System.exit(0);
    }

    public void help() {
        MusicPlayer.play("click");
        PageController.showPanel("help");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_S:
                this.startGame();
                break;
            case KeyEvent.VK_M:
                this.mute();
                break;
            case KeyEvent.VK_E:
                this.exit();
                break;
            case KeyEvent.VK_C:
                this.changeBall();
                break;
            case KeyEvent.VK_H:
                this.help();
                break;
        }
    }
}