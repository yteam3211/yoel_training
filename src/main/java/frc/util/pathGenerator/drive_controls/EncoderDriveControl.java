package frc.util.pathGenerator.drive_controls;

import frc.util.DriveSystem;
/**
 * Add your docs here.
 */
public class EncoderDriveControl extends DriveControl {

    public EncoderDriveControl(DriveSystem driveSystem) {
        super(driveSystem);
    }

    @Override
    public double getRobotErrorLeftPosition(int index) {
        return path.left[index].pos - getLeftEncoderDistance();        
    }

    @Override
    public double getRobotErrorRightPosition(int index) {
        return path.right[index].pos - getRightEncoderDistance();
    }

    @Override
    public void resetSensors() {
        driveSystem.resetSensors(0);
        
    }
}
