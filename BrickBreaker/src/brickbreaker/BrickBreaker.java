/*
 * Name: Saman Sandhu
 * Date: June 20, 2018
 * BrickBreaker project
 * Teacher: Mr. Rexhepi
 * Course: ICS 4UI
 * Description: A remake of the game Brick Breaker from Black Berry
*/

// refer to package
package brickbreaker;
// import all external classes
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;

// BrickBreaker class
public class BrickBreaker {

    // main static method
    public static void main(String[] args) {
        
        // initial JFrame
        JFrame frame = new JFrame("Brick Breaker");
        // set size
        frame.setPreferredSize(new Dimension(700, 600));
        frame.setMinimumSize(new Dimension(700, 600));
        frame.setMaximumSize(new Dimension(700, 600));
        frame.setResizable(false);
        // what to do when the close button is pressed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // set background
        frame.getContentPane().setBackground(Color.black);
        frame.setLocationRelativeTo(null);
        // add class to JFrame
        frame.add(new Gameplay());
        // set visible
        frame.setVisible(true);
    }
}
