/**
 * Sample solution for SWEN20003 Object Oriented Software Development
 * Project 1, Semester 2, 2019
 *
 * @author Eleanor McMurtry
 */

import bagel.*;
import bagel.util.Point;
import bagel.util.Side;
import bagel.util.Vector2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.system.windows.WindowsLibrary;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.omg.CORBA.PUBLIC_MEMBER;

public class ShadowBounce extends AbstractGame {
    // the max number of pegs is 66 (3.csv)
	private Peg[] pegs = new Peg[70];
    //private Ball ball;
    private static final Point BALL_POSITION = new Point(512, 32);
    private static final double PEG_OFFSET = 100;
    private Bucket bucket;
    private PowerUp powerUp;
    private boolean isPowerUp = true;
    private Ball[] balls;
    
    // initial game level, level 1
    private static final int LEVEL = 1;
    
    // current level of the game
    private int currentLevel = LEVEL;
    
    // number of shots
    private int numberOfShots = 20;
    private static final int SHOTSRUNOUTOF = 0;
    
    // blue pegs counter
    private int bluePegsCounter = 0;
    
    // number of red pegs
    private int numberOfRedPegs = 0;
    
    // does green peg and/or fireball active?
    boolean isGreenPeg, isFireBall;
    
    

    public ShadowBounce() {
    	balls = new Ball[3];
    	initializeGameBoard(currentLevel);
    	
    	
    }

    @Override
    protected void update(Input input) { 
    	bucket.update();
    	if(isPowerUp && powerUp != null) {
    		powerUp.update();
    		for (int k = 0; k<3; k++) {
    			if (balls[k]!=null) {
    				powerupCollision(k);
        			bucketCollision(k);
    			}
    			
    		}
    	}
    	// pegs collision check 
    	for (int i = 0; i < pegs.length; ++i) {
            if (pegs[i] != null) {
            	for(int k = 0; k<3;k++) {
            		if (balls[k] != null && pegs[i]!=null && balls[k].intersects(pegs[i]) ) {
            			if(isFireBall) {
            				fireballCollision(i);
            			}
            			ballReaction(balls[k]);
            			pegTypeAction(i);
            			
            		}else {
            			if(pegs[i]!=null) {
                			pegs[i].update();

            			}
            		}
            	}
            }
    	}
    	// when all balls out of screen, new a ball at balls[0]
    	if (balls[0] == null && balls[1]==null && balls[2] == null && input.wasPressed(MouseButtons.LEFT)) {
    			if(isFireBall) {
        		
    				balls[0] = new FireBall(BALL_POSITION, input.directionToMouse(BALL_POSITION),"res/fireball.png");
        		
    			}
    			else {
        		
    				balls[0] = new Ball(BALL_POSITION, input.directionToMouse(BALL_POSITION),"res/ball.png");
        		
    			}
    			
    			// only balls[0] count 
    	    	numberOfShots--;
    	        System.out.println("shots remaining " + numberOfShots);
    	}
    	
    	for (int k = 0; k<3; k++) {
    		if (balls[k]!=null) {
    			balls[k].update();
    			if (balls[k].outOfScreen()) {
    				balls[k]=null;
    				isGameEnd();
    			}
    		}
    	}
    	
    	
        
    	
    }
    
