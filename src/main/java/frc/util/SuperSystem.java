package frc.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import frc.util.dashboard.SuperSubSystemTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * @author Matan Steinmetz
 */
public class SuperSystem extends SubsystemBase {
    private String nameSystem;
    // private static List<SubsystemBase> allSuperSubsystems = new ArrayList<SubsystemBase>();
    private final SuperSubSystemTab tab;
    private static HashMap<String, SuperSubSystemTab> allTabs = new HashMap<>();

    public SuperSystem(String nameSystem) {
        // allSuperSubsystems.add(this);
        this.nameSystem = nameSystem;
        if (!allTabs.containsKey(nameSystem))
            allTabs.put(nameSystem, new SuperSubSystemTab(nameSystem, this));
        tab = allTabs.get(nameSystem);
    }

    public SuperSubSystemTab getTab() {
        return tab;
    }

    public String getNameSystem() {
        return nameSystem;
    }

    // public static void periodics() {
        // for (final SubsystemBase subsystem : allSuperSubsystems) {
        //   subsystem.periodic();
        // }
    //   }    

}
