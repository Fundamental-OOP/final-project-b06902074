package object;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.HashMap;
import config.Config;
import java.awt.Color;
import props.*;

public class GenObstacle {
    private List<Double> xAxis;
    private Map<Double, Function<List<Object>, Obstacle>> obstacleMap;
    private List<Double> obstacleMapKey;
    private double emptyProb = Config.emptyProb;
    private Random random = new Random();

    public GenObstacle(List<Double> _xAxis) {
        this.xAxis = _xAxis;

        this.obstacleMap = new HashMap<Double, Function<List<Object>, Obstacle>>();
        this.obstacleMap.put(Config.squareBrickProb, arg -> {
            return new SquareBrick(new double[] { (Double) arg.get(0), (Double) arg.get(1) }, (Color) arg.get(3),
                    (Integer) arg.get(2), Config.brickSize);
        });
        this.obstacleMap.put(Config.triangleBrickProb, arg -> {
            return new TriangleBrick(new double[] { (Double) arg.get(0), (Double) arg.get(1) }, (Color) arg.get(3),
                    (Integer) arg.get(2), Config.brickSize);
        });
        this.obstacleMap.put(Config.randomShootProb, arg -> {
            return new RandomShootProp(new double[] { (Double) arg.get(0), (Double) arg.get(1) });
        });
        this.obstacleMap.put(Config.horizontalLaserProb, arg -> {
            return new HorizontalLaserProp(new double[] { (Double) arg.get(0), (Double) arg.get(1) });
        });
        this.obstacleMap.put(Config.verticalLaserProb, arg -> {
            return new VerticalLaserProp(new double[] { (Double) arg.get(0), (Double) arg.get(1) });
        });
        this.obstacleMap.put(Config.blackHoleProb, arg -> {
            return new BlackHoleProp(new double[] { (Double) arg.get(0), (Double) arg.get(1) });
        });

        this.obstacleMapKey = this.obstacleMap.keySet().stream().sorted().collect(Collectors.toList());
    }

    public List<Obstacle> generateRow(int level, double yAxis) {
        List<Obstacle> ret = new ArrayList<Obstacle>();

        double addxAxis = xAxis.get(random.nextInt(xAxis.size()));
        ret.add(new AddProp(new double[] { addxAxis, yAxis }));

        final Color color = (level % 10 == 0 ? Config.DoubleBrickColor : Config.BrickColor);
        final Integer brickCount = (level % 10 == 0 ? level * 2 : level);

        for (double x : xAxis) {
            if (x == addxAxis)
                continue;
            double rd = this.random.nextDouble();
            if (rd <= this.emptyProb) {
                continue;
            } else {
                for (Double k : this.obstacleMapKey) {
                    if (rd <= k) {

                        List<Object> argument = new ArrayList<Object>() {
                            {
                                add(x);
                                add(yAxis);
                                add(brickCount);
                                add(color);
                            }
                        };
                        ret.add(this.obstacleMap.get(k).apply(argument));

                        break;
                    }
                }
            }
        }

        return ret;
    }

    public List<Obstacle> generateBricks(int level, List<Double> yAxis) {
        List<Obstacle> ret = new ArrayList<Obstacle>();

        for (double y : yAxis) {
            ret.addAll(this.generateRow(level, y));
        }

        return ret;
    }

    public Map<Double, Function<List<Object>, Obstacle>> getObstacleMap() {
        return this.obstacleMap;
    }

    public List<Double> getObstacleMapKey() {
        return this.obstacleMapKey;
    }

    public double getEmptyProb() {
        return this.emptyProb;
    }
}