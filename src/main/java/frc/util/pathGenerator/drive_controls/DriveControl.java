package frc.util.pathGenerator.drive_controls;

import frc.robot.Constants;
import frc.util.pathGenerator.Path;
import frc.util.DriveSystem;
/**
 * @author Matan Steinmetz
 */
public abstract class DriveControl {
    protected DriveSystem driveSystem;
    protected Path path;

    public DriveControl(DriveSystem driveSystem) {
        this.driveSystem = driveSystem;
    }

    public abstract double getRobotErrorRightPosition(int index);

    public abstract double getRobotErrorLeftPosition(int index);

    public abstract void resetSensors();

    public void setPath(Path path) {
        this.path = path;
    }

    public double getRightPosition() {
        return driveSystem.getRightPosition();
    }

    public double getLeftPosition() {
        return driveSystem.getLeftPosition();
    }

    public double getRightEncoderDistance(){
        return driveSystem.getRightEncoderDistance();
    }
    
    public double getLeftEncoderDistance(){
        return driveSystem.getLeftEncoderDistance();
    }

    // ----- Util ----- \\
    /**
     * @param angle
     * @return distance
     */
    public static double angle2Distance(double angle) {
        return Constants.ROBOT_WIDTH / 2 * Math.toRadians(angle);
    }

    /**
     * @param distance
     * @return angle
     */
    public static double distance2Angle(double distance) {
        return Math.toDegrees(distance / Constants.ROBOT_WIDTH * 2);
    }

    /**
     * @param distanceLeft
     * @param distanceRight
     * @return angle
     */
    public static double distance2Angle(double distanceLeft, double distanceRight) {
        return Math.toDegrees((distanceLeft - distanceRight) / Constants.ROBOT_WIDTH);
    }
}
