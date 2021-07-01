package props;

import config.Config;
import images.ImageHandler;
import info.GameInfo;
import musics.MusicPlayer;
import object.Ball;

public class BlackHoleProp extends Prop {

	public BlackHoleProp(double[] pos) {
		super(pos, Config.PropSize, ImageHandler.getImage("blackhole"));
		this.setScale(2);
	}

	@Override
	protected void perform(Ball ball) {
		MusicPlayer.play("blackhole");
		double initX = GameInfo.getStartPos();
		if (initX == -1) {
			GameInfo.setStartPos(Config.ballInitPositionX);
			initX = Config.ballInitPositionX;
		}
		ball.updatePostion(new double[] { initX, Config.ballInitPositionY });
		ball.setTouchedBase(true);
	}

	@Override
	public String info() {
		return "Don't touch it!! Otherwise your ball will be teleported to base.";
	}
}
