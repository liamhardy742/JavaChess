import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.ArrayList;

public class Chess extends JPanel{
    
    private static final boolean WHITE = true;
    private static final boolean BLACK = false;
    public static Board board;
    public static int[] mostRecentSquareClicked = new int[]{-1,-1};
    public static boolean moveColor = WHITE;
    public static boolean hasMadeMove = false;
    public static int pieceOrigX = -1;
    public static int pieceOrigY = -1;
    public static int pieceGoingToX = -1;
    public static int pieceGoingToY = -1;
    public static JFrame frame = new MyFrame(board);

    public static void main(String[] args) {
        
        board = new Board(8,8);
        board.addFenPieces("r6k1/5p2/6p1/8/7p/8/6PP/6K1");
        //setUpBoard(board);
        
        frame.setResizable(false);
        frame.add(new Chess());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(480, 505);
        frame.setVisible(true);
        JButton b = new JButton();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                final int row = Integer.valueOf(i);
                final int col = Integer.valueOf(j);
                b = new JButton();
                b.setBounds(i * 60,j * 60, 60, 60);
                b.addActionListener(e -> Chess.squareClicked(row,col,board));
                frame.add(b);
            }
        }

        runGame(board);
        /* Board testBoard = new Board(8,8);
        int[] pos = new int[2];
        pos[0] = 5;
        pos[1] = 4;
        Knight testKnight = new Knight("White",pos,testBoard);
        testBoard.visualize();
        testKnight.getMoves(); */
        
    }

    public static void runGame(Board board) {
        boolean playerToMoveWhite = true;
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> pos;
        Piece movingPiece;
        while(true) {
            board.visualize();
            //get move
            
            if (board.numberOfLegalMoves(true) == 0) {
                System.out.println("Checkmate for black");
                System.exit(0);
            }
            if (board.numberOfLegalMoves(false) == 0) {
                System.out.println("Checkmate for white");
                System.exit(0);
            }
            /* 
            if (moveColor == WHITE) {
                System.out.println("White to move. Enter a move in the form: <column> <rank> to <column> <rank>");
            }
            else {
                System.out.println("Black to move. Enter a move in the form: <column> <rank> to <column> <rank>");
            }
            System.out.print("White has lost these pieces:");
            for (Piece curPiece : board.whiteLostPieces) {
                System.out.print(curPiece);
            }
            System.out.println();
            System.out.print("Black has lost these pieces:");
            for (Piece curPiece : board.blackLostPieces) {
                System.out.print(curPiece);
            } */
            while (!hasMadeMove) {}
            hasMadeMove = false;
            /*
            pos = new ArrayList<>();
            pos.add(pieceGoingToX);
            pos.add(pieceGoingToY);
            movingPiece = board.pieceAt(pieceOrigX,pieceOrigY);
             if (movingPiece == null) {
                System.out.println("No piece on that square, try again.");
                continue;
            }
            if (movingPiece.getColor() != moveColor) {
                if (moveColor == WHITE) {
                    System.out.println("That piece is not white, choose a white piece to move.");
                }
                else {
                    System.out.println("That piece is not black, choose a black piece to move.");
                }
                continue;
            }
            if (!movingPiece.getLegalMoves().contains(pos)) {
                System.out.println("That is not a valid move, try again.");
                continue;
            }
            System.out.println("About to call move"); 
            movingPiece.move(pieceGoingToX,pieceGoingToY);
            
            //swap move color
            if (moveColor == WHITE) {
                moveColor = BLACK;
            }
            else {
                moveColor = WHITE;
            } */
        }
    }

    public static void setUpBoard(Board board) {
        board.addNormalPieces();
    }
    
    public static void squareClicked(int x, int y, Board board) {
        frame.repaint();
        System.out.println("Most recent square clicked:" + mostRecentSquareClicked[0] + ", " + mostRecentSquareClicked[1]); 
        if (mostRecentSquareClicked[0] != -1 && board.boardArray[mostRecentSquareClicked[0]][mostRecentSquareClicked[1]] != null
            && board.boardArray[mostRecentSquareClicked[0]][mostRecentSquareClicked[1]].getColor() == moveColor) {
                ArrayList<Integer> list = new ArrayList<Integer>();
                list.add(x);
                list.add(y);
                if (board.boardArray[mostRecentSquareClicked[0]][mostRecentSquareClicked[1]].getLegalMoves().contains(list)) {
                    board.boardArray[mostRecentSquareClicked[0]][mostRecentSquareClicked[1]].move(x,y);
                    hasMadeMove = true;
                    moveColor = !moveColor;
                }
        }
        mostRecentSquareClicked = new int[]{x,y};
        frame.repaint();
        board.visualize();

        /* if (board.boardArray[x][y] != null) {
            if (board.boardArray[x][y].getMoves().size() == 0) {
                System.out.println("That piece has no moves");
            }
            else {
                System.out.println("These are the piece on (" + x + ", " + y + ")'s moves:");
                for (ArrayList<Integer> list : board.boardArray[x][y].getMoves()) {
                    System.out.print("[" + list.get(0) + ",");
                    System.out.println(list.get(1) + "]");
                }
            }
        } */
    }

    @Override
    public void paintComponent(Graphics gr) {
        int len = 60;
        int wid = 60;

        //len = (int) (len * horScaleFactor);
        //wid = (int) (len * vertScaleFactor);
        super.paintComponent(gr);
        Graphics2D gr2D = (Graphics2D)gr;
        Rectangle2D rectangle;
        System.out.println("Reseting board to empty board");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) {
                    gr2D.setColor(new Color(240,217,181));
                }
                else {
                    gr2D.setColor(new Color(181,136,99));
                }
                rectangle = new Rectangle(i * wid, j * len, wid, len);
                gr2D.fillRect(i * wid, j * len, wid, len);
                gr2D.draw(rectangle);
            }
        }
         
        System.out.println("Adding pieces to the board!");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece curPiece = board.boardArray[i][j];
                if (curPiece != null) {
                    if (curPiece.getColor()) {
                        if (curPiece instanceof Pawn ) {
                            Image currImage;
                            File file = new File("/Users/liam/Downloads/WhitePawn.png");
                            try {
                                currImage = ImageIO.read(file);
                            } catch (Exception e) {
                                currImage = null;
                            }
                            finally {}
                            gr.drawImage(currImage,60 * i, 60 * j, null);
                        }
                        else if (curPiece instanceof King ) {
                            Image currImage;
                            File file = new File("/Users/liam/Downloads/WhiteKing.png");
                            try {
                                currImage = ImageIO.read(file);
                            } catch (Exception e) {
                                currImage = null;
                            }
                            finally {}
                            gr.drawImage(currImage,60 * i, 60 * j, null);
                        }
                        else if (curPiece instanceof Queen) {
                            Image currImage;
                            File file = new File("/Users/liam/Downloads/WhiteQueen.png");
                            try {
                                currImage = ImageIO.read(file);
                            } catch (Exception e) {
                                currImage = null;
                            }
                            finally {}
                            gr.drawImage(currImage,60 * i, 60 * j, null);
                        }
                        else if (curPiece instanceof Knight) {
                            Image currImage;
                            File file = new File("/Users/liam/Downloads/WhiteKnight.png");
                            try {
                                currImage = ImageIO.read(file);
                            } catch (Exception e) {
                                currImage = null;
                            }
                            finally {}
                            gr.drawImage(currImage,60 * i, 60 * j, null);
                        }
                        else if (curPiece instanceof Bishop) {
                            Image currImage;
                            File file = new File("/Users/liam/Downloads/WhiteBishop.png");
                            try {
                                currImage = ImageIO.read(file);
                            } catch (Exception e) {
                                currImage = null;
                            }
                            finally {}
                            gr.drawImage(currImage,60 * i, 60 * j, null);
                        }
                        else if (curPiece instanceof Rook) {
                            Image currImage;
                            File file = new File("/Users/liam/Downloads/WhiteRook.png");
                            try {
                                currImage = ImageIO.read(file);
                            } catch (Exception e) {
                                currImage = null;
                            }
                            finally {}
                            gr.drawImage(currImage,60 * i, 60 * j, null);
                        }
                    }
                    else {
                        if (curPiece instanceof Pawn ) {
                            Image currImage;
                            File file = new File("/Users/liam/Downloads/BlackPawn.png");
                            try {
                                currImage = ImageIO.read(file);
                            } catch (Exception e) {
                                currImage = null;
                            }
                            finally {}
                            gr.drawImage(currImage,60 * i, 60 * j, null);
                        }
                        else if (curPiece instanceof King ) {
                            Image currImage;
                            File file = new File("/Users/liam/Downloads/BlackKing.png");
                            try {
                                currImage = ImageIO.read(file);
                            } catch (Exception e) {
                                currImage = null;
                            }
                            finally {}
                            gr.drawImage(currImage,60 * i, 60 * j, null);
                        }
                        else if (curPiece instanceof Queen) {
                            Image currImage;
                            File file = new File("/Users/liam/Downloads/BlackQueen.png");
                            try {
                                currImage = ImageIO.read(file);
                            } catch (Exception e) {
                                currImage = null;
                            }
                            finally {}
                            gr.drawImage(currImage,60 * i, 60 * j, null);
                        }
                        else if (curPiece instanceof Knight) {
                            Image currImage;
                            File file = new File("/Users/liam/Downloads/BlackKnight.png");
                            try {
                                currImage = ImageIO.read(file);
                            } catch (Exception e) {
                                currImage = null;
                            }
                            finally {}
                            gr.drawImage(currImage,60 * i, 60 * j, null);
                        }
                        else if (curPiece instanceof Bishop) {
                            Image currImage;
                            File file = new File("/Users/liam/Downloads/BlackBishop.png");
                            try {
                                currImage = ImageIO.read(file);
                            } catch (Exception e) {
                                currImage = null;
                            }
                            finally {}
                            gr.drawImage(currImage,60 * i, 60 * j, null);
                        }
                        else if (curPiece instanceof Rook) {
                            Image currImage;
                            File file = new File("/Users/liam/Downloads/BlackRook.png");
                            try {
                                currImage = ImageIO.read(file);
                            } catch (Exception e) {
                                currImage = null;
                            }
                            finally {}
                            gr.drawImage(currImage,60 * i, 60 * j, null);
                        }
                    }
                }
                else {
                    //print square again
                }
            }
        }
        
        /*
        //add black knights
        Image currImage;
        File file = new File("/Users/liam/Downloads/BlackKnight.png");
        try {
            currImage = ImageIO.read(file);
        } catch (Exception e) {
            currImage = null;
        }
        finally {}
        gr.drawImage(currImage, 60, 0, null);
        gr.drawImage(currImage, 360, 0, null);
        

        //add white knights
        file = new File("/Users/liam/Downloads/WhiteKnight.png");
        try {
            currImage = ImageIO.read(file);
        } catch (Exception e) {
            currImage = null;
        }
        finally {}
        gr.drawImage(currImage, 60, 420, null);
        gr.drawImage(currImage, 360, 420, null);
        
        //add black rooks
        file = new File("/Users/liam/Downloads/BlackRook.png");
        try {
            currImage = ImageIO.read(file);
        } catch (Exception e) {
            currImage = null;
        }
        finally {}
        gr.drawImage(currImage, 0, 0, null);
        gr.drawImage(currImage, 420, 0, null);

        //add white rooks
        file = new File("/Users/liam/Downloads/WhiteRook.png");
        try {
            currImage = ImageIO.read(file);
        } catch (Exception e) {
            currImage = null;
        }
        finally {}
        gr.drawImage(currImage, 0, 420, null);
        gr.drawImage(currImage, 420, 420, null);

        //add black bishops
        file = new File("/Users/liam/Downloads/BlackBishop.png");
        try {
            currImage = ImageIO.read(file);
        } catch (Exception e) {
            currImage = null;
        }
        finally {}
        gr.drawImage(currImage, 120, 0, null);
        gr.drawImage(currImage, 300, 0, null);

        //add white bishops
        file = new File("/Users/liam/Downloads/WhiteBishop.png");
        try {
            currImage = ImageIO.read(file);
        } catch (Exception e) {
            currImage = null;
        }
        finally {}
        gr.drawImage(currImage, 120, 420, null);
        gr.drawImage(currImage, 300, 420, null);

        //add black queen
        file = new File("/Users/liam/Downloads/BlackQueen.png");
        try {
            currImage = ImageIO.read(file);
        } catch (Exception e) {
            currImage = null;
        }
        finally {}
        gr.drawImage(currImage, 180, 0, null);

        //add white queen 
        file = new File("/Users/liam/Downloads/WhiteQueen.png");
        try {
            currImage = ImageIO.read(file);
        } catch (Exception e) {
            currImage = null;
        }
        finally {}
        gr.drawImage(currImage, 180, 420, null);

        //add black king
        file = new File("/Users/liam/Downloads/BlackKing.png");
        try {
            currImage = ImageIO.read(file);
        } catch (Exception e) {
            currImage = null;
        }
        finally {}
        gr.drawImage(currImage, 240, 0, null);

        //add white king
        file = new File("/Users/liam/Downloads/WhiteKing.png");
        try {
            currImage = ImageIO.read(file);
        } catch (Exception e) {
            currImage = null;
        }
        finally {}
        gr.drawImage(currImage, 240, 420, null);
        
        //*/
    }
}

