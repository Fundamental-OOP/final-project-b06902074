package controller;

import config.Config;
import info.GameInfo;

import event.EventHandler;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class MouseController extends Controller implements MouseListener, MouseMotionListener {
	private boolean shootLicense = false;

	@Override
	public void mouseClicked(MouseEvent e) {
		calculateAngle(e.getX(), e.getY());
		EventHandler.invoke("DrawTrajectory");
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		this.shootLicense = true;
		calculateAngle(e.getX(), e.getY());
		EventHandler.invoke("DrawTrajectory");
	}

	private void calculateAngle(double coordinateX, double coordinateY) {
		double x = coordinateX;
		double y = Config.pageHeight - coordinateY - Config.ballInitPositionY;
		if(y < 0) return;
		angle = Math.atan2( y, x - GameInfo.getStartPos()) * 180 / Math.PI;
		angle = Math.max(10, Math.min(170, angle));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(this.shootLicense)
			EventHandler.invoke("Shoot");
		this.shootLicense = false;
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e){
	}

}
