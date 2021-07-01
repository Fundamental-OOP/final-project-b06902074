package props;

import config.Config;
import event.EventHandler;
import images.ImageHandler;
import musics.MusicPlayer;
import object.Ball;

public class VerticalLaserProp extends Prop {
    public VerticalLaserProp(double[] _pos) {
        super(_pos, Config.PropSize, ImageHandler.getImage("verticalLaser"));
        this.setScale(1.6);
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
    protected void perform(Ball ball) {
        roundVanish = true;
        MusicPlayer.playAfterFinish("laser");
        EventHandler.invoke("VerticalLaser", this.pos[0]);
    }

    @Override
    public String info() {
        return "When touching, it will launch a vertical laser to break the bricks.";
    }
}
