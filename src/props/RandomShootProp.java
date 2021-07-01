package props;

import java.util.Random;

import object.Ball;
import config.Config;
import images.ImageHandler;
import musics.MusicPlayer;

public class RandomShootProp extends Prop {
    private Random random = new Random();

    public RandomShootProp(double[] _pos) {
        super(_pos, Config.PropSize, ImageHandler.getImage("randomShoot"));
        this.setScale(1.5);
    }

    @Override
    protected void perform(Ball ball) {
        double x = Math.min(Math.max(random.nextDouble() * 2 - 1, -0.95), 0.95);
        double y = Math.sqrt(1 - x * x);
        ball.updateDirection(new double[] { x, y });
        MusicPlayer.playAfterFinish("randomshoot");
    }

    @Override
    protected Boolean isCollision(Ball ball) {
        double[] ball_pos = ball.getPos();
        double[] ball_dir = ball.getDirection();
        double dist = Math.pow(pos[0] - ball_pos[0], 2) + Math.pow(pos[1] - ball_pos[1], 2);
        double size = this.getSize();
        if (dist <= Math.pow(size + ball.getRadius(), 2)
                && dist >= Math.pow(Math.max(size + ball.getRadius() - ball.getSpeed(), 0), 2)) {
            if ((pos[0] - ball_pos[0]) * ball_dir[0] + (pos[1] - ball_pos[1]) * ball_dir[1] >= 0) { // Use out
                this.roundVanish = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public String info() {
        return "When touching, it will change the direction of the ball randomly.";
    }
}