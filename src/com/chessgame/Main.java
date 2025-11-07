package com.chessgame;

import javax.swing.SwingUtilities;
import com.chessgame.Frame.Home;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Home().setVisible(true);
			}
		});
	}
}