package org.usfirst.frc.team4611.robot.commands.auton.dualTargets;

import org.usfirst.frc.team4611.robot.RobotMap;
import org.usfirst.frc.team4611.robot.commands.arm.MovePotPos;
import org.usfirst.frc.team4611.robot.commands.drive.AutonForward;
import org.usfirst.frc.team4611.robot.commands.drive.StopAndRepositionTalons;
import org.usfirst.frc.team4611.robot.commands.elevator.MoveElevatorToPos;
import org.usfirst.frc.team4611.robot.commands.elevator.ResetElevator;
import org.usfirst.frc.team4611.robot.commands.pigeon.PigeonAdjust;
import org.usfirst.frc.team4611.robot.commands.solenoid.GrabBox;
import org.usfirst.frc.team4611.robot.commands.solenoid.PushBox;
import org.usfirst.frc.team4611.robot.commands.solenoid.ReleaseBox;
import org.usfirst.frc.team4611.robot.logging.Logger;
import org.usfirst.frc.team4611.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class StartLeftScaleRightScaleRight extends CommandGroup {

	public StartLeftScaleRightScaleRight() {
		//addSequential(new ResetElevator());
		addSequential(new GrabBox());
		addSequential(new StopAndRepositionTalons());
		//addParallel(new MoveElevatorToPos(Elevator.ELEVATOR_TOP/2));
		//addParallel(new MovePotPos(RobotMap.POTSWITCH));
		addSequential(new AutonForward(RobotMap.MOREWAY));
		addSequential(new StopAndRepositionTalons());
		addSequential(new PigeonAdjust(RobotMap.turnAngle1 + 5));
		addSequential(new StopAndRepositionTalons());
		addSequential(new AutonForward(RobotMap.crossToScale+50));
		addSequential(new StopAndRepositionTalons());
		addSequential(new PigeonAdjust(-RobotMap.turnAngle1));
		//addParallel(new MoveElevatorToPos(Elevator.ELEVATOR_TOP));
		//addParallel(new MovePotPos(RobotMap.POTMAX));
		addSequential(new StopAndRepositionTalons());
		addSequential(new AutonForward(55));
		addSequential(new StopAndRepositionTalons());
		addSequential(new PigeonAdjust(-40));
		addSequential(new StopAndRepositionTalons());
		addSequential(new ReleaseBox());
		addSequential(new PushBox());
		addSequential(new PigeonAdjust(-100));
		addSequential(new StopAndRepositionTalons());
	}
	protected void initialize() {
		Logger.log("initialized", this.getClass().getName());
	}
}
