package com.jwin.tictactoe;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class BoardGUI extends JFrame {
    private Container pane;
    private JButton[][] cells;
    private JMenuBar menuBar;
    private JMenu menu;
    JMenuItem quitButton;
    JMenuItem newGameButton;
    boolean gameOver = false;
    final char X = 'X';
    final char O = 'O';
    char curPlayer;
    char firstPlayer;

    public BoardGUI() {
        super();
        pane = getContentPane();
        pane.setLayout(new GridLayout(3, 3));
        setTitle("Welcome to TicTacToe");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cells = new JButton[3][3];
        initBoard();
        initMenu();

        chooseOptions();
    }

    private void chooseOptions() {
        JFrame frame = new JFrame("Choose Letter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton chooseButton = new JButton("Click here to choose your letter");
        chooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Component source = (Component) e.getSource();
                Object response = JOptionPane.showInputDialog(source, "Player 1 choose your letter", "Choose Letter",
                        JOptionPane.QUESTION_MESSAGE, null, new Character[]{X, O}, X);
                firstPlayer = (Character) response;
                curPlayer = firstPlayer;
                frame.dispose();
                setVisible(true);
            }
        });
        frame.add(chooseButton, BorderLayout.CENTER);
        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void initMenu() {
        menuBar = new JMenuBar();
        menu = new JMenu("File");
        quitButton = new JMenuItem("Quit");
        newGameButton = new JMenuItem("New Game");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Goodbye");
                System.exit(0);
            }
        });
        menu.add(newGameButton);
        menu.add(quitButton);
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    private void reset() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j].setText("");
            }
        }
        curPlayer = firstPlayer;
        gameOver = false;
    }

    private void initBoard() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                JButton button = new JButton();
                button.setFont(new Font(Font.SERIF, Font.BOLD, 100));
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton thisButton = (JButton) e.getSource();
                        if (thisButton.getText().equals("") && !gameOver) {
                            thisButton.setText(String.valueOf(curPlayer));
                            checkWinner();
                            changePlayer();
                        }
                    }
                });
                cells[i][j] = button;
                pane.add(button);
            }
        }
    }

    private void checkWinner() {
        if (rowCheck() || colCheck() || diagCheck()) {
            gameOver = true;
            String winner;
            if (curPlayer == firstPlayer)
                winner = "Player 1";
            else
                winner = "Player 2";
            JOptionPane.showMessageDialog(null, winner + " Won!", "Congratulations", JOptionPane.PLAIN_MESSAGE);
        } else if (boardIsFull()) {
            gameOver = true;
            JOptionPane.showMessageDialog(null, "The game was tied!", "TIE!", JOptionPane.PLAIN_MESSAGE);
        }
    }

    private boolean rowCheck() {
        for (JButton[] cell : cells) {
            String s0 = cell[0].getText();
            String s1 = cell[1].getText();
            String s2 = cell[2].getText();
            if (s0.equals(String.valueOf(curPlayer)) && s0.equals(s1) && s0.equals(s2)) {
                return true;
            }
        }
        return false;
    }

    private boolean colCheck() {
        for (int col = 0; col < cells[0].length; col++) {
            String s0 = cells[0][col].getText();
            String s1 = cells[1][col].getText();
            String s2 = cells[2][col].getText();
            if (s0.equals(String.valueOf(curPlayer)) && s0.equals(s1) && s0.equals(s2)) {
                return true;
            }
        }
        return false;
    }

    private boolean diagCheck() {
        String tl = cells[0][0].getText();
        String mid = cells[1][1].getText();
        String br = cells[2][2].getText();
        String tr = cells[0][2].getText();
        String bl = cells[2][0].getText();
        if (mid.equals(String.valueOf(curPlayer))) {
            return (mid.equals(tl) && mid.equals(br)) || (mid.equals(tr) && mid.equals(bl));
        }
        return false;
    }

    private boolean boardIsFull() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                if (cells[i][j].getText().equals(""))
                    return false;
            }
        }
        return true;
    }

    private void changePlayer() {
        if (curPlayer == X) {
            curPlayer = O;
        } else {
            curPlayer = X;
        }
    }

}
