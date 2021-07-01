package object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import config.Config;

public class TriangleBrick extends Brick {
    private double[] direction;
    private Random random = new Random();

    public TriangleBrick(double[] _pos, Color _color, int _count, double[] _size) {
        super(_pos, _color, _count, _size);

        double[] candidate = new double[] { 1.0, -1.0 };
        double[] direction = new double[] { candidate[this.random.nextInt(candidate.length)] * Config.brickSize[0] / 2,
                candidate[this.random.nextInt(candidate.length)] * Config.brickSize[1] / 2 };
        this.direction = direction;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);

        List<Double[]> vertice = this.getVertices();
        int[] x = vertice.stream().mapToInt(pt -> (int) Math.round(pt[0])).toArray();
        int[] y = vertice.stream().mapToInt(pt -> Config.pageHeight - (int) Math.round(pt[1])).toArray();

        Polygon triangle = new Polygon(x, y, 3);

        this.drawCenteredString(g, String.valueOf(this.count), triangle, Config.font);
        return;
    };

    public void drawCenteredString(Graphics g, String text, Polygon triangle, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = triangle.xpoints[0];
        int y = triangle.ypoints[0];
        int[] margin = new int[] { (int) this.size[0] / 10, (int) this.size[1] / 10 };

        if (this.direction[0] < 0) {
            x += margin[0];
        } else {
            x -= metrics.stringWidth(text) + margin[0];
        }

        if (this.direction[1] < 0) {
            y -= margin[1];
        } else {
            y += metrics.getHeight() + margin[1] - metrics.getAscent() / 2;
        }
        g.setFont(font);
        g.drawString(text, x, y);
        g.drawPolygon(triangle);
    }

    @Override
    protected List<Double[]> getVertices() {
        int coef = this.direction[0] * this.direction[1] < 0 ? -1 : 1;
        List<Double[]> vertice = new ArrayList<Double[]>();

        vertice.add(new Double[] { this.pos[0] + this.direction[0], this.pos[1] + this.direction[1] });
        vertice.add(new Double[] { this.pos[0] + coef * this.direction[0], this.pos[1] - coef * this.direction[1] });
        vertice.add(new Double[] { this.pos[0] - coef * this.direction[0], this.pos[1] + coef * this.direction[1] });

        return vertice;
    }

    @Override
    public String info() {
        return "Nothing but a triangle brick. There's a surprise in every 10 rounds.";
    }
}
