package object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import config.Config;
import images.ImageHandler;

public class Wall extends Obstacle {
    protected Color color;
    private double up;
    private double left;
    private double right;
    private BufferedImage wallHorizontal = ImageHandler.getImage("wallHorizontal");
    private BufferedImage wallVertical = ImageHandler.getImage("wallVertical");

    public Wall(Color color, double up, double left, double right) {
        // FAKE
        super(new double[] { 0, 0 });

        this.up = up;
        this.left = left;
        this.right = right;
        this.color = color;
    }

    public void collisionJudge(Ball ball) {
        double[] position = ball.getPos();
        double[] direction = ball.getDirection();
        double radius = ball.getRadius();
        if (this.inWall(position, direction, radius)) {
            return;
        }
        if (position[0] - radius <= this.left || position[0] + radius >= this.right) {
            ball.updateDirection(new double[] { -direction[0], direction[1] });
        }
        if (position[1] + radius >= up) {
            ball.updateDirection(new double[] { direction[0], -direction[1] });
        }
    };

    private boolean inWall(double[] position, double[] direction, double radius) {
        return (position[0] + radius > this.right + Config.speed && direction[0] < 0)
                || (position[0] - radius < this.left - Config.speed && direction[0] > 0)
                || (position[1] + radius > this.up + Config.speed && direction[1] < 0);
    }

    @Override
    public void draw(Graphics g) {
        // TODO Auto-generated method stub
        // Left Wall
        g.drawImage(wallHorizontal, 0, 0, (int) Config.wallHorizontalMargin, (int) Config.pageHeight, null);
        // Right Wall
        g.drawImage(wallHorizontal, (int) (Config.wallRight), 0, (int) Config.wallHorizontalMargin,
                (int) Config.pageHeight, null);
        // Upper Wall
        g.drawImage(wallVertical, 0, 0, (int) Config.pageWidth, (int) Config.wallVerticalMargin, null);
    }
}
