/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.util.vision.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.util.vision.Limelight;
import frc.util.vision.Limelight.limelightLEDMode;

/**
 * This command will cahnge LED mode to blink / on / off.
 */
public class LimelightLEDChangeModeCommand extends Command {

  protected Limelight limelight_;
  protected double timeToBlink_, startTime_;
  protected limelightLEDMode ledMode_;

  /**
   * This constractor will make LED to blink.
   * <p>
   * 
   * @param limelight   - limelight to blink is LED.
   * @param timeToBlink - who mach time (in sec) LED will blink.
   * @param ledMode     - when finish to blink what mode should LED be (on / off).
   */
  public LimelightLEDChangeModeCommand(Limelight limelight, double timeToBlink, limelightLEDMode ledMode) {
    limelight_ = limelight;
    timeToBlink_ = timeToBlink;
    ledMode_ = ledMode;
  }

  /**
   * This constractor will make LED to turn on / off.
   * <p>
   * 
   * @param limelight - limelight to blink is LED.
   * @param ledMode   - what mode should LED be (on / off).
   */
  public LimelightLEDChangeModeCommand(Limelight limelight, limelightLEDMode ledMode) {
    limelight_ = limelight;
    timeToBlink_ = 0;
    ledMode_ = ledMode;
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
    startTime_ = Timer.getFPGATimestamp();

    limelight_.setLEDMode(timeToBlink_ == 0 ? ledMode_ : limelightLEDMode.kBlink);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return Timer.getFPGATimestamp() - startTime_ > timeToBlink_;
  }
  
  @Override
  public void end(boolean interrupted) {
    limelight_.setLEDMode(ledMode_);
  }
    
  @Override
  public boolean runsWhenDisabled() {
    return true;
  }
}