class Board {
    public Piece[][] boardArray;
    public ArrayList<Piece> whiteLostPieces;
    public ArrayList<Piece> blackLostPieces;

    public Board(int x, int y) {
        this.boardArray = new Piece[x][y];
        this.whiteLostPieces = new ArrayList<Piece>();
        this.blackLostPieces = new ArrayList<Piece>();
    }

    public Piece pieceAt(int x, int y) {
        return this.boardArray[x][y];   
    }

    public void addNormalPieces() {
       this.addFenPieces("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
    }

    public void addFenPieces(String Fen) {
        char[] charArray = Fen.toCharArray();
        int xIndex = 0;
        int yIndex = 0;
        for (char curr : charArray) {
            int[] pos = new int[2];
            if (curr == '/') {
                yIndex++;
                xIndex = 0;
                continue;
            }
            else if (49 <= curr  && curr <= 57) {
                xIndex += curr - 48;
            }
            else if (curr == 'n') {
                pos[0] = xIndex;
                pos[1] = yIndex;
                Knight newKnight = new Knight("Black",pos,this);
                xIndex++;
            }
            else if (curr == 'N') {
                pos[0] = xIndex;
                pos[1] = yIndex;
                Knight newKnight = new Knight("White",pos,this);
                xIndex++;
            }
            else if (curr == 'b') {
                pos[0] = xIndex;
                pos[1] = yIndex;
                Bishop newBishop = new Bishop("Black",pos,this);
                xIndex++;
            }
            else if (curr == 'B') {
                pos[0] = xIndex;
                pos[1] = yIndex;
                Bishop newBishop = new Bishop("White",pos,this);
                xIndex++;
            }
            else if (curr == 'r') {
                pos[0] = xIndex;
                pos[1] = yIndex;
                Rook newRook = new Rook("Black",pos,this);
                xIndex++;
            }
            else if (curr == 'R') {
                pos[0] = xIndex;
                pos[1] = yIndex;
                Rook newRook = new Rook("White",pos,this);
                xIndex++;
            }
            else if (curr == 'p') {
                pos[0] = xIndex;
                pos[1] = yIndex;
                Pawn newPawn = new Pawn("Black",pos,this);
                xIndex++;
            }
            else if (curr == 'P') {
                pos[0] = xIndex;
                pos[1] = yIndex;
                Pawn newPawn = new Pawn("White",pos,this);
                xIndex++;
            }
            else if (curr == 'q') {
                pos[0] = xIndex;
                pos[1] = yIndex;
                Queen newQueen = new Queen("Black",pos,this);
                xIndex++;
            }
            else if (curr == 'Q') {
                pos[0] = xIndex;
                pos[1] = yIndex;
                Queen newQueen = new Queen("White",pos,this);
                xIndex++;
            }
            else if (curr == 'k') {
                pos[0] = xIndex;
                pos[1] = yIndex;
                King newKing = new King("Black",pos,this);
                xIndex++;
            }
            else if (curr == 'K') {
                pos[0] = xIndex;
                pos[1] = yIndex;
                King newKing = new King("White",pos,this);
                xIndex++;
            }
        }
    }

    public void visualize() {
        System.out.println(" ------------------------------ ");
        for (int secIndex = 0; secIndex < this.boardArray[0].length; secIndex++) {
            System.out.print("|");
            for (int firstIndex = 0; firstIndex < this.boardArray.length; firstIndex++) {
                if (this.boardArray[firstIndex][secIndex] != null) {
                    System.out.print(this.boardArray[firstIndex][secIndex].toString());
                }
                else {
                    System.out.print(" ");
                }
                
                System.out.print(" | ");
            }
            System.out.println();
            if (secIndex != this.boardArray[0].length - 1) {
                System.out.println("|------------------------------|");
            }
            else {
                System.out.println(" ------------------------------ ");
            }
        }
    }

    public int numberOfLegalMoves(boolean color) {
        int sum = 0;
        for (int col = 0; col < this.boardArray.length; col++) {
            for (int rank = 0; rank < this.boardArray[0].length; rank++) {
                if (this.boardArray[col][rank] != null && this.boardArray[col][rank].getColor() == color ) {
                    sum += this.boardArray[col][rank].getLegalMoves().size();
                }
            }
        }
        return sum;
    }
}

class Piece {
    protected Boolean white;
    protected int[] position;
    protected Board board;

