/**
 * SWEN20003 Object Oriented Software Development
 * Project 2B, Semester 2, 2019
 *
 * @author XiaoJun Zhang
 */

import bagel.Window;
import bagel.util.Point;
import bagel.util.Vector2;

/**
 * this class inherit Sprite class
 *
 */
public class Bucket extends Sprite{
    private Vector2 velocity;


	public Bucket(Point point) {
		super(point,"res/bucket.png");
		velocity = new Vector2(512,744);
		velocity = Vector2.left.mul(4);
	}

	@Override
	/**
	 * update the movement of the bucket
	 */
	public void update() {
		
		super.move(velocity);
		
		if (super.getRect().left() < 0 || super.getRect().right() > Window.getWidth()) {
			
			// System.out.println("velocity "+ velocity);
			
            velocity = new Vector2(-velocity.x, velocity.y);
        }

		super.draw();
	}

}
