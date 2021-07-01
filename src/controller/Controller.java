package controller;

import config.Config;
import java.awt.Graphics;

public abstract class Controller {
    protected static double angle;
    protected double LOWERBOUND = Config.angleLowerBound;
    protected double UPPERBOUND = Config.angleUpperBound;

    public Controller() {
        angle = Config.initAngle;
    }

    public double[] getDirection() {
		double direction_x = Math.cos(Math.toRadians(angle));
		double direction_y = Math.sqrt(1 - direction_x * direction_x);
		return new double[] { direction_x, direction_y };
	}

    public void drawTrajectory(Graphics g, double ballX) {
        g.setColor(Config.trajectoryColor);
        double distanceToLeftWall = ballX - Config.wallLeft;
        double distanceToRightWall = Config.wallRight - ballX;
        double distanceToTopWall = Config.wallUp - Config.ballInitPositionY;
        double cos = Math.cos(Math.toRadians(angle));
        double tan = Math.tan(Math.toRadians(angle));
        if (cos > distanceToRightWall / Math.hypot(distanceToRightWall, distanceToTopWall)) {
            g.drawLine((int) ballX, (int) (Config.pageHeight - Config.ballInitPositionY),
                    (int) Config.wallRight, (int) (Config.pageHeight
                            - ((Config.wallRight - ballX) * tan + Config.ballInitPositionY)));
        } else if (-cos > distanceToLeftWall / Math.hypot(distanceToLeftWall, distanceToTopWall)) {
            g.drawLine((int) ballX, (int) (Config.pageHeight - Config.ballInitPositionY),
                    (int) Config.wallLeft, (int) (Config.pageHeight
                            - ((ballX - Config.wallLeft) * (-tan) + Config.ballInitPositionY)));
        } else {
            g.drawLine((int) ballX, (int) (Config.pageHeight - Config.ballInitPositionY),
                    (int) ((Config.wallUp - Config.ballInitPositionY) / tan + ballX),
                    (int) (Config.pageHeight - Config.wallUp));
        }
    }

    public void initDirection() {
        angle = Config.initAngle;
    }
}
