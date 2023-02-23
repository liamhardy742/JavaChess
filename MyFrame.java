import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.*;
import javax.imageio.ImageIO;
import java.io.*;

public class MyFrame extends JFrame implements ActionListener {
    
    Board board;

    public MyFrame(Board board) {
        this.board = board;
    }

    public void wasClicked() {
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }

}

