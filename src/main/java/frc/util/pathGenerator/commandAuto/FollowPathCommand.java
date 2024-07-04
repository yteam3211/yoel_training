/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.util.pathGenerator.commandAuto;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.util.PID.Gains;
import frc.util.pathGenerator.Path;
import frc.util.pathGenerator.Point;
import frc.util.pathGenerator.drive_controls.DriveControl;
import frc.util.DriveSystem;
/**
 * @author Amitai Algom
 */
public class FollowPathCommand extends Command {
    private Path path;
    private double leftLestError, rightLestError, leftOutput, rightOutput, errorLeft, errorRight;;
    private Gains gains;
    private DriveControl dc;
    private DriveSystem driveSystem;
    private Point leftPoint;
    private Point rightPoint;
    int index;

    /**
     * @param path      path to follow
     * @param gains     gains to use
     * @param isReverse if need to follow in reverse
     */
    public FollowPathCommand(DriveSystem driveSystem, Path path, Gains gains, DriveControl dc) {
        this.path = path;
        this.gains = gains;
        this.driveSystem = driveSystem;
        this.dc = dc;

        addRequirements(driveSystem);
    }

    @Override
    public void initialize() {
        dc.resetSensors();
        driveSystem.stopOutput();
        rightLestError = 0;
        leftLestError = 0;
        rightOutput = 0;
        leftOutput = 0;
        index = 0;
        dc.setPath(path);
    }

    @Override
    public void execute() {
        leftPoint = path.left[index];
        rightPoint = path.right[index];

        errorLeft = dc.getRobotErrorLeftPosition(index);
        errorRight = dc.getRobotErrorRightPosition(index);
        driveSystem.getTab().putInDashboard("errorL", errorLeft, true);
        driveSystem.getTab().putInDashboard("errorR", errorRight, true);
        leftOutput = (gains.kp * errorLeft + gains.kd * ((errorLeft - leftLestError) / Robot.kDefaultPeriod)
                + (gains.kv * leftPoint.vel + gains.ka * leftPoint.acc));
        rightOutput = (gains.kp * errorRight + gains.kd * ((errorRight - rightLestError) / Robot.kDefaultPeriod)
                + (gains.kv * rightPoint.vel + gains.ka * rightPoint.acc));

        driveSystem.tank(leftOutput, rightOutput);

        leftLestError = errorLeft;
        rightLestError = errorRight;
        index++;
    }

    @Override
    public boolean isFinished() {
        return index >= (path.left.length - 1);
    }

    @Override
    public void end(boolean interrupted) {
        driveSystem.setOutput(0);
    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }
}