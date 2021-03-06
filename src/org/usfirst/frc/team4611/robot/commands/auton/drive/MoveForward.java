package org.usfirst.frc.team4611.robot.commands.auton.drive;

import org.usfirst.frc.team4611.robot.Robot;
import org.usfirst.frc.team4611.robot.subsystems.mecanum.TalonMecanum;

import edu.wpi.first.wpilibj.command.Command;

public class MoveForward extends Command {
	
	private int inches;
	public final double INCH_PU_MULT = 215.910640625;
	
	public MoveForward(int inches) {
		this.requires(Robot.mecanum);
		this.inches = inches;
	}
	
	protected void initialize() {
		System.out.println("Hereer");
	}
	
	protected void execute() {
		System.out.println(Robot.mecanum.getAverageSensorPos());
		Robot.mecanum.moveForward(inches);
	}

	@Override
	protected boolean isFinished() {
		System.out.println(Math.abs((Robot.mecanum.getAverageSensorPos()*(((TalonMecanum)Robot.mecanum)).INCH_PU_MULT)-inches));
		return Math.abs((Robot.mecanum.getAverageSensorPos())-inches*this.INCH_PU_MULT) <= 70;
	}
}