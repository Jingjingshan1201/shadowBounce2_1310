import bagel.Window;
import bagel.util.Point;
import bagel.util.Vector2;

public class Bucket extends Sprite{
    private Vector2 velocity;


	public Bucket(Point point) {
		super(point,"res/bucket.png");
		velocity = new Vector2(512,744);
		velocity = Vector2.right.mul(4);
	}

	@Override
	public void update() {
		
		super.move(velocity);
		
		if (super.getRect().left() < 0 || super.getRect().right() > Window.getWidth()) {
			
			System.out.println("velocity "+ velocity);
			
            velocity = new Vector2(-velocity.x, velocity.y);
        }

		super.draw();
	}

}
