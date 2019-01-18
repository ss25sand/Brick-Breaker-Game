// refer to package
package brickbreaker;
// import all external classes
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

// MapGenerator Class
public class MapGenerator {
    
    // intialize all variables
    public int map[][];
    public int brickWidth, brickHeight;
    
    int hitPointArray[] = new int[12];
    public Random rand = new Random();
    public int randomIndex;
    
    public Color clr;
    
    // generator function
    public void generator(int row, int col, int stage, int hitPoints){
        // create new map array
        map = new int[row][col];
        // add hitpoint from stage function to array
        hitPointArray[stage-1] = hitPoints;
        // for loop to assign random values to each brick
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                // random index generator
                randomIndex = rand.nextInt(stage);
                // assign value
                map[i][j] = hitPointArray[randomIndex];
            }
        }
        // set brick width and height variables
        brickWidth = 540/col;
        brickHeight = 150/row;
    }
    
    // draw function
    public void draw(Graphics2D g){
          
        // for loop to check entire array
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                // if value is 1
                if(map[i][j] == 1){
                    clr = Color.white;
                    drawBlock((Graphics2D) g, i, j, clr);
                }
                // if value is 2
                if(map[i][j] == 2){
                    clr = Color.blue;
                    drawBlock((Graphics2D) g, i, j, clr);
                }
                // if value is 3
                if(map[i][j] == 3){
                    clr = Color.orange;
                    drawBlock((Graphics2D) g, i, j, clr);
                }
                // if value is 4
                if(map[i][j] == 4){
                    clr = Color.red;
                    drawBlock((Graphics2D) g, i, j, clr);
                }
            }
        } 
    }
    
    // drawBlck function to make code more efficient
    public void drawBlock(Graphics2D g, int i, int j, Color clr){
        
        // set color
        g.setColor(clr);
        // draw rectangle
        g.fillRect(j*brickWidth+80, i*brickHeight+50, brickWidth, brickHeight);
        // make line bolder
        g.setStroke(new BasicStroke(3));
        // set color
        g.setColor(Color.black);
        // draw rectangle
        g.drawRect(j*brickWidth+80, i*brickHeight+50, brickWidth, brickHeight);
    }
    
    // setBrickValue function to decrease value of brick after collision
    public void setBrickValue(int row, int col){
        map[row][col] -= 1 ;
    }
}
