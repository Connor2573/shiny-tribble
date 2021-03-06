package org.usfirst.frc.team4611.robot.commands.teleop.drive;

import org.usfirst.frc.team4611.robot.Robot;
import org.usfirst.frc.team4611.robot.subsystems.baseclasses.MecanumBase;

import edu.wpi.first.wpilibj.command.Command;

public class Move extends Command {
		
	private MecanumBase mecanum;
	
	public Move(MecanumBase mecanum) {
		this.mecanum = mecanum;
		this.requires(Robot.mecanum);
	}
	
	protected void execute() {
		mecanum.move();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}