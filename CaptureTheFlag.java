//rename some of the methods
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class CaptureTheFlag{
  

  public static void main(String[] args){
    String redText;
    String blueText;
    redText = args[0];
    blueText = args[1];
    Field f = new Field();
    f.START_FROM_BASE = true;
    System.out.println(f.minX + "," + f.minY);
    

    /* --------------------------------------------- */
    /* create players in the game                    */
    /* 0 - seeker2
     * 1 - aggressor
     * 2 - defender 
     * 3 - liberator
     * 4 - flaghunter
   */ 

      // field , number of players , team , type    
    Team redSeek    = new Team(f , 15 , 0 , 0); // red seeker
    Team blueSeek   = new Team(f , 15 , 1 , 0); // blue seeker
    Team redAggre   = new Team(f , 2 , 0 , 1);  // red aggressor
    Team blueAggre  = new Team(f , 2 , 1 , 1);  // blue aggressor
    Team redDefend  = new Team(f , 1 , 0 , 2);  // red defender
    Team blueDefend = new Team(f , 1 , 1 , 2);  // blue defender
    Team redLib     = new Team(f , 5 , 0 , 3);  // red liberator
    Team blueLib    = new Team(f , 5 , 1 , 3);  // blue liberator
    Team redFlag    = new Team(f , 1 , 0 , 4);  // red flag hunter 
    Team blueFlag   = new Team(f , 1 , 1 , 4);  // blue flag hunter
    Team redDecoy   = new Team(f , 5 , 0 , 5);  // red decoy
    Team blueDecoy  = new Team(f , 5 , 1 , 5);  // blue decoy
    /* ------------------------------------------- */
    /* play the game                               */

    boolean gameRunning = true;
    
    long iterations = 0;
    while( gameRunning ){
      iterations += 1;
   
      /* allow players to think about what to do and change directions */
      f.play();
      
      /* move all players */
      f.update();
      
      /* redraw all the players in their new positions */
      f.draw();
      
      
      /* give some message to display on the field */
      if(iterations < 100){
        f.view.message("game on!");
      }else if(iterations < 300){
        f.view.message("keeps on going...");
      }else{
        f.view.message("and going...");
      }
      
      // uncomment this if game is moving too fast , sleep for 10 ms
      try{ Thread.sleep(10); } catch(Exception e) {}
      
      gameRunning = f.gameStillRunning();
    }

    Team redFile = new Team(redText, f, "reds");
    Team blueFile = new Team(blueText, f, "blues"); 

  }
  
}