import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class Game implements Runnable {

    @Override
    public void run() {
        JFrame frame = new JFrame("Chess");
        frame.setLocation(300, 300);
        
        //gameboard
        GameBoard gameboard = new GameBoard();
        
        //panel
        JPanel panel = new JPanel();
        frame.getContentPane().add(panel);
        panel.add(gameboard);
        frame.add(panel, BorderLayout.CENTER);
             
        //undo button
        JButton undo = new JButton("Undo last move");
        undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameboard.undoLastMove();
            }
        });
        
        //Jpanel for button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);
        control_panel.add(undo);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    
    public static void main(String [] args) {
        SwingUtilities.invokeLater(new Game());
    }
}
