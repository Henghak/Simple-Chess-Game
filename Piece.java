import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;

public abstract class Piece {
    private boolean white;
    private String name;
    private Square currentsquare;
    private BufferedImage img;
    
    public Piece(boolean color, Square square, String img) {
        this.white = color;
        this.currentsquare = square;
        try {
            if (this.img == null) {
                this.img = ImageIO.read(new File(img));
            }
        } catch (IOException e) {
            System.out.println("Image Path Not Found: " + e.getMessage());
        }
    }
    
    public boolean isWhite() {
        return this.white;
    }
    
    //Checking if an offset will leave the board and if 
    // it can move there (special case for pawn & king)
    public boolean canOffset(GameBoard gameboard, Coordinate start, int offsetX, int offsetY) {
        int currentX = start.getX();
        int currentY = start.getY();
        if (currentX + offsetX <= 7 && currentX + offsetX >= 0 &&
                currentY + offsetY <= 7 && currentY + offsetY >= 0) {
            Square[][] board = gameboard.getBoard();
            Square endsquare = board [currentY + offsetY][currentX + offsetX];
            if (endsquare.getPiece() == null || endsquare.getPiece().isWhite() != this.white) {
                return true;
            } 
        }
        return false;
    }
    
    public void draw(Graphics g) {
        if (this.currentsquare != null) {
            Coordinate c = this.currentsquare.getCoordinate();
            g.drawImage(this.img, c.getX() * 80 + 10, c.getY() * 80 + 10, 60, 60, null);
        }
    }
       
    public boolean isValidMove(GameBoard gameboard, Square end) {
        List<Coordinate> movelist = this.possibleMoves(gameboard);
        Coordinate endcoors = end.getCoordinate();
        if (movelist.indexOf(endcoors) != -1) {
            return true;
        }
        return false;
    }
    public void move(GameBoard gameboard, Square end) {
        if (this.isValidMove(gameboard, end)) {
            if (end.getPiece() != null) {
                if (this.white) {
                    List<Piece> list = gameboard.getBlackPieces();
                    list.remove(list.indexOf(end.getPiece()));
                } else {
                    List<Piece> list = gameboard.getWhitePieces();
                    list.remove(list.indexOf(end.getPiece())); 
                }
            }
            this.currentsquare.reset();
            this.currentsquare = end;
            end.setPiece(this);
        } 
    }
    public abstract List<Coordinate> possibleMoves(GameBoard gameboard);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Square getCurrentSquare() {
        return this.currentsquare;
    }

    public void setCurrentSquare(Square newsquare) {
        this.currentsquare = newsquare;
    }

}