    public Piece() {
        this.white = true;
        this.position = new int[2];
        this.board = new Board(8,8);
    }

    public void move(int x, int y) {
        if (this.board.boardArray[x][y] != null) {
            this.board.boardArray[x][y].capture();
        }
        this.board.boardArray[position[0]][position[1]] = null;
        this.position = new int[]{x,y};
        this.board.boardArray[x][y] = this;
        
    }

    public void capture() {
        System.out.println("Capture has been called!");
        if (this.white) {
            this.board.whiteLostPieces.add(this);
        }
        else {
            this.board.blackLostPieces.add(this);
        }
    }

    public ArrayList<ArrayList<Integer>> getMoves() {
        ArrayList<ArrayList<Integer>> outputArrayList = new ArrayList<ArrayList<Integer>>();
        return outputArrayList;
    }

    public Boolean getColor() {
        return this.white;
    }

    public String toString() {
        return "GenericPiece";
    }

    public ArrayList<ArrayList<Integer>> getLegalMoves() {
        ArrayList<ArrayList<Integer>> movesToCheck = this.getMoves();
        int width = this.board.boardArray.length;
        int height = this.board.boardArray[0].length;
        Piece currentPiece = null;
        Piece tempPiece = null;
        //System.out.println("There are " + movesToCheck.size() + " moves to check");
        ArrayList<ArrayList<Integer>> movesToRemove = new ArrayList<ArrayList<Integer>>();
        for (ArrayList<Integer> move : movesToCheck) {
            //check all other moves, see if captures king.
            tempPiece = this.board.boardArray[move.get(0)][move.get(1)];
            this.board.boardArray[move.get(0)][move.get(1)] = this;
            this.board.boardArray[this.position[0]][this.position[1]] = null;
            outerloop:
            for (int col = 0; col < width; col++) {
                for (int rank = 0; rank < height; rank++) {
                    currentPiece = this.board.boardArray[col][rank];
                    //System.out.println("Checking " + currentPiece + " piece");
                    if (currentPiece != null && currentPiece.getColor() != this.white) {
                        if (canCaptureKing(currentPiece,currentPiece.getMoves())) {
                            /*if (currentPiece.getColor()) {
                                System.out.println("Piece on " + col + ", " + rank + " can capture the Black king.");
                            }
                            else {
                                System.out.println("Piece on " + col + ", " + rank + " can capture the White king.");
                            } */
                            
                            //remove current move from moveList
                            movesToRemove.add(move);
                            break outerloop;
                        }
                    }
                }
            }
            this.board.boardArray[this.position[0]][this.position[1]] = this;
            this.board.boardArray[move.get(0)][move.get(1)] = tempPiece;
        }
        movesToCheck.removeAll(movesToRemove);
        /* ArrayList<ArrayList<Integer>> outputOfGetMoves = this.getMoves();
        System.out.println("Output of getMoves():");
        for (ArrayList<Integer> currArrayList : outputOfGetMoves) {
            System.out.print("[");
            for (Integer currItem : currArrayList) {
                System.out.print(currItem);
                System.out.print(",");
            }
            System.out.println("]");
        }
        System.out.println("Output of getLegalMoves():");
        for (ArrayList<Integer> currArrayList : movesToCheck) {
            System.out.print("[");
            for (Integer currItem : currArrayList) {
                System.out.print(currItem);
                System.out.print(",");
            }
            System.out.println("]");
        } */
        return movesToCheck;
    }

