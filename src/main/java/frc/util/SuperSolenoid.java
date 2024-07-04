/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.util;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants;

/**
 * This class will make it easy to use Solenoid in the code.
 */
public class SuperSolenoid {
    private Solenoid solenoid = null;
    private boolean solenoidDefualtPosition = false; // false = reverse, true = forward

    private DoubleSolenoid doubleSolenoid = null;

    public static ShuffleboardTab tab = Shuffleboard.getTab("Solenoid");
    private static int solenoidCount = 0;
    
    /**
     * @param name    the name for the solenoid.
     * @param channel The channel on the PCM to control (0..7).
     * @param default what is default position when there is no signal (disable)
     *                false = reverse, true = forward
     */
    public SuperSolenoid(String name, int channel, boolean defualt) {
        solenoid = new Solenoid(PneumaticsModuleType.CTREPCM, channel);
        solenoidDefualtPosition = defualt;

        Command open = new InstantCommand(() -> {
            forward();
        });

        Command close = new InstantCommand(() -> {
            reverse();
        });

        open.setName("open " + name);
        close.setName("close " + name);

        tab.add(name + "open", open).withPosition(0, solenoidCount);
        tab.add(name + "close", close).withPosition(1, solenoidCount);
        solenoidCount++;
    }

    /**
     * @param name           the name for the solenoid.
     * @param forwardChannel The forward channel number on the PCM (0..7).
     * @param reverseChannel The reverse channel number on the PCM (0..7).
     */
    public SuperSolenoid(String name, int forwardChannel, int reverseChannel) {
        doubleSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, forwardChannel, reverseChannel);

        Command open = new InstantCommand(() -> {
            forward();
        });

        Command close = new InstantCommand(() -> {
            reverse();
        });

        open.setName("open " + name);
        close.setName("close " + name);

        tab.add(name + "open", open).withPosition(0, solenoidCount);
        tab.add(name + "close", close).withPosition(1, solenoidCount);

        solenoidCount++;
    }

    /**
     * This function move solenoid forwad.
     */
    public void forward() {
        if (solenoid == null) {
            doubleSolenoid.set(Value.kForward);
        } else {
            solenoid.set(solenoidDefualtPosition ? false : true);
        }
    }

    /**
     * This function move solenoid reverse.
     */
    public void reverse() {
        if (solenoid == null) {
            doubleSolenoid.set(Value.kReverse);
        } else {
            solenoid.set(solenoidDefualtPosition ? true : false);
        }
    }

    /**
     * this will stop to send signal to solenoid, not recommend for single solenoid.
     */
    public void off() {
        if (solenoid == null) {
            doubleSolenoid.set(Value.kOff);
        } else {
            solenoid.set(false);
        }
    }

    /**
     * This function will change solenoid position.
     */
    public void changePosition() {
        if (isForward()) {
            reverse();
        } else {
            forward();
        }
    }

    /**
     * This function check if solenoid is Off.
     * <p>
     * This function is not recommend to use!.
     * <p>
     * 
     * @return is solenoid off.
     */
    public boolean isOff() {
        if (solenoid == null) {
            return doubleSolenoid.get() == Value.kOff;
        } else {
            return !solenoid.get();
        }
    }

    /**
     * This function check if solenoid is forward.
     * <p>
     * 
     * @return is solenoid forward.
     */
    public boolean isForward() {
        if (solenoid == null) {
            return doubleSolenoid.get() == Value.kForward;
        } else {
            return solenoid.get();
        }
    }

    /**
     * This function check if solenoid is reverse.
     * <p>
     * 
     * @return is solenoid reverse.
     */
    //TODO i change here something maybe ineed to return that to the basic 
    public boolean isReverse() {
        if (solenoid == null) {
            return doubleSolenoid.get() == Value.kReverse;
            // return doubleSolenoid.get() == Value.kForward;
        } else {
            return !solenoid.get();
        }
    }

    /**
     * This will change solenoid position.
     * @param reverse ture to change position to erverse.
     */
    public void changePosition(boolean reverse){
        System.out.println(isOff());
        if(reverse){
            reverse();
        } else {
            forward();
        }
    }
}