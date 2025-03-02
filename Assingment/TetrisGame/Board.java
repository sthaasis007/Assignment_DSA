package Assingment.TetrisGame;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Board extends JPanel implements ActionListener {
    private final int BOARD_WIDTH = 10;
    private final int BOARD_HEIGHT = 20;
    private final int TILE_SIZE = 30;
    private Timer timer;
    private boolean isPaused = false;
    private int currentX = 0;
    private int currentY = 0;
    private Shape currentPiece;
    private Shape.Tetrominoes[][] board;

    public Board() {
        setFocusable(true);
        board = new Shape.Tetrominoes[BOARD_HEIGHT][BOARD_WIDTH];
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!isPaused && currentPiece.getShape() != Shape.Tetrominoes.NoShape) {
                    handleKeyPress(e.getKeyCode());
                }
            }
        });
    }

    public void startGame() {
        clearBoard();
        newPiece();
        timer = new Timer(400, this);
        timer.start();
    }

    private void handleKeyPress(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_LEFT -> movePiece(-1, 0);
            case KeyEvent.VK_RIGHT -> movePiece(1, 0);
            case KeyEvent.VK_DOWN -> movePiece(0, 1);
            case KeyEvent.VK_UP -> rotatePiece();
            case KeyEvent.VK_SPACE -> dropPiece();
            case KeyEvent.VK_P -> isPaused = !isPaused;
        }
        repaint();
    }

    private void movePiece(int dx, int dy) {
        if (canMove(currentX + dx, currentY + dy, currentPiece)) {
            currentX += dx;
            currentY += dy;
        }
    }

    private void rotatePiece() {
        Shape rotated = currentPiece.rotateRight();
        if (canMove(currentX, currentY, rotated)) {
            currentPiece = rotated;
        }
    }

    private void dropPiece() {
        while (canMove(currentX, currentY + 1, currentPiece)) {
            currentY++;
        }
        pieceLanded();
    }

    private boolean canMove(int newX, int newY, Shape shape) {
        for (int i = 0; i < 4; i++) {
            int x = newX + shape.getX(i);
            int y = newY + shape.getY(i);
            if (x < 0 || x >= BOARD_WIDTH || y >= BOARD_HEIGHT || (y >= 0 && board[y][x] != Shape.Tetrominoes.NoShape)) {
                return false;
            }
        }
        return true;
    }

    private void pieceLanded() {
        for (int i = 0; i < 4; i++) {
            int x = currentX + currentPiece.getX(i);
            int y = currentY + currentPiece.getY(i);
            board[y][x] = currentPiece.getShape();
        }
        clearLines();
        newPiece();
    }

    private void clearLines() {
        for (int row = BOARD_HEIGHT - 1; row >= 0; row--) {
            boolean fullRow = true;
            for (int col = 0; col < BOARD_WIDTH; col++) {
                if (board[row][col] == Shape.Tetrominoes.NoShape) {
                    fullRow = false;
                    break;
                }
            }
            if (fullRow) {
                for (int r = row; r > 0; r--) {
                    System.arraycopy(board[r - 1], 0, board[r], 0, BOARD_WIDTH);
                }
                row++;
            }
        }
    }

    private void newPiece() {
        currentPiece = new Shape();
        currentPiece.setRandomShape();
        currentX = BOARD_WIDTH / 2 - 1;
        currentY = 0;
        if (!canMove(currentX, currentY, currentPiece)) {
            timer.stop();
            JOptionPane.showMessageDialog(this, "Game Over!", "Tetris", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void clearBoard() {
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                board[i][j] = Shape.Tetrominoes.NoShape;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
        drawCurrentPiece(g);
    }

    private void drawBoard(Graphics g) {
        for (int row = 0; row < BOARD_HEIGHT; row++) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                if (board[row][col] != Shape.Tetrominoes.NoShape) {
                    drawSquare(g, col, row, board[row][col]);
                }
            }
        }
    }

    private void drawCurrentPiece(Graphics g) {
        if (currentPiece.getShape() != Shape.Tetrominoes.NoShape) {
            for (int i = 0; i < 4; i++) {
                int x = currentX + currentPiece.getX(i);
                int y = currentY + currentPiece.getY(i);
                drawSquare(g, x, y, currentPiece.getShape());
            }
        }
    }

    private void drawSquare(Graphics g, int x, int y, Shape.Tetrominoes shape) {
        Color colors[] = {Color.BLACK, Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.PINK, Color.CYAN};
        g.setColor(colors[shape.ordinal()]);
        g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        g.setColor(Color.WHITE);
        g.drawRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isPaused) {
            if (canMove(currentX, currentY + 1, currentPiece)) {
                currentY++;
            } else {
                pieceLanded();
            }
            repaint();
        }
    }
}