    public static boolean canCaptureKing(Piece piece, ArrayList<ArrayList<Integer>> moveList) {
        for (ArrayList<Integer> insideList : moveList) { 
            //if move captures a king of opposite color
            if (piece.board.boardArray[insideList.get(0)][insideList.get(1)] instanceof King 
                && piece.board.boardArray[insideList.get(0)][insideList.get(1)].getColor() != piece.getColor()) {
                return true;
            }
        }

        return false;
    }
}

class Knight extends Piece {
    public Knight(String color,int[] position,Board board) {
        if (color.equals("White")) {
            this.white = true;
        }
        else if (color.equals("Black")) {
            this.white = false;
        }
        else {
            throw new IllegalArgumentException("Invalid color, should be: White or Black");
        }
        this.board = board;
        this.position = new int[2];
        this.position[0] = position[0];
        this.position[1] = position[1];
        this.board.boardArray[position[0]][position[1]] = this;
    }

    public ArrayList<ArrayList<Integer>> getMoves() {
        ArrayList<ArrayList<Integer>> outputArrayList = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> currMoveArrayList = new ArrayList<Integer>();
        
        //Two positions left, then one up or down
        if (this.position[0] > 1 && this.position[1] > 0 
            && (this.board.boardArray[this.position[0] - 2][this.position[1] - 1] == null
            || this.board.boardArray[this.position[0] - 2][this.position[1] - 1].getColor() != this.white)) {
            currMoveArrayList.add(this.position[0] - 2);
            currMoveArrayList.add(this.position[1] - 1);
            outputArrayList.add(currMoveArrayList);
        }
        if (this.position[0] > 1 
            && this.position[1] < this.board.boardArray[0].length - 1
            && (this.board.boardArray[this.position[0] - 2][this.position[1] + 1] == null
            || this.board.boardArray[this.position[0] - 2][this.position[1] + 1].getColor() != this.white)) {
            currMoveArrayList = new ArrayList<Integer>();
            currMoveArrayList.add(this.position[0] - 2);
            currMoveArrayList.add(this.position[1] + 1);
            outputArrayList.add(currMoveArrayList);
        }
        //Two positions right, then one up or down
        if (this.position[0] < this.board.boardArray.length - 2 
            && this.position[1] > 0
            && (this.board.boardArray[this.position[0] + 2][this.position[1] - 1] == null
            || this.board.boardArray[this.position[0] + 2][this.position[1] - 1].getColor() != this.white)) {
            currMoveArrayList = new ArrayList<Integer>();
            currMoveArrayList.add(this.position[0] + 2);
            currMoveArrayList.add(this.position[1] - 1);
            outputArrayList.add(currMoveArrayList);
        }
        if (this.position[0] < this.board.boardArray.length - 2
            && this.position[1] < this.board.boardArray[0].length - 1
            && (this.board.boardArray[this.position[0] + 2][this.position[1] + 1] == null
            || this.board.boardArray[this.position[0] + 2][this.position[1] + 1].getColor() != this.white)) {
            currMoveArrayList = new ArrayList<Integer>();
            currMoveArrayList.add(this.position[0] + 2);
            currMoveArrayList.add(this.position[1] + 1);
            outputArrayList.add(currMoveArrayList);
        }
        //Two positions up, then one right or left
        if (this.position[1] > 1 && this.position[0] > 0
            && (this.board.boardArray[this.position[0] - 1][this.position[1] - 2] == null
            || this.board.boardArray[this.position[0] - 1][this.position[1] - 2].getColor() != this.white)) {
            currMoveArrayList = new ArrayList<Integer>();
            currMoveArrayList.add(this.position[0] - 1 );
            currMoveArrayList.add(this.position[1] - 2);
            outputArrayList.add(currMoveArrayList);
        }
        if (this.position[1] > 1 
            && this.position[0] < this.board.boardArray.length - 1
            && (this.board.boardArray[this.position[0] + 1][this.position[1] - 2] == null
            || this.board.boardArray[this.position[0] + 1][this.position[1] - 2].getColor() != this.white)) {
            currMoveArrayList = new ArrayList<Integer>();
            currMoveArrayList.add(this.position[0] + 1 );
            currMoveArrayList.add(this.position[1] - 2);
            outputArrayList.add(currMoveArrayList);
        }
        //Two positions down, then one right or left
        if (this.position[1] < this.board.boardArray[0].length - 2 
            && this.position[0] > 0
            && (this.board.boardArray[this.position[0] - 1][this.position[1] + 2] == null
            || this.board.boardArray[this.position[0] - 1][this.position[1] + 2].getColor() != this.white)) {
            currMoveArrayList = new ArrayList<Integer>();
            currMoveArrayList.add(this.position[0] - 1 );
            currMoveArrayList.add(this.position[1] + 2);
            outputArrayList.add(currMoveArrayList);
        }
        if (this.position[1] < this.board.boardArray[0].length - 2 
            && this.position[0] < this.board.boardArray.length - 1
            && (this.board.boardArray[this.position[0] + 1][this.position[1] + 2] == null
            || this.board.boardArray[this.position[0] + 1][this.position[1] + 2].getColor() != this.white)) {
            currMoveArrayList = new ArrayList<Integer>();
            currMoveArrayList.add(this.position[0] + 1 );
            currMoveArrayList.add(this.position[1] + 2);
            outputArrayList.add(currMoveArrayList);
        }
        return outputArrayList;
    }

