package pages;

import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import config.Config;
import images.ImageHandler;
import musics.MusicPlayer;
import object.Ball;
import object.Button;

public class ChangeBall extends ShowPage {
	public ChangeBall(int w, int h) {
		super(w, h);

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		JPanel above = new JPanel(new GridBagLayout());
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.anchor = GridBagConstraints.NORTH;

		Icon ballListLabel = new ImageIcon(ImageHandler.getImage("game_ball_list"));
		JLabel label = new JLabel(ballListLabel);
		above.add(label, gbc);

		gbc.insets = new Insets(-120, 0, 150, 0);
		gbc.ipadx = 100;
		above.setBackground(null);
		above.setOpaque(false);

		this.add(above, gbc);

		GridBagConstraints gbcButton = new GridBagConstraints();

		gbcButton.weightx = 1;

		JPanel buttons = new JPanel(new GridBagLayout());

		Icon leftIcon = new ImageIcon(
				ImageHandler.getImage("arrowLeft").getScaledInstance(100, 100, Image.SCALE_SMOOTH));
		Button btnLeft = new Button(leftIcon, e -> this.change(-1));

		Icon rightIcon = new ImageIcon(
				ImageHandler.getImage("arrowRight").getScaledInstance(100, 100, Image.SCALE_SMOOTH));
		Button btnRight = new Button(rightIcon, e -> this.change(1));

		for (int i = 0; i < 10; i++) {
			gbcButton.gridx = i;
			gbcButton.gridy = 0;
			if (i == 0)
				buttons.add(btnLeft, gbcButton);
			else if (i == 9)
				buttons.add(btnRight, gbcButton);
			else {
				Component temp = Box.createRigidArea(new Dimension(30, 100));
				temp.setBackground(null);
				buttons.add(temp, gbcButton);
			}
		}
		buttons.setOpaque(false);

		this.add(buttons, gbcButton);

		GridBagConstraints gbcBottom = new GridBagConstraints();

		JPanel bottom = new JPanel(new GridBagLayout());
		gbcBottom.gridwidth = GridBagConstraints.REMAINDER;
		gbcBottom.anchor = GridBagConstraints.PAGE_END;
		gbcBottom.insets = new Insets(10, 0, 0, 0);

		Icon backIcon = new ImageIcon(ImageHandler.getImage("button_back"));
		Button btnMain = new Button(backIcon, e -> back());
		bottom.add(btnMain, gbcBottom);
		bottom.setOpaque(false);

		this.add(bottom, gbcBottom);

		addKeyListener(this);
		this.setVisible(false);
	}

	private void back() {
		MusicPlayer.play("click");
		PageController.switchPanel("main");
	}

	private void change(int direction) {
		MusicPlayer.play("click");
		Config.ballIndex = Math.max(Math.min(Config.ballIndex + direction, Config.ballSizeList.size() - 1), 0);
		this.resetBall();
		repaint();
	}

	private void resetBall() {
		Config.ballSize = Config.ballSizeList.get(Config.ballIndex);
		Config.ballInitPositionX = Config.pageWidth / 2;
		Config.ballInitPositionY = Config.base + Config.ballSize;
		Config.ballColor = Config.ballColorList.get(Config.ballIndex);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_B:
				this.back();
				break;
			case KeyEvent.VK_LEFT:
				this.change(-1);
				break;
			case KeyEvent.VK_RIGHT:
				this.change(1);
				break;
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Ball ball = new Ball(Config.ballColor, Config.ballSize,
				new double[] { Config.pageWidth / 2, Config.pageHeight / 2 + 40 });
		ball.draw(g);
	}
}
