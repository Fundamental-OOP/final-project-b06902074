package pages;

import javax.swing.JPanel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.awt.event.KeyEvent;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import images.ImageHandler;

import info.GameInfo;
import musics.MusicPlayer;
import object.Button;

public class Settlement extends ShowPage {
    private int point;

    public Settlement(int w, int h) {
        super(w, h);
        point = GameInfo.getPoints();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel labels = new JPanel(new GridBagLayout());
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;

        JLabel label = new JLabel("Game Over...");
        label.setFont(new Font("Serif", Font.PLAIN, 50));
        labels.add(label, gbc);

        gbc.insets = new Insets(20, 0, 0, 0);

        JLabel showpt = new JLabel(String.format("Your Score: %d", point));
        showpt.setFont(new Font("Serif", Font.PLAIN, 30));
        labels.add(showpt, gbc);

        gbc.insets = new Insets(0, 0, 20, 0);
        gbc.ipadx = 100;
        labels.setBackground(null);
        labels.setOpaque(false);
        this.add(labels, gbc);

        GridBagConstraints gbcButton = new GridBagConstraints();

        gbcButton.gridwidth = GridBagConstraints.REMAINDER;
        gbcButton.anchor = GridBagConstraints.CENTER;
        gbcButton.insets.bottom = 10;

        JPanel buttons = new JPanel(new GridBagLayout());

        Icon menuIcon = new ImageIcon(ImageHandler.getImage("button_menu"));
        Button btnMain = new Button(menuIcon, e -> this.backToMainPage());

        Icon exitIcon = new ImageIcon(ImageHandler.getImage("button_exit"));
        Button btnExit = new Button(exitIcon, e -> this.exit());

        Icon restartIcon = new ImageIcon(ImageHandler.getImage("button_restart"));
        Button btnRestart = new Button(restartIcon, e -> this.restart());

        buttons.add(btnRestart, gbcButton);
        buttons.add(btnExit, gbcButton);
        buttons.add(btnMain, gbcButton);

        buttons.setBackground(null);
        buttons.setOpaque(false);
        gbcButton.gridy = 1;

        gbcButton.insets = new Insets(20, 0, 60, 0);

        this.add(buttons, gbcButton);

        addKeyListener(this);
    }

    public void backToMainPage() {
        MusicPlayer.play("click");
        PageController.removePanel("game");
        PageController.switchPanel("main");
    }

    public void exit() {
        MusicPlayer.play("click");
        System.exit(0);
    }

    public void restart() {
        MusicPlayer.play("click");
        BufferedImage background = ImageHandler.getImage("background");
        Game game = new Game(panelW, panelH, background);
        PageController.addPanel("game", game);
        PageController.switchPanel("game");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_Q) {
            backToMainPage();
        } else if (e.getKeyCode() == KeyEvent.VK_E) {
            this.exit();
        } else if (e.getKeyCode() == KeyEvent.VK_R) {
            this.restart();
        }
    }
}