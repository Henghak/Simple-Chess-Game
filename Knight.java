
import java.util.*;

public class Knight extends Piece {

    public Knight(boolean color, Square square) {
        super(color, square, color ? "pngs/WhiteKnight.png" : "pngs/BlackKnight.png");
        super.setName("Knight");
    }

    @Override
    public List<Coordinate> possibleMoves(GameBoard gameboard) {
        List<Coordinate> list = new ArrayList<>();
        Square[][] board = gameboard.getBoard();
        Coordinate start = super.getCurrentSquare().getCoordinate();
        if (super.canOffset(gameboard, start, -1, 2)) {
            list.add(board[start.getY() + 2][start.getX() - 1].getCoordinate());
        }
        if (super.canOffset(gameboard, start, -1, -2)) {
            list.add(board[start.getY() - 2][start.getX() - 1].getCoordinate());
        }
        if (super.canOffset(gameboard, start, 1, 2)) {
            list.add(board[start.getY() + 2][start.getX() + 1].getCoordinate());
        }
        if (super.canOffset(gameboard, start, 1, -2)) {
            list.add(board[start.getY() - 2][start.getX() + 1].getCoordinate());
        }
        if (super.canOffset(gameboard, start, 2, 1)) {
            list.add(board[start.getY() + 1][start.getX() + 2].getCoordinate());
        }
        if (super.canOffset(gameboard, start, 2, -1)) {
            list.add(board[start.getY() - 1][start.getX() + 2].getCoordinate());
        }
        if (super.canOffset(gameboard, start, -2, 1)) {
            list.add(board[start.getY() + 1][start.getX() - 2].getCoordinate());
        }
        if (super.canOffset(gameboard, start, -2, -1)) {
            list.add(board[start.getY() - 1][start.getX() - 2].getCoordinate());
        }
        
        return list;
    }
    

}
