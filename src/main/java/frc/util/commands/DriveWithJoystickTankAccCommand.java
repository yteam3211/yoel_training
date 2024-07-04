
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.util.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.util.DriveSystem;
public class DriveWithJoystickTankAccCommand extends Command {

	private double leftJ, rightJ, right, left, maxAdd, sl, sr, restraintOutput;
	private double lastLeft_, lastRight_;
	private DoubleSupplier right_, left_;

	private DriveSystem driveSystem;

	/**
	 * @param speed           need to be speed axis
	 * @param turn            need to be turn axis
	 * @param maxAdd          need to be who mach to add between speeds..
	 * @param restraintOutput need to be the max output to give to motors
	 */
	public DriveWithJoystickTankAccCommand(DriveSystem driveSystem, DoubleSupplier left, DoubleSupplier right,
			double maxAdd, double restraintOutput) {
		this.right_ = right;
		this.left_ = left;
		this.maxAdd = maxAdd;
		this.restraintOutput = restraintOutput;

		this.driveSystem = driveSystem;

		addRequirements(driveSystem);
	}

	// public DriveWithJoysticksAccCommand(DriveSystem driveSystem2, DoubleSupplier
	// speed2, DoubleSupplier turn2,
	// double maxAdd2, int restraintOutput2) {
	// }

	@Override
	public void initialize() {
		lastLeft_ = 0;
		lastRight_ = 0;
	}

	@Override
	public void execute() {
		leftJ = -getAxisValue(left_.getAsDouble(), 0.02);// 4
		rightJ = -getAxisValue(right_.getAsDouble(), 0.02);

		left = leftJ;
		right = rightJ;

		if (Math.abs(left) > 1) {
			left = left / Math.abs(left);
		}

		if (Math.abs(right) > 1) {
			right = right / Math.abs(right);
		}

		sl = left - lastLeft_ > 0 ? 1 : -1;// turn / Math.abs(turn);
		sr = right - lastRight_ > 0 ? 1 : -1;

		left = Math.abs(left - lastLeft_) > maxAdd ? lastLeft_ + maxAdd * sl : left;
		right = Math.abs(right - lastRight_) > maxAdd ? lastRight_ + maxAdd * sr : right;

		driveSystem.tank(left * restraintOutput, right * restraintOutput);

		lastLeft_ = left;
		lastRight_ = right;
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void end(boolean interrupted) {
		driveSystem.tank(0, 0);
	}

	private double getAxisValue(double axisValue, double minValue) {
		return Math.abs(axisValue) <= minValue ? 0 : axisValue;
	}
}
