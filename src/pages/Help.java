package pages;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import config.Config;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import images.ImageHandler;
import musics.MusicPlayer;
import object.Button;
import object.GenObstacle;
import object.Obstacle;
import props.AddProp;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.awt.Graphics;

public class Help extends ShowPage {
    private int InfoXAxis = Config.InfoXAxis;
    private int ObstacleXAxis = Config.ObstacleXAxis;
    private double yAxis = Config.ObstacleYAxis;
    private double yInterval = Config.ObstacleYInterval;
    private int tipXAxis = Config.tipXAxis;
    private List<Obstacle> obstacles = new ArrayList<Obstacle>();;

    public Help(int w, int h) {
        super(w, h);

        new Thread(() -> repaint()).start();

        setLayout(new GridBagLayout());

        GridBagConstraints gbcButton = new GridBagConstraints();
        gbcButton.anchor = GridBagConstraints.NORTH;

        JPanel buttons = new JPanel(new GridBagLayout());

        Icon backIcon = new ImageIcon(ImageHandler.getImage("button_back").getScaledInstance(100, 20, 4));
        Button btnBack = new Button(backIcon, e -> this.back());

        gbcButton.insets = new Insets(Config.pageHeight - 25, Config.pageWidth - 100, 5, 15);
        buttons.add(btnBack);

        buttons.setBackground(null);
        buttons.setOpaque(false);
        this.add(buttons, gbcButton);

        addKeyListener(this);
        obstacles.add(new AddProp(new double[] { (double) ObstacleXAxis, yAxis }));
        yAxis += yInterval;
        drawObstacles();
    }

    public void back() {
        MusicPlayer.play("click");
        PageController.hidePanel();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_B) {
            this.back();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(Config.helpFont);
        g.drawString("Controlled by keyboard:", tipXAxis, 20);
        g.drawString("Press LEFT and RIGHT to control the shooting angle", InfoXAxis, 40);
        g.drawString("Press SPACE to shoot the ball", InfoXAxis, 60);

        g.drawString("Controlled by Mouse:", tipXAxis, 80);
        g.drawString("Click the mouse to set the angle. ", InfoXAxis, 100);
        g.drawString("When release, The ball will be shooted.", InfoXAxis, 120);

        for (Obstacle obstacle : this.obstacles) {
            obstacle.draw(g);
            g.setColor(Config.helpStringColor);
            g.setFont(Config.helpFont);
            int curHeight = Config.pageHeight - (int) obstacle.getPos()[1];
            String info = obstacle.info();
            for (int i = 0; i < info.length(); i += Config.charLimitNum) {
                g.drawString(info.substring(i, Math.min(info.length() - 1, i + Config.charLimitNum)), this.InfoXAxis,
                        curHeight);
                curHeight += g.getFontMetrics().getHeight();
            }
        }
    }

    public void drawObstacles() {
        GenObstacle obstacleGenerator = new GenObstacle(new ArrayList<Double>() {
            {
                add((double) ObstacleXAxis);
            }
        });
        Map<Double, Function<List<Object>, Obstacle>> mapping = obstacleGenerator.getObstacleMap();
        List<Double> keys = obstacleGenerator.getObstacleMapKey();

        for (double k : keys) {
            obstacles.add(mapping.get(k).apply(new ArrayList<Object>() {
                {
                    add((double) ObstacleXAxis);
                    add(yAxis);
                    add(87);
                    add(Config.BrickColor);
                }
            }));
            yAxis += yInterval;
        }
    }
}
