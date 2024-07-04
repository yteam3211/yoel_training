// /*----------------------------------------------------------------------------*/
// /* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
// /* Open Source Software - may be modified and shared by FRC teams. The code   */
// /* must be accompanied by the FIRST BSD license file in the root directory of */
// /* the project.                                                               */
// /*----------------------------------------------------------------------------*/

// package frc.util.vision.commands;

// import java.util.function.BooleanSupplier;

// import edu.wpi.first.wpilibj2.command.Command;
// import frc.util.DriveSystem;
// import frc.util.PID.Gains;
// import frc.util.SuperNavX;
// import frc.util.PID.PIDController;
// import frc.util.vision.Limelight;
// import frc.util.vision.Limelight.limelightCameraMode;
// import frc.util.vision.Limelight.limelightLEDMode;

// public class Drive2TargetByVisionCommand extends Command {
//   private double max_speed, speed, turn, deadBand, defaultAngleOfSet, startAngle;
//   private Limelight limelight;
//   private Gains gains_speed, gains_turn;
//   private BooleanSupplier fihinish_when;

//   private PIDController PID_Turn = new PIDController(), PID_Speed = new PIDController();

//   private DriveSystem driveSystem;

//   public Drive2TargetByVisionCommand(DriveSystem driveSystem, double max_speed, Limelight limelight, Gains gains_speed, SuperNavX navX,
//       Gains gains_turn, BooleanSupplier fihinish_when, double deadBand, double defaultAngleOfSet) {
//     this.limelight = limelight;
//     this.max_speed = max_speed;
//     this.gains_speed = gains_speed;
//     this.gains_turn = gains_turn;
//     this.fihinish_when = fihinish_when;
//     this.deadBand = deadBand;
//     this.defaultAngleOfSet = defaultAngleOfSet;

//     this.driveSystem = driveSystem;

//     addRequirements(driveSystem);
//   }

//   // Called just before this Command runs the first time
//   @Override
//   public void initialize() {
//     startAngle = SuperNavX.getInstance().getAngle();

//     limelight.setCameraMode(limelightCameraMode.kVision);
//     limelight.setLEDMode(limelightLEDMode.kOn);

//     PID_Speed.setGains(gains_speed);
//     PID_Turn.setGains(gains_turn);

//     PID_Speed.resetValues();
//     PID_Turn.resetValues();
//   }

//   // Called repeatedly when this Command is sch eduled to run
//   @Override
//   public void execute() {
//     if (limelight.isValid()) {
//       PID_Turn.setTargetPosition(SuperNavX.getInstance().getAngle() + limelight.getX());
//     } else {
//       PID_Turn.setTargetPosition(startAngle + defaultAngleOfSet);
//     }

//     speed = Math.min(max_speed, -PID_Speed.getOutput(limelight.getY()));
//     turn = PID_Turn.getOutput(SuperNavX.getInstance().getAngle());

//     speed = speed < 0 ? speed - deadBand : speed + deadBand;
//     turn = turn < 0 ? turn - deadBand : turn + deadBand;

//     driveSystem.tank(speed + turn, speed - turn);

//     // if (turn < 0) {
//     //   driveSystem.tank(speed + turn + deadBand, speed - turn - deadBand);
//     // } else {
//     //   driveSystem.tank(speed + turn - deadBand, speed - turn + deadBand);
//     // }
//   }

//   // Make this return true when this Command no longer needs to run execute()
//   @Override
//   public boolean isFinished() {
//     return fihinish_when.getAsBoolean();
//   }

//   // Called once after isFinished returns true
//   @Override
//   public void end(boolean interrupted) {
//     driveSystem.tank(0, 0);
//   }
// }
