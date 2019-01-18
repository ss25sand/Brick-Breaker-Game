// refer to package
package brickbreaker;
// import all external classes
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

// Gameplay class
public class Gameplay extends JPanel implements MouseMotionListener, ActionListener, KeyListener{

// initialize all variables
    
    // GUI variables
    private JButton playButton, exitButton, titleLabel;
    private Font titleFont = new Font("Time New Roman", Font.PLAIN, 70);
    private Font normalFont = new Font("Time New Roman", Font.PLAIN, 20);
    
    // score and play variable
    private boolean play = false;
    private int score = 0;
    
    // totalBrick variable
    private int totalBricks = 9;
    
    // timer and delay variables
    private Timer timer;
    private int delay = 5;
    
    // position variables
    private int playerX = 310;
    private int ballPosX = 120;
    private int ballPosY = 350;
    private int ballDirX = -1;
    private int ballDirY = -2;
    
    // call class
    private MapGenerator mapGen = new MapGenerator();
    
    // stage variables
    private int stage = 1;
    private int extraRow,hitPoints;
    
    // constructor
    public Gameplay(){
        
        setLayout(null);
        // button used as title label
        titleLabel = new JButton("Brick Breaker");
        titleLabel.setFont(titleFont);
        titleLabel.setBounds(100,100,500,150);
        titleLabel.setBackground(Color.white);
        titleLabel.setForeground(Color.black);
        // play button to play game                                     // GUI CODE
        playButton = new JButton("Play");
        playButton.setFont(normalFont);
        playButton.setBounds(200,400,100,50);
        // exit button to exit game
        exitButton = new JButton("Exit");
        exitButton.setFont(normalFont);
        exitButton.setBounds(400,400,100,50);
        // add an action listener to play button
        playButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                playButton.setVisible(false);
                StartGame();
                System.out.println("Pressed");
            }
        });
        // add an action listener to exit button
        exitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                exitButton.setVisible(false);
                System.exit(0);
                System.out.println("Pressed");
            }
        });
        // add all the buttons to the screen
        add(titleLabel);
        add(playButton);
        add(exitButton);
        
        // call function to start game
        // StartGame();
    }
    
    // StartGame method
    public void StartGame(){
        
        // call the stages function
        stage(stage);
        // add all the listeners
        addMouseMotionListener(this);
        addKeyListener(this);
        // turn communication between game and keyboard on
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        // start timer
        timer = new Timer(delay, this);
        timer.start();
    }
    
    // paint method
    public void paint(Graphics g){
        
        // background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);
        // drawing map
        mapGen.draw((Graphics2D) g);
        // borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);
        // scores
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("Score: "+score, 560, 30);
        // paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);
        // ball
        g.setColor(Color.yellow);
        g.fillOval(ballPosX, ballPosY, 20, 20);
        // if all the bricks are broken
        if(totalBricks <= 0){
            play = false;
            ballDirX = 0;
            ballDirY = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Stage "+(stage)+" Complete", 245, 300);
            g.setFont(new Font("serif", Font.BOLD, 25));
            g.drawString("Please Enter to Continue", 225, 325);
        }
        // if the ball is missed by the player
        if(ballPosY > 570){
            play = false;
            ballDirX = 0;
            ballDirY = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Game Over", 275, 300);
            g.setFont(new Font("serif", Font.BOLD, 25));
            g.drawString("Score: "+score, 300, 325);
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Please Enter to Restart!", 235, 350);
        }
        g.dispose(); 
    }
    
    // stage method
    public void stage(int stage){
        
        if(stage <= 3){
            extraRow = 0;
            hitPoints = 1;
        }
        if(stage > 3 && stage <= 6){
            extraRow = 1;
            hitPoints = 2;
        }
        if(stage > 6 && stage <= 9){
            extraRow = 2;
            hitPoints = 3;
        }
        if(stage > 9 && stage <= 12){
            extraRow = 3;
            hitPoints = 4;
        }

        play = true;
        ballPosX = 120;
        ballPosY = 350;
        ballDirX = -1;
        ballDirY = -2;
        playerX = 310;
        totalBricks = (3+extraRow)*(2+stage);
        mapGen.generator(3+extraRow,2+stage, stage, hitPoints);
        
        repaint();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {

        timer.start();
        // if playing game
        if(play){
            // if ball collides with player
            if(new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))){
                ballDirY = -ballDirY;
            }
            // check entire 2D array
            A: for(int i = 0; i < mapGen.map.length; i++){
                for(int j = 0; j < mapGen.map[0].length; j++){
                    // if the brick value is greater than 0
                    if(mapGen.map[i][j] > 0){
                        int brickX = j*mapGen.brickWidth+80;
                        int brickY = i*mapGen.brickHeight+50;
                        int brickWidth = mapGen.brickWidth;
                        int brickHeight = mapGen.brickHeight;
                        // set 2 rectangles
                        Rectangle brickRect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
                        // if ball collides with brick
                        if(ballRect.intersects(brickRect)){
                            // call function
                            mapGen.setBrickValue(i, j);
                            // if the brick value is equal to zero
                            if(mapGen.map[i][j] == 0){
                                totalBricks--;
                            }
                            // increase score on every collision
                            score += 5;
                            // if ball hits brick
                            if(ballPosX + 19 <= brickRect.x || ballPosX + 1 >= brickRect.x + brickRect.width){
                                ballDirX = -ballDirX;
                            }
                            else{
                                ballDirY = -ballDirY;
                            }
                            // break back to for loops
                            break A;
                        }
                    }
                }
            }
            // update ball position
            ballPosX += ballDirX;
            ballPosY += ballDirY;
            // if ball hits left border
            if(ballPosX < 0){
                ballDirX = -ballDirX;
            }
            // if ball hits right border
            if(ballPosX > 670){
                ballDirX = -ballDirX;
            }
            // if ball hits top border
            if(ballPosY < 0){
                ballDirY = -ballDirY;
            }
        }
        // call paint function
        repaint();
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {
        
        // get mouse position
        playerX = e.getX() - 50;
        // if player is trying to left screen to right
        if(playerX >= 600){
            playerX = 600;
        }
        else{
            moveRight();
        }
        // if player is trying to left screen to left
        if(playerX < 10 ){
            playerX = 10;
        }
        else{
            moveLeft();
        }
        e.consume();
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        
        // if enter key is pressed
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            // if game is not playing
            if(!play){
                // if there are no bricks
                if(totalBricks <=0){
                    stage++;
                    stage(stage);  
                }
                // restart game
                else{
                    stage = 1;
                    stage(stage);
                    score = 0;
                }
            }
        }
        e.consume();
    }
    
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
    
    // moreRight method to move player right
    public void moveRight(){
        play = true;
        playerX += 20;
    }
    
    // moreLeft method to move player left
    public void moveLeft(){
        play = true;
        playerX -= 20;
    }
}