
public class Square {
    private Piece currentpiece;
    private boolean isBlack;
    private Coordinate position;
    
    public Square(Piece piece, boolean color, Coordinate position) {
        this.currentpiece = piece;
        this.isBlack = color;
        this.position = position;
    }
    
    public Piece getPiece() {
        return this.currentpiece;
    }
    
    public void setPiece(Piece newpiece) {
        this.currentpiece = newpiece;
    }
    
    public void reset() {
        this.currentpiece = null;
    }

    public boolean getColor() {
        return isBlack;
    }

    public Coordinate getCoordinate() {
        return position;
    }

}
