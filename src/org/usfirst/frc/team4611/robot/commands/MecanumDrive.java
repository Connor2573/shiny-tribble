package org.usfirst.frc.team4611.robot.commands;

import org.usfirst.frc.team4611.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class MecanumDrive extends Command{
	
	public MecanumDrive(){
		this.requires(Robot.tankDrive); //This command uses this subsystem
	}
	
	protected void execute() { //execute is called every 20 miliseconds
		double YVal = Robot.oi.filter(Robot.oi.leftJoy.getY()); //Grab the Y value of the joystick and pass 
		double XVal = Robot.oi.filter(Robot.oi.leftJoy.getX());//it through the filter
		double ZVal = Robot.oi.filter(Robot.oi.rightJoy.getX());
		double finalZVal= ZVal* 180;
	    Robot.tankDrive.move(YVal, -XVal, -ZVal); 
	    System.out.println("YVal: "+YVal);
	    System.out.println("XVal: "+XVal);
	    System.out.println("ZVal: "+ZVal);
	    //Then pass that double to the method "move" in tankDrive
	   // Robot.tankDrive.movePolar(XVal,finalZVal, YVal);
	
	}
	
	@Override
	protected boolean isFinished() {
		return false; //Don't stop running this command 
	}

}
