/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.util.motor;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;

/**
 * This class is SuperMotor of VictorSP
 * 
 * @author Amitai Algom
 */
public class SuperVictorSP extends VictorSP implements SuperMotor {
    private Encoder encoder;
    private DutyCycleEncoder dutyCycleEncoder;

    /**
     * 
     * @param channel    PWM id
     * @param indexOfPDP PDP id
     */
    public SuperVictorSP(int channel) {
        super(channel);
    }

    public SuperVictorSP(int channel, Encoder encoder, double distancePerPulse, boolean reverseDirection) {
        super(channel);
        this.encoder = encoder;
        encoder.setDistancePerPulse(distancePerPulse);
        encoder.setReverseDirection(reverseDirection);
    }

    public SuperVictorSP(int channel, DutyCycleEncoder dutyCycleEncoder, double distancePerRotation) {
        super(channel);
        this.dutyCycleEncoder = dutyCycleEncoder;
        dutyCycleEncoder.setDistancePerRotation(distancePerRotation);
    }

    @Override
    public void setOutput(double setOutput) {
        super.set(setOutput);
    }

    @Override
    public double getOutput() {
        return super.get();
    }

    @Override
    public double getAmper() {
        // return RobotContainer.PDP.getCurrent(indexOfPDP);
        return 0;
    }

    @Override
    public void setMode(Object mode) {
        throw new NullPointerException("this motor can't use on this funch");

    }

    @Override
    public double getVelocity() {
        throw new NullPointerException("this motor don't have encoder");
        // return Double.NaN;
    }

    @Override
    public double getPosition() {
        if (encoder != null) {
            return encoder.getDistance();
        } else if (dutyCycleEncoder != null) {
            return dutyCycleEncoder.getDistance();
        } else {
            throw new NullPointerException("this motor don't have encoder");
        }

    }

    @Override
    public void reset(double pos) {
    }
}
