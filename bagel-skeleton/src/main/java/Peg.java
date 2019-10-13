/**
 * Sample solution for SWEN20003 Object Oriented Software Development
 * Project 1, Semester 2, 2019
 *
 * @author Eleanor McMurtry
 */

import java.awt.Shape;

import bagel.util.Point;

public class Peg extends Sprite {
    
	// attributes
	private String shape, color;
	
	
	// , String shape, String color
	
	public Peg(Point point) {
        super(point, "res/peg.png");
        
        // shape
        // color
        
    }

    @Override
    public void update() {
        super.draw();
    }
}
