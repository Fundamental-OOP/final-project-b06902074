package config;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

public abstract class Config {
	// generator configs
	public static double emptyProb = 0.5;
	public static double squareBrickProb = 0.75;
	public static double triangleBrickProb = 0.9;
	public static double randomShootProb = 0.93;
	public static double verticalLaserProb = 0.96;
	public static double horizontalLaserProb = 0.99;
	public static double blackHoleProb = 1.0;

	// Props configs
	public static double PropSize = 10.0;
	public static float AddStroke = 3.0f;
	public static Color AddColor = Color.CYAN;
	public static Color RandomColor = Color.MAGENTA;
	public static Color HorizonLaserColor = Color.BLUE;
	public static Color VerticalLaserColor = Color.BLUE;

	// brick configs
	public static double[] brickSize = { 50.0, 50.0 };
	public static double brickBrickHorizontalInterval = 5.0;
	public static double brickBrickVerticalInterval = 5.0;
	public static double initBrickRowNum = 3;
	public static double brickColumnNum = 7;
	public static double brickRowNum = 8;
	public static Color BrickColor = new Color(255, 0, 0);
	public static Color DoubleBrickColor = new Color(75, 0, 255);
	public static int downwardTimes = 100;

	// wall configs
	public static double wallBrickHorizontalInterval = 3.0;
	public static double wallBrickUpperInterval = brickSize[1];
	public static double wallHorizontalMargin = 30.0;
	public static double wallVerticalMargin = 30.0;
	public static double base = 30;
	public static Color wallColor = Color.BLACK;

	// page configs
	public static int pageWidth = (int) (2 * (wallHorizontalMargin + wallBrickHorizontalInterval)
			+ brickColumnNum * brickSize[0] + (brickColumnNum - 1) * brickBrickHorizontalInterval);
	public static int pageHeight = (int) (wallVerticalMargin + wallBrickUpperInterval + brickRowNum * brickSize[1]
			+ (brickRowNum - 1) * brickBrickVerticalInterval + base);

	// environment configs
	public static double wallLeft = wallHorizontalMargin;
	public static double wallRight = pageWidth - wallHorizontalMargin;
	public static double wallUp = pageHeight - wallVerticalMargin;
	public static double brickLeftmost = wallLeft + wallBrickHorizontalInterval + brickSize[0] / 2;
	public static double brickUpmost = wallUp - wallBrickUpperInterval - brickSize[1] / 2;

	// controller configs
	public static double initAngle = 90.0;
	public static double perStepAngleChange = 0.5;
	public static double angleUpperBound = 170.0;
	public static double angleLowerBound = 10.0;
	public static Color trajectoryColor = Color.LIGHT_GRAY;

	// ball configs
	public static int ballIndex = 0;
	public static List<Color> ballColorList = new ArrayList<Color>() {
		{
			add(Color.WHITE);
			add(Color.CYAN);
		}
	};
	public static List<Double> ballSizeList = new ArrayList<Double>() {
		{
			add(5.0);
			add(10.0);
		}
	};

	public static double ballSize = ballSizeList.get(ballIndex);
	public static double speed = 1;
	public static double ballInitPositionX = pageWidth / 2;
	public static double ballInitPositionY = base + ballSize;
	public static Color ballColor = ballColorList.get(ballIndex);

	// shoot configs
	public static int sleepTime = 2;
	public static int finishSleepTime = 500;
	public static int shootInterval = (int) (4 * ballSize);
	public static double angleThreshold = 3;
	public static double directionYThreshold = Math.sin(Math.toRadians(angleThreshold));
	public static int noCollisionThreshold = 1000;

	// laser configs
	public static double laserWidth = 30.0;
	public static int laserLife = 10;
	public static Color laserColor = Color.YELLOW;

	// other configs
	public static String musicDirectory = "music";
	public static long minMusicInterval = 40000;
	public static String imageDirectory = "image";
	public static Font font = new Font("Serif", Font.BOLD, 20);
	public static Font levelLabelFont = new Font(Font.MONOSPACED, Font.BOLD, 30);
	public static Font ballNumLabelFont = new Font(Font.MONOSPACED, Font.BOLD, 10);

	// Background
	public static String imageMainBackground = "MainBackground";

	// Character
	public static double[] characterSize = new double[] { 50, 50 };
	public static String characterImage = "character";

	// Help
	public static int tipXAxis = 40;
	public static int InfoXAxis = 90;
	public static int ObstacleXAxis = 50;
	public static double ObstacleYAxis = 400;
	public static double ObstacleYInterval = -60;
	public static int charLimitNum = 50;
	public static Font helpFont = new Font("Serif", Font.PLAIN, 15);
	public static Color helpStringColor = Color.BLACK;
}
