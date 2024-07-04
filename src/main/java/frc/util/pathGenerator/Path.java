package frc.util.pathGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.wpi.first.wpilibj.Filesystem;
import frc.robot.Constants;

public class Path {
    public Point[] left;
    public Point[] right;
    // public Double[] angles;
    private double startAngle;

    public Path(String autoName) {
        this(autoName, 0);
    }

    /**
     * 
     * @param autoName   - the name of the auto
     * @param startAngle - the start angle
     */
    public Path(String autoName, double startAngle) {
        this.startAngle = startAngle;
        String pathFolder = Filesystem.getDeployDirectory().toString() + "/" + autoName;
        right = loudFromCsv(pathFolder + "/" + autoName + ".left.csv");
        left = loudFromCsv(pathFolder + "/" + autoName + ".right.csv");
        // angles = loudAngel(pathFolder + "/" + autoName + ".coords.csv");
    }

    /**
     * 
     * @param index
     * @return the angle from this point
     */
    public double getAngle(int index) {
        return startAngle + Math.toDegrees(((right[index].pos - left[index].pos) 
        / Constants.ROBOT_WIDTH));
        // return angles[index];
    }

    public void inReverse(){
        Point[] temp = left;
        left = right;
        right = temp;

        for (int i = 0; i < left.length; i++) {
            left[i].acc *= -1;
            left[i].pos *= -1;
            left[i].vel *= -1;
        }

        
        for (int i = 0; i < right.length; i++) {
            right[i].acc *= -1;
            right[i].pos *= -1;
            right[i].vel *= -1;
        }
    }

    /**
     * 
     * @param filePath the path of the file
     * @return array of points
     */
    Point[] loudFromCsv(String filePath) {
        List<Point> points = new ArrayList<Point>();
        try {
            File csvFile = new File(filePath);
            // TODO this is may cause problem
            Scanner myReader = new Scanner(csvFile);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.charAt(0) == 'a') {
                    continue;
                }
                String[] pointArr = data.split(",", 0);
                // move the scv to list;
                Point point = new Point(pointArr[0], pointArr[1], pointArr[2]);
                points.add(point);
            }

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        Point[] pointsArray = new Point[points.size()];
        return points.toArray(pointsArray);
    }


    // Double[] loudAngel(String filePath){
    //     List<Double> angels = new ArrayList<Double>();
    //     try {
    //         File csvFile = new File(filePath);
    //         // TODO this is may cause problem
    //         Scanner myReader = new Scanner(csvFile);
    //         while (myReader.hasNextLine()) {
    //             String data = myReader.nextLine();
    //             if (data.charAt(0) == 'a') {
    //                 continue;
    //             }
    //             String[] pointArr = data.split(",", 0);
    //             // move the scv to list;
    //             System.out.println();
    //             Double angel = Double.parseDouble(pointArr[0]);
    //             angels.add(angel);
    //         }

    //         myReader.close();
    //     } catch (FileNotFoundException e) {
    //         System.out.println("An error occurred.");
    //         e.printStackTrace();
    //     }
    //     Double[] angelsArray = new Double[angels.size()];
    //     return angels.toArray(angelsArray);
    // }
}