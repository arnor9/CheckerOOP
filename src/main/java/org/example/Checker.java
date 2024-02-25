package org.example;


import java.util.Scanner;

class Spot{
    private Piece piece;
    private int x;
    private int y;

    public Spot(int x, int y, Piece piece){
        this.piece = piece;
        this.x = x;
        this.y = y;
    }
    public Piece getPiece(){
        return this.piece;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString(){
        if (piece != null){
            return piece.isPlayer1() ? "1": "2";
        }else {
            return " ";
        }
    }
}

class Drawboard{
    private Board board;
    public Drawboard(Board board){
        this.board = board;
    }
    public void draw(Spot[][] boxes){
        System.out.println("   0 1 2 3 4 5 6 7    <- X-axis");
        System.out.println("  +----------------+");

        for (int i = 0; i < boxes.length; i++) {
            System.out.print(i + " |");
            for (int j = 0; j < boxes.length; j++) {
                System.out.print(boxes[j][i] + " ");
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("  +----------------+");
        System.out.println("   0 1 2 3 4 5 6 7");
        System.out.println(" ");
        System.out.println("Turn of player no. 1");
        System.out.println("Coordinate of piece to move");
        System.out.print("Enter X: ");
        Scanner scanner1 = new Scanner(System.in);
        board.setCurrentX(scanner1.nextInt());
        System.out.print("Enter Y: ");
        board.setCurrentY(scanner1.nextInt());
        System.out.println("Coordinate of new position");
        System.out.print("Enter X: ");
        board.setNewX(scanner1.nextInt());
        System.out.print("Enter Y: ");
        board.setNewY(scanner1.nextInt());
    }
}

class Board {
    private final int size = 8;
    private Spot[][] boxes;
    private Drawboard drawboard;
    private int xCord;
    private int yCord;
    private int newXCord;
    private int newYCord;

    public Board() {
        this.boxes = new Spot[size][size];
    }

    public void startboard() {
        this.drawboard = new Drawboard(this);
        for (int i = 0; i < boxes.length; i++) {
            for (int j = 0; j < boxes.length; j++) {
                boxes[i][j] = new Spot(i, j, null);
            }
        }

        for (int i = 0; i < boxes.length; i += 2) {
            boxes[i][1] = new Spot(0, 1, new Pawn(true));
            boxes[i][5] = new Spot(0, 1, new Pawn(false));
            boxes[i][7] = new Spot(0, 1, new Pawn(false));
        }
        for (int i = 1; i < boxes.length; i += 2) {
            boxes[i][0] = new Spot(0, 1, new Pawn(true));
            boxes[i][2] = new Spot(0, 1, new Pawn(true));
            boxes[i][6] = new Spot(0, 1, new Pawn(false));
        }
        drawboard.draw(boxes);
    }

    public void setCurrentX(int xCord) {
        this.xCord = xCord;
    }

    public int getxCord() {
        return xCord;
    }

    public void setCurrentY(int yCord) {
        this.yCord = yCord;
    }

    public int getYCord() {
        return yCord;
    }

    public void setNewX(int newXCord) {
        this.newXCord = newXCord;
    }

    public int getNewXCord() {
        return newXCord;
    }

    public void setNewY(int newYCord) {
        this.newYCord = newYCord;
    }

    public int getNewYCord() {
        return newYCord;
    }

    public Spot[][] getBoxes() {
        return boxes;
    }
}


abstract class Piece{
    private boolean player1;
    public Piece(boolean player1) {
        this.player1 = player1;
    }

    public boolean isPlayer1() {
        return player1;
    }
}

class Pawn extends Piece{
    private Game game;

    public Pawn(boolean player1){
        super(player1);

    }
}

class Player{
    boolean one;
    public Player(boolean one){
        this.one = one;
    }

}


class Game{
    private Player[] players;
    private Board board;
    private Spot[][] boxes;
    private Drawboard drawboard;

    public Game(Player p1, Player p2, Board board, Drawboard drawboard){
        this.players = new Player[]{p1, p2};
        this.board = new Board();
        this.drawboard = drawboard;
        this.boxes = board.getBoxes();
    }

    public void initialize(){
        board.startboard();
    }
    public boolean GameOver(){
        return board.getxCord() == -1;
    }
    public void validMove() {
        int xDiff = Math.abs(board.getxCord() - board.getNewXCord());
        int yDiff = Math.abs(board.getYCord() - board.getNewYCord());
        if (xDiff == 1 && yDiff == 1) {
            move(board.getxCord(), board.getYCord(), board.getNewXCord(), board.getNewYCord());
        } else {
            System.out.println("This move is not possible, try again");
        }
    }
    public void move(int currentX, int currentY, int newX, int newY){
        Piece pieceToMove = boxes[currentX][currentY].getPiece();
        boxes[currentX][currentY] = new Spot(currentX, currentY, null);
        boxes[newX][newY] = new Spot(newX, newY, pieceToMove);
        drawboard.draw(boxes);

    }

}

public class Checker{
    public static void main(String[] args) {
        Player p1 = new Player(true);
        Player p2 = new Player(false);
        Board board = new Board();
        Drawboard drawboard = new Drawboard(board);
        Game game = new Game(p1, p2, board, drawboard);
        game.initialize();
        while (!game.GameOver()) {
            game.validMove();
        }
        System.out.println("Game Over!");
    }
}