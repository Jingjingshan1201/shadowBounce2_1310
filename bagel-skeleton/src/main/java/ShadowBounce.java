/**
 * Sample solution for SWEN20003 Object Oriented Software Development
 * Project 1, Semester 2, 2019
 *
 * @author Eleanor McMurtry
 */

import bagel.*;
import bagel.util.Point;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.omg.CORBA.PUBLIC_MEMBER;

public class ShadowBounce extends AbstractGame {
    // the max number of pegs is 66 (3.csv)
	private Peg[] pegs = new Peg[70];
    private Ball ball;
    private static final Point BALL_POSITION = new Point(512, 32);
    private static final double PEG_OFFSET = 100;
    private Bucket bucket;
    
    // initial game level, level 1
    private static final int LEVEL = 2;
    
    // blue pegs counter
    private int bluePegsCounter = 0;

    public ShadowBounce() {
    	//super(1024, 640);
    	for (int i = 0; i < pegs.length; i++) {
        	pegs[i] = null;
        }
    	Point point = new Point (512, 744);
    	bucket = new Bucket(point);
    	
        
        // read csv file and assign (position, color, and shape) to pegs
        readPegsPosition(LEVEL);
        
        // randomly change 1/5 blue pegs to red pegs
        randomlyChangeToRedPegs(bluePegsCounter);
    }

    @Override
    protected void update(Input input) {
        // Check all non-deleted pegs for intersection with the ball
    	bucket.update();
        for (int i = 0; i < pegs.length; ++i) {
            if (pegs[i] != null) {
                if (ball != null && ball.intersects(pegs[i])) {
                    pegs[i] = null;
                } else {
                    pegs[i].update();
                }
            }
        }

        // If we don't have a ball and the mouse button was clicked, create one
        if (input.wasPressed(MouseButtons.LEFT) && ball == null) {
            ball = new Ball(BALL_POSITION, input.directionToMouse(BALL_POSITION),"res/ball.png");
        }

        if (ball != null) {
            ball.update();

            // Delete the ball when it leaves the screen
            if (ball.outOfScreen()) {
                ball = null;
            }
        }
    }
    
    // 每个turn刚开始的时候调用 看这个turn有没有power-up
    public boolean isPowerUp () {
    	if (Math.random()*10 == 1) {
    		return true;
    	}else {
    		return false; 
    	}
    }

    public static void main(String[] args) {
        new ShadowBounce().run();
    }
    
    
    // read pegs' position from csv
    public void readPegsPosition(int level) {
    	
    	// csv file path
    	String csvFilePathString = "csvFile/" + level + ".csv";
    	
    	File csv = new File(csvFilePathString); // CSV文件路径
        csv.setReadable(true);//设置可读
        csv.setWritable(true);//设置可写
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(csv));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line = "";
        String everyLine = "";
        ArrayList<String> allString = new ArrayList<>();
        try {
            while ((line = br.readLine()) != null) // 读取到的内容给line变量
            {
                everyLine = line;                
                System.out.println(everyLine);
                allString.add(everyLine);
                
                
                String color, shape, xCoordinate, yCoordinate;
                
                String[] colorShapeXY = everyLine.split(",");
                
                // shape and color of the peg
                String colorShape = colorShapeXY[0];
                String[] colorNshape = colorShape.split("_");
                // color
                if(colorNshape.length == 2) {
                	
                	color = colorNshape[0];
                    shape = "";
                	
                }
                // color and shape
                else {
                	
                	color = colorNshape[0];
                    shape = colorNshape[2];
                	
                }

                //position of the peg
                xCoordinate = colorShapeXY[1];
                yCoordinate = colorShapeXY[2];
                
                Point point = new Point(Double.valueOf(xCoordinate) , Double.valueOf(yCoordinate));
                String imagePath;
                
                if(color.equals("blue")) {
                	                	
                	if(shape.equals("horizontal")) {
                		
                		imagePath = "res/horizontal-peg.png";

                	}
                	else if(shape.equals("vertical")) {
                		
                		imagePath = "res/vertical-peg.png";
                		
                	}
                	else {
                		
                		imagePath = "res/peg.png";
                		
                	}
                	
                	pegs[allString.size() - 1] = new Peg(point, imagePath, shape);
                	
                	bluePegsCounter++;
                	
                }
                else if(color.equals("grey")) {
                	
                	if(shape.equals("horizontal")) {
                		
                		imagePath = "res/grey-horizontal-peg.png";

                	}
                	else if(shape.equals("vertical")) {
                		
                		imagePath = "res/grey-vertical-peg.png";
                		
                	}
                	else {
                		
                		imagePath = "res/grey-peg.png";
                		
                	}
                	
                	pegs[allString.size() - 1] = new GreyPeg(point, imagePath, shape);
                	
                }
                
                
                
            }
            System.out.println("csv表格中所有行数：" + allString.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        
    public void randomlyChangeToRedPegs(int numberOfBluePegs) {
    	
    	Random rand = new Random();
    	
    	int numberOfRedPegs = numberOfBluePegs / 5;
    	
    	while(numberOfRedPegs > 0) {
    		
    		int randomNumber = rand.nextInt(pegs.length);
    		
    		// RedPeg.class.isInstance(pegs[randomNumber])
    		if(pegs[randomNumber] != null && pegs[randomNumber].getClass().equals(Peg.class)) {
    			
    			Point point = pegs[randomNumber].getPoint();
    			String shape = pegs[randomNumber].getShape();
    			String imagePath;
    			
    			pegs[randomNumber] = null;
    			
    			if(shape.equals("horizontal")) {
            		
            		imagePath = "res/red-horizontal-peg.png";

            	}
            	else if(shape.equals("vertical")) {
            		
            		imagePath = "res/red-vertical-peg.png";
            		
            	}
            	else {
            		
            		imagePath = "res/red-peg.png";
            		
            	}
    			
    			pegs[randomNumber] = new RedPeg(point, imagePath, shape);
    			
    			numberOfRedPegs--;
    			
    		}
    	}
    	
    }
    
}
