package frc.util.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.util.SuperInterface;

/**
 * This class will reset the sensors in the subsystem.
 *
 * @author Matan Steinmetz
 */
public class ResetSensorsCommand extends InstantCommand {
  /**
   * @param superInterface the subsystem or super class to reset is sensors.
   */
  public ResetSensorsCommand(SuperInterface superInterface, double pos) {
    super(() -> superInterface.resetSensors(pos));
  }

  @Override
  public boolean runsWhenDisabled() {
    return true;
  }
}
