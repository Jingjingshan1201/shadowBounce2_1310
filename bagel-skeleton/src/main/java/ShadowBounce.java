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

public class ShadowBounce extends AbstractGame {
    private Peg[] pegs = new Peg[50];
    private Ball ball;
    private static final Point BALL_POSITION = new Point(512, 32);
    private static final double PEG_OFFSET = 100;

    public ShadowBounce() {
    	for (int i = 0; i < pegs.length; i++) {
        	pegs[i] = null;
        }
        
        // test csv
        readPegsPosition(1);
    }

    @Override
    protected void update(Input input) {
        // Check all non-deleted pegs for intersection with the ball
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
            ball = new Ball(BALL_POSITION, input.directionToMouse(BALL_POSITION));
        }

        if (ball != null) {
            ball.update();

            // Delete the ball when it leaves the screen
            if (ball.outOfScreen()) {
                ball = null;
            }
        }
    }

    public static void main(String[] args) {
        new ShadowBounce().run();
    }
    
    
    // read position from csv
    public void readPegsPosition(int level) {
    	
    	// csv file path
    	String csvFilePathString = "csvFile\\" + level + ".csv";

    	// blue_peg_horizontal,448,500
    	
    	
    	
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
                
                String color, shape, xCoordinate, yCoordinate;
                
                String[] colorShapeXY = everyLine.split(",");
                
                String colorShape = colorShapeXY[0];
                
                String[] colorNshape = colorShape.split("_");
                
                // color
                if(colorNshape.length == 2) {
                	
                	color = colorNshape[0];
                    shape = null;
                	
                }
                // color and shape
                else {
                	
                	color = colorNshape[0];
                    shape = colorNshape[2];
                	
                }

                xCoordinate = colorShapeXY[1];
                yCoordinate = colorShapeXY[2];
                
                
                
                
                
                System.out.println(everyLine);
                allString.add(everyLine);
            }
            System.out.println("csv表格中所有行数：" + allString.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    	
    	
    	
    	
    }
    
    
}
