import bagel.Window;
import bagel.util.Point;
import bagel.util.Vector2;

public class Bucket extends Sprite{
    private Vector2 velocity;


	public Bucket(Point point) {
		super(point,"res/bucket.png");
		//Vector2 direction = new Vector2(512,600);
		velocity = new Vector2(512,744);
		velocity = velocity.mul(4);
	}

	@Override
	public void update() {
		velocity = velocity.add(Vector2.right);
		super.move(velocity);
		
		if (super.getRect().left() < 0 || super.getRect().right() > Window.getWidth()) {
            velocity = new Vector2(-velocity.x, velocity.y);
        }
		super.draw();
	}

}
