package com.chessgame.Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Help extends JFrame {
    private JTextArea instructionsText;
    private JButton backButton;

    public Help() {
        initializeFrame();
        createComponents();
        setupLayout();
        setupEventListeners();
    }

    private void initializeFrame() {
        setTitle("Chess Master Pro - Instructions");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void createComponents() {
        instructionsText = new JTextArea();
        instructionsText.setEditable(false);
        instructionsText.setLineWrap(true);
        instructionsText.setWrapStyleWord(true);
        instructionsText.setFont(new Font("Arial", Font.PLAIN, 16));
        instructionsText.setBackground(Color.WHITE);
        instructionsText.setText(getInstructionsContent());

        backButton = new JButton("Back to Home");
        backButton.setFont(new Font("Arial", Font.BOLD, 18));
        backButton.setPreferredSize(new Dimension(200, 50));
    }

    private String getInstructionsContent() {
        return "CHESS MASTER PRO - INSTRUCTIONS\n\n" +
                "GAME RULES:\n" +
                "• Chess is a two-player strategy game played on an 8x8 board.\n" +
                "• Each player controls 16 pieces: 1 King, 1 Queen, 2 Rooks, 2 Bishops, 2 Knights, and 8 Pawns.\n" +
                "• White always moves first.\n" +
                "• The objective is to checkmate the opponent's King.\n\n" +
                "PIECE MOVEMENT:\n" +
                "• King: Moves one square in any direction\n" +
                "• Queen: Moves any number of squares diagonally, horizontally, or vertically\n" +
                "• Rook: Moves any number of squares horizontally or vertically\n" +
                "• Bishop: Moves any number of squares diagonally\n" +
                "• Knight: Moves in an L-shape (2 squares in one direction, then 1 square perpendicular)\n" +
                "• Pawn: Moves forward one square, captures diagonally\n\n" +
                "SPECIAL MOVES:\n" +
                "• Castling: King moves 2 squares toward a Rook, and the Rook moves to the other side\n" +
                "• En Passant: Special pawn capture\n" +
                "• Promotion: Pawn reaching the opposite end becomes a Queen, Rook, Bishop, or Knight\n\n" +
                "HOW TO PLAY:\n" +
                "1. Click on a piece to select it\n" +
                "2. Possible moves will be highlighted\n" +
                "3. Click on the destination square to move\n" +
                "4. Press LEFT ARROW key to undo a move\n" +
                "5. The game ends when a King is checkmated or it's a stalemate";
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(Color.LIGHT_GRAY);

        JLabel titleLabel = new JLabel("Chess Instructions", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.BLUE);

        JScrollPane scrollPane = new JScrollPane(instructionsText);
        scrollPane.setPreferredSize(new Dimension(800, 500));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        buttonPanel.add(backButton);

        contentPanel.add(titleLabel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(contentPanel);
    }

    private void setupEventListeners() {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Home().setVisible(true);
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Help().setVisible(true);
            }
        });
    }
}