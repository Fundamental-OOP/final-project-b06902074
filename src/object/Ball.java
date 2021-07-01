package object;

import java.awt.Color;
import java.awt.Graphics;

import info.GameInfo;
import config.Config;

public class Ball {
    private Color color;
    private double radius;
    private double[] pos;
    private double[] direction = new double[] { 0.0, 0.0 };
    private boolean touchedBase = false;
    private double speed = Config.speed;

    public Ball(Color color, double radius, double[] pos) {
        this.color = color;
        this.radius = radius;
        this.pos = pos;
    }

    public boolean updatePosition() { // return is finished
        if (touchedBase) {
            this.pos[1] = Config.ballInitPositionY;
            double startPos = GameInfo.getStartPos();
            if (pos[0] < startPos)
                pos[0] = Math.min(startPos, pos[0] + speed * direction[0]);
            else if (pos[0] > startPos)
                pos[0] = Math.max(startPos, pos[0] + speed * direction[0]);
            else {
                this.direction = new double[] { 0.0, 0.0 };
                return true;
            }
        } else {
            pos[0] += speed * direction[0];
            pos[1] += speed * direction[1];
        }

        return false;
    }

    public void updatePostion(double[] pos) {
        this.pos = pos;
    }

    public void setTouchedBase(boolean touched) {
        touchedBase = touched;
    }

    public void updateDirection(double[] direction) {
        this.direction = direction;
    }

    public double getRadius() {
        return this.radius;
    }

    public double[] getPos() {
        return this.pos;
    }

    public double[] getDirection() {
        return this.direction;
    }

    public Color getColor() {
        return this.color;
    }

    public boolean getTouchedBase() {
        return this.touchedBase;
    }

    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillOval((int) Math.round(this.pos[0] - this.radius),
                Config.pageHeight - (int) Math.round(this.pos[1] + this.radius), (int) Math.round(2 * this.radius),
                (int) Math.round(2 * this.radius));
    }

    public double getSpeed() {
        return speed;
    }
}