
import java.util.*;

public class King extends Piece {
    private boolean hasMoved;
    
    public King(boolean color, Square square) {
        super(color, square, color ? "pngs/WhiteKing.png" : "pngs/BlackKing.png");
        hasMoved = false;
        super.setName("King");
    }
    
    public boolean getHasMoved() {
        return this.hasMoved;
    }
    
    public void setHasMoved(boolean hasmoved) {
        this.hasMoved = hasmoved;
    }

    @Override
    public void move(GameBoard gameboard, Square end) {
        Square[][] board = gameboard.getBoard();
        if (super.isValidMove(gameboard, end)) {
            //check for castles
            if (!this.hasMoved && (end == board[7][7] || end == board[7][0] 
                    || end == board[0][7] || end == board[0][0])) {
                castle(gameboard, end);
            } else {
                if (end.getPiece() != null) {
                    if (super.isWhite()) {
                        List<Piece> list = gameboard.getBlackPieces();
                        list.remove(list.indexOf(end.getPiece()));
                    } else {
                        List<Piece> list = gameboard.getWhitePieces();
                        list.remove(list.indexOf(end.getPiece())); 
                    }
                }
                end.setPiece(super.getCurrentSquare().getPiece());
                super.getCurrentSquare().reset();
                super.setCurrentSquare(end);
            }
            this.hasMoved = true;
        } 
    }
    
    @Override
    public List<Coordinate> possibleMoves(GameBoard gameboard) {
        List<Coordinate> result = new ArrayList<>();
        Coordinate start = super.getCurrentSquare().getCoordinate();
        List<Coordinate> badSquares = dangerousCoors(gameboard);
        Square[][] board = gameboard.getBoard();
        
        //checking to castle
        if (!this.hasMoved) {
            if (super.isWhite()) {
                if (board[7][7].getPiece() != null && board[7][7].getPiece() instanceof Rook) {
                    Rook rook = (Rook)board[7][7].getPiece();
                    if (!rook.getHasMoved() && !badSquares.contains(board[7][6].getCoordinate()) 
                            && !badSquares.contains(board[7][5].getCoordinate())
                            && board[7][6].getPiece() == null && board[7][5].getPiece() == null) {
                        result.add(board[7][7].getCoordinate());
                    }
                }
                if (board[7][0].getPiece() != null && board[7][0].getPiece() instanceof Rook) {
                    Rook rook = (Rook)board[7][0].getPiece();
                    if (!rook.getHasMoved() && !badSquares.contains(board[7][2].getCoordinate()) 
                            && !badSquares.contains(board[7][3].getCoordinate())
                            && board[7][2].getPiece() == null && board[7][3].getPiece() == null) {
                        result.add(board[7][0].getCoordinate());
                    }
                }
            } else {
                if (board[0][7].getPiece() != null && board[0][7].getPiece() instanceof Rook) {
                    Rook rook = (Rook)board[0][7].getPiece();
                    if (!rook.getHasMoved() && !badSquares.contains(board[0][6].getCoordinate()) 
                            && !badSquares.contains(board[0][5].getCoordinate())
                            && board[0][6].getPiece() == null && board[0][5].getPiece() == null) {
                        result.add(board[0][7].getCoordinate());
                    }
                }
                if (board[0][0].getPiece() != null && board[0][0].getPiece() instanceof Rook) {
                    Rook rook = (Rook)board[0][0].getPiece();
                    if (!rook.getHasMoved() && !badSquares.contains(board[0][2].getCoordinate()) 
                            && !badSquares.contains(board[0][3].getCoordinate())
                            && board[0][2].getPiece() == null && board[0][3].getPiece() == null) {
                        result.add(board[0][0].getCoordinate());
                    }
                }
            }
        }
        
        //checking normal single moves (doesn't check if capture is possible)
        if (super.canOffset(gameboard, start, 1, 1) 
                && !badSquares.contains(board[start.getY() + 1][start.getX() + 1]
                        .getCoordinate())) {
            result.add(board[start.getY() + 1][start.getX() + 1].getCoordinate());
        }
        if (super.canOffset(gameboard, start, 0, 1) 
                && !badSquares.contains(board[start.getY() + 1][start.getX()].getCoordinate())) {
            result.add(board[start.getY() + 1][start.getX()].getCoordinate());
        }
        if (super.canOffset(gameboard, start, 1, 0) 
                && !badSquares.contains(board[start.getY()][start.getX() + 1].getCoordinate())) {
            result.add(board[start.getY()][start.getX() + 1].getCoordinate());
        }
        if (super.canOffset(gameboard, start, -1, -1) 
                && !badSquares.contains(board[start.getY() - 1][start.getX() - 1]
                        .getCoordinate())) {
            result.add(board[start.getY() - 1][start.getX() - 1].getCoordinate());
        }
        if (super.canOffset(gameboard, start, -1, 1) 
                && !badSquares.contains(board[start.getY() + 1][start.getX() - 1]
                        .getCoordinate())) {
            result.add(board[start.getY() + 1][start.getX() - 1].getCoordinate());
        }
        if (super.canOffset(gameboard, start, 1, -1) 
                && !badSquares.contains(board[start.getY() - 1][start.getX() + 1]
                        .getCoordinate())) {
            result.add(board[start.getY() - 1][start.getX() + 1].getCoordinate());
        }
        if (super.canOffset(gameboard, start, -1, 0) 
                && !badSquares.contains(board[start.getY()][start.getX() - 1].getCoordinate())) {
            result.add(board[start.getY()][start.getX() - 1].getCoordinate());
        }
        if (super.canOffset(gameboard, start, 0, -1) 
                && !badSquares.contains(board[start.getY() - 1][start.getX()].getCoordinate())) {
            result.add(board[start.getY() - 1][start.getX()].getCoordinate());
        }
        return result; 
    }
    
