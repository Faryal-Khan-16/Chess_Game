package com.chessgame.Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home extends JFrame {
    private JButton startButton;
    private JButton instructionsButton;
    private JButton exitButton;
    private JLabel backgroundLabel;

    public Home() {
        initializeFrame();
        createComponents();
        setupLayout();
        setupEventListeners();
    }

    private void initializeFrame() {
        setTitle("Chess Master Pro - Home");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void createComponents() {
        startButton = new JButton("START");
        instructionsButton = new JButton("INSTRUCTIONS");
        exitButton = new JButton("EXIT");

        // Style buttons
        styleButton(startButton);
        styleButton(instructionsButton);
        styleButton(exitButton);

        // Background (you can set your own image path)
        backgroundLabel = new JLabel();
        backgroundLabel.setLayout(new GridBagLayout());
        try {
            ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("/Resources/images/chess-background.jpg"));
            backgroundLabel.setIcon(backgroundIcon);
        } catch (Exception e) {
            backgroundLabel.setBackground(Color.DARK_GRAY);
            backgroundLabel.setOpaque(true);
        }
    }

    private void styleButton(JButton button) {
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 24));
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        button.setPreferredSize(new Dimension(250, 60));
        button.setFocusPainted(false);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(3, 1, 0, 20));
        buttonPanel.add(startButton);
        buttonPanel.add(instructionsButton);
        buttonPanel.add(exitButton);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(buttonPanel);

        backgroundLabel.add(centerPanel);
        add(backgroundLabel, BorderLayout.CENTER);
    }

    private void setupEventListeners() {
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Frame().setVisible(true);
                dispose();
            }
        });

        instructionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Help().setVisible(true);
                dispose();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Home().setVisible(true);
            }
        });
    }
}