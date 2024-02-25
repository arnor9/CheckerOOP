package org.example;


import java.util.Scanner;

class Spot{
    private Piece piece;
    private Pawn pawn;
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
            return pawn.getOne() ? "1": "2";
        }else {
            return " ";
        }
    }
}

class Board {
    private final int size = 8;
    private Spot[][] boxes;


    public Board(){
 //       this.startboard();
    }

    public void startboard() {
        boxes = new Spot[size][size];
        Draw draw = new Draw();
        for (int i = 0; i < boxes.length; i++) {
            for (int j = 0; j < boxes.length; j++) {
                boxes[i][j] = new Spot(i, j, null);
            }
        }

        for (int i = 0; i < boxes.length; i++) {
            boxes[i][1] = new Spot(0, 1, new Pawn(true));
            boxes[i][5] = new Spot(0, 1, new Pawn(false));
            boxes[i][7] = new Spot(0, 1, new Pawn(false));
        }
        for (int i = 1; i < boxes.length; i++) {
            boxes[i][0] = new Spot(0, 1, new Pawn(true));
            boxes[i][2] = new Spot(0, 1, new Pawn(true));
            boxes[i][6] = new Spot(0, 1, new Pawn(false));
        }
        draw.draw(boxes);
    }
}

class Draw{
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
        int xCordinate = scanner1.nextInt();
        System.out.print("Enter Y: ");
        //Scanner scanner2 = new Scanner(System.in);
        int yCordinate = scanner1.nextInt();
    }
}

abstract class Piece{
    public abstract void validMove();
}

class Pawn extends Piece{
    private Spot currentPosition;
    private Spot newPosition;
    boolean one;

    public Pawn(boolean one){
        this.one = one;

    }
    public boolean getOne(){
        return one;
    }
    @Override
    public void validMove() {

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

    public Game(){
        this.players = new Player[2];
        this.board = new Board();
    }

    public void initialize(Player p1, Player p2){
        players[0] = p1;
        players[1] = p2;

        board.startboard();
    }

}

public class Checker{
    public static void main(String[] args) {
        Player p1 = new Player(true);
        Player p2 = new Player(false);
        Game game = new Game();
        game.initialize(p1, p2);


    }
}