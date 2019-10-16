import java.util.Random;

import bagel.Window;
import bagel.util.Point;
import bagel.util.Vector2;

public class PowerUp extends Sprite {

    private static final double SPEED = 3;
    
    // random destination point
    private Point destination;
    
    private Vector2 velocity;
	
	// Point point, Vector2 direction
    public PowerUp(Point point) {
    	super(point, "res/powerup.png");
		
		destinationVelocity();
		
	}

	@Override
	public void update() {
				
		
		// calculate the distance between powerup and destination
		double distance = destination.asVector().sub(super.getRect().topLeft().asVector()).length();
		// System.out.println("destinaion " + destination.toString());
		// System.out.println("topleft " + super.getRect().topLeft().toString());
		// System.out.println("distance " + distance);
		
		
		if(distance > 5) {
        	
        	// moving towards to the destination
			super.move(velocity);
			// System.out.println("da guo 5");
        	
        }
        else {
        	
        	// create a new destination
        	// update velocity
        	destinationVelocity();
        	// System.out.println("destinaion " + destination.toString());
        	// System.out.println("topleft " + super.getRect().topLeft().toString());
        	// System.out.println("velocity " + velocity.toString());
        	
    		
    		super.move(velocity);
    		
    		// System.out.println("xiao guo 5");
        	
        	
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
