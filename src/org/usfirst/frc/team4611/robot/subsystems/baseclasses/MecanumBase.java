package org.usfirst.frc.team4611.robot.subsystems.baseclasses;

import edu.wpi.first.wpilibj.command.Subsystem;
import jaci.pathfinder.Trajectory;

public abstract class MecanumBase extends Subsystem {

	public abstract void move();
	public abstract void moveForward(double speed);
	public abstract void moveBackward(double speed);
	public abstract void resetEncoders();
	
	public abstract void rotate(double velocity);
	public abstract int getAverageSensorPos();
	
	public abstract void moveVelocityAuton(double YVal);
	public abstract double getVelocity();
	
	public abstract void followTrajectory(Trajectory t);
	
	public abstract void setTrajectorySpeeds();
}
