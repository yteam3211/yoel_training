/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.util.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.util.SuperSolenoid;

/**
 * This command will change solenoid position.
 */
public class SolenoidChangePositionCommand extends InstantCommand {

  /**
   * This constractor will change solenoid position.
   * 
   * @param solenoid solenid to change.
   */
  public SolenoidChangePositionCommand(SuperSolenoid solenoid) {
    super(() -> solenoid.changePosition());
  }

  /**
   * This constractor will change solenoid position to what you whant.
   * @param solenoid  solenid to change.
   * @param reverse   true to change to reverse.
   */
  
  public SolenoidChangePositionCommand(SuperSolenoid solenoid, boolean reverse) {
    super(() -> solenoid.changePosition(reverse));
  }
}
