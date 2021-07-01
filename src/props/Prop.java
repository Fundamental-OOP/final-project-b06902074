package props;

import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import object.Obstacle;
import object.Ball;
import config.Config;

public abstract class Prop extends Obstacle {
    private double size;
    private BufferedImage icon;
    private Stroke stroke = new BasicStroke(Config.AddStroke);
    private double scale = 1;

    public Prop(double[] _pos, double size, BufferedImage icon) {
        super(_pos);
        this.icon = icon;
        this.size = size;
    }

    protected abstract void perform(Ball ball);

    public Prop(double[] _pos, double _size) {
        super(_pos);
        size = _size;
    }

    protected void setScale(double scale) {
        this.scale = scale;
    }

    protected Boolean isCollision(Ball ball) {
        double[] ballPos = ball.getPos();
        if (Math.pow(pos[0] - ballPos[0], 2) + Math.pow(pos[1] - ballPos[1], 2) < Math.pow(size + ball.getRadius(), 2))
            return true;
        return false;
    }

    protected double getSize() {
        return this.size;
    }

    @Override
    public void collisionJudge(Ball ball) {
        if (isCollision(ball)) {
            this.roundVanish = true;
            perform(ball);
        }
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Stroke oldStroke = g2.getStroke();
        double size = this.size * this.scale;
        g2.setStroke(this.stroke);
        g.drawImage(this.icon, (int) (this.pos[0] - size), (int) (Config.pageHeight - (this.pos[1] + size)),
                (int) (2 * size), (int) (2 * size), null);
        g2.setStroke(oldStroke);
    }
}
