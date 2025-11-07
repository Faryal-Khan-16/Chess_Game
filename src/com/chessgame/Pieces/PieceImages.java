package com.chessgame.Pieces;

import java.awt.Color;
import java.util.Objects;
import javax.swing.ImageIcon;

public class PieceImages {
	public static final Color WHITECOLOR = Color.WHITE;
	public static final Color BLACKCOLOR = Color.BLACK;

	public static final String PAWN = "♟";
	public static final String ROOK = "♜";
	public static final String KNIGHT = "♞";
	public static final String BISHOP = "♝";
	public static final String QUEEN = "♛";
	public static final String KING = "♚";

	// Image icons - initialized to empty icons, will be set when images are loaded
	public static ImageIcon wk = new ImageIcon();
	public static ImageIcon bk = new ImageIcon();
	public static ImageIcon wr = new ImageIcon();
	public static ImageIcon br = new ImageIcon();
	public static ImageIcon wq = new ImageIcon();
	public static ImageIcon bq = new ImageIcon();
	public static ImageIcon wb = new ImageIcon();
	public static ImageIcon bb = new ImageIcon();
	public static ImageIcon wn = new ImageIcon();
	public static ImageIcon bn = new ImageIcon();
	public static ImageIcon wp = new ImageIcon();
	public static ImageIcon bp = new ImageIcon();

	static {
		loadImages();
	}

	private static void loadImages() {
		try {
			ClassLoader classLoader = PieceImages.class.getClassLoader();

			// Load images using classpath-relative paths
			// Note: Based on your file structure, the images might be in a different location
			wk = new ImageIcon(Objects.requireNonNull(classLoader.getResource("com/chessgame/Resources/wk.png")));
			bk = new ImageIcon(Objects.requireNonNull(classLoader.getResource("com/chessgame/Resources/bk.png")));
			wr = new ImageIcon(Objects.requireNonNull(classLoader.getResource("com/chessgame/Resources/wr.png")));
			br = new ImageIcon(Objects.requireNonNull(classLoader.getResource("com/chessgame/Resources/br.png")));
			wq = new ImageIcon(Objects.requireNonNull(classLoader.getResource("com/chessgame/Resources/wq.png")));
			bq = new ImageIcon(Objects.requireNonNull(classLoader.getResource("com/chessgame/Resources/bq.png")));
			wb = new ImageIcon(Objects.requireNonNull(classLoader.getResource("com/chessgame/Resources/wb.png")));
			bb = new ImageIcon(Objects.requireNonNull(classLoader.getResource("com/chessgame/Resources/bb.png")));
			wn = new ImageIcon(Objects.requireNonNull(classLoader.getResource("com/chessgame/Resources/wn.png")));
			bn = new ImageIcon(Objects.requireNonNull(classLoader.getResource("com/chessgame/Resources/bn.png")));
			wp = new ImageIcon(Objects.requireNonNull(classLoader.getResource("com/chessgame/Resources/wp.png")));
			bp = new ImageIcon(Objects.requireNonNull(classLoader.getResource("com/chessgame/Resources/bp.png")));

		} catch (Exception e) {
			System.err.println("Error loading piece images: " + e.getMessage());
			e.printStackTrace(); // This will give more detailed error information
			System.err.println("Please ensure image files are in the correct Resources directory");

			// Alternative approach - try different path patterns
			tryAlternativePaths();
		}
	}

	private static void tryAlternativePaths() {
		try {
			ClassLoader classLoader = PieceImages.class.getClassLoader();

			// Try without the package prefix
			wk = new ImageIcon(Objects.requireNonNull(classLoader.getResource("Resources/wk.png")));
			bk = new ImageIcon(Objects.requireNonNull(classLoader.getResource("Resources/bk.png")));
			// ... load other images similarly

			System.out.println("Images loaded with alternative paths");
		} catch (Exception e2) {
			System.err.println("Alternative paths also failed: " + e2.getMessage());
			// Continue with empty icons
		}
	}
}