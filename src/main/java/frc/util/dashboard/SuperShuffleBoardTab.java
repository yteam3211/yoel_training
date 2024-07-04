/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.util.dashboard;

import java.util.HashMap;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.WidgetType;

/**
 * Add your docs here.
 */
public class SuperShuffleBoardTab {
    protected final ShuffleboardTab tab;
    protected final HashMap<String, GenericEntry> keys = new HashMap<>();

    public SuperShuffleBoardTab(final String name) {
        tab = Shuffleboard.getTab(name);
    }

    /**
     * This function will put your data in Shuffleboard
     * 
     * @param key
     * @param value
     * @param checkIfCompetition if true when the robot in comption the data will not be send
     */
    public void putInDashboard(final String key, final Object value, boolean checkIfCompetition) {
        if (checkIfCompetition && DriverStation.isFMSAttached())
            return;
        if (keys.containsKey(key)) {
            keys.get(key).setValue(value);
        } else {
            keys.put(key, tab.add(key, value).getEntry());
        }
    }

    /**
     * This function will put your data in a list view in Shuffleboard
     * 
     * @param listName
     * @param key
     * @param value
     */
    public void putInDashboard(final String listName, final String key, final Object value,
            boolean checkIfCompetition) {
        if (checkIfCompetition && DriverStation.isFMSAttached())
            return;
        if (keys.containsKey(key)) {
            keys.get(key).setValue(value);
        } else {
            keys.put(key, tab.getLayout(listName, BuiltInLayouts.kList).add(key, value).getEntry());
        }
    }

    public void putInDashboard(final String key, final Object value, final int columnIndex, final int rowIndex,
            boolean checkIfCompetition) {
        if (checkIfCompetition && DriverStation.isFMSAttached())
            return;
        if (keys.containsKey(key)) {
            keys.get(key).setValue(value);
        } else {
            keys.put(key, tab.add(key, value).withPosition(columnIndex, rowIndex).getEntry());
        }
    }

    public void putInDashboard(final String key, final Object value, final int columnIndex, final int rowIndex,
            final WidgetType widgetType, boolean checkIfCompetition) {
        if (checkIfCompetition && DriverStation.isFMSAttached())
            return;
        if (keys.containsKey(key)) {
            keys.get(key).setValue(value);
        } else {
            keys.put(key, tab.add(key, value).withPosition(columnIndex, rowIndex).withWidget(widgetType).getEntry());
        }
    }

    public void putInDashboard(final String key, final Object value, final int columnIndex, final int rowIndex,
            final WidgetType widgetType, final int width, final int height, boolean checkIfCompetition) {
        if (checkIfCompetition && DriverStation.isFMSAttached())
            return;
        if (keys.containsKey(key)) {
            keys.get(key).setValue(value);
        } else {
            keys.put(key, tab.add(key, value).withPosition(columnIndex, rowIndex).withWidget(widgetType)
                    .withSize(width, height).getEntry());
        }
    }

    public double getFromDashboard(final String key, final double defaultValue) {
        if (keys.containsKey(key))
            if (keys.get(key).isValid())
                if (keys.get(key).get().isDouble())
                    return keys.get(key).get().getDouble();

        return defaultValue;
    }

    public String getFromDashboard(final String key, final String defaultValue) {
        if (keys.containsKey(key))
            if (keys.get(key).get().isValid())
                if (keys.get(key).get().isDouble())
                    return keys.get(key).get().getString();

        return defaultValue;
    }

    public boolean getFromDashboard(final String key, final boolean defaultValue) {
        if (keys.containsKey(key))
            if (keys.get(key).get().isValid())
                if (keys.get(key).get().isDouble())
                    return keys.get(key).get().getBoolean();

        return defaultValue;
    }

    public ShuffleboardTab getTab() {
        return tab;
    }
}
