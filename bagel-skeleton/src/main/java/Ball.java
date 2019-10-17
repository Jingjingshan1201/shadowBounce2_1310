/**
 * SWEN20003 Object Oriented Software Development
 * Project 2B, Semester 2, 2019
 *
 * @author XiaoJun Zhang
 */

import bagel.Window;
import bagel.util.Point;
import bagel.util.Vector2;

public class Ball extends Sprite {
    private Vector2 velocity;
    private static final double GRAVITY = 0.15;
    private static final double SPEED = 10;
    
    private Point currentPoint;

    public Ball(Point point, Vector2 direction, String imagePath) {
        super(point, imagePath);
        velocity = direction.mul(SPEED);
        this.currentPoint = point;
    }


    /**
     * check the ball is out of screen or not
     * @return whether ball is out of screen
     */
	public boolean outOfScreen() {
        return super.getRect().top() > Window.getHeight();
    }

    @Override
    /**
     * update the ball's movement
     */
    public void update() {
    	
    	currentPoint = super.getRect().topLeft();
    	
        velocity = velocity.add(Vector2.down.mul(GRAVITY));
        super.move(velocity);

        if (super.getRect().left() < 0 || super.getRect().right() > Window.getWidth()) {
            velocity = new Vector2(-velocity.x, velocity.y);
        }
        
        // bounce off at the top
        if (super.getRect().top() < 0) {
            velocity = new Vector2(velocity.x, -velocity.y);
        }

        super.draw();
    }


	public Vector2 getVelocity() {
		return velocity;
	}


	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}


	public Point getCurrentPoint() {
		return currentPoint;
	}


	public void setCurrentPoint(Point currentPoint) {
		this.currentPoint = currentPoint;
	}


	
}