    //Coordinates restricting the king's movement
    public List<Coordinate> dangerousCoors(GameBoard gameboard) {
        return super.isWhite() ? gameboard.getBlackPlayerMoves() : gameboard.getWhitePlayerMoves();
    }
    
    //Checks if the king is in check
    public boolean inCheck(GameBoard gameboard) {
        List<Coordinate> badCoors = this.dangerousCoors(gameboard);
        if (badCoors.contains(super.getCurrentSquare().getCoordinate())) {
            return true;
        }
        return false;
    }
    
    
    //Castle function, doesn't check for danger beforehand
    public void castle(GameBoard gameboard, Square rooksquare) {
        Rook rook = (Rook)rooksquare.getPiece();
        Coordinate rookcoors = rooksquare.getCoordinate();
        Square[][] board = gameboard.getBoard();
        if (super.isWhite()) {
            King king = (King)board[7][4].getPiece();
            if (rookcoors.getX() == 7) {
                rook.move(gameboard, board[7][5]);
                board[7][6].setPiece(king);
                super.setCurrentSquare(board[7][6]);
                board[7][4].reset();               
            }
            if (rookcoors.getX() == 0) {
                rook.move(gameboard, board[7][3]);
                board[7][2].setPiece(king);
                super.setCurrentSquare(board[7][2]);
                board[7][4].reset(); 
            }
        } else {
            King king = (King)board[0][4].getPiece();
            if (rookcoors.getX() == 7) {
                rook.move(gameboard, board[0][5]);
                board[0][6].setPiece(king);
                super.setCurrentSquare(board[0][6]);
                board[0][4].reset();               
            }
            if (rookcoors.getX() == 0) {
                rook.move(gameboard, board[0][3]);
                board[0][2].setPiece(king);
                super.setCurrentSquare(board[0][2]);
                board[0][4].reset(); 
            }    
        }
        this.hasMoved = true;
    }
}
