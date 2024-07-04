package frc.robot.commands.auto;

import frc.util.DriveSystem;
import frc.util.SuperNavX;
import frc.util.commands.TurnInPlace;
import frc.util.pathGenerator.commandAuto.AutoGenerator;

public class ExampleAutoCommand extends AutoGenerator{

    public ExampleAutoCommand(DriveSystem driveSystem, SuperNavX navX) {
        super("ExampleAutoCommand", driveSystem.getAutoGains(), driveSystem, navX, 0);
        addCommands(new TurnInPlace(driveSystem, navX, 180, 1));
}
    
}
