package pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.image.BufferedImage;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Image;

import config.Config;

import props.RandomShootProp;
import controller.KeyboardController;
import controller.MouseController;
import event.EventHandler;
import images.ImageHandler;
import info.GameInfo;
import musics.MusicPlayer;
import object.Ball;
import object.Base;
import object.Brick;
import object.Button;
import object.Character;
import object.Obstacle;
import object.GenObstacle;
import object.Wall;

public class Game extends ShowPage {
    private List<Ball> balls = new ArrayList<Ball>();
    private List<Obstacle> obstacles = new ArrayList<Obstacle>();
    private KeyboardController keyboardController = new KeyboardController();
    private MouseController mouseController = new MouseController();
    private boolean drawTrajectory = true;
    private Map<Double, Integer> horizontalLaserLife = new HashMap<Double, Integer>();
    private Map<Double, Integer> verticalLaserLife = new HashMap<Double, Integer>();
    private int noCollisionNum = 0;
    private GenObstacle genObstacle;
    private JLabel level;
    private JLabel ballNumLabel;
    private Character character;

    public Game(int w, int h, BufferedImage background) {
        super(w, h, background);
        GameInfo.init();

        this.character = new Character();

        registEvent();
        registLisener();

        addSingleBall();
        initObstacle();
        initLabel();
    }

    private void registEvent() {
        EventHandler.register("Shoot", pos -> this.shoot());
        EventHandler.register("DrawTrajectory", pos -> this.repaint());
        EventHandler.register("HorizontalLaser", pos -> this.invokeLaser(true, pos));
        EventHandler.register("VerticalLaser", pos -> this.invokeLaser(false, pos));
    }

    private void registLisener() {
        this.addKeyListener(this);
        this.addKeyListener(this.keyboardController);
        this.addMouseListener(this.mouseController);
        this.addMouseMotionListener(this.mouseController);

    }

    private void initObstacle() {
        this.obstacles.add(new Wall(Color.BLACK, Config.wallUp, Config.wallLeft, Config.wallRight));
        this.obstacles.add(new Base());

        List<Double> xAxis = new ArrayList<Double>();
        for (int i = 0; i < Config.brickColumnNum; i++) {
            xAxis.add(Config.brickLeftmost + i * (Config.brickBrickHorizontalInterval + Config.brickSize[0]));
        }
        this.genObstacle = new GenObstacle(xAxis);

        double yAxis = Config.brickUpmost;
        this.obstacles.addAll(this.genObstacle.generateRow(GameInfo.getLevel(), yAxis));
    }

    private void initLabel() {
        this.setLayout(new GridBagLayout());
        FontMetrics levelMetrics = this.getFontMetrics(Config.levelLabelFont);
        FontMetrics ballNumMetrics = this.getFontMetrics(Config.ballNumLabelFont);
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel above = new JPanel(new GridBagLayout());

        Icon settingIcon = new ImageIcon(ImageHandler.getImage("setting").getScaledInstance(levelMetrics.getHeight(),
                levelMetrics.getHeight(), Image.SCALE_SMOOTH));
        Button settingButton = new Button(settingIcon, e -> this.setting());
        gbc.insets = new Insets(0, 0, 0,
                (int) (Config.pageWidth - 2 * levelMetrics.getHeight() - Config.pageWidth / 2));
        above.add(settingButton, gbc);

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        this.level = new JLabel("");
        this.level.setFont(Config.levelLabelFont);
        this.level.setForeground(Color.WHITE);
        this.level.setOpaque(false);
        this.updateLevelLabel();

        gbc.insets = new Insets(0, 0, 0, Config.pageWidth / 2 - levelMetrics.getHeight());
        above.add(this.level, gbc);

        gbc.insets = new Insets(0, 0,
                (int) (Config.pageHeight - Config.wallVerticalMargin - Config.base + ballNumMetrics.getAscent()), 0);
        above.setOpaque(false);
        this.add(above, gbc);

        JPanel bottom = new JPanel(new GridBagLayout());
        gbc.insets = new Insets(0, 0, (int) (Config.base - ballNumMetrics.getAscent()), 0);

        this.ballNumLabel = new JLabel("");
        this.ballNumLabel.setFont(Config.ballNumLabelFont);
        this.ballNumLabel.setForeground(Color.WHITE);
        this.ballNumLabel.setOpaque(false);
        this.updateBallNumLabel(1);

        bottom.add(this.ballNumLabel, gbc);
        gbc.insets = new Insets(0, 0, 0, 0);

        bottom.setOpaque(false);
        this.add(bottom, gbc);
        this.setOpaque(false);
    }

