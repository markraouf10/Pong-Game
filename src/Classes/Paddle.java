package Classes;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Paddle extends Rectangle {
    int id;
    int yVelocity;
    int speed=10;
    
    
    Paddle(int x,int y ,int PADDLE_WIDTH,int PADDLE_HEIGHT,int id ){  //constructor bta3 el paddle
        super(x,y,PADDLE_WIDTH,PADDLE_HEIGHT);
        this.id = id ;
    }   
    public void keyPressed(KeyEvent e){
        switch(id){
            case (1):
            if(e.getKeyCode()==KeyEvent.VK_W){   //player rkm 2 moving upward 
                setYDirection(-speed);
                move();
            }
                 if(e.getKeyCode()==KeyEvent.VK_S){  //player rkm 2 moving downward
                setYDirection(speed);
                move();
                 
            }   
                 break; 
            case(2):
            if(e.getKeyCode()==KeyEvent.VK_UP){    //player rkm 1 move up
                setYDirection(-speed);
                move();
            }
                 if(e.getKeyCode()==KeyEvent.VK_DOWN){   //player rkm 1 move down
                setYDirection(speed);
                move();
            } 
                 break;
        } 
    }
    public void keyReleased(KeyEvent e){        //stops the paddle 
        switch(id){
            case(1):
            if(e.getKeyCode()==KeyEvent.VK_W){   //btw2f player 2
                setYDirection(0);
                move();
            }
                 if(e.getKeyCode()==KeyEvent.VK_S){  // same
                setYDirection(0);
                move();
                 
            }   
                 break; 
            case(2):
            if(e.getKeyCode()==KeyEvent.VK_UP){   //btw2f player 1
                setYDirection(0);
                move();
            }
                 if(e.getKeyCode()==KeyEvent.VK_DOWN){  //btw2f player 1
                setYDirection(0);
                move();
            } 
                 break;
        } 
        
    }
    public void setYDirection (int yDirection){
        yVelocity = yDirection;
    }
    public void move(){    // 
     y = y + yVelocity;
    }
    public void draw(Graphics g){  // shakl el paddle
        if(id==1)
            g.setColor(Color.blue);  // shakl paddle player 2
        if(id==2)
            g.setColor(Color.red);    //shakl paddle 1
        g.fillRect(x, y, width, height);
    }
}
