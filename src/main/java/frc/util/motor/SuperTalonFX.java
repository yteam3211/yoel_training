/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.util.motor;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import frc.util.PID.Gains;

/**
 * This class is SuperMotor of TalonFX
 * 
 * @author Amitai Algom
 */
public class SuperTalonFX extends TalonFX implements SuperMotor {
    private TalonFXControlMode mode = TalonFXControlMode.PercentOutput;
    private double positionMultiply = 1, velocityMultiply = 1;

    /**
     * This constractor is to master motor
     * 
     * @param deviceNumber can id
     * @param amps         amper limitation
     * @param inverted     when side motor move
     * @param PhaseSensor  when side sensor move
     * @param mNeutralMode mode of motor brake or coast
     * @param gains
     */
    public SuperTalonFX(int deviceNumber, int amps, boolean inverted, boolean PhaseSensor, NeutralMode mNeutralMode,
            Gains gains, TalonFXControlMode mode) {
        super(deviceNumber);
        this.mode = mode;

        configFactoryDefault();
        configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 20);

        setInverted(inverted);
        setSensorPhase(PhaseSensor);
        setNeutralMode(mNeutralMode);
        configNominalOutputForward(0, 0);
        configNominalOutputReverse(0, 0);
        configPeakOutputForward(1, 0);
        configPeakOutputReverse(-1, 0);
        selectProfileSlot(0, 0);
        config_kF(0, gains.Kf);
        config_kP(0, gains.kp);
        config_kI(0, gains.ki);
        config_kD(0, gains.kd);
        setSelectedSensorPosition(0, 0, 0);
    }

    /**
     * This constructor is to slave motor
     * 
     * @param leader       The master that motor follow
     * @param deviceNumber can id
     * @param amps         amper limitation
     * @param inverted     when side motor move
     */
    public SuperTalonFX(IMotorController leader, int deviceNumber, int amps, boolean inverted) {
        super(deviceNumber);

        selectProfileSlot(0, 0);
        setInverted(inverted);
        follow(leader);
    }

    @Override
    public void setOutput(double setOutput) {
        if (mode == TalonFXControlMode.Position || mode == TalonFXControlMode.MotionMagic)
            setOutput *= positionMultiply;
        else if (mode == TalonFXControlMode.Velocity)
            setOutput *= velocityMultiply;
        super.set(mode, setOutput);
    }

    @Override
    public double getOutput() {
        return super.getMotorOutputPercent();
    }

    @Override
    public double getAmper() {
       return super.getMotorOutputVoltage();
    }

    @Override
    public void setMode(Object mode) {
        if (mode instanceof TalonFXControlMode)
            mode = (TalonFXControlMode) mode;
    }

    @Override
    public double getVelocity() {
        return super.getSelectedSensorVelocity();
    }

    @Override
    public double getPosition() {
        return super.getSelectedSensorPosition();
    }

    @Override
    public void reset(double pos) {
        super.setSelectedSensorPosition(0);
    }
}
