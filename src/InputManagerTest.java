
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.io.*;
import javax.sound.sampled.*;

import com.brackeen.javagamebook.graphics.*;
import com.brackeen.javagamebook.input.*;
import com.brackeen.javagamebook.test.GameCore;
import java.awt.geom.AffineTransform; //TP

/**
    InputManagerTest tests the InputManager with a simple
    run-and-jump mechanism. The player moves and jumps using
    the arrow keys and the space bar.
    <p>Also, InputManagerTest demonstrates pausing a game
    by not updating the game elements if the game is paused.
*/
public class InputManagerTest extends GameCore {

    public static void main(String[] args) {
        new InputManagerTest().run();
    }

   // protected GameAction jump;
    protected GameAction exit;
    protected GameAction moveLeft;
    protected GameAction moveRight;
    protected GameAction pause;
    ///NYTT
    protected GameAction moveo;
    
    protected InputManager inputManager;
    private Player player;
    private Ball ball;
    private Ball ball2;  
	private Ball ball3;    
    private Image bgImage;
    private Image bgImage2;    
    private boolean paused;
    //Deklareras som instansvariabler
    private Animation animo;
    private Animation anim;
	private Animation ball_anim;
	private Animation ball2_anim;
	private Animation ball3_anim;	
	private float ballvelo1,ballvelo2,ballvelo3;
	private int score = 0;
			

    public void init() {
        super.init();
        Window window = screen.getFullScreenWindow();
        inputManager = new InputManager(window);

        // use these lines for relative mouse mode
        //inputManager.setRelativeMouseMode(true);
        //inputManager.setCursor(InputManager.INVISIBLE_CURSOR);

        createGameActions();
        createSprite();
        paused = false;
    }


    /**
        Tests whether the game is paused or not.
    */
    public boolean isPaused() {
        return paused;
    }


    /**
        Sets the paused state.
    */
    public void setPaused(boolean p) {
        if (paused != p) {
            this.paused = p;
            inputManager.resetAllGameActions();
        }
    }


    public void update(long elapsedTime) {
        // check input that can happen whether paused or not
        checkSystemInput();

        if (!isPaused()) {
            // check game input
            checkGameInput();

            // update sprite
            player.update(elapsedTime);
            ball.update(elapsedTime);
            ball2.update(elapsedTime);            
            ball3.update(elapsedTime);  
            
            if(isCollision(player, ball)){
            	Random randomGenerator = new Random();
            	int randnumb = randomGenerator.nextInt(750);
            		
            		
					ball.setVelocityY(ballvelo1);
					ball.setX(randnumb);
					ball.setY(-10);
					score++;
					ballvelo1+=0.02f;
					
//           	ball.setVelocityX(10.1f);
//         		ball.setVelocityY(10.1f);  
		System.out.println(score);

        	}
        	
            if(isCollision(player, ball2)){
            	Random randomGenerator = new Random();
            	int randnumb = randomGenerator.nextInt(750);
            	
					ball2.setVelocityY(ballvelo2);
					ball2.setX(randnumb);
					ball2.setY(-10);
					score++;
					ballvelo2+=0.02f;
//            	ball.setVelocityX(10.1f);
//         		ball.setVelocityY(10.1f); 
		System.out.println(score);

        	}    

            if(isCollision(player, ball3)){
            	Random randomGenerator = new Random();
            	int randnumb = randomGenerator.nextInt(750);           	
            	
					ball3.setVelocityY(ballvelo3);
					ball3.setX(randnumb);
					ball3.setY(-10);
					score++;
			ballvelo3+=0.02f;
//            	ball.setVelocityX(10.1f);
//         		ball.setVelocityY(10.1f);   
		System.out.println(score);
        	}      
		}
        
    }


    /**
        Checks input from GameActions that can be pressed
        regardless of whether the game is paused or not.
    */
    public void checkSystemInput() {
        if (pause.isPressed()) {
            setPaused(!isPaused());
        }
        if (exit.isPressed()) {
            stop();
        }
    }
    
        


    /**
        Checks input from GameActions that can be pressed
        only when the game is not paused.
    */
    public void checkGameInput() {
        float velocityX = 0;
        if (moveLeft.isPressed()) {
            velocityX-=Player.SPEED;
        }
        if (moveRight.isPressed()) {
            velocityX+=Player.SPEED;
        }

        if (moveo.isPressed()) {
      	  if (moveLeft.isPressed()) {
        	    velocityX-=Player.XSPEED;
     	   }
     	   if (moveRight.isPressed()) {
     	       velocityX+=Player.XSPEED;
        	}        	
  // velocityX+=Player.XSPEED;          
        }        
  
         
        
        player.setVelocityX(velocityX);
       

        /*if (jump.isPressed() &&
            player.getState() != Player.STATE_JUMPING){
            player.jump();
        }*/
    }


    public void draw(Graphics2D g) {
        // draw background
        
	
		//TP
		AffineTransform transform = new AffineTransform();
		
		// TP   translate the player
         transform.setToTranslation(player.getX(),player.getY());
         
         	// if the sprite is moving left, flip the image
           /* if (player.getVelocityX() < 0) {
                transform.scale(-1, 1);
               
                transform.translate(-player.getWidth(), 0);
                
            }*/
            if(moveo.isPressed()){
            	player.setAnimation(animo);
            	
            	
            
            }else{
            	player.setAnimation(anim);
            }
            if(moveo.isPressed() == true){
            
        		// draw background
				g.drawImage(bgImage2, 0, 0, null);
            }else{
            	g.drawImage(bgImage, 0, 0, null);
            }
            
            
            // draw sprite Glöm inte transform TP
        g.drawImage(player.getImage(),
            transform,
            null);

        g.drawImage(ball.getImage(),
        	Math.round(ball.getX()), 
        	Math.round(ball.getY()), 
        	null); 
        	
        g.drawImage(ball2.getImage(),
        	Math.round(ball2.getX()), 
        	Math.round(ball2.getY()), 
        	null); 
        	
        g.drawImage(ball3.getImage(),
        	Math.round(ball3.getX()), 
        	Math.round(ball3.getY()), 
        	null);  
        	
        g.drawString("Points: " + score, 50, 100);
 
        // draw sprite
        /*g.drawImage(player.getImage(),
            Math.round(player.getX()),
            Math.round(player.getY()),
            null);*/
    }


    /**
        Creates GameActions and maps them to keys.
    */
    public void createGameActions() {
        /*jump = new GameAction("jump",
            GameAction.DETECT_INITAL_PRESS_ONLY);*/
        exit = new GameAction("exit",
            GameAction.DETECT_INITAL_PRESS_ONLY);
        moveLeft = new GameAction("moveLeft");
        moveRight = new GameAction("moveRight");
        
        pause = new GameAction("pause",
            GameAction.DETECT_INITAL_PRESS_ONLY);
            
        ///NYTT
		moveo = new GameAction("moveo");
		 inputManager.mapToKey(moveo, KeyEvent.VK_SPACE);
		 
		 
        inputManager.mapToKey(exit, KeyEvent.VK_ESCAPE);
        inputManager.mapToKey(pause, KeyEvent.VK_P);

        // jump with spacebar or mouse button
       /* inputManager.mapToKey(jump, KeyEvent.VK_SPACE);
        inputManager.mapToMouse(jump,
            InputManager.MOUSE_BUTTON_1);*/

        // move with the arrow keys...
        inputManager.mapToKey(moveLeft, KeyEvent.VK_LEFT);
        inputManager.mapToKey(moveRight, KeyEvent.VK_RIGHT);

        // ... or with A and D.
        inputManager.mapToKey(moveLeft, KeyEvent.VK_A);
        inputManager.mapToKey(moveRight, KeyEvent.VK_D);

        // use these lines to map player movement to the mouse
        //inputManager.mapToMouse(moveLeft,
        //  InputManager.MOUSE_MOVE_LEFT);
        //inputManager.mapToMouse(moveRight,
        //  InputManager.MOUSE_MOVE_RIGHT);

	}
	public boolean isCollision(Sprite s1, Sprite s2){
		int s1y = Math.round(s1.getY());
		int s2y = Math.round(s2.getY());		
		int s1x = Math.round(s1.getX());
		int s2x = Math.round(s2.getX());			
		return (s1y < s2y + s2.getHeight() && s2y < s1y + s1.getHeight()
		&& s1x < s2x + s2.getWidth() && s2x < s1x + s1.getWidth());		
		
	}

    /**
        Load images and creates the Player sprite.
    */
    private void createSprite() {
        // load images
        bgImage = loadImage("../images/background.png");
        
        Image player1 = loadImage("../images/player1.png");
        Image player2 = loadImage("../images/player2.png");
        Image player3 = loadImage("../images/player3.png");
        

        // create animation
         anim = new Animation();    
        anim.addFrame(player1, 250);
        anim.addFrame(player2, 150);
        anim.addFrame(player1, 150);
        anim.addFrame(player2, 150);
        anim.addFrame(player3, 200);
        anim.addFrame(player2, 150);
        
        Image ballbild = loadImage("../images/ball.png"); 
        Animation ball_anim = new Animation();
        ball_anim.addFrame(ballbild, 150);

        Image ball2bild = loadImage("../images/ball.png"); 
        Animation ball2_anim = new Animation();
        ball2_anim.addFrame(ball2bild, 150);     
        
        Image ball3bild = loadImage("../images/ball.png"); 
        Animation ball3_anim = new Animation();
        ball2_anim.addFrame(ball3bild, 150);          
        
        ///NYTT!!!1
        bgImage2 = loadImage("../images/background2.png");        
        Image player1o = loadImage("../images/player1_o.png");
        Image player2o = loadImage("../images/player2_o.png");
        Image player3o = loadImage("../images/player3_o.png");
        //       
         animo = new Animation();
        
         
        animo.addFrame(player1o, 250);
        animo.addFrame(player2o, 150);
        animo.addFrame(player1o, 150);
        animo.addFrame(player2o, 150);
        animo.addFrame(player3o, 200);
        animo.addFrame(player2o, 150);
        
        ballvelo1 = 0.15f;
		ballvelo2 = 0.13f;
		ballvelo3 = 0.18f;
		
		player = new Player(animo);
        player.setFloorY(screen.getHeight() - player.getHeight());
		
		ball = new Ball(ball_anim);
		ball.setX(350);
		ball.setY(0);
		ball.setVelocityY(ballvelo2);
		
		ball2 = new Ball(ball_anim);
		ball2.setX(550);
		ball2.setY(0);
		ball2.setVelocityY(ballvelo1);
		
		ball3 = new Ball(ball_anim);
		ball3.setX(650);
		ball3.setY(0);
		ball3.setVelocityY(ballvelo3);		
		
    }


}