    private void updateLevelLabel() {
        String level = String.valueOf(GameInfo.getLevel());
        this.level.setText(level);
    }

    private void updateBallNumLabel(int num) {
        String ballNum = "x " + String.valueOf(num);
        this.ballNumLabel.setText(ballNum);
    }

    private void invokeLaser(Boolean horizontal, Double pos) {
        for (Obstacle obstacle : this.obstacles) {
            double obstaclePos = horizontal ? obstacle.getPos()[1] : obstacle.getPos()[0];
            if (obstaclePos == pos) {
                if (obstacle instanceof Brick) {
                    Brick brick = (Brick) obstacle;
                    brick.minusCount();
                }
            }
        }
        if (horizontal) {
            this.horizontalLaserLife.put(pos, Config.laserLife);
        } else {
            this.verticalLaserLife.put(pos, Config.laserLife);
        }
        repaint();
    }

    public void finish() {
        Settlement settlement = new Settlement(panelW, panelH);
        PageController.addPanel("settlement", settlement);
        PageController.switchPanel("settlement");
    }

    private void sleep() {
        try {
            TimeUnit.MILLISECONDS.sleep(Config.sleepTime);
        } catch (Exception err) {

        }
    }

    public void shoot() {
        System.out.println(GameInfo.getBallNum());
        GameInfo.addPoint(1);
        EventHandler.ignore("Shoot");
        EventHandler.ignore("DrawTrajectory");
        this.drawTrajectory = false;
        this.noCollisionNum = 0;
        double[] dir = this.keyboardController.getDirection();
        int totalBall = this.balls.size();
        boolean isfinished = false;

        GameInfo.setStartPos(-1.0);
        for (Ball ball : this.balls) {
            this.updateBallNumLabel(this.balls.size() - this.balls.indexOf(ball) - 1);
            ball.setTouchedBase(false);
            ball.updateDirection(dir);

            for (int i = 0; i < Config.shootInterval; i++) {
                this.Update();
            }
        }

        this.ballNumLabel.setText(" ");

        while (this.Update() != totalBall)
            ;

        while (!this.character.isAtCorrectPos()) {
            this.character.updatePosition();
            repaint();
            sleep();
        }

        for (int i = this.balls.size(); i < GameInfo.getBallNum(); i++) {
            this.addSingleBall();
        }

        for (int i = 0; i < this.obstacles.size(); i++) {
            Obstacle obstacle = this.obstacles.get(i);
            if (obstacle.getRoundVanish()) {
                this.obstacles.remove(obstacle);
                i--;
            }
        }

        for (int i = 0; i < Config.downwardTimes; i++) {
            for (Obstacle obstacle : this.obstacles) {
                obstacle.downward(Config.downwardTimes);
            }
            repaint();
            this.sleep();
        }

        List<Obstacle> removeList = new ArrayList<Obstacle>();
        for (Obstacle obstacle : this.obstacles) {
            if (obstacle.getPos()[1] < 100) {
                if (obstacle instanceof Brick)
                    isfinished = true;
                else if (!(obstacle instanceof Wall) && !(obstacle instanceof Base))
                    removeList.add(obstacle);
            }
        }
        this.obstacles.removeAll(removeList);

        GameInfo.levelUp();
        for (Obstacle brick : this.genObstacle.generateRow(GameInfo.getLevel(), Config.brickUpmost)) {
            this.obstacles.add(brick);
        }

        for (int i = 0; i < 5; i++) {
            repaint();
        }
        if (isfinished) {
            try {
                TimeUnit.MILLISECONDS.sleep(Config.finishSleepTime);
            } catch (Exception err) {

            }
            finish();
        }
        this.keyboardController.initDirection();
        EventHandler.register("Shoot", pos -> this.shoot());
        EventHandler.register("DrawTrajectory", pos -> this.repaint());
        this.drawTrajectory = true;
        this.updateLevelLabel();
        this.updateBallNumLabel(this.balls.size());
    }

