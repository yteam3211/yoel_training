package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PS5Controller;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ExampleSubsystem;


// Yteam loadButtons
public class RobotButtons {

    // Joysticks:
    public static Joystick driverJoystick = new Joystick(0);

    // Triggers:
    public Trigger ExampleButton = new Trigger(() -> driverJoystick.getRawButton(PS5Controller.Button.kCross.value));
    public Trigger MyButton = new Trigger(() -> driverJoystick.getRawButton(PS5Controller.Button.kCircle.value));

    public void loadButtons(ExampleSubsystem exampleSubsystem) {
        // Triggers active
        ExampleButton.whileTrue(new ExampleCommand(exampleSubsystem, 0.5));
        MyButton.onTrue(new ExampleCommand(exampleSubsystem, 0.5));
    }
}