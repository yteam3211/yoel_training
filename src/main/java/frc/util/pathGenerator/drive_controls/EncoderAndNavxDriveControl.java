package frc.util.pathGenerator.drive_controls;

import frc.util.SuperNavX;
import frc.util.DriveSystem;
/**
 * @author Matan Steinmetz
 */
public class EncoderAndNavxDriveControl extends DriveControl {
    private double errorAngle;
    private SuperNavX navX;

    public EncoderAndNavxDriveControl(DriveSystem driveSystem, SuperNavX navX) {
        super(driveSystem);
        this.navX = navX;
    }


    public EncoderAndNavxDriveControl(DriveSystem driveSystem, SuperNavX navX, double angle) {
        super(driveSystem);
        this.navX = navX;
        navX.resetSensors(angle);
    }
    
    @Override
    public double getRobotErrorRightPosition(int index){
        // double add = navX.getSuperAngle() > 360 ? 360 : 0;
        double add = 0;
        errorAngle = angle2Distance(path.getAngle(index) + add - navX.getSuperAngle());
        return path.right[index].pos - getRightEncoderDistance() + errorAngle;

    }
    @Override
    public  double getRobotErrorLeftPosition(int index){
        // driveSystem.getTab().putInDashboard("PathAngle", path.getAngle(index), true);
        // double add = navX.getSuperAngle() > 360 ? 360 : 0;
        double add = 0;
        errorAngle = angle2Distance(path.getAngle(index) + add - navX.getSuperAngle());
        // driveSystem.getTab().putInDashboard("errorAngle", errorAngle, true);
        return path.left[index].pos - getLeftEncoderDistance() - errorAngle;
    }

    @Override
    public void resetSensors() {
        driveSystem.resetSensors(0);
        // navX.resetNavx();
        
    }
}