    public String toString() {
        if (this.white) {
            return "N";
        }
        return "n";
    }

}

class Bishop extends Piece {
    public Bishop(String color, int[] position, Board board) {
        if (color.equals("White")) {
            this.white = true;
        }
        else if (color.equals("Black")) {
            this.white = false;
        }
        else {
            throw new IllegalArgumentException("Invalid color, should be: White or Black");
        }
        this.position = new int[2];
        this.position[0] = position[0];
        this.position[1] = position[1];
        this.board = board;
        this. board.boardArray[position[0]][position[1]] = this;
    }

    public String toString() {
        if (this.white) {
            return "B";
        }
        return "b";
    }

    public ArrayList<ArrayList<Integer>> getMoves() {
        ArrayList<ArrayList<Integer>> outputArrayList = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> currMoveArrayList = new ArrayList<Integer>();
        int currXPos, currYPos;
        //add diagonal direction
        currXPos = this.position[0] + 1;
        currYPos = this.position[1] + 1;
        while (currXPos < this.board.boardArray.length && currYPos < this.board.boardArray[0].length) {
            if (this.board.boardArray[currXPos][currYPos] != null 
                && this.board.boardArray[currXPos][currYPos].getColor() == this.white) {
                break;
            }
            currMoveArrayList = new ArrayList<Integer>();
            currMoveArrayList.add(currXPos);
            currMoveArrayList.add(currYPos);
            outputArrayList.add(currMoveArrayList);
            if (this.board.boardArray[currXPos][currYPos] != null) {
                break;
            }
            currXPos++;
            currYPos++;
        }
        currXPos = this.position[0] - 1;
        currYPos = this.position[1] - 1;
        while (currXPos >= 0 && currYPos >= 0) {
            if (this.board.boardArray[currXPos][currYPos] != null 
                && this.board.boardArray[currXPos][currYPos].getColor() == this.white) {
                break;
            }
            currMoveArrayList = new ArrayList<Integer>();
            currMoveArrayList.add(currXPos);
            currMoveArrayList.add(currYPos);
            outputArrayList.add(currMoveArrayList);
            if (this.board.boardArray[currXPos][currYPos] != null) {
                break;
            }
            currXPos--;
            currYPos--;
        }
        currXPos = this.position[0] + 1;
        currYPos = this.position[1] - 1;
        while (currXPos < this.board.boardArray.length && currYPos >= 0) {
            if (this.board.boardArray[currXPos][currYPos] != null 
                && this.board.boardArray[currXPos][currYPos].getColor() == this.white) {
                break;
            }
            currMoveArrayList = new ArrayList<Integer>();
            currMoveArrayList.add(currXPos);
            currMoveArrayList.add(currYPos);
            outputArrayList.add(currMoveArrayList);
            if (this.board.boardArray[currXPos][currYPos] != null) {
                break;
            }
            currXPos++;
            currYPos--;
        }
        currXPos = this.position[0] - 1;
        currYPos = this.position[1] + 1;
        while (currXPos >= 0 && currYPos < this.board.boardArray[0].length) {
            if (this.board.boardArray[currXPos][currYPos] != null 
                && this.board.boardArray[currXPos][currYPos].getColor() == this.white) {
                break;
            }
            currMoveArrayList = new ArrayList<Integer>();
            currMoveArrayList.add(currXPos);
            currMoveArrayList.add(currYPos);
            outputArrayList.add(currMoveArrayList);
            if (this.board.boardArray[currXPos][currYPos] != null) {
                break;
            }
            currXPos--;
            currYPos++;
        }
        return outputArrayList;
    }
}

class Pawn extends Piece {
    protected boolean facingUp;
    protected boolean hasMoved;

