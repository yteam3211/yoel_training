// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.commands.ExampleCommand;
import frc.util.SuperSystem;


// Yteam Example Subsystem
public class ExampleSubsystem extends SuperSystem {
  // Motors, Selenoid and Sensors declaration


  public ExampleSubsystem() {
    super("ExampleSubsystem");
    setDefaultCommand(new ExampleCommand(this));
  }

  /** Creates a new ExampleSubsystem. */
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

}
