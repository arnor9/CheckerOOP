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

    }
}

class Board {
    private final int size = 8;
    private Spot[][] boxes;
    private Drawboard drawboard;
    private Game game;
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
    private boolean isPlayer1Turn = true;

    public Game(Player p1, Player p2, Board board, Drawboard drawboard){
        this.players = new Player[]{p1, p2};
        this.board = board;
        this.drawboard = drawboard;
        this.boxes = board.getBoxes();
    }

    public void initialize(){
        board.startboard();
    }
    public void inputHandler(){
        if (isPlayer1Turn) {
            System.out.println("Turn of player no. 1");
        } else {
            System.out.println("Turn of player no. 2");
        }
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
    public boolean GameOver(){
        return board.getxCord() == -1;
    }
    public boolean validMove(int currentX, int currentY, int newX, int newY, Piece pieceToMove) {
        if (currentX < 0 || currentX > 7 || currentY < 0 || currentY > 7 ||
                newX < 0 || newX > 7 || newY < 0 || newY > 7){
            return false;
        } else if (pieceToMove.isPlayer1() == isPlayer1Turn && boxes[newX][newY].getPiece() == null){

            int xDiff = Math.abs(currentX - newX);
            int yDiff = currentY - newY;
            if (xDiff == 1) {
                if (isPlayer1Turn && yDiff == -1) {
                    return true;
                } else if (!isPlayer1Turn && yDiff == 1) {
                  return true;
             }
            }
        }
        return false;
    }
    public boolean move(){
        Piece pieceToMove = boxes[board.getxCord()][board.getYCord()].getPiece();
        if (validMove(board.getxCord(), board.getYCord(), board.getNewXCord(), board.getNewYCord(), pieceToMove)) {
            boxes[board.getxCord()][board.getYCord()] = new Spot(board.getxCord(), board.getYCord(), null);
            boxes[board.getNewXCord()][board.getNewYCord()] = new Spot(board.getNewXCord(), board.getNewYCord(), pieceToMove);
            drawboard.draw(boxes);
            System.out.println("Piece moved! \n");
            toggleTurn();
            inputHandler();
            return true;
        } else {
                System.out.println("this was illigal move, try again!");
        }
        return false;
    }
    public void toggleTurn(){
        isPlayer1Turn = !isPlayer1Turn;
    }
    public boolean isPlayer1Turn(){
        return isPlayer1Turn;
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
        while(!game.GameOver()) {
            game.inputHandler();
            while (!game.move()){
                game.inputHandler();
            }
            System.out.println("Invalid move, try again");
        }
    }
}