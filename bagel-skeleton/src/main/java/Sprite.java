/**
 * SWEN20003 Object Oriented Software Development
 * Project 2B, Semester 2, 2019
 *
 * @author XiaoJun Zhang
 */

import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;
import bagel.util.Vector2;

/**
 * 
 * this is an abstract class with abstract method update()
 * basic methods for all sprite in the game
 */
public abstract class Sprite {
    private Image image;
    private Rectangle rect;

    public Sprite(Point point, String imageSrc) {
        image = new Image(imageSrc);
        rect = image.getBoundingBoxAt(point);
    }

    public Rectangle getRect() {
        return rect;
    }

    /**
     * check intersection between two sprite
     * @param other the sprite checked with calling object
     * @return whether intersect
     */
    public boolean intersects(Sprite other) {
        return rect.intersects(other.rect);
    }

    /**
     * move the triangle of the calling object to next position
     * @param dx the next position to move to
     */
    public void move(Vector2 dx) {
        rect.moveTo(rect.topLeft().asVector().add(dx).asPoint());
    }

    /**
     * draw the sprite
     */
    public void draw() {
        image.draw(rect.centre().x, rect.centre().y);
    }

    public abstract void update();
}
