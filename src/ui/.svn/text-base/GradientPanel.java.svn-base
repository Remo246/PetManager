package ui;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

class GradientPanel extends JPanel {

	private static final long serialVersionUID = 2634468151274736872L;

	@Override
	protected void paintComponent(Graphics g) {
		if (!isOpaque()) {
			super.paintComponent(g);
			return;
		}

		int w = getWidth();
		int h = getHeight();

		Color color1 = getBackground();
		Color color2 = color1.darker();

		Graphics2D g2d = (Graphics2D) g;
		GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);

		g2d.setPaint(gp);
		g2d.fillRect(0, 0, w, h);

		setOpaque(false);
		super.paintComponent(g);
		setOpaque(true);
	}
}
