package frc.util.pathGenerator.commandAuto;


import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.util.commands.ResetSensorsCommand;
import frc.util.SuperNavX;
import frc.util.PID.Gains;
import frc.util.pathGenerator.Path;
import frc.util.pathGenerator.drive_controls.DriveControl;
import frc.util.pathGenerator.drive_controls.EncoderAndNavxDriveControl;
import frc.util.DriveSystem;
public class AutoGenerator extends SequentialCommandGroup {
    private Gains gains;
    private DriveControl dc;
    private DriveSystem driveSystem;
    private String namePath;
    /**
     * 
     * @param namePath the exaxct name of the path file
     * @param gains the gains(pid and mp[ka,kv]) you want to use in the autonums
     * @param driveSystem
     * @param dc the drive contorller you want to use, EncoderDriveControl for straight drive and
     *  EncoderAndNavxDrive for other.(whan you check the pid and mp vaule is recommand to start with the no navx one)

     * @param angle the start angle of the robot in the autonums(defult 0)
     */
    public AutoGenerator(String namePath, Gains gains, DriveSystem driveSystem, SuperNavX navX, DriveControl dc,
            double angle) {
        this.namePath = namePath;
        this.gains = gains;
        this.dc = dc;
        this.driveSystem = driveSystem;

        addCommands(new ResetSensorsCommand(navX, angle));
    }
    /**
     * 
     * @param namePath the exaxct name of the path file
     * @param gains the gains(pid and mp[ka,kv]) you want to use in the autonums
     * @param driveSystem
     * @param dc the drive contorller you want to use, EncoderDriveControl for straight drive and
     *  EncoderAndNavxDrive for other.(whan you check the pid and mp vaule is recommand to start with the no navx one)
     * @param navX
     */
    public AutoGenerator(String namePath, Gains gains, DriveSystem driveSystem, DriveControl dc, SuperNavX navX) {
        this.namePath = namePath;
        this.gains = gains;
        this.dc = dc;
        this.driveSystem = driveSystem;

        addCommands(new ResetSensorsCommand(navX, 0));
    }
    /**
     * 
     * @param namePath the exaxct name of the path file
     * @param gains the gains(pid and mp[ka,kv]) you want to use in the autonums
     * @param driveSystem
     * @param navX
     * @param angle the start angle of the robot in the autonums(defult 0)
     */
    public AutoGenerator(String namePath, Gains gains, DriveSystem driveSystem, SuperNavX navX, double angle) {
        this.namePath = namePath;
        this.gains = gains;
        this.dc = new EncoderAndNavxDriveControl(driveSystem, navX);
        this.driveSystem = driveSystem;
        addCommands(new ResetSensorsCommand(navX, angle));
    }
    /**
     * 
     * @param namePath the exaxct name of the path file
     * @param gains the gains(pid and mp[ka,kv]) you want to use in the autonums
     * @param driveSystem 
     * @param navX
     */
    public AutoGenerator(String namePath, Gains gains, DriveSystem driveSystem, SuperNavX navX) {
        this.namePath = namePath;
        this.gains = gains;
        this.dc = new EncoderAndNavxDriveControl(driveSystem, navX);
        this.driveSystem = driveSystem;

        addCommands(new ResetSensorsCommand(navX, 0));
    }

    /**
     * This function will add follow path command.
     * <p>
     * 
     * @param path      path to follow
     * @param otherSide is need to follow path to other side
     * @return follow path command
     */
    public Command addFollowPathCommand(Path path) {
        return addFollowPathCommand(path, dc);
    }

    /**
     * This function will add follow path command.
     * <p>
     * 
     * @param path      path to follow
     * @param dc_       drive controller to use
     * @param otherSide is need to follow path to other side
     * @return follow path command
     */
    public Command addFollowPathCommand(Path path, DriveControl dc) {
        return new FollowPathCommand(driveSystem, path, gains, dc);
    }

    public String getNamePath() {
        return namePath;
    }
}