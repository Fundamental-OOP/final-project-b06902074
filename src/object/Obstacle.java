package object;

import java.awt.Graphics;

import config.Config;

public abstract class Obstacle {
    protected double[] pos;
    protected boolean nowVanish;
    protected boolean roundVanish;

    public Obstacle(double[] _pos) {
        pos = _pos;
        nowVanish = false;
        roundVanish = false;
    }

    public abstract void collisionJudge(Ball ball);

    public abstract void draw(Graphics g);

    public boolean getNowVanish() {
        return nowVanish;
    }

    public boolean getRoundVanish() {
        return roundVanish;
    }

    public void downward(int times) {
        this.pos[1] -= (Config.brickSize[1] + Config.brickBrickVerticalInterval) / times;
    }

    public double[] getPos() {
        return pos;
    }

    public String info() {
        return "";
    }
}
