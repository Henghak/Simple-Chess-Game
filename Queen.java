
import java.util.*;

public class Queen extends Piece {
    private Rook rook;
    private Bishop bishop;
    
    public Queen(boolean color, Square square) {
        super(color, square, color ? "pngs/WhiteQueen.png" : "pngs/BlackQueen.png");
        this.rook = new Rook(color, super.getCurrentSquare());
        this.bishop = new Bishop(color, super.getCurrentSquare());
        super.setName("Queen");
    }
    
    public Rook getRook() {
        return this.rook;
    }
    
    public Bishop getBishop() {
        return this.bishop;
    }
    
    @Override
    public void setCurrentSquare(Square newsquare) {
        super.setCurrentSquare(newsquare);
        rook.setCurrentSquare(super.getCurrentSquare());
        bishop.setCurrentSquare(super.getCurrentSquare());
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
            Square initialsquare = super.getCurrentSquare();
            end.setPiece(this);
            super.setCurrentSquare(end);
            rook.setCurrentSquare(super.getCurrentSquare());
            bishop.setCurrentSquare(super.getCurrentSquare());
            initialsquare.reset();         
        } 
    }

    @Override
    public List<Coordinate> possibleMoves(GameBoard gameboard) {
        List<Coordinate> result = new ArrayList<>();
        List<Coordinate> rookmoves = this.rook.possibleMoves(gameboard);
        List<Coordinate> bishopmoves = this.bishop.possibleMoves(gameboard);
        result.addAll(rookmoves);
        result.addAll(bishopmoves);
        return result;
    }   

}