    private int Update() {
        int landBall = 0;
        for (Ball ball : this.balls) {
            for (int i = 0; i < this.obstacles.size(); i++) {
                Obstacle obstacle = this.obstacles.get(i);
                obstacle.collisionJudge(ball);
                if (obstacle.getNowVanish()) {
                    this.obstacles.remove(obstacle);
                    i--;
                }
            }

            landBall += ball.updatePosition() ? 1 : 0;
        }

        for (Ball ball : this.balls) {
            if (ball.getTouchedBase()) {
                continue;
            }
            if (ball.getPos()[1] <= Config.ballInitPositionY) {
                break;
            }
            if (Math.abs(ball.getDirection()[1]) > Config.directionYThreshold) {
                break;
            }
            boolean noObstacleAtSameY = true;
            for (Obstacle obstacle : this.obstacles) {
                if (obstacle instanceof Brick && Math
                        .abs(ball.getPos()[1] - obstacle.getPos()[1]) < (Config.brickSize[1] / 2 + ball.getRadius())) {
                    noObstacleAtSameY = false;
                    break;
                }
            }
            if (noObstacleAtSameY) {
                this.noCollisionNum++;
                if (this.noCollisionNum > Config.noCollisionThreshold) {
                    double[] pos = ball.getPos().clone();
                    pos[1] += (ball.getDirection()[1] / Math.tan(Math.toRadians(5))) * ball.getRadius();
                    pos[1] = Math.max(Math.min(pos[1], Config.wallUp - Config.PropSize), Config.base + Config.PropSize);

                    this.obstacles.add(new RandomShootProp(pos));
                    this.noCollisionNum = 0;
                }
            }
            break;
        }

        this.character.updatePosition();
        repaint();

        this.sleep();
        return landBall;
    }

    private void addSingleBall() {
        this.balls.add(new Ball(Config.ballColor, Config.ballSize,
                new double[] { GameInfo.getStartPos(), Config.ballInitPositionY }));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.background, (int) Config.wallLeft, (int) (Config.pageHeight - Config.wallUp),
                (int) (Config.wallRight - Config.wallLeft), (int) (Config.wallUp - Config.base), null);
        for (Ball ball : this.balls) {
            ball.draw(g);
        }
        for (Obstacle obstacle : this.obstacles) {
            obstacle.draw(g);
        }
        if (drawTrajectory) {
            this.keyboardController.drawTrajectory(g, this.balls.get(0).getPos()[0]);
            this.mouseController.drawTrajectory(g, this.balls.get(0).getPos()[0]);
        }
        this.character.draw(g);

        List<Double> removeList = new ArrayList<Double>();
        this.horizontalLaserLife.forEach((pos, life) -> {
            BufferedImage icon = ImageHandler.getImage("horizontalLighten");
            g.drawImage(icon, (int) (int) Config.wallLeft, (int) (Config.pageHeight - (pos + Config.laserWidth / 2)),
                    (int) (Config.wallRight - Config.wallLeft), (int) Config.laserWidth, null);
            this.horizontalLaserLife.put(pos, --life);
            if (life == 0) {
                removeList.add(pos);
            }
        });
        removeList.forEach(pos -> this.horizontalLaserLife.remove(pos));

        removeList.clear();
        this.verticalLaserLife.forEach((pos, life) -> {
            BufferedImage icon = ImageHandler.getImage("verticalLighten");
            g.drawImage(icon, (int) (pos - Config.laserWidth / 2), (int) (Config.pageHeight - Config.wallUp),
                    (int) Config.laserWidth, (int) (Config.wallUp - Config.base), null);

            this.verticalLaserLife.put(pos, --life);
            if (life == 0) {
                removeList.add(pos);
            }
        });
        removeList.forEach(pos -> this.verticalLaserLife.remove(pos));
    }

    private void setting() {
        MusicPlayer.play("click");
        PageController.switchPanel("setting");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_S) {
            this.setting();
        }
    }
}
