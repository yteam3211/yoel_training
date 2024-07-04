/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.util.dashboard;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj2.command.Command;
import frc.util.SuperSystem;

public class SuperSubSystemTab extends SuperShuffleBoardTab {
    private final ShuffleboardLayout commandList;

    public SuperSubSystemTab(String name, SuperSystem system) {
        super(name);
        commandList = tab.getLayout("commands", BuiltInLayouts.kList).withPosition(0, 0).withSize(2, 6);
        // .withPosition(RobotConstants.Dashboard.MAX_WIDTH_GRID - 2, 0).withSize(2,
        // RobotConstants.Dashboard.MAX_HIGH_GRID + 5);
        // .withProperties(Map.of("Label position", "HIDDEN"));
    }

    public void addSubsystem(String name, SuperSystem system) {
        commandList.add(name, system);
    }

    public void addCommandToDashboard(final String commandName, final Command command) {
        command.setName(commandName);
        commandList.add(command);
    }
}
