/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.util.vision.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.util.DriveSystem;
import frc.util.PID.Gains;
import frc.util.PID.PIDController;
import frc.util.vision.Limelight;
import frc.util.vision.Limelight.limelightCameraMode;
import frc.util.vision.Limelight.limelightLEDMode;

public class DriveStayInPlaceWithVisionCommand extends Command {
  /**
   * Creates a new DriveStayInPlaceNewCommand.
   */
  DriveSystem driveSystem;
  Limelight limelight;
  double deadBand, errorX, errorY, xPos, yPos, outputRight, outputLeft, maxSpeed;
  PIDController yController = new PIDController(), xController = new PIDController();
  Gains gains;

  public DriveStayInPlaceWithVisionCommand(DriveSystem driveSystem, double deadBand, Gains gains, Limelight limelight,
      double maxSpeed) {
    addRequirements(driveSystem);
    this.driveSystem = driveSystem;
    this.deadBand = deadBand;
    this.gains = gains;
    this.limelight = limelight;
    this.maxSpeed = maxSpeed;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    limelight.setLEDMode(limelightLEDMode.kOn);
    limelight.setCameraMode(limelightCameraMode.kVision);
     
    yController.setTargetPosition(limelight.getY());
    xController.setTargetPosition(0);

    yController.setGains(gains);
    xController.setGains(gains);

    yController.resetValues();
    xController.resetValues();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    xPos = limelight.getX();
    yPos = limelight.getY();

    outputRight = + xController.getOutput(xPos);
    outputLeft = - xController.getOutput(xPos);

    outputLeft = outputLeft < 0 ? outputLeft - deadBand : outputLeft + deadBand;
    outputRight = outputRight < 0 ? outputRight - deadBand : outputRight + deadBand;

    outputLeft = outputLeft > maxSpeed ? maxSpeed : outputLeft;
    outputRight = outputRight > maxSpeed ? maxSpeed : outputRight;

    outputLeft = Math.abs(xPos) > 0.3 ? outputLeft : 0;
    outputRight = Math.abs(xPos) > 0.3 ? outputRight : 0; 

    driveSystem.tank(outputLeft, outputRight);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveSystem.tank(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
