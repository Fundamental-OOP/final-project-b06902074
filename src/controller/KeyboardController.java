package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import event.EventHandler;
import config.Config;

public class KeyboardController extends Controller implements KeyListener {

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			System.out.println("Shoot in keyborad");
			EventHandler.invoke("Shoot");
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			System.out.println("Left in keyborad");
			if (angle < UPPERBOUND) {
				angle += Config.perStepAngleChange;
				EventHandler.invoke("DrawTrajectory");
			}
			System.out.printf("angle: %f\n", angle);
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (angle > LOWERBOUND) {
				angle -= Config.perStepAngleChange;
				EventHandler.invoke("DrawTrajectory");
			}
			System.out.println("Right in keyborad");
			System.out.printf("angle: %f\n", angle);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
