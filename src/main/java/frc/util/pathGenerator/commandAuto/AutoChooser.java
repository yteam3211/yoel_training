package frc.util.pathGenerator.commandAuto;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Command;

public class AutoChooser {
    private Command autoCommand = null;
    SendableChooser<Command> autoChooser = new SendableChooser<>();

    public AutoChooser(AutoGenerator defultAuto, AutoGenerator[] autoCommands) {

        for (AutoGenerator command : autoCommands) {
            System.out.println(command.getNamePath());
            autoChooser.addOption(command.getNamePath(), command);
        }
        autoChooser.setDefaultOption(defultAuto.getNamePath(), defultAuto);

        SmartDashboard.putData("Auto Chooser", autoChooser);
        
    }

    public Command getAutoCommand() {
        return autoChooser.getSelected();
    }


    public void stopAuto() {
        if (autoCommand != null) {
            autoCommand.cancel();
        }
    }
}
