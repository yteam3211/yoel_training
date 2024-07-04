// /*----------------------------------------------------------------------------*/
// /* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
// /* Open Source Software - may be modified and shared by FRC teams. The code   */
// /* must be accompanied by the FIRST BSD license file in the root directory of */
// /* the project.                                                               */
// /*----------------------------------------------------------------------------*/

// package frc.util.vision.commands;

// import edu.wpi.first.wpilibj2.command.Command;
// import frc.robot.RobotMap;
// import frc.util.DriveSystem;
// import frc.util.Gains;
// import frc.util.controllers.MPController;
// import frc.util.vision.Limelight;

// public class DriveTurn2TargetMPCommand extends Command {

//   protected MPController leftMP, rightMP;
//   protected DriveSystem subsystem;
//   protected Limelight limelight;
//   protected double startAngle = 0;

//   /**
//    * Creates a new DriveTurn2TargetMPCommand.
//    */
//   public DriveTurn2TargetMPCommand(DriveSystem subsystem, Limelight limelight, Gains gains, double V, double A) {
//     addRequirements(subsystem);

//     this.limelight = limelight;
//     this.subsystem = subsystem;

//     leftMP = new MPController(gains, V, A);
//     rightMP = new MPController(gains, V, A);
//   }

//   // Called when the command is initially scheduled.
//   @Override
//   public void initialize() {
//     subsystem.resetSensors();

//     startAngle = limelight.getX();

//     leftMP.setSetpoint(Math.toRadians(limelight.getX()) * RobotMap.ROBOT_WIDTH / 2);
//     rightMP.setSetpoint(Math.toRadians(limelight.getX()) * RobotMap.ROBOT_WIDTH / 2);

//     leftMP.reset();
//     rightMP.reset();

//     leftMP.setReverse(limelight.getX() < 0);
//     rightMP.setReverse(limelight.getX() > 0);
//   }

//   // Called every time the scheduler runs while the command is scheduled.
//   @Override
//   public void execute() {
//     subsystem.tank(leftMP.calculate((setPoint) -> {
//       return setPoint - Math.abs(subsystem.getLeftEncoderDistance() + angleToDistance(setPoint));
//     }), rightMP.calculate((setPoint) -> {
//       return setPoint - Math.abs(subsystem.getRightEncoderDistance() - angleToDistance(setPoint));
//     }));
//   }

//   public double angleToDistance(double pos) {
//     return RobotMap.ROBOT_WIDTH * Math.toRadians(startAngle - pos / RobotMap.ROBOT_WIDTH / 2 - this.limelight.getX())
//         / 2;
//   }

//   // Called once the command ends or is interrupted.
//   @Override
//   public void end(boolean interrupted) {
//   }

//   // Returns true when the command should end.
//   @Override
//   public boolean isFinished() {
//     return leftMP.isFinish() || rightMP.isFinish();
//   }
// }
