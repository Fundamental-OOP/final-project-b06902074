package pages;

import javax.swing.JPanel;

import config.Config;
import images.ImageHandler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

public abstract class ShowPage extends JPanel implements KeyListener {
    protected int panelW, panelH;
    protected BufferedImage background;

    public ShowPage(int w, int h, BufferedImage background) {
        this.setPanelSize(w, h);
        this.background = background;
    }

    public ShowPage(int w, int h) {
        this.setPanelSize(w, h);
        this.background = ImageHandler.getOpacityImage(Config.imageMainBackground, 0.3);
    }

    private void setPanelSize(int w, int h) {
        this.panelW = w;
        this.panelH = h;
        this.setSize(w, h);
        this.setVisible(false);
    }

    protected void init() {
        this.repaint();
    };

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.background, 0, 0, panelW, panelH, null);
    }
}