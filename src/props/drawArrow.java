package props;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class drawArrow {
    public static void draw(Graphics g1, int x1, int y1, int x2, int y2, int ARR_SIZE) {
        Graphics2D g = (Graphics2D) g1.create();

        double dx = x2 - x1, dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx * dx + dy * dy);
        AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
        at.concatenate(AffineTransform.getRotateInstance(angle));
        g.transform(at);

        // Draw horizontal arrow starting in (0, 0)
        g.drawLine(ARR_SIZE / 2, 0, len, 0);
        g.fillPolygon(new int[] { len, len - ARR_SIZE, len - ARR_SIZE, len }, new int[] { 0, -ARR_SIZE, ARR_SIZE, 0 },
                4);
    }
}

// Modify from https://gist.github.com/rkkautsar/7529b4816eba4dc24ed6