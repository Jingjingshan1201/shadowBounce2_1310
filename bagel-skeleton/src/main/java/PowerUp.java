/**
 * SWEN20003 Object Oriented Software Development
 * Project 2B, Semester 2, 2019
 *
 * @author XiaoJun Zhang
 */

import java.util.Random;

import bagel.Window;
import bagel.util.Point;
import bagel.util.Vector2;


/**
 * this class inherit Sprite class
 *
 */
public class PowerUp extends Sprite {

    private static final double SPEED = 3;
    
    // random destination point
    private Point destination;
    
    private Vector2 velocity;
	
    public PowerUp(Point point) {
    	super(point, "res/powerup.png");
		
		destinationVelocity();
		
	}

	@Override
	/**
	 * update the movement of the power up
	 */
	public void update() {
				
		
		// calculate the distance between powerup and destination
		double distance = destination.asVector().sub(super.getRect().topLeft().asVector()).length();
		
		
		if(distance > 5) {
        	
        	// moving towards to the destination
			super.move(velocity);
        	
        }
        else {
        	
        	// create a new destination
        	// update velocity
        	destinationVelocity();
    		
    		super.move(velocity);
        	
        	
        }
		
		super.move(velocity);
        
        super.draw();
		
	}
	
	private void destinationVelocity() {
		
		Random rand = new Random();
		// generate x and y for destination point
		destination = new Point(Window.getWidth() * rand.nextDouble(), Window.getHeight() * rand.nextDouble());
		
		// calculate the velocity
		velocity = destination.asVector().sub(super.getRect().topLeft().asVector()).normalised().mul(SPEED);
		
	}
	

}
