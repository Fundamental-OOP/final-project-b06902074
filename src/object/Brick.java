package object;

import java.awt.Color;
import java.util.List;

import musics.MusicPlayer;

public abstract class Brick extends Obstacle {
    protected Color color;
    protected int count;
    protected double[] size;
    protected int colorGradient;

    public Brick(double[] _pos, Color _color, int _count, double[] _size) {
        super(_pos);
        this.color = _color;
        this.count = _count;
        this.size = _size;
        this.colorGradient = 255 / _count;
    }

    // Compute vertex and place in clockwise
    protected abstract List<Double[]> getVertices();

    public void minusCount() {
        this.count -= 1;
        if (this.count <= 0) {
            this.nowVanish = true;
        }
        this.color = new Color(this.color.getRed(), this.color.getGreen() + this.colorGradient, this.color.getBlue());
    }

    @Override
    public void collisionJudge(Ball ball) {
        boolean collided = false;
        double[] ballPos = ball.getPos();
        double[] ballDir = ball.getDirection();
        double radius = ball.getRadius();

        List<Double[]> vertices = this.getVertices();

        // Compute the distance between ball and vertex
        for (Double[] vertex : vertices) {
            double xDiff = ballPos[0] - vertex[0];
            double yDiff = ballPos[1] - vertex[1];

            if (Math.hypot(xDiff, yDiff) < radius) {
                double cornerToBallLength = Math.hypot(xDiff, yDiff);
                double[] base = new double[] { xDiff / cornerToBallLength, yDiff / cornerToBallLength };
                ball.updateDirection(computeAngle(base, ballDir));

                collided = true;
            }
        }

        // Compute whether the ball is close to the edge
        for (int i = 0; i < vertices.size(); i++) {
            Double[] pt1 = vertices.get(i);
            Double[] pt2 = vertices.get((i + 1) % vertices.size());

            // Let L: ax + by + c = 0
            double a1 = pt1[1] - pt2[1]; // y1 - y2
            double b1 = -(pt1[0] - pt2[0]); // x2 - x1
            double a2 = b1;
            double b2 = -a1;

            double len1 = Math.hypot(a1, b1);
            double c11 = -a1 * pt1[0] - b1 * pt1[1];
            double c12 = -a1 * (pt1[0] + a1 / len1 * radius) - b1 * (pt1[1] + b1 / len1 * radius);
            double c21 = -a2 * pt1[0] - b2 * pt1[1];
            double c22 = -a2 * pt2[0] - b2 * pt2[1];

            double sign1 = Math.signum(a1 * ballPos[0] + b1 * ballPos[1] + c11);
            double sign2 = Math.signum(a1 * ballPos[0] + b1 * ballPos[1] + c12);
            double sign3 = Math.signum(a2 * ballPos[0] + b2 * ballPos[1] + c21);
            double sign4 = Math.signum(a2 * ballPos[0] + b2 * ballPos[1] + c22);

            if (sign1 * sign2 <= 0 && sign3 * sign4 <= 0) {
                double[] base = new double[] { a1 / len1, b1 / len1 };
                ball.updateDirection(computeAngle(base, ballDir));
                collided = true;
            }
        }

        if (collided) {
            MusicPlayer.play("touched");
            this.minusCount();
        }
    };

    private double[] computeAngle(double[] base, double[] ballDir) {
        double projLength = Math.abs(ballDir[0] * base[0] + ballDir[1] * base[1]);
        double[] proj = new double[] { base[0] * projLength, base[1] * projLength };
        double[] norm = new double[] { ballDir[0] + proj[0], ballDir[1] + proj[1] };
        double[] reflect = new double[] { -ballDir[0] + 2 * norm[0], -ballDir[1] + 2 * norm[1] };

        double length = Math.hypot(reflect[0], reflect[1]);
        return new double[] { reflect[0] / length, reflect[1] / length };
    }

    @Override
    public String info() {
        return "It's a brick";
    }
}
