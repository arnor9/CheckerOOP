import java.util.Scanner;
class Spot{
    private Piece piece;
    private int x, y;

    public Spot(int x, int y, Piece piece){
        this.piece = piece;
        this.x = x;
        this.y = y;
    }
    public Piece getPiece(){
        return this.piece;
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
class Board {
    private final int SIZE = 8;
    private Spot[][] boxes;
    private int xCord;
    private int yCord;
    private int newXCord;
    private int newYCord;
    public Board() {
        this.boxes = new Spot[SIZE][SIZE];
    }
    public void startBoard() {
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
        draw(boxes);
    }
    public void setCurrentX(int xCord) {
        this.xCord = xCord;
    }
    public int getXCord() {
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
    public void draw(Spot[][] boxes){
        System.out.println("   0 1 2 3 4 5 6 7    <- X-axis");
        System.out.println("  +----------------+");

        for (int i = 0; i < boxes.length; i++) {
            System.out.print(i + " |");
            for (Spot[] box : boxes) {
                System.out.print(box[i] + " ");
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("  +----------------+");
        System.out.println("   0 1 2 3 4 5 6 7");
        System.out.println(" ");
    }
}
abstract class Piece {
    private boolean player1;
    public Piece(boolean player1) {
        this.player1 = player1;
    }
    public boolean isPlayer1() {
        return player1;
    }
}

class Pawn extends Piece {
    public Pawn(boolean player1) {
        super(player1);
    }
}

public class Checker {
    private Board board;
    private Spot[][] boxes;
    private Piece pieceToMove;
    private boolean isPlayer1Turn = true;
    private boolean isGameActive = true;
    public Checker(Board board) {
        this.board = board;
        this.boxes = board.getBoxes();
    }
    public void initialize() {
        board.startBoard();
    }
    public void inputHandler() {
        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;
        while (!validInput) {
            if (isPlayer1Turn) {
                System.out.println("Turn of player no. 1");
            } else {
                System.out.println("Turn of player no. 2");
            }
            System.out.println("Coordinate of piece to move");
            int oldX = getInput("    Enter X: ", scanner);
            int oldY = getInput("    Enter Y:", scanner);
            System.out.println("Coordinate of new position");
            int newX = getInput("    Enter X:", scanner);
            int newY = getInput("    Enter Y:", scanner);
            if (oldX > 7 || oldX < -1 && oldY > 7 || oldY < -1 && newX > 7 || newX < -1 && newY > 7 || newY < -1) {
                System.out.println("\nThis was an invalid move, try again! \n");
                board.draw(boxes);
            } else {
                board.setCurrentX(oldX);
                board.setCurrentY(oldY);
                board.setNewX(newX);
                board.setNewY(newY);
                validInput = true;
            }
        }
    }
    public int getInput(String message, Scanner scanner) {
        System.out.print(message);
        int temp = scanner.nextInt();
        if (temp == -1) {
            endGame();
        }
        return temp;
    }
    public boolean isGameActive() {
        return isGameActive;
    }
    public void endGame() {
        System.exit(0);
    }
    public boolean validMove(int currentX, int currentY, int newX, int newY, Piece pieceToMove) {
        if (pieceToMove.isPlayer1() == isPlayer1Turn && boxes[newX][newY].getPiece() == null) {
            int xDiff = Math.abs(currentX - newX);
            int yDiff = currentY - newY;
            if (xDiff == 1) {
                if (isPlayer1Turn && yDiff == -1) {
                    return true;
                } else return !isPlayer1Turn && yDiff == 1;
            }
        }
        return false;
    }
    public void move() {
        if (board.getXCord() >= 7 || board.getXCord() <= 0 && board.getYCord() >= 7 || board.getYCord() <= 0) {
            System.out.println("This is a invalid move, try again");
        }
        pieceToMove = boxes[board.getXCord()][board.getYCord()].getPiece();
        if (validMove(board.getXCord(), board.getYCord(), board.getNewXCord(), board.getNewYCord(), pieceToMove)) {
            boxes[board.getXCord()][board.getYCord()] = new Spot(board.getXCord(), board.getYCord(), null);
            boxes[board.getNewXCord()][board.getNewYCord()] = new Spot(board.getNewXCord(), board.getNewYCord(), pieceToMove);
            board.draw(boxes);
            System.out.println("Piece moved! \n");
            toggleTurn();
        } else {
            System.out.println("\nThis was an invalid move, try again! \n");
            board.draw(boxes);
        }
    }
    public void toggleTurn() {
        isPlayer1Turn = !isPlayer1Turn;
    }
    public static void main(String[] args) {
        Board board = new Board();
        Checker checker = new Checker(board);
        checker.initialize();
        while (checker.isGameActive()) {
            checker.inputHandler();
            checker.move();
        }
        System.out.println("Game over!");
    }
}