package object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import config.Config;

import java.awt.Font;
import java.awt.FontMetrics;

public class SquareBrick extends Brick {
    public SquareBrick(double[] _pos, Color _color, int _count, double[] _size) {
        super(_pos, _color, _count, _size);
    }

    @Override
    public void draw(Graphics g) {
        List<Double[]> vertice = this.getVertices();
        int x = (int) Math.round(vertice.get(0)[0]);
        int y = Config.pageHeight - (int) Math.round(vertice.get(0)[1]);

        g.setColor(this.color);
        this.drawCenteredString(g, String.valueOf(this.count),
                new Rectangle(x, y, (int) this.size[0], (int) this.size[1]), Config.font);
    };

    public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g.drawRect(rect.x, rect.y, rect.width, rect.height);
        g.setColor(this.color);
        g.setFont(font);
        g.drawString(text, x, y);
    }

    @Override
    protected List<Double[]> getVertices() {
        List<Double[]> vertice = new ArrayList<Double[]>();

        vertice.add(new Double[] { this.pos[0] - this.size[0] / 2, this.pos[1] + this.size[1] / 2 });
        vertice.add(new Double[] { this.pos[0] + this.size[0] / 2, this.pos[1] + this.size[1] / 2 });
        vertice.add(new Double[] { this.pos[0] + this.size[0] / 2, this.pos[1] - this.size[1] / 2 });
        vertice.add(new Double[] { this.pos[0] - this.size[0] / 2, this.pos[1] - this.size[1] / 2 });

        return vertice;
    }

    @Override
    public String info() {
        return "Nothing but a square brick. There's a surprise in every 10 rounds.";
    }
}
