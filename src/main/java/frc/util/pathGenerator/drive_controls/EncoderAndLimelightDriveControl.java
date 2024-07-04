// package frc.util.pathGeneratorNew.drive_controls;

// import frc.util.electronics.sensors.Limelight;
// import frc.util.SuperNavX;
// import frc.util.pathGeneratorNew.DrivePosition;
// import frc.util.pathGeneratorNew.PointSegments;
// import frc.util.DriveSystem;
// /**
//  * @author Matan Steinmetz
//  */
// public class EncoderAndLimelightDriveControl extends DriveControl {
//     private DrivePosition errorPosition = new DrivePosition();
//     private double endAngle, errorAngle;
//     private Limelight limelight;
//     private SuperNavX navX;

//     /**
//      * @param driveSystem to use
//      * @param navX        to use
//      * @param limelight   to use
//      * @param endAngle    of the path
//      */
//     public EncoderAndLimelightDriveControl(DriveSystem driveSystem, SuperNavX navX, Limelight limelight,
//             double endAngle) {
//         super(driveSystem);

//         this.limelight = limelight;
//         this.endAngle = endAngle;
//         this.navX = navX;
//     }

//     @Override
//     public DrivePosition getRobotErrorPosition(PointSegments point) {
//         if (limelight.isValid()) {
//             errorAngle = angle2Distance((endAngle - point.angle) - limelight.getX());
//         } else {
//             errorAngle = angle2Distance(point.angle - navX.getSuperAngle());
//         }

//         errorPosition.left = point.leftPoint.pos - getLeftPosition() + errorAngle;
//         errorPosition.right = point.rightPoint.pos - getRightPosition() - errorAngle;

//         return errorPosition;
//     }
// }
