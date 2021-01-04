import java.util.*;

public class Rook extends Piece {
    private boolean hasMoved;
    
    public Rook(boolean white, Square square) {
        super(white, square, white ? "pngs/WhiteRook.png" : "pngs/BlackRook.png");
        hasMoved = false;
        super.setName("Rook");
    }
    
    public boolean getHasMoved() {
        return this.hasMoved;
    }
    
    public void setHasMoved(boolean hasmoved) {
        this.hasMoved = hasmoved;
    }

    @Override
    public void move(GameBoard gameboard, Square end) {
        if (super.isValidMove(gameboard, end)) {
            if (end.getPiece() != null) {
                if (super.isWhite()) {
                    List<Piece> list = gameboard.getBlackPieces();
                    list.remove(list.indexOf(end.getPiece()));
                } else {
                    List<Piece> list = gameboard.getWhitePieces();
                    list.remove(list.indexOf(end.getPiece())); 
                }
            }
            super.getCurrentSquare().reset();
            super.setCurrentSquare(end); 
            end.setPiece(this);
            this.hasMoved = true;
        } 
    }

    @Override
    public List<Coordinate> possibleMoves(GameBoard gameboard) {
        List<Coordinate> result = new ArrayList<>();
        Coordinate start = super.getCurrentSquare().getCoordinate();  
        Square[][] board = gameboard.getBoard();
        //check moves going up
        int counter = -1;
        while (super.canOffset(gameboard, start, 0, counter)) {
            result.add(board[start.getY() + counter][start.getX()].getCoordinate());
            if (board[start.getY() + counter][start.getX()].getPiece() != null) {
                if (board[start.getY() + counter][start.getX()]
                        .getPiece().isWhite() != super.isWhite()) {
                    break;
                }
            }                
            counter--;
        }
        //check moves going down
        counter = 1;
        while (super.canOffset(gameboard, start, 0, counter)) {
            result.add(board[start.getY() + counter][start.getX()].getCoordinate());
            if (board[start.getY() + counter][start.getX()].getPiece() != null) {
                if (board[start.getY() + counter][start.getX()]
                        .getPiece().isWhite() != super.isWhite()) {
                    break;
                }
            } 
            counter++;
        }
        //check moves going left
        counter = -1;
        while (super.canOffset(gameboard, start, counter, 0)) {
            result.add(board[start.getY()][start.getX() + counter].getCoordinate());
            if (board[start.getY()][start.getX() + counter].getPiece() != null) {
                if (board[start.getY()][start.getX() + counter]
                        .getPiece().isWhite() != super.isWhite()) {
                    break;
                }
            } 
            counter--;
        }
        //check moves going right
        counter = 1;
        while (super.canOffset(gameboard, start, counter, 0)) {
            result.add(board[start.getY()][start.getX() + counter].getCoordinate());
            if (board[start.getY()][start.getX() + counter].getPiece() != null) {
                if (board[start.getY()][start.getX() + counter]
                        .getPiece().isWhite() != super.isWhite()) {
                    break;
                }
            } 
            counter++;
        }
        return result;
    }
}