    // 每个turn刚开始的时候调用 看这个turn有没有power-up
    // 0.0<=Math.random()<1.0
    // 10%
    public boolean checkForPowerUp () {
    	if (Math.random()*10 < 1) {
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
    	
    	// 每次调用 是不是应该确保 bluePegsCounter == 0;
    	// 因为每次读 都相当于 进入了新的一关
    	bluePegsCounter = 0;
    	
    	
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
                // System.out.println(everyLine);
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
            // System.out.println("csv表格中所有行数：" + allString.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // randomly change 1/5 blue pegs to red pegs
    public void randomlyChangeToRedPegs() {
    	
    	Random rand = new Random();
    	
    	numberOfRedPegs = bluePegsCounter / 5;
    	
    	int redPegsCounter = 0;
    	
    	while(redPegsCounter < numberOfRedPegs) {
    		
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
    			
    			redPegsCounter++;
    			
    		}
    	}
    	
    }
    
    // randomly change 1 blue pegs to green pegs
    public void randomlyChangeToGreenPeg() {
    	
    	Random rand = new Random();
    	
    	boolean changeToGreen = false;
    	
    	while(!changeToGreen) {
    		
    		int randomNumber = rand.nextInt(pegs.length);
    		
    		if(pegs[randomNumber] != null && pegs[randomNumber].getClass().equals(Peg.class)) {
    			
    			Point point = pegs[randomNumber].getPoint();
    			String shape = pegs[randomNumber].getShape();
    			String imagePath;
    			
    			pegs[randomNumber] = null;
    			
    			if(shape.equals("horizontal")) {
            		
            		imagePath = "res/green-horizontal-peg.png";

            	}
            	else if(shape.equals("vertical")) {
            		
            		imagePath = "res/green-vertical-peg.png";
            		
            	}
            	else {
            		
            		imagePath = "res/green-peg.png";
            		
            	}
    			
    			pegs[randomNumber] = new GreenPeg(point, imagePath, shape);
    			
    			changeToGreen = true;
    		
    		}
    	}
    	
    }
    
    // initialize a game board
    public void initializeGameBoard(int currentLevel) {
    	
    	for (int k = 0; k<3; k++) {
    		balls[k] = null;
    	}
    	numberOfShots = 20;
    	
    	// one shot
    	isGreenPeg = false;
    	
    	// one turn
    	isFireBall = false;
    	
    	isGreenPeg = false;
    	
    	
    	
    	//initialize array of pegs
    	for (int i = 0; i < pegs.length; i++) {
        	pegs[i] = null;
        }
    	Point point = new Point (512, 744);
    	bucket = new Bucket(point);
    	
    	// read csv file and assign (position, color, and shape) to pegs
    	// update bluePegsCounter
        readPegsPosition(currentLevel);
        // System.out.println(bluePegsCounter);
        
        // randomly change 1/5 blue pegs to red pegs
        // update numberOfRedPegs
        randomlyChangeToRedPegs();
        
        // randomly change 1 blue peg to green peg
        randomlyChangeToGreenPeg();
        
        // !注释下面的部分，测试power
        // ***************  test for power up ****************
//        Random rand = new Random();
//		// generate x and y for destination point
//		Point p = new Point(Window.getWidth() * rand.nextDouble(), Window.getHeight() * rand.nextDouble());
//    	powerUp = new PowerUp(p);
    	
    	// ************* end ****************
        
//        // check for power up
        isPowerUp = checkForPowerUp();
        if(isPowerUp) {
        	
        	System.out.println("power uppppp");
        	
        	Random rand = new Random();
    		// generate x and y for destination point
    		Point p = new Point(Window.getWidth() * rand.nextDouble(), Window.getHeight() * rand.nextDouble());
        	powerUp = new PowerUp(p);
        	
        }
        else {
        	System.out.println("no power uppppp");
        }
    	
    }
    
    // check for peg's type
    public void pegTypeAction(int index) {
    	
    	if(!pegs[index].getClass().equals(GreyPeg.class)) {
    		
    		// if it is a red peg
    		if(pegs[index].getClass().equals(RedPeg.class)) {
    		
    			numberOfRedPegs--;
    			
    		
    		
    		}
    	
    		if(pegs[index].getClass().equals(GreenPeg.class)) {
    		
    			// two new balls
    				isGreenPeg = true;
    				Point greenPegPosition = pegs[index].getPoint();
    				Vector2 baseVelRight = new Vector2 (Math.sqrt(50),Math.sqrt(50));
    				Vector2 baseVelLeft = new Vector2 (-Math.sqrt(50),Math.sqrt(50));

  				
    				if(balls[0].getClass().equals(Ball.class)) {
    					balls[1] = new Ball (greenPegPosition, baseVelRight.div(10),"res/ball.png");
        				balls[2] = new Ball (greenPegPosition,baseVelLeft.div(10),"res/ball.png");
    				}

    				else {
        				balls[1] = new FireBall (greenPegPosition, baseVelRight.div(10),"res/fireball.png");
        				balls[2] = new FireBall (greenPegPosition, baseVelLeft.div(10),"res/fireball.png");

    				}
    			  		
    		}
    	
    		pegs[index] = null;
    	}
    	
    }
    
    // check for game end
    public void isGameEnd() {
    	
    	// goes to next level
        if(numberOfRedPegs == 0 && (currentLevel + 1) <= 4) {
        	
        	currentLevel += 1;
        	initializeGameBoard(currentLevel);
        	
        	
        }
        // highest level reached
        else if(numberOfRedPegs == 0 && (currentLevel + 1) > 4) {
        	
        	System.out.println("tong guan la");
        	Window.close();
        	
        	
        }
        // run out of shots, initial a new game at current level
        else if(numberOfShots == SHOTSRUNOUTOF) {
        	
        	initializeGameBoard(currentLevel);
        	
        }
    	
    }
    
    public void fireballCollision (int i) {
    	System.out.println("fire ball");
		// fire ball
    	//  all pegs within 70 pixels of the struck peg’s centre are destroyed
		for (int j = 0; j < pegs.length; j++) {
    		
    		if (pegs[j] != null && i != j) {
    			
    			double pegToPeg = pegs[i].getRect().centre().asVector().sub(pegs[j].getRect().centre().asVector()).length();
    			
        		
        		if(pegToPeg < 70) {
        			
        			pegTypeAction(j);
                	
        		}
    			
    		}
    	}
    }
    
    public void ballReaction (Ball ball) {
    	//String intersectedSide = ball.getRect().intersectedAt(ball.getRect().centre(), ball.getVelocity()).toString();

    	String intersectedSide = ball.getRect().intersectedAt(ball.getRect().topLeft(), ball.getVelocity()).toString();
    	
    	if(intersectedSide.equals("TOP") || intersectedSide.equals("BOTTOM")) {
    		
    		ball.setVelocity(new Vector2(ball.getVelocity().x, -ball.getVelocity().y));
    		
    	}
    	else if(intersectedSide.equals("LEFT") || intersectedSide.equals("RIGHT")) {
    		
    		ball.setVelocity(new Vector2(-ball.getVelocity().x, ball.getVelocity().y));
			
		}
    }
    
    public void bucketCollision(int k) {
    	if(balls[k] != null && balls[k].intersects(bucket)) {
    		
    		numberOfShots++;
    		System.out.println("number of shots + 1");
    		System.out.println("shots remaining " + numberOfShots);
    		balls[k] = null;
    		
    		isGameEnd();
    		
    	}
    }
    
    public void powerupCollision (int k) {
    	if(isPowerUp && powerUp != null) {
    		if(balls[k] != null && balls[k].intersects(powerUp)) {
			
    			System.out.print("wan dan yao kong zhi zhen le " + isPowerUp);
    			powerUp = null;
    			isFireBall = true;
			
    			// change the ball to fire ball
    			Point ballPoint = balls[k].getCurrentPoint();
    			Vector2 ballDirection = balls[k].getVelocity();
    			balls[k] = null;
    			balls[k] = new FireBall(ballPoint, ballDirection.div(10),"res/fireball.png");			  			    			
    		}
    	}
    }
    

    
}
