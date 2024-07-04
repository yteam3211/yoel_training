// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.util.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.util.DriveSystem;
import frc.util.SuperNavX;
import frc.util.PID.Gains;

public class TurnInPlace extends Command {
  DriveSystem driveSystem;
  SuperNavX navX;
  double angle, LastAngle, maxOutput;
  DoubleSupplier angleDouble = null;
  int count, reverse;
  Gains gains;
  double sumError = 0;

  public TurnInPlace(DriveSystem driveSystem, SuperNavX navX, double angle, double maxOutput, double reset) {
    addRequirements(driveSystem);
    this.angle = angle;
    this.driveSystem = driveSystem;
    this.navX = navX;
    this.gains = driveSystem.getTurnGains();
    count = 0;
    this.maxOutput = maxOutput;
    this.reverse = 1;
    navX.resetSensors(reset);
  }

  public TurnInPlace(DriveSystem driveSystem, SuperNavX navX, double angle, double maxOutput) {
    addRequirements(driveSystem);
    this.angle = angle;
    this.driveSystem = driveSystem;
    this.navX = navX;
    this.gains = driveSystem.getTurnGains();
    count = 0;
    this.maxOutput = maxOutput;
    // this.reverse = reverse ? -1 : 1;
    this.reverse = 1;
  }


  public TurnInPlace(DriveSystem driveSystem, SuperNavX navX, double angle) {
    addRequirements(driveSystem);
    this.angle = angle;
    this.driveSystem = driveSystem;
    this.navX = navX;
    this.gains = driveSystem.getTurnGains();
    count = 0;
    this.maxOutput = 1;
    // this.reverse = reverse ? -1 : 1;
    this.reverse = 1;
  }


  public TurnInPlace(DriveSystem driveSystem, SuperNavX navX, DoubleSupplier angle) {
    addRequirements(driveSystem);
    this.angle = 3211;
    this.angleDouble = angle;
    this.driveSystem = driveSystem;
    this.navX = navX;
    this.gains = driveSystem.getTurnGains();
    count = 0;
    this.maxOutput = 1;
    // this.reverse = reverse ? -1 : 1;
    this.reverse = 1;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if(angleDouble != null )angle = angleDouble.getAsDouble();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    driveSystem.getTab().putInDashboard("turn", false, true);
    double errorAngle = navX.getSuperAngle() - angle;
    if(sumError * errorAngle <= 0) sumError = 0;
    sumError += errorAngle;
    double output = (gains.kp * errorAngle + gains.kd * (errorAngle - LastAngle) + gains.ki * sumError);
    output = Math.abs(output) > maxOutput ? (output > 0 ? maxOutput : maxOutput * -1 ): output;
    driveSystem.tank(reverse * output, -1 * reverse * output);
    LastAngle = errorAngle; 
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveSystem.getTab().putInDashboard("turn", !interrupted, true);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(Math.abs(angle - navX.getSuperAngle()) < 1) count++;
    else count = 0;
    return count > 5;
  }
}
