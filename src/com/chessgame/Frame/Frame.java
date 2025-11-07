package com.chessgame.Frame;

import javax.swing.JFrame;
import java.awt.Dimension;

public class Frame extends JFrame {
	private static final long serialVersionUID = -4442947819954124379L;
	public static final int WIDTH = 640;
	public static final int HEIGHT = 640;

	public Frame() {
		initializeFrame();
	}

	private void initializeFrame() {
		this.setContentPane(new Panel());
		this.setTitle("Chess Master Pro");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}