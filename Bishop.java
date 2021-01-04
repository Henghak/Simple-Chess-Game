
import java.util.*;

public class Bishop extends Piece {
    
    public Bishop(boolean color, Square square) {
        super(color, square, color ? "pngs/WhiteBishop.png" : "pngs/BlackBishop.png");
        super.setName("Bishop");
    }

    @Override
    public List<Coordinate> possibleMoves(GameBoard gameboard) {
        List<Coordinate> result = new ArrayList<>();
        Coordinate start = super.getCurrentSquare().getCoordinate();
        Square[][] board = gameboard.getBoard();
        int counterx = 1;
        int countery = 1;
        // adding bottom-right diagonal moves
        while (super.canOffset(gameboard, start, counterx, countery)) {
            result.add(board[start.getY() + countery][start.getX() + counterx].getCoordinate());
            if (board[start.getY() + countery][start.getX() + counterx].getPiece() != null) {
                if (board[start.getY() + countery][start.getX() + counterx]
                        .getPiece().isWhite() != super.isWhite()) {
                    break;
                }
            }
            counterx++;
            countery++;           
        }
        //adding bottom-left diagonal moves
        counterx = -1;
        countery = 1;
        while (super.canOffset(gameboard, start, counterx, countery)) {
            result.add(board[start.getY() + countery][start.getX() + counterx].getCoordinate());
            if (board[start.getY() + countery][start.getX() + counterx].getPiece() != null) {
                if (board[start.getY() + countery][start.getX() + counterx]
                        .getPiece().isWhite() != super.isWhite()) {
                    break;
                }
            }
            counterx--;
            countery++;           
        }
        //adding top-right diagonal moves
        counterx = 1;
        countery = -1;
        while (super.canOffset(gameboard, start, counterx, countery)) {
            result.add(board[start.getY() + countery][start.getX() + counterx].getCoordinate());
            if (board[start.getY() + countery][start.getX() + counterx].getPiece() != null) {
                if (board[start.getY() + countery][start.getX() + counterx]
                        .getPiece().isWhite() != super.isWhite()) {
                    break;
                }
            }
            counterx++;
            countery--;            
        }
        //adding top-left diagonal moves
        counterx = -1;
        countery = -1;
        while (super.canOffset(gameboard, start, counterx, countery)) {
            result.add(board[start.getY() + countery][start.getX() + counterx].getCoordinate());
            if (board[start.getY() + countery][start.getX() + counterx].getPiece() != null) {
                if (board[start.getY() + countery][start.getX() + counterx]
                        .getPiece().isWhite() != super.isWhite()) {
                    break;
                }
            }
            counterx--;
            countery--;            
        }
        
        return result;
    }  

}
