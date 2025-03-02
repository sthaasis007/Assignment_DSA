package Assingment.TetrisGame;


import java.util.Random;

public class Shape {
    public enum Tetrominoes { NoShape, ZShape, SShape, LineShape, TShape, SquareShape, LShape, MirroredLShape }

    private Tetrominoes pieceShape;
    private int[][] coords;

    public Shape() {
        coords = new int[4][2];
        setShape(Tetrominoes.NoShape);
    }

    public void setShape(Tetrominoes shape) {
        pieceShape = shape;
    }

    public void setRandomShape() {
        Random random = new Random();
        int x = random.nextInt(7) + 1;
        setShape(Tetrominoes.values()[x]);
    }

    public Tetrominoes getShape() {
        return pieceShape;
    }

    public int getX(int index) { return coords[index][0]; }
    public int getY(int index) { return coords[index][1]; }
    public Shape rotateRight() { return this; }
}

