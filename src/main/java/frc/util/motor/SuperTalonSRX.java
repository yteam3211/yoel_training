/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.util.motor;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.util.PID.Gains;

/**
 * This class is SuperMotor of TalonSRX
 * 
 * @author Amitai Algom
 */
public class SuperTalonSRX extends TalonSRX implements SuperMotor {
    private ControlMode mode = ControlMode.PercentOutput;
    private double positionMultiply = 1, velocityMultiply = 1;

    /**
     * This constractor is to master motor
     * 
     * @param deviceNumber id can
     * @param amps         amper limitation
     * @param inverted     when side motor move
     */
    public SuperTalonSRX(int deviceNumber, int amps, boolean inverted) {
        super(deviceNumber);

        selectProfileSlot(0, 0);
        setInverted(inverted);
        configFactoryDefault();
        /* Set the peak and nominal outputs */
        configNominalOutputForward(0, 0);
        configNominalOutputReverse(0, 0);
        configPeakOutputForward(1, 0);
        configPeakOutputReverse(-1, 0);
    }

    /**
     * This constractor is to master motor
     * 
     * @param deviceNumber     id can
     * @param amps             amper limitation
     * @param inverted         when side motor move
     * @param positionMultiply changing Position
     * @param velocityMultiply changing velocity
     */
    public SuperTalonSRX(int deviceNumber, int amps, boolean inverted, boolean SensorPhase, double sensorPos,
            double positionMultiply, double velocityMultiply, Gains gains, ControlMode mode) {
        super(deviceNumber);

        this.positionMultiply = positionMultiply;
        this.velocityMultiply = velocityMultiply;
        this.mode = mode;
        /* Configure Sensor Source for Primary PID */
        configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);

        /**
         * Configure Talon SRX Output and Sensor direction accordingly Invert Motor to
         * have green LEDs when driving Talon Forward / Requesting Positive Output Phase
         * sensor to have positive increment when driving Talon Forward (Green LED)
         */

        setInverted(inverted);
        setSensorPhase(SensorPhase);

        /* Set relevant frame periods to be at least as fast as periodic rate */
        setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, 0);
        setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, 0);

        /* Set the peak and nominal outputs */
        configNominalOutputForward(0, 0);
        configNominalOutputReverse(0, 0);
        configPeakOutputForward(1, 0);
        configPeakOutputReverse(-1, 0);

        selectProfileSlot(0, 0);
        selectProfileSlot(0, 0);
        config_kF(0, gains.Kf);
        config_kP(0, gains.kp);
        config_kI(0, gains.ki);
        config_kD(0, gains.kd);

        setSelectedSensorPosition((int) (sensorPos * positionMultiply));
    }

    /**
     * This constractor is to slave motor
     * 
     * @param leader       The master that motor follow
     * @param deviceNumber id can
     * @param amps         amper limitation
     * @param inverted     when side motor move
     */
    public SuperTalonSRX(IMotorController leader, int deviceNumber, int amps, boolean inverted) {
        super(deviceNumber);

        selectProfileSlot(0, 0);
        setInverted(inverted);
        follow(leader);
    }

    @Override
    public void setOutput(double setOutput) {
        if (mode == ControlMode.Position || mode == ControlMode.MotionMagic)
            setOutput *= positionMultiply;
        else if (mode == ControlMode.Velocity)
            setOutput *= velocityMultiply;

        set(mode, setOutput);
    }

    @Override
    public double getOutput() {
        return getMotorOutputPercent();
    }

    @Override
    public double getAmper() {
        return getSupplyCurrent();
    }

    @Override
    public void setMode(Object mode) {
        if (mode instanceof ControlMode)
            mode = (ControlMode) mode;
    }

    @Override
    public double getVelocity() {
        return getSelectedSensorVelocity() * velocityMultiply;
    }

    @Override
    public double getPosition() {
        return getSelectedSensorPosition() * positionMultiply;
    }

    @Override
    public void reset(double pos) {
        setSelectedSensorPosition((int) (pos * positionMultiply));
    }

    public void setPIDFVA(Gains Gains) {

    }
}
