/**
 * Sample solution for SWEN20003 Object Oriented Software Development
 * Project 1, Semester 2, 2019
 *
 * @author Eleanor McMurtry
 */

import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;
import bagel.util.Vector2;

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

    public boolean intersects(Sprite other) {
        return rect.intersects(other.rect);
    }

    public void move(Vector2 dx) {
        rect.moveTo(rect.topLeft().asVector().add(dx).asPoint());
    }

    public void draw() {
        image.draw(rect.centre().x, rect.centre().y);
    }

    public abstract void update();
}