    public Pawn(String color, int[] position, Board board) {
        if (color.equals("White")) {
            this.white = true;
            this.facingUp = true;
        }
        else if (color.equals("Black")) {
            this.white = false;
            this.facingUp = false;
        }
        else {
            throw new IllegalArgumentException("Invalid color, should be: White or Black");
        }
        this.position = new int[2];
        this.position[0] = position[0];
        this.position[1] = position[1];
        this.hasMoved = false;
        this.board = board;
        this.board.boardArray[position[0]][position[1]] = this;
    }

    public String toString() {
        if (this.white) {
            return "P";
        }
        return "p";
    }

    public ArrayList<ArrayList<Integer>> getMoves() {
        ArrayList<ArrayList<Integer>> outputArrayList = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> currMoveArrayList = new ArrayList<Integer>();
        if (this.facingUp) {
            if (!hasMoved) {
                if (this.position[1] > 1 
                    && this.board.boardArray[this.position[0]][this.position[1] - 1] == null
                    && this.board.boardArray[this.position[0]][this.position[1] - 2] == null) {
                        currMoveArrayList.add(this.position[0]);
                        currMoveArrayList.add(this.position[1] - 2);
                        outputArrayList.add(currMoveArrayList);
                    }
            }
            if (this.position[1] > 0) {
                if (this.board.boardArray[this.position[0]][this.position[1] - 1] == null) {
                    currMoveArrayList = new ArrayList<Integer>();
                    currMoveArrayList.add(this.position[0]);
                    currMoveArrayList.add(this.position[1] - 1);
                    outputArrayList.add(currMoveArrayList);
                }
                //if can capture to the right
                if (this.position[0] < this.board.boardArray.length - 1 
                    && this.board.boardArray[this.position[0] + 1][this.position[1] - 1] != null 
                    && this.board.boardArray[this.position[0] + 1][this.position[1] - 1].getColor() != this.white) {
                    currMoveArrayList = new ArrayList<Integer>();
                    currMoveArrayList.add(this.position[0] + 1);
                    currMoveArrayList.add(this.position[1] - 1);
                    outputArrayList.add(currMoveArrayList);
                }
                //if can capture to the left
                if (this.position[0] > 0 
                    && this.board.boardArray[this.position[0] - 1][this.position[1] - 1] != null
                    && this.board.boardArray[this.position[0] - 1][this.position[1] - 1].getColor() != this.white) {
                    currMoveArrayList = new ArrayList<Integer>();
                    currMoveArrayList.add(this.position[0] - 1);
                    currMoveArrayList.add(this.position[1] - 1);
                    outputArrayList.add(currMoveArrayList);
                }
            }
        }
        else {
            if (!hasMoved) {
                if (this.position[1] < this.board.boardArray[0].length - 2 
                    && this.board.boardArray[this.position[0]][this.position[1] + 1] == null
                    && this.board.boardArray[this.position[0]][this.position[1] + 2] == null) {
                        currMoveArrayList = new ArrayList<Integer>();
                        currMoveArrayList.add(this.position[0]);
                        currMoveArrayList.add(this.position[1] + 2);
                        outputArrayList.add(currMoveArrayList);
                    }
            }
            if (this.position[1] < this.board.boardArray[0].length - 1
                && this.board.boardArray[this.position[0]][this.position[1] + 1] == null) {
                currMoveArrayList = new ArrayList<Integer>();
                currMoveArrayList.add(this.position[0]);
                currMoveArrayList.add(this.position[1] + 1);
                outputArrayList.add(currMoveArrayList);
            }
            //if can capture to bottom right
            if (this.position[0] < this.board.boardArray[0].length - 1
                && this.board.boardArray[this.position[0] + 1][this.position[1] + 1] != null
                && this.board.boardArray[this.position[0] + 1][this.position[1] + 1].getColor() != this.white) {
                currMoveArrayList = new ArrayList<Integer>();
                currMoveArrayList.add(this.position[0] + 1);
                currMoveArrayList.add(this.position[1] + 1);
                outputArrayList.add(currMoveArrayList);
            }
            //if can capture to bottom left
            if (this.position[0] > 0 
                && this.board.boardArray[this.position[0] - 1][this.position[1] + 1] != null
                && this.board.boardArray[this.position[0] - 1][this.position[1] + 1].getColor() != this.white) {
                currMoveArrayList = new ArrayList<Integer>();
                currMoveArrayList.add(this.position[0] - 1);
                currMoveArrayList.add(this.position[1] + 1);
                outputArrayList.add(currMoveArrayList);
            }
        }
        //Check for promotion

        //TODOTOTOO
        return outputArrayList;
    }
}

class Rook extends Piece {
    public Rook(String color, int[] position, Board board) {
        if (color.equals("White")) {
            this.white = true;
        }
        else if (color.equals("Black")) {
            this.white = false;
        }
        else {
            throw new IllegalArgumentException("Invalid color, should be: White or Black");
        }
        this.position = new int[2];
        this.position[0] = position[0];
        this.position[1] = position[1];
        this.board = board;
        this.board.boardArray[position[0]][position[1]] = this;
    }

    public String toString() {
        if (this.white) {
            return "R";
        }
        return "r";
    }

