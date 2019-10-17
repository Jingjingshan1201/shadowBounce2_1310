/**
 * SWEN20003 Object Oriented Software Development
 * Project 2B, Semester 2, 2019
 *
 * @author XiaoJun Zhang
 */

import java.awt.Shape;

import bagel.util.Point;

/**
 * this class inherit Sprite class
 * 
 *
 */
public class Peg extends Sprite {
	
	private Point point;
	private String shape;
	
    public Peg(Point point, String imagePath, String shape) {
        super(point, imagePath);
        
        this.point = point;
        this.shape = shape;
    }
	
    @Override
    public void update() {
        super.draw();
    }
    
    public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}
}
