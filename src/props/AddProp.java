package props;

import config.Config;
import images.ImageHandler;
import object.Ball;
import info.GameInfo;
import musics.MusicPlayer;

public class AddProp extends Prop {

    public AddProp(double[] _pos) {
        super(_pos, Config.PropSize, ImageHandler.getImage("add"));
    }

    @Override
    protected void perform(Ball ball) {
        GameInfo.addBall();
        MusicPlayer.play("addprop");
        nowVanish = true;
    }

    @Override
    public String info() {
        return "Must appear in every level. When touching it, you will have one additional ball.";
    }
}