
import java.util.*;

public class Pawn extends Piece {
    private boolean hasMoved;
    
    public Pawn(boolean color, Square square) {
        super(color, square, color ? "pngs/WhitePawn.png" : "pngs/BlackPawn.png");
        super.setName("Pawn");
        this.hasMoved = false;
    }
    
    public boolean getHasMoved() {
        return this.hasMoved;
    }
    
    public void setHasMoved(boolean hasmoved) {
        this.hasMoved = hasmoved;
    }

    @Override
    public void move(GameBoard gameboard, Square end) {
        if (this.isValidMove(gameboard, end)) {
            Coordinate c = end.getCoordinate();
            List<Piece> list = super.isWhite() ? gameboard.getBlackPieces() 
                    : gameboard.getWhitePieces(); 
            if (end.getPiece() != null) {             
                list.remove(list.indexOf(end.getPiece()));
            }
            //promotion
            if (c.getY() == 7) {
                List<Piece> blackPieces = gameboard.getBlackPieces();
                Queen queen = new Queen(false, end);
                end.setPiece(queen);
                super.getCurrentSquare().reset();
                blackPieces.remove(blackPieces.indexOf(this));
                blackPieces.add(queen);
            } else if (c.getY() == 0) {
                List<Piece> whitePieces = gameboard.getWhitePieces();
                Queen queen = new Queen(true, end);
                end.setPiece(queen);
                super.getCurrentSquare().reset();
                whitePieces.remove(whitePieces.indexOf(this));
                whitePieces.add(queen);
            } else {
                end.setPiece(super.getCurrentSquare().getPiece());
                super.getCurrentSquare().reset();
                super.setCurrentSquare(end);
            }          
            hasMoved = true;
        } 
    }
    @Override
    public List<Coordinate> possibleMoves(GameBoard gameboard) {
        List<Coordinate> list = new ArrayList<>();
        Coordinate start = super.getCurrentSquare().getCoordinate();
        Square[][] board = gameboard.getBoard();
        
        //separate white and black
        if (super.isWhite()) {  
            //moving forward
            if (super.canOffset(gameboard, start, 0, -1)) {
                Square oneUp = board[start.getY() - 1][start.getX()];
                if (oneUp.getPiece() == null) {
                    list.add(oneUp.getCoordinate());
                    if (!this.hasMoved && super.canOffset(gameboard, start, 0, -2)) {
                        Square twoUp = board[start.getY() - 2][start.getX()];
                        if (twoUp.getPiece() == null) {
                            list.add(twoUp.getCoordinate());
                        }
                    }
                }
            }
            if (super.canOffset(gameboard, start, -1, -1)) {
                Square endsquare = board [start.getY() - 1][start.getX() - 1];
                if (endsquare.getPiece() != null && !endsquare.getPiece().isWhite()) {
                    list.add(endsquare.getCoordinate());
                }
            }
            if (super.canOffset(gameboard, start, 1, -1)) {
                Square endsquare = board [start.getY() - 1][start.getX() + 1];
                if (endsquare.getPiece() != null && !endsquare.getPiece().isWhite()) {
                    list.add(endsquare.getCoordinate());
                }
            }
        } else {
            if (super.canOffset(gameboard, start, 0, 1)) {
                Square oneUp = board[start.getY() + 1][start.getX()];
                if (oneUp.getPiece() == null) {
                    list.add(oneUp.getCoordinate());
                    if (!this.hasMoved && super.canOffset(gameboard, start, 0, 2)) {
                        Square twoUp = board[start.getY() + 2][start.getX()];
                        if (twoUp.getPiece() == null) { 
                            list.add(twoUp.getCoordinate());
                        }
                    }
                }
            }
            if (super.canOffset(gameboard, start, -1, 1)) {
                Square endsquare = board [start.getY() + 1][start.getX() - 1];
                if (endsquare.getPiece() != null && endsquare.getPiece().isWhite()) {
                    list.add(endsquare.getCoordinate());
                }
            }
            if (super.canOffset(gameboard, start, 1, 1)) {
                Square endsquare = board [start.getY() + 1][start.getX() + 1];
                if (endsquare.getPiece() != null && endsquare.getPiece().isWhite()) {
                    list.add(endsquare.getCoordinate());
                }
            }
        }        
        return list;
    }
    
}
