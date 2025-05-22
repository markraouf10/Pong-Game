package Classes;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;


public class GamePanel extends JPanel implements Runnable{
    
    static final int GAME_WIDTH = 1000 ;    // est3mlt final 3l4an ml3b4 feha tany odam 
    static final int GAME_HEIGHT = (int)(GAME_WIDTH*(0.5555))  ;  // 5/9=0.5555 da el standard ping pong table dimensions
    static final Dimension SCREEN_SIZE = new Dimension( GAME_WIDTH,GAME_HEIGHT);
    static final int BALL_DIAMETER = 20;
    static final int PADDLE_WIDTH = 25;
    static final int PADDLE_HEIGHT = 100;
    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random ;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    Score score;
    Image bg;              //soret el bg gwa el game phase
    Image menuBg;          //soret el bg fel main menu
    int gameState = 0; // 0 = menu, 1 = playing, 2 = game over
    JPanel pausePanel;
    
    static final int WINNING_SCORE = 15;
    boolean paused = false;
    JButton resumeButton, restartButton, menuButton;

    GamePanel(){
        newPaddles();
        newBall();
        score = new Score(GAME_WIDTH,GAME_HEIGHT);
        this.setFocusable(true);
        this.addKeyListener(new AL());  //since AL is an inner class yb2a da by extend KeyAdapters class
        this.setPreferredSize(SCREEN_SIZE);  //Dimensions
        this.setLayout(null);

        //assets --> bgs
        bg = new ImageIcon("Assets/background.jpg").getImage();
        menuBg = new ImageIcon("Assets/menu_bg.jpg").getImage();

        // Pause panel
        pausePanel = new JPanel();
        pausePanel.setLayout(null);
        pausePanel.setBackground(new Color(0, 0, 0, 150)); // translucent black
        pausePanel.setBounds(250, 150, 500, 300);
        pausePanel.setVisible(false);

        JLabel pausedLabel = new JLabel("PAUSED", JLabel.CENTER);
        pausedLabel.setFont(new Font("Consolas", Font.BOLD, 40));
        pausedLabel.setForeground(Color.WHITE);
        pausedLabel.setBounds(150, 20, 200, 50);
        pausePanel.add(pausedLabel);

        JLabel resumeHint = new JLabel("Resume [P]", JLabel.CENTER);
        resumeHint.setFont(new Font("Arial", Font.PLAIN, 18));
        resumeHint.setForeground(Color.LIGHT_GRAY);
        resumeHint.setBounds(150, 80, 200, 30);
        pausePanel.add(resumeHint);

        JLabel restartHint = new JLabel("Restart [R]", JLabel.CENTER);
        restartHint.setFont(new Font("Arial", Font.PLAIN, 18));
        restartHint.setForeground(Color.LIGHT_GRAY);
        restartHint.setBounds(150, 110, 200, 30);
        pausePanel.add(restartHint);

        JLabel menuHint = new JLabel("Main Menu [Esc]", JLabel.CENTER);
        menuHint.setFont(new Font("Arial", Font.PLAIN, 18));
        menuHint.setForeground(Color.LIGHT_GRAY);
        menuHint.setBounds(150, 140, 200, 30);
        pausePanel.add(menuHint);

        resumeButton = new JButton("Resume");
        resumeButton.setBounds(150, 170, 200, 40);
        resumeButton.setFont(new Font("Arial", Font.BOLD, 18));
        resumeButton.addActionListener(e -> {
            paused = false;
            pausePanel.setVisible(false);
        });
        pausePanel.add(resumeButton);

        restartButton = new JButton("Restart");
        restartButton.setBounds(150, 215, 200, 40);
        restartButton.setFont(new Font("Arial", Font.BOLD, 18));
        restartButton.addActionListener(e -> restartGame());
        pausePanel.add(restartButton);

        menuButton = new JButton("Main Menu");
        menuButton.setBounds(150, 260, 200, 40);
        menuButton.setFont(new Font("Arial", Font.BOLD, 18));
        menuButton.addActionListener(e -> backToMenu());
        pausePanel.add(menuButton);

        this.add(pausePanel);

        gameThread = new Thread(this);  //implementing the runnuable interface
        gameThread.start() ;
    }

    public void newBall(){
        random = new Random();
        ball = new Ball((GAME_WIDTH/2)-(BALL_DIAMETER/2),random.nextInt(GAME_HEIGHT-BALL_DIAMETER),BALL_DIAMETER,BALL_DIAMETER);
    } 

    public void newPaddles(){
        paddle1 = new Paddle(0,((GAME_HEIGHT/2)-(PADDLE_HEIGHT/2)),PADDLE_WIDTH,PADDLE_HEIGHT,1); 
        paddle2 = new Paddle(GAME_WIDTH-PADDLE_WIDTH,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,2);
    }

