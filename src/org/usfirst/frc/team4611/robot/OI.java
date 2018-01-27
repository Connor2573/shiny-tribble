package org.usfirst.frc.team4611.robot;

import org.usfirst.frc.team4611.robot.commands.auton.AutoGrab;
import org.usfirst.frc.team4611.robot.commands.drive.StrafeLeft;
import org.usfirst.frc.team4611.robot.commands.drive.StrafeRight;
import org.usfirst.frc.team4611.robot.potentiometer.MovePotDown;
import org.usfirst.frc.team4611.robot.potentiometer.MovePotSwitch;
import org.usfirst.frc.team4611.robot.potentiometer.MovePotUp;
import org.usfirst.frc.team4611.robot.potentiometer.stopPot;
import org.usfirst.frc.team4611.robot.commands.solenoid.ExtendSolenoid;
import org.usfirst.frc.team4611.robot.commands.solenoid.RetractSolenoid;
import org.usfirst.frc.team4611.robot.commands.solenoid.ToggleSolenoid;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;


/**
 * This is where we create all of out buttons and joysticks and 
 * set up the "UI" of the bot for the drivers. You're gonna end up 
 * here a lot when people complain about buttons needing to be changed
 */

public class OI {
	public static Joystick leftJoy;
	public static Joystick rightJoy;
	public Button strafeLeft;
	public Button strafeRight;
	public Button linearActuatorUp;
	public Button linearActuatorDown;
	public Button linearActuatorSwitch;
	public Button autoGrabBox;
	public Button solToggle;
	public Button solE;
	public Button solR;

	public OI (){
		
	
		leftJoy = new Joystick(RobotMap.leftJoyPort); //The left joystick exists on this port in robot map
		rightJoy = new Joystick(RobotMap.rightJoyPort); //The right joystick exists on this port in robot map
		strafeLeft= new JoystickButton(rightJoy, 4);
		strafeRight= new JoystickButton(rightJoy, 5);
		autoGrabBox = new JoystickButton(leftJoy, RobotMap.autoGrabButtPort);
		solToggle = new JoystickButton(leftJoy, RobotMap.solTogglePort);
		solE = new JoystickButton(leftJoy, RobotMap.solEPort);
		solR = new JoystickButton(leftJoy, RobotMap.solRPort);
	
		RobotMap.updateValue(RobotMap.joyStickSubTable, RobotMap.leftJoyXID, leftJoy.getX());
		RobotMap.updateValue(RobotMap.joyStickSubTable, RobotMap.leftJoyYID, leftJoy.getY());
		RobotMap.updateValue(RobotMap.joyStickSubTable, RobotMap.leftJoyZID, leftJoy.getZ());
		RobotMap.updateValue(RobotMap.joyStickSubTable, RobotMap.rightJoyXID, rightJoy.getX());
		RobotMap.updateValue(RobotMap.joyStickSubTable, RobotMap.rightJoyYID, rightJoy.getY());
		RobotMap.updateValue(RobotMap.joyStickSubTable, RobotMap.rightJoyZID, rightJoy.getZ());

		this.strafeRight.whileHeld(new StrafeRight((double)RobotMap.getValue(RobotMap.mecanumSubTable, RobotMap.strafePowerID)));
		this.strafeLeft.whileHeld(new StrafeLeft((double)RobotMap.getValue(RobotMap.mecanumSubTable, RobotMap.strafePowerID)));
		this.strafeRight.whileHeld(new StrafeRight((double)RobotMap.getValue(RobotMap.mecanumSubTable, RobotMap.strafePowerID)));
		this.strafeLeft.whileHeld(new StrafeLeft((double)RobotMap.getValue(RobotMap.mecanumSubTable, RobotMap.strafePowerID)));
		
		linearActuatorUp = new JoystickButton(leftJoy, 5);
		linearActuatorDown = new JoystickButton(leftJoy, 4);
		linearActuatorSwitch = new JoystickButton(leftJoy, 3);
		
		linearActuatorUp.whileHeld(new MovePotUp((double)RobotMap.getValue(RobotMap.linearActuatorSubTable, RobotMap.LASpeedID)));
		linearActuatorUp.whenReleased(new stopPot());
		
		linearActuatorDown.whileHeld(new MovePotDown((double)RobotMap.getValue(RobotMap.linearActuatorSubTable, RobotMap.LASpeedID)));
		linearActuatorDown.whenReleased(new stopPot());
		
		linearActuatorSwitch.whileHeld(new MovePotSwitch((double)RobotMap.getValue(RobotMap.linearActuatorSubTable, RobotMap.LASpeedID)));
		linearActuatorSwitch.whenReleased(new stopPot());
		
		this.solToggle.whenPressed(new ToggleSolenoid());
		this.solE.whenPressed(new ExtendSolenoid());
		this.solR.whenPressed(new RetractSolenoid());
		this.autoGrabBox.whenPressed(new AutoGrab() );
		if(!(boolean)RobotMap.getValue(RobotMap.switcherSubTable, RobotMap.switcherID)) {
			this.setupVictor();
		}else{
			this.setupTalon();
		}
	}
	
	public void setupVictor() {
		this.stopTalon();
		RobotMap.setupVictor();
	}
	
	public void stopVictor() {
		RobotMap.driveTrainBL.stopMotor();
		RobotMap.driveTrainBR.stopMotor();
		RobotMap.driveTrainFL.stopMotor();
		RobotMap.driveTrainFR.stopMotor();
	}
	
	/**
	 * Called at the beginning of the program and whenever there is a change on the Shuffleboard
	 * Use to setup any victor-specific joystick or other io buttons
	 */	
	public void setupTalon() {
		this.stopVictor();
		RobotMap.setupTalon();
	}
	
	/**
	 * Called at the beginning of the program and whenever there is a change on the Shuffleboard
	 * Use to setup any talon-specific joystick or other io buttons
	 */	
	public void stopTalon() {
	}
	
	public double filter(double raw) //We pass joystick values through the filter here and edit the raw value
    {
        if (Math.abs(raw) < (double)RobotMap.getValue(RobotMap.mecanumSubTable, RobotMap.deadZoneID)) {
            return 0; //If the value passed is less than 15% ignore it. This is reffered to as a deadzone
        } else {
            return  raw * (double)RobotMap.getValue(RobotMap.mecanumSubTable, RobotMap.motorPowerID); //Set the output to a ceratin percent of of the input
        }
    }
	
	public double strafeFilter(double raw) {
		if (Math.abs(raw) < (double)RobotMap.getValue(RobotMap.mecanumSubTable, RobotMap.deadZoneID)) {
            return 0; //If the value passed is less than 15% ignore it. This is reffered to as a deadzone
        } else {
            return  raw * Math.min((double)RobotMap.getValue(RobotMap.mecanumSubTable, RobotMap.motorPowerID) * 2, 1); //Set the output to a ceratin percent of of the input
        }
	}
	
	public double yFilter(double raw)
	{
		if (Math.abs(raw)< (double)RobotMap.getValue(RobotMap.mecanumSubTable, RobotMap.deadZoneYID)) {
			return 0;
		}
		else {
			return raw * (double)RobotMap.getValue(RobotMap.mecanumSubTable, RobotMap.motorPowerID);
		}
	
	}
}
