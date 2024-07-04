/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.util.PID;

/**
 * This class will calculate PID output.
 * 
 * @author Matan Steinmetz
 */
public class PIDController {
    protected double error = 0, lastError = 0, errorSum = 0, output = 0, targetPosition = 0, maxOutput = 1;
    protected Gains gains;

    /**
     * If you use this constrictor make sure to use @{@code setGains()} before
     * starting to calculate output!
     */
    public PIDController() {
    }

    /**
     * @param gains_ need to be gains to use for PID.
     */
    public PIDController(Gains gains_) {
        gains = gains_;
    }

    /**
     * @param gains_    need to be gains to use for PID.
     * @param position_ need to be target position.
     */
    public PIDController(Gains gains_, double position_) {
        gains = gains_;
        targetPosition = position_;
    }

    /**
     * @param gains_     need to be gains to use for PID.
     * @param position_  need to be target position.
     * @param maxOutput_ need to be max abs output to send to motor
     */
    public PIDController(Gains gains_, double position_, double maxOutput_) {
        gains = gains_;
        targetPosition = position_;
        maxOutput = maxOutput_;
    }

    public void setMaxOutput(double maxOutput) {
        this.maxOutput = maxOutput;
    }

    /**
     * @param position need to be controll target position (the position you want to
     *                 go).
     */
    public void setTargetPosition(double position) {
        targetPosition = position;
    }

    /**
     * This function reset PID values.
     */
    public void resetValues() {
        error = 0;
        lastError = 0;
        errorSum = 0;
    }

    /**
     * This function need to be call in the execute function in command class.
     * <p>
     * This function calculate the output with PID algorithm and update all PID
     * values.
     * <p>
     * 
     * @param position need to be subsystem current position.
     * @return output for motor.
     */
    public double getOutput(double position) {
        // Calculate error.
        error = targetPosition - position;

        // Begin summing the errors into the integral term if the error is below a
        // threshold,
        // and reset it if not. This is to prevent the integral from growing too large.
        if (Math.abs(error) < gains.KiThreshold || gains.KiThreshold == 0) {
            errorSum += error;

            if (error > 0 ^ lastError > 0) { // ?
                errorSum = 0;
            }
        } else {
            errorSum = 0;
        }

        output = error * gains.kp + errorSum * gains.ki + (error - lastError) * gains.kd;

        // Saveing last error.
        lastError = error;

        // Combine all the parts of the PID function into the PID algorithm and return
        // the result as output.
        // Also making sure output is not higher then max output.
        return Math.abs(output) > Math.abs(maxOutput) ? maxOutput * output / Math.abs(output) : output;
    }

    public double getError(){
        return error;
    }

    /**
     * @param gains_ need to be gains to use.
     */
    public void setGains(Gains gains_) {
        gains = gains_;
    }
}
