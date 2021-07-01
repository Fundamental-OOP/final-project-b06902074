package info;

import config.Config;

public class GameInfo {
	private static int points;
	private static int level;
	private static int ballNum;
	private static double startPos;

	public static void init() {
		GameInfo.points = 0;
		GameInfo.level = 1;
		GameInfo.ballNum = 1;
		GameInfo.startPos = Config.ballInitPositionX;
	}

	public static int getBallNum() {
		return GameInfo.ballNum;
	}

	public static int getLevel() {
		return GameInfo.level;
	}

	public static int getPoints() {
		return GameInfo.points;
	}

	public static double getStartPos() {
		return GameInfo.startPos;
	}

	public static void setStartPos(double pos) {
		GameInfo.startPos = pos;
	}

	public static void addBall() {
		GameInfo.ballNum++;
	}

	public static void levelUp() {
		GameInfo.level++;
	}

	public static void addPoint(int pt) {
		points += pt;
	}
}
