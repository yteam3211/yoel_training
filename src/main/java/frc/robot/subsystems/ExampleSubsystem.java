// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.commands.ExampleCommand;
import frc.util.SuperSystem;
import frc.util.motor.SuperTalonFXPheonix6;


// Yteam Example Subsystem
public class ExampleSubsystem extends SuperSystem {
  // Motors, Selenoid and Sensors declaration
  public SuperTalonFXPheonix6 motor;
  
  public ExampleSubsystem() {
    super("ExampleSubsystem");
    motor = new SuperTalonFXPheonix6(0, getName(), null, null, null, 0, 0, 0);
    setDefaultCommand(new ExampleCommand(this));
  }

  /** Creates a new ExampleSubsystem. */
  public void set_output(double output){
    motor.setMotorOutput(output);
  }

  @Override
  public void periodic() { 
    System.out.println("hiii");
    // This method will be called once per scheduler run
  }

}
