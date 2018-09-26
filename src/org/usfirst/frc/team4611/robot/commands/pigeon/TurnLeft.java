package org.usfirst.frc.team4611.robot.commands.pigeon;

import org.usfirst.frc.team4611.robot.Robot;
import org.usfirst.frc.team4611.robot.subsystems.sensors.pigeon.Pigeon;

import edu.wpi.first.wpilibj.command.Command;

public class TurnLeft extends Command {

	private double desiredAngle;
	private double startingPigeonAngle;
	private double currentPigeonHeading;
	private double errorAngle;
	private double maxRPM = 1500;
	private double angle;
	private double speed;
	 
	private Pigeon pigeon;
	private final double rotationDifference = 3.5;

	public TurnLeft(Pigeon pig, double angle) {
		this.angle = angle; 
		this.requires(Robot.mecanum);
	}
	
	protected void initialize() {
		
		startingPigeonAngle = pigeon.getCurrentAngle();

		// desired angle is the difference between where we start and the angle to the box
		desiredAngle = startingPigeonAngle - angle;
	
		// Account for drifting
		angle += rotationDifference;

	}
	protected void execute() {

		// how far do we have to go b4 we get to the target?
		errorAngle = pigeon.getAbolsuteAngleError(desiredAngle);
		
		// how do we respond to that error?
		double pVal = errorAngle * .06;
		
		// set our speed to that adjusted speed
		speed = Math.min(maxRPM, maxRPM * pVal);

		/**
		 * check to see if we are where we need to be before
		 * we even move. we might be there.
		 */
		if(!isFinished()) {
			Robot.mecanum.rotate(-speed);
		 }
	}
	
	protected boolean isFinished(){

		if(Math.abs(this.desiredAngle-currentPigeonHeading) <= 1) {
			return true;
		}
		
		if(currentPigeonHeading > desiredAngle) {
			return true;
		}
		return false;
	}
	
}