/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.util.vision.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.util.vision.Limelight;

/**
 * This command will cahnge limlight camera mode to vision / dariver / USB.
 */
public class LimelightChangePiplineCommand extends InstantCommand {

  /**
   * This command change camera mode.
   * <p>
   * 
   * @param limelight limelight to use.
   * @param pipline   pipieline number to use.
   */
  public LimelightChangePiplineCommand(Limelight limelight, int pipline) {
    super(() -> limelight.setPipeline(pipline));
  }

  @Override
  public boolean runsWhenDisabled() {
    return true;
  }
}