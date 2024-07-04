/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.util.motor;

/**
 * This class is interface of all Supermotor
 * 
 * @author Amitai Algom
 */
public interface SuperMotor {
    /**
     * this funcion give to motor output
     * 
     * @param setOutput output to motor
     */
    public void setOutput(double setOutput);

    /**
     * 
     * @return Output of motor
     */
    public double getOutput();

    /**
     * 
     * @return The current the motor receives
     */
    public double getAmper();

    /**
     * 
     * @param mode this is mode of control(only in TalonSrx, SparkMax...)
     */
    public void setMode(Object mode);

    /**
     * 
     * @return The Velocity that motor available
     */
    public double getVelocity();

    /**
     * 
     * @return Position of encoder
     */
    public double getPosition();

    /**
     * 
     * @param pos The Position the reset resets to
     */
    public void reset(double pos);
}