    public void restartGame() {
        score.player1 = 0;
        score.player2 = 0;
        newPaddles();
        newBall();
        gameState = 1;
        paused = false;
        pausePanel.setVisible(false);
        repaint();
    }

    public void backToMenu() {
        score.player1 = 0;
        score.player2 = 0;
        paused = false;
        gameState = 0;
        pausePanel.setVisible(false);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (gameState == 0) {
            drawMenu(g);
        } else if (gameState == 1) {
            g.drawImage(bg, 0, 0, GAME_WIDTH, GAME_HEIGHT, this);
            paddle1.draw(g);
            paddle2.draw(g);
            ball.draw(g);
            score.draw(g);
        } else if (gameState == 2) {
            drawGameOver(g);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    public void drawMenu(Graphics g) {
        g.drawImage(menuBg, 0, 0, GAME_WIDTH, GAME_HEIGHT, this);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Consolas", Font.BOLD, 50));
        g.drawString("PONG GAME", 330, 200);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString("Press ENTER to Start", 320, 300);
    }

    public void drawGameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Consolas", Font.BOLD, 40));
        String winner = score.player1 > score.player2 ? "Player 1 Wins!" : "Player 2 Wins!";
        g.drawString(winner, 320, 250);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString("Press R to Restart or Q to Quit", 250, 320);
    }

    public void move(){
        paddle1.move();
        paddle2.move();
        ball.move();
    }

    public void checkCollision(){
        if(ball.y <= 0 || ball.y >= GAME_HEIGHT - BALL_DIAMETER) {
            ball.setYDirection(-ball.yVelocity);
        }

        if(ball.intersects(paddle1)){
            ball.xVelocity = Math.abs(ball.xVelocity) + 1;
            ball.yVelocity += (ball.yVelocity > 0) ? 1 : -1;
            ball.setXDirection(ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }

        if(ball.intersects(paddle2)){
            ball.xVelocity = -Math.abs(ball.xVelocity) - 1;
            ball.yVelocity += (ball.yVelocity > 0) ? 1 : -1;
            ball.setXDirection(ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }

        if(paddle1.y < 0) paddle1.y = 0;
        if(paddle1.y > GAME_HEIGHT - PADDLE_HEIGHT) paddle1.y = GAME_HEIGHT - PADDLE_HEIGHT;
        if(paddle2.y < 0) paddle2.y = 0;
        if(paddle2.y > GAME_HEIGHT - PADDLE_HEIGHT) paddle2.y = GAME_HEIGHT - PADDLE_HEIGHT;

        if (ball.x <= 0) {
            score.player2++;
            newPaddles();
            newBall();
        }

        if (ball.x >= GAME_WIDTH - BALL_DIAMETER) {
            score.player1++;
            newPaddles();
            newBall();
        }
    }

public void checkGameOver() {
    if (score.player1 >= WINNING_SCORE || score.player2 >= WINNING_SCORE) {
        gameState = 2;
        gameThread.interrupt();

        String winner = score.player1 > score.player2 ? PongGame.loggedInUsername : "opponent";
        PlayerDAO.incrementWins(winner);

        // 🏆 Get leaderboard data
        List<String[]> leaderboard = PlayerDAO.getLeaderboard();

        StringBuilder msg = new StringBuilder();
        msg.append("🏆 Top 5 Players 🏆\n\n");
        msg.append(String.format("%-15s | %s\n", "Username", "Wins"));
        msg.append("---------------------------\n");

        for (String[] row : leaderboard) {
            msg.append(String.format("%-15s | %s\n", row[0], row[1]));
        }
        JOptionPane.showMessageDialog(this, msg.toString(), "Leaderboard", JOptionPane.INFORMATION_MESSAGE);
    }
}


    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;

        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            if (delta >= 1) {
                if (!paused && gameState == 1) {
                    move();
                    checkCollision();
                    checkGameOver();
                    repaint();
                } else {
                    repaint(); // draw pause/menu
                }
                delta--;
            }
        }
    }

    public class AL extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            if (gameState == 0 && e.getKeyCode() == KeyEvent.VK_ENTER) {
                gameState = 1;
            } else if (gameState == 2) {
                if (e.getKeyCode() == KeyEvent.VK_R) restartGame();
                if (e.getKeyCode() == KeyEvent.VK_Q) System.exit(0);
            }

            if (e.getKeyCode() == KeyEvent.VK_P && gameState == 1) {
                paused = !paused;
                pausePanel.setVisible(paused);
            }

            if (paused && e.getKeyCode() == KeyEvent.VK_R) restartGame();
            if (paused && e.getKeyCode() == KeyEvent.VK_ESCAPE) backToMenu();

            if (gameState == 1 && !paused) {
                paddle1.keyPressed(e);
                paddle2.keyPressed(e);
            }
        }

        public void keyReleased(KeyEvent e) {
            if (gameState == 1 && !paused) {
                paddle1.keyReleased(e);
                paddle2.keyReleased(e);
            }
        }
    }

}