    public ArrayList<ArrayList<Integer>> getMoves() {
        ArrayList<ArrayList<Integer>> outputArrayList = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> currMoveArrayList = new ArrayList<Integer>();
        
        int currXPos = this.position[0] + 1;
        while (currXPos < this.board.boardArray.length) {
            if (this.board.boardArray[currXPos][this.position[1]] != null 
                && this.board.boardArray[currXPos][this.position[1]].getColor() == this.white) {
                break;
            }
            currMoveArrayList = new ArrayList<Integer>();
            currMoveArrayList.add(currXPos);
            currMoveArrayList.add(this.position[1]);
            outputArrayList.add(currMoveArrayList);
            if (this.board.boardArray[currXPos][this.position[1]] != null) {
                break;
            }
            currXPos++;
        }
        currXPos = this.position[0] - 1;
        while (currXPos >= 0) {
            if (this.board.boardArray[currXPos][this.position[1]] != null 
                && this.board.boardArray[currXPos][this.position[1]].getColor() == this.white) {
                break;
            }
            currMoveArrayList = new ArrayList<Integer>();
            currMoveArrayList.add(currXPos);
            currMoveArrayList.add(this.position[1]);
            outputArrayList.add(currMoveArrayList);
            if (this.board.boardArray[currXPos][this.position[1]] != null) {
                break;
            }
            currXPos--;
        }
        int currYPos = this.position[1] + 1;
        while (currYPos < this.board.boardArray[0].length) {
            if (this.board.boardArray[this.position[0]][currYPos] != null 
                && this.board.boardArray[this.position[0]][currYPos].getColor() == this.white) {
                break;
            }
            currMoveArrayList = new ArrayList<Integer>();
            currMoveArrayList.add(this.position[0]);
            currMoveArrayList.add(currYPos);
            outputArrayList.add(currMoveArrayList);
            if (this.board.boardArray[this.position[0]][currYPos] != null) {
                break;
            }
            currYPos++;
        }
        currYPos = this.position[1] - 1;
        while (currYPos >= 0) {
                if (this.board.boardArray[this.position[0]][currYPos] != null 
                && this.board.boardArray[this.position[0]][currYPos].getColor() == this.white) {
                break;
            }
            currMoveArrayList = new ArrayList<Integer>();
            currMoveArrayList.add(this.position[0]);
            currMoveArrayList.add(currYPos);
            outputArrayList.add(currMoveArrayList);
            if (this.board.boardArray[this.position[0]][currYPos] != null) {
                break;
            }
            currYPos--;
        }
        return outputArrayList;
    }
}

class Queen extends Piece {
    public Queen(String color, int[] position, Board board) {
        if (color.equals("White")) {
            this.white = true;
        }
        else if (color.equals("Black")) {
            this.white = false;
        }
        else {
            throw new IllegalArgumentException("Invalid color, should be: White or Black");
        }
        this.position = new int[2];
        this.position[0] = position[0];
        this.position[1] = position[1];
        this.board = board;
        this.board.boardArray[position[0]][position[1]] = this;
    }

    public String toString() {
        if (this.white) {
            return "Q";
        }
        return "q";
    }

    public ArrayList<ArrayList<Integer>> getMoves() {
        ArrayList<ArrayList<Integer>> outputArrayList = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> currMoveArrayList = new ArrayList<Integer>();
        
        //Add horizontal directions
        int currXPos = this.position[0] + 1;
        while (currXPos < this.board.boardArray.length) {
            if (this.board.boardArray[currXPos][this.position[1]] != null 
                && this.board.boardArray[currXPos][this.position[1]].getColor() == this.white) {
                break;
            }
            currMoveArrayList = new ArrayList<Integer>();
            currMoveArrayList.add(currXPos);
            currMoveArrayList.add(this.position[1]);
            outputArrayList.add(currMoveArrayList);
            if (this.board.boardArray[currXPos][this.position[1]] != null) {
                break;
            }
            currXPos++;
        }
        currXPos = this.position[0] - 1;
        while (currXPos >= 0) {
            if (this.board.boardArray[currXPos][this.position[1]] != null 
                && this.board.boardArray[currXPos][this.position[1]].getColor() == this.white) {
                break;
            }
            currMoveArrayList = new ArrayList<Integer>();
            currMoveArrayList.add(currXPos);
            currMoveArrayList.add(this.position[1]);
            outputArrayList.add(currMoveArrayList);
            if (this.board.boardArray[currXPos][this.position[1]] != null) {
                break;
            }
            currXPos--;
        }
        int currYPos = this.position[1] + 1;
        while (currYPos < this.board.boardArray[0].length) {
            if (this.board.boardArray[this.position[0]][currYPos] != null 
                && this.board.boardArray[this.position[0]][currYPos].getColor() == this.white) {
                break;
            }
            currMoveArrayList = new ArrayList<Integer>();
            currMoveArrayList.add(this.position[0]);
            currMoveArrayList.add(currYPos);
            outputArrayList.add(currMoveArrayList);
            if (this.board.boardArray[this.position[0]][currYPos] != null) {
                break;
            }
            currYPos++;
        }
        currYPos = this.position[1] - 1;
        while (currYPos >= 0) {
            if (this.board.boardArray[this.position[0]][currYPos] != null 
                && this.board.boardArray[this.position[0]][currYPos].getColor() == this.white) {
                break;
            }
            currMoveArrayList = new ArrayList<Integer>();
            currMoveArrayList.add(this.position[0]);
            currMoveArrayList.add(currYPos);
            outputArrayList.add(currMoveArrayList);
            if (this.board.boardArray[this.position[0]][currYPos] != null) {
                break;
            }
            currYPos--;
        }

        //add diagonal direction
        currXPos = this.position[0] + 1;
        currYPos = this.position[1] + 1;
        while (currXPos < this.board.boardArray.length && currYPos < this.board.boardArray[0].length) {
            if (this.board.boardArray[currXPos][currYPos] != null 
                && this.board.boardArray[currXPos][currYPos].getColor() == this.white) {
                break;
            }
            currMoveArrayList = new ArrayList<Integer>();
            currMoveArrayList.add(currXPos);
            currMoveArrayList.add(currYPos);
            outputArrayList.add(currMoveArrayList);
            if (this.board.boardArray[currXPos][currYPos] != null) {
                break;
            }
            currXPos++;
            currYPos++;
        }
        currXPos = this.position[0] - 1;
        currYPos = this.position[1] - 1;
        while (currXPos >= 0 && currYPos >= 0) {
            if (this.board.boardArray[currXPos][currYPos] != null 
                && this.board.boardArray[currXPos][currYPos].getColor() == this.white) {
                break;
            }
            currMoveArrayList = new ArrayList<Integer>();
            currMoveArrayList.add(currXPos);
            currMoveArrayList.add(currYPos);
            outputArrayList.add(currMoveArrayList);
            if (this.board.boardArray[currXPos][currYPos] != null) {
                break;
            }
            currXPos--;
            currYPos--;
        }
        currXPos = this.position[0] + 1;
        currYPos = this.position[1] - 1;
        while (currXPos < this.board.boardArray.length && currYPos >= 0) {
            if (this.board.boardArray[currXPos][currYPos] != null 
                && this.board.boardArray[currXPos][currYPos].getColor() == this.white) {
                break;
            }
            currMoveArrayList = new ArrayList<Integer>();
            currMoveArrayList.add(currXPos);
            currMoveArrayList.add(currYPos);
            outputArrayList.add(currMoveArrayList);
            if (this.board.boardArray[currXPos][currYPos] != null) {
                break;
            }
            currXPos++;
            currYPos--;
        }
        currXPos = this.position[0] - 1;
        currYPos = this.position[1] + 1;
        while (currXPos >= 0 && currYPos < this.board.boardArray[0].length) {
            if (this.board.boardArray[currXPos][currYPos] != null 
                && this.board.boardArray[currXPos][currYPos].getColor() == this.white) {
                break;
            }
            currMoveArrayList = new ArrayList<Integer>();
            currMoveArrayList.add(currXPos);
            currMoveArrayList.add(currYPos);
            outputArrayList.add(currMoveArrayList);
            if (this.board.boardArray[currXPos][currYPos] != null) {
                break;
            }
            currXPos--;
            currYPos++;
        }
        return outputArrayList;
    }

}

class King extends Piece {
    public King(String color, int[] position, Board board) {
        if (color.equals("White")) {
            this.white = true;
        }
        else if (color.equals("Black")) {
            this.white = false;
        }
        else {
            throw new IllegalArgumentException("Invalid color, should be: White or Black");
        }
        this.position = new int[2];
        this.position[0] = position[0];
        this.position[1] = position[1];
        this.board = board;
        this.board.boardArray[position[0]][position[1]] = this;
    }

