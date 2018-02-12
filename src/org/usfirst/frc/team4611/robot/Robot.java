package org.usfirst.frc.team4611.robot;

import org.usfirst.frc.team4611.robot.subsystems.Arm;
//import org.usfirst.frc.team4611.robot.commands.cameraupdater.CameraUpdater;
import org.usfirst.frc.team4611.robot.logging.Logger;
import org.usfirst.frc.team4611.robot.subsystems.DriveTrain;
import org.usfirst.frc.team4611.robot.subsystems.Elevator;
import org.usfirst.frc.team4611.robot.subsystems.Solenoid;
import org.usfirst.frc.team4611.robot.subsystems.UltrasonicSensor;
import edu.wpi.cscore.UsbCamera;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static DriveTrain mecanum;
	public static Elevator el;
	public static Arm arm;
	public static UltrasonicSensor ultrasonic;
	public static Solenoid sol;
	public static OI oi;
	

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();
	public static UsbCamera camera;
	
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		RobotMap.init(); //Run the method "init" in RobotMap
		

		CameraServer.getInstance().startAutomaticCapture();
		
		//Initialize the subsystems
		mecanum = new DriveTrain();
		el = new Elevator();
		arm = new Arm();
		sol = new Solenoid();
		ultrasonic = new UltrasonicSensor();
		oi = new OI();
		camera = CameraServer.getInstance().startAutomaticCapture();
		//new CameraUpdater();
		
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		RobotMap.defaults.saveProperties();
		Logger.robotDisabled();
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {

		//autonomousCommand = new DriveBlock();
		
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		if(Robot.el.isSwitchSet()) {
			RobotMap.elevator_Talon.setSelectedSensorPosition(0, 0, 0);
		}
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		
		
		if (autonomousCommand != null) 
			autonomousCommand.cancel();
		
		//Checks to see if the driver has updated the switch for which motors are being used
		if((boolean)RobotMap.getValue(RobotMap.switcherSubTable, RobotMap.switcherID)) {
			//If it's true, it st  arts talon setup
			RobotMap.setupTalon();
		}else if(!(boolean)RobotMap.getValue(RobotMap.switcherSubTable, RobotMap.switcherID)) {
			//If it's false, it starts victor setup
			RobotMap.setupVictor();
		}
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		ultrasonic.getInches();
		System.out.println("Limit Switch: "+ RobotMap.limitSwitch.get());
		System.out.println("Elevator Pos: " + RobotMap.elevator_Talon.getSelectedSensorPosition(0));
		if(Robot.el.isSwitchSet()) {
			RobotMap.elevator_Talon.setSelectedSensorPosition(0, 0, 0);
		}
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
	
		
	
}
