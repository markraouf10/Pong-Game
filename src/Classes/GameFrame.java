package Classes;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GameFrame extends JFrame {
    GamePanel panel ;
    
    GameFrame(){
        panel = new GamePanel();
        this.add(panel);
        this.setTitle("Pong Game");
        this.setResizable(false);  //No Resizing
        this.setBackground(Color.black);  //Background color
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //lama ydoso X y2fl el app
        this.pack();  //pack method causes the window to be sized in its prefered size (adjust accordingly)
        this.setVisible(true);
        this.setLocationRelativeTo(null); //make the window appear in the middle of the screen
        
        
         
    }
}
