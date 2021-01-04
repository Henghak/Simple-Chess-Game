import java.util.List;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class GameBoard extends JComponent implements MouseListener {
    private Square[][] board;
    private List<Piece> blackPieces;
    private List<Piece> whitePieces;
    private boolean whitePlayerTurn;
    private List<Coordinate> whitePlayerMoves;
    private List<Coordinate> blackPlayerMoves; 
    private Square previousClick;
    private King blackKing;
    private King whiteKing;
    private Piece lastPieceCaptured;
    private boolean lastMoveCapture;
    private Square lastMoveFinal;
    private Square lastMoveInit;
    
    public GameBoard() {
        this.board = new Square[8][8];
        this.whitePlayerTurn = true;
        this.lastMoveCapture = false;
        boolean currentcolor = false;
        blackPieces = new ArrayList<>();
        whitePieces = new ArrayList<>();
        //adding squares to 2-D array
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Coordinate currentcoors = new Coordinate(col, row);
                board[row][col] = new Square(null, currentcolor, currentcoors);
                currentcolor = !currentcolor;
            }
            currentcolor = !currentcolor;
        }
        addPieces();
        updateWhitePlayerMoves();
        updateBlackPlayerMoves();
        System.out.println("White Player's Turn");
        this.addMouseListener(this);
    }
    
    public void addPieces() {
        //adding pawns
        for (int col = 0; col < 8; col++) {
            board[1][col].setPiece(new Pawn(false, board[1][col]));
            board[6][col].setPiece(new Pawn(true, board[6][col]));
            blackPieces.add(board[1][col].getPiece());
            whitePieces.add(board[6][col].getPiece());
        }
        //adding rooks
        board[0][0].setPiece(new Rook(false, board[0][0]));
        board[0][7].setPiece(new Rook(false, board[0][7]));
        board[7][0].setPiece(new Rook(true, board[7][0]));
        board[7][7].setPiece(new Rook(true, board[7][7]));
        blackPieces.add(board[0][0].getPiece());
        blackPieces.add(board[0][7].getPiece());
        whitePieces.add(board[7][0].getPiece());
        whitePieces.add(board[7][7].getPiece());
        //adding knights
        board[0][1].setPiece(new Knight(false, board[0][1]));
        board[0][6].setPiece(new Knight(false, board[0][6]));
        board[7][1].setPiece(new Knight(true, board[7][1]));
        board[7][6].setPiece(new Knight(true, board[7][6]));
        blackPieces.add(board[0][1].getPiece());
        blackPieces.add(board[0][6].getPiece());
        whitePieces.add(board[7][1].getPiece());
        whitePieces.add(board[7][6].getPiece());
        //adding bishops
        board[0][2].setPiece(new Bishop(false, board[0][2]));
        board[0][5].setPiece(new Bishop(false, board[0][5]));
        board[7][2].setPiece(new Bishop(true, board[7][2]));
        board[7][5].setPiece(new Bishop(true, board[7][5]));
        blackPieces.add(board[0][2].getPiece());
        blackPieces.add(board[0][5].getPiece());
        whitePieces.add(board[7][2].getPiece());
        whitePieces.add(board[7][5].getPiece());
        //adding queens
        board[0][3].setPiece(new Queen(false, board[0][3]));
        board[7][3].setPiece(new Queen(true, board[7][3]));
        blackPieces.add(board[0][3].getPiece());
        whitePieces.add(board[7][3].getPiece());
        //adding kings
        board[0][4].setPiece(new King(false, board[0][4]));
        board[7][4].setPiece(new King(true, board[7][4]));
        blackPieces.add(board[0][4].getPiece());
        whitePieces.add(board[7][4].getPiece());
        this.blackKing = (King) board[0][4].getPiece();
        this.whiteKing = (King) board[7][4].getPiece();
    }
    
    public void updateWhitePlayerMoves() {
        List<Coordinate> result = new ArrayList<>();
        for (Piece p : this.whitePieces) {
            if (!(p instanceof King)) {
                result.addAll(p.possibleMoves(this));
            }
        }
        whitePlayerMoves = result;
    }
    
    public void updateBlackPlayerMoves() {
        List<Coordinate> result = new ArrayList<>();
        for (Piece p : this.blackPieces) {
            if (!(p instanceof King)) {
                result.addAll(p.possibleMoves(this));
            }
        }
        blackPlayerMoves = result;
    }
    
    
    public void boardPrint() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Square current = board[row][col];
                String squarecolor = current.getColor() ? "Black" : "White";
                if (current.getPiece() != null) {                   
                    String piececolor = current.getPiece().isWhite() ? "White" : "Black";
                    System.out.print("(" + current.getCoordinate().getX() + "," 
                            + current.getCoordinate().getY() + ") " 
                                 + squarecolor + " square, " 
                                 + piececolor + " " + current.getPiece().getName() + " ;; ");
                } else {
                    System.out.print("(" + current.getCoordinate().getX() + "," 
                            + current.getCoordinate().getY() + ") "
                            + squarecolor + " square ;;");
                }
            }
            System.out.println();
        }
    }

    public Square[][] getBoard() {
        return this.board;
    }
    
    public List<Piece> getBlackPieces() {
        return this.blackPieces;
    }
    
    public List<Piece> getWhitePieces() {
        return this.whitePieces;
    }

    public List<Coordinate> getWhitePlayerMoves() {
        return this.whitePlayerMoves;
    }
    
    public List<Coordinate> getBlackPlayerMoves() {
        return this.blackPlayerMoves;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        int currentX = 0;
        int currentY = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col].getColor()) {
                    g.setColor(Color.gray);
                } else {
                    g.setColor(Color.LIGHT_GRAY);
                }
                g.fillRect(currentX, currentY, 80, 80);
                currentX += 80;
            }
            currentY += 80;
            currentX = 0;
        }
        for (Piece p : this.whitePieces) {
            p.draw(g);
        }
        for (Piece p : this.blackPieces) {
            p.draw(g);
        }
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 800);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point p = e.getPoint();
        Coordinate c = new Coordinate(p.x / 80, p.y / 80);
        if (previousClick == null) {
            Square s = this.board[c.getY()][c.getX()];
            System.out.print("(" + c.getX() + "," + c.getY() + ") ");
            if (s.getPiece() != null && s.getPiece().isWhite() == this.whitePlayerTurn) {
                System.out.println("Piece Selected");
                this.previousClick = s;
            } else {
                System.out.print("Invalid Piece  ");
            }
        } else {
            Square end = this.board[c.getY()][c.getX()];
            Piece pie = this.previousClick.getPiece();
            if (pie.isValidMove(this, end)) { 
                this.lastMoveInit = this.previousClick;
                //capture?
                if (end.getPiece() != null) {
                    this.lastPieceCaptured = end.getPiece();
                    this.lastMoveCapture = true;
                } else {
                    this.lastMoveCapture = false;
                }
                pie.move(this, end);
                updateWhitePlayerMoves();
                updateBlackPlayerMoves();
                this.lastMoveFinal = end;
                //undo-ing if move made puts own king in check
                if ((this.whitePlayerTurn && this.whiteKing.inCheck(this))
                        || (!this.whitePlayerTurn && this.blackKing.inCheck(this))) {
                    System.out.print("Puts own King in check, " + "whiteKing in check: " 
                        + this.whiteKing.inCheck(this) + ", blackKing in check: " 
                            + this.blackKing.inCheck(this) + ", ");
                    this.undoLastMove();
                    updateWhitePlayerMoves();
                    updateBlackPlayerMoves();
                    this.whitePlayerTurn = !this.whitePlayerTurn;
                } else {   
                    repaint();
                    boolean temp = false;
                    this.whitePlayerTurn = !this.whitePlayerTurn;
                    if (this.blackKing.inCheck(this)) {
                        System.out.println("BLACK IS IN CHECK");
                        temp = true;
                    }
                    if (this.whiteKing.inCheck(this)) {
                        System.out.println("WHITE IS IN CHECK");
                        temp = true;
                    }
                    if (temp && this.isCheckmate()) {
                        System.out.print(this.whitePlayerTurn ? "WHITE KING IS CHECKMATED!" 
                                : "BLACK KING IS CHECKMATED!");
                        System.out.println(" GAME OVER!");
                        return;
                    }
                }
                System.out.println(this.whitePlayerTurn ? "White Player's Turn" 
                        : "Black Player's Turn");
            } else {
                System.out.println("Invalid move to (" + c.getX() + "," + c.getY() + ")");
            }
            this.previousClick = null;
        }
    }
    
    public void undoLastMove() {
        if (this.lastMoveFinal.getPiece() == null || this.lastMoveInit.getPiece() != null) {
            return;
        }
        System.out.println("Undo");

        Piece p = this.lastMoveFinal.getPiece();
        p.setCurrentSquare(this.lastMoveInit);
        this.lastMoveInit.setPiece(p);
        this.lastMoveFinal.reset();

        if (this.lastMoveCapture) {
            this.lastMoveFinal.setPiece(this.lastPieceCaptured);
            if (this.whitePlayerTurn) {
                this.whitePieces.add(lastPieceCaptured);
            } else {
                this.blackPieces.add(lastPieceCaptured);
            }
        }
        this.lastMoveCapture = false;
        this.updateBlackPlayerMoves();
        this.updateWhitePlayerMoves();
        this.whitePlayerTurn = !this.whitePlayerTurn;
        repaint();
    }
    
    public boolean isCheckmate() {
        List<Piece> moves = this.whitePlayerTurn ? this.whitePieces : this.blackPieces;
        for (Piece p : moves) {
            Square init = p.getCurrentSquare();
            for (Coordinate c : p.possibleMoves(this)) {
                if (board[c.getY()][c.getX()].getPiece() == null) {
                    boolean hasMoved = false;
                    if (p instanceof Pawn) {
                        Pawn paw = (Pawn)p;
                        hasMoved = paw.getHasMoved();
                    }
                    if (p instanceof King) {
                        King kin = (King)p;
                        hasMoved = kin.getHasMoved();
                    }
                    if (p instanceof Rook) {
                        Rook rok = (Rook)p;
                        hasMoved = rok.getHasMoved();
                    }
                    p.move(this, board[c.getY()][c.getX()]);
                    if (p instanceof Pawn) {
                        Pawn paw = (Pawn)p;
                        paw.setHasMoved(hasMoved);
                    }
                    if (p instanceof King) {
                        King kin = (King)p;
                        kin.setHasMoved(hasMoved);
                    }
                    if (p instanceof Rook) {
                        Rook rok = (Rook)p;
                        rok.setHasMoved(hasMoved);
                    }
                    this.updateBlackPlayerMoves();
                    this.updateWhitePlayerMoves();
                    if (this.whitePlayerTurn ? !this.whiteKing.inCheck(this) 
                            : !this.blackKing.inCheck(this)) {
                        board[c.getY()][c.getX()].reset();
                        init.setPiece(p);
                        p.setCurrentSquare(init);     
                        this.updateBlackPlayerMoves();
                        this.updateWhitePlayerMoves();
                        return false;
                    }
                    board[c.getY()][c.getX()].reset();
                    init.setPiece(p);
                    p.setCurrentSquare(init);
                    this.updateBlackPlayerMoves();
                    this.updateWhitePlayerMoves();
                } else {
                    Piece canCapture = board[c.getY()][c.getX()].getPiece();
                    boolean hasMoved = false;
                    if (p instanceof Pawn) {
                        Pawn paw = (Pawn)p;
                        hasMoved = paw.getHasMoved();
                    }
                    if (p instanceof King) {
                        King kin = (King)p;
                        hasMoved = kin.getHasMoved();
                    }
                    if (p instanceof Rook) {
                        Rook rok = (Rook)p;
                        hasMoved = rok.getHasMoved();
                    }
                    p.move(this, board[c.getY()][c.getX()]);
                    this.updateBlackPlayerMoves();
                    this.updateWhitePlayerMoves();
                    
                    if (p instanceof Pawn) {
                        Pawn paw = (Pawn)p;
                        paw.setHasMoved(hasMoved);
                    }
                    if (p instanceof King) {
                        King kin = (King)p;
                        kin.setHasMoved(hasMoved);
                    }
                    if (p instanceof Rook) {
                        Rook rok = (Rook)p;
                        rok.setHasMoved(hasMoved);
                    }
                    if (this.whitePlayerTurn ? !this.whiteKing.inCheck(this) 
                            : !this.blackKing.inCheck(this)) {
                        board[c.getY()][c.getX()].reset();
                        board[c.getY()][c.getX()].setPiece(canCapture);
                        canCapture.setCurrentSquare(board[c.getY()][c.getX()]);
                        if (canCapture.isWhite()) {
                            this.whitePieces.add(canCapture);
                        } else {
                            this.blackPieces.add(canCapture);
                        }
                        init.setPiece(p);
                        p.setCurrentSquare(init);     
                        this.updateBlackPlayerMoves();
                        this.updateWhitePlayerMoves();
                        return false;
                    }
                    board[c.getY()][c.getX()].setPiece(canCapture);
                    canCapture.setCurrentSquare(board[c.getY()][c.getX()]);
                    if (canCapture.isWhite()) {
                        this.whitePieces.add(canCapture);
                    } else {
                        this.blackPieces.add(canCapture);
                    }
                    init.setPiece(p);
                    p.setCurrentSquare(init);
                    this.updateBlackPlayerMoves();
                    this.updateWhitePlayerMoves();
                }
                
            }
            
        }
        return true;
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {}

    @Override
    public void mouseExited(MouseEvent arg0) {}

    @Override
    public void mousePressed(MouseEvent arg0) {}

    @Override
    public void mouseReleased(MouseEvent arg0) {}
    
}
