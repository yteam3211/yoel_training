           /*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.util.motor;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.controls.VelocityDutyCycle;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.controls.MotionMagicDutyCycle;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import frc.util.PID.Gains;

/**
 * This class is SuperMotor of TalonFX
 * 
 * @author Amitai Flint
 */
public class SuperTalonFXPheonix6 extends TalonFX {

    // control requests
    private final DutyCycleOut DutyCycleOut = new DutyCycleOut(0);
    private final VoltageOut VoltageOut = new VoltageOut(0);
    private final VelocityDutyCycle VelocityDutyCycle = new VelocityDutyCycle(0);
    private final PositionDutyCycle PositionDutyCycle = new PositionDutyCycle(0);
    private final MotionMagicDutyCycle MotionMagicDutyCycle = new MotionMagicDutyCycle(0);

    // configs
    private final FeedbackConfigs feedbackConfigs = new FeedbackConfigs();
    private final Slot0Configs slot0Configs = new Slot0Configs();
    private final MotorOutputConfigs motorOutputconfigs = new MotorOutputConfigs();
    private final MotionMagicConfigs motionMagicConfigs = new MotionMagicConfigs();
    private final SoftwareLimitSwitchConfigs softwareLimitSwitchConfigs = new SoftwareLimitSwitchConfigs();
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
    public SuperTalonFXPheonix6(int deviceNumber, String canbusName, InvertedValue inverted, NeutralModeValue neutralMode,
            Gains gains, int canconderID, double Acceleration, double maxVel) {
        super(deviceNumber, canbusName);

        
        getPosition().setUpdateFrequency(50); 

        feedbackConfigs.FeedbackRemoteSensorID = canconderID;
        feedbackConfigs.FeedbackSensorSource = (canconderID == 0) ? FeedbackSensorSourceValue.RotorSensor : 
        FeedbackSensorSourceValue.RemoteCANcoder;
        
        
        slot0Configs.kP = gains.kp;
        slot0Configs.kI = gains.ki;
        slot0Configs.kD = gains.kd;
        slot0Configs.kV = gains.kv;
        slot0Configs.kG = gains.kg;
        slot0Configs.kS = gains.ks;
        slot0Configs.kG = gains.ka;


        motorOutputconfigs.Inverted = inverted;
        motorOutputconfigs.NeutralMode = neutralMode;

        motionMagicConfigs.MotionMagicCruiseVelocity = maxVel;
        motionMagicConfigs.MotionMagicAcceleration = Acceleration;

        MotionMagicDutyCycle.Slot = 0;
        PositionDutyCycle.Slot = 0;
        VelocityDutyCycle.Slot = 0;

        getConfigurator().apply(feedbackConfigs);
        getConfigurator().apply(slot0Configs);
        getConfigurator().apply(motorOutputconfigs);
        getConfigurator().apply(motionMagicConfigs);
        getConfigurator().apply(feedbackConfigs);
    }

    /**
     * This constructor is to slave motor
     * 
     * @param leader       The master that motor follow
     * @param deviceNumber can id
     * @param amps         amper limitation
     * @param inverted     when side motor move
     */
    public SuperTalonFXPheonix6(int leaderID, int deviceNumber, String canbusName, boolean inverted) {
        super(deviceNumber, canbusName);

        setControl(new Follower(leaderID, inverted));
    }

    
    /**
     * A DutyCycle control request outputs a proportion of the supply voltage, which ranges from -1.0 to +1.0.
     * @param output Proportion of supply voltage to apply in fractional units between -1 and +1.
     */
    public void setMotorOutput(double output) {
        setControl(DutyCycleOut.withOutput(output));
    }

    /**
     * @param position the target position in rotations.
     */
    public void setMotorPosition(int position) {
        setControl(PositionDutyCycle.withPosition(position));
    }

    /**
     * @param voltage Voltage to attempt to drive at.
     */
    public void setVoltageOut(double voltage) {
        setControl(VoltageOut.withOutput(voltage));
    }

    /**
     * @param velocity Velocity to drive toward in rotations per second.
     */
    public void setVelocity(double velocity) {
        setControl(VelocityDutyCycle.withVelocity(velocity));
    }

    public void setMotionMagic(double position) {
        setControl(MotionMagicDutyCycle.withPosition(position));
    }

    public void setLimit(double forwardThreshold, double reverseThreshold) {
        softwareLimitSwitchConfigs.ForwardSoftLimitEnable = true; 
        softwareLimitSwitchConfigs.ForwardSoftLimitThreshold = forwardThreshold;
        softwareLimitSwitchConfigs.ReverseSoftLimitEnable = true;
        softwareLimitSwitchConfigs.ReverseSoftLimitThreshold = reverseThreshold; 
        getConfigurator().apply(softwareLimitSwitchConfigs);
    }

    
    public double getOutput() {
        getDutyCycle().refresh();
        return getDutyCycle().getValueAsDouble();
    }

    
    public double getMVoltage() {
        getMotorVoltage().refresh();
        return getMotorVoltage().getValueAsDouble();
    }

    
    public double getMotorVelocity() {
        return super.getVelocity().getValueAsDouble();
    }

    public double getMotorPosition(){
        return super.getPosition().getValueAsDouble();
    }

    public void resetSensorPosition(double pos) {
        setPosition(pos);   
    }
}