    public ArrayList<ArrayList<Integer>> getMoves() {
        ArrayList<ArrayList<Integer>> outputArrayList = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> currMoveArrayList = new ArrayList<Integer>();
        //right
        if (this.position[0] < this.board.boardArray.length - 1 && (this.board.boardArray[this.position[0] + 1][this.position[1]] == null 
            || this.board.boardArray[this.position[0] + 1][this.position[1]].getColor() != this.white)) {
            currMoveArrayList.add(this.position[0] + 1);
            currMoveArrayList.add(this.position[1]);
            outputArrayList.add(currMoveArrayList);
        }
        //bottom
        if (this.position[1] < this.board.boardArray[0].length - 1 && (this.board.boardArray[this.position[0]][this.position[1] + 1] == null 
            || this.board.boardArray[this.position[0]][this.position[1] + 1].getColor() != this.white)) {
            currMoveArrayList = new ArrayList<Integer>();
            currMoveArrayList.add(this.position[0]);
            currMoveArrayList.add(this.position[1] + 1);
            outputArrayList.add(currMoveArrayList);
        }
        //top
        if (this.position[1] > 0 && (this.board.boardArray[this.position[0]][this.position[1] - 1] == null 
            || this.board.boardArray[this.position[0]][this.position[1] - 1].getColor() != this.white)) {
            currMoveArrayList = new ArrayList<Integer>();
            currMoveArrayList.add(this.position[0]);
            currMoveArrayList.add(this.position[1] - 1);
            outputArrayList.add(currMoveArrayList);
        }
        //left
        if (this.position[0] > 0 && (this.board.boardArray[this.position[0] - 1][this.position[1]] == null 
            || this.board.boardArray[this.position[0] - 1][this.position[1]].getColor() != this.white)) {
            currMoveArrayList = new ArrayList<Integer>();
            currMoveArrayList.add(this.position[0] - 1);
            currMoveArrayList.add(this.position[1]);
            outputArrayList.add(currMoveArrayList);
        }
        //bottom right
        if (this.position[1] < this.board.boardArray[0].length - 1 && this.position[0] < this.board.boardArray.length - 1 && (this.board.boardArray[this.position[0] + 1][this.position[1] + 1] == null 
            || this.board.boardArray[this.position[0] + 1][this.position[1] + 1].getColor() != this.white)) {
            currMoveArrayList = new ArrayList<Integer>();
            currMoveArrayList.add(this.position[0] + 1);
            currMoveArrayList.add(this.position[1] + 1);
            outputArrayList.add(currMoveArrayList);
        }
        //bottom left
        if (this.position[1] < this.board.boardArray[0].length - 1 && this.position[0] > 0 && (this.board.boardArray[this.position[0] - 1][this.position[1] + 1] == null 
            || this.board.boardArray[this.position[0] - 1][this.position[1] + 1].getColor() != this.white)) {
            currMoveArrayList = new ArrayList<Integer>();
            currMoveArrayList.add(this.position[0] - 1);
            currMoveArrayList.add(this.position[1] + 1);
            outputArrayList.add(currMoveArrayList);
        }
        //top right
        if (this.position[1] > 0 && this.position[0] < this.board.boardArray.length - 1 && (this.board.boardArray[this.position[0] + 1][this.position[1] - 1] == null 
            || this.board.boardArray[this.position[0] + 1][this.position[1] - 1].getColor() != this.white)) {
            currMoveArrayList = new ArrayList<Integer>();
            currMoveArrayList.add(this.position[0] + 1);
            currMoveArrayList.add(this.position[1] - 1);
            outputArrayList.add(currMoveArrayList);
        }
        //top left
        if (this.position[1] > 0 && this.position[0] > 0 && (this.board.boardArray[this.position[0] - 1][this.position[1] - 1] == null 
            || this.board.boardArray[this.position[0] - 1][this.position[1] - 1].getColor() != this.white)) {
            currMoveArrayList = new ArrayList<Integer>();
            currMoveArrayList.add(this.position[0] - 1);
            currMoveArrayList.add(this.position[1] - 1);
            outputArrayList.add(currMoveArrayList);
        }
        return outputArrayList;
    }

    public String toString() {
        if (this.white) {
            return "K";
        }
        return "k";
    }
} 
