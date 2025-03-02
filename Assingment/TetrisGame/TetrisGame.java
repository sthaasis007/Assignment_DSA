package Assingment.TetrisGame;

import javax.swing.*;

public class TetrisGame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Tetris");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 600);
            frame.setResizable(false);

            Board board = new Board();
            frame.add(board);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            board.startGame();
        });
    }
}

