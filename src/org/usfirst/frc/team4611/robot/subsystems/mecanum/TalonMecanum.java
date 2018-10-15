package org.usfirst.frc.team4611.robot.subsystems.mecanum;

import java.util.HashMap;

import org.usfirst.frc.team4611.robot.OI;
import org.usfirst.frc.team4611.robot.Robot;
import org.usfirst.frc.team4611.robot.commands.teleop.drive.Move;
import org.usfirst.frc.team4611.robot.networktables.NetTableManager;
import org.usfirst.frc.team4611.robot.subsystems.baseclasses.MecanumBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class TalonMecanum extends MecanumBase {
	
	private int maxRPM = 400; //Reduced from 1200
	
	private WPI_TalonSRX frontLeft = new WPI_TalonSRX(0);
	private WPI_TalonSRX frontRight = new WPI_TalonSRX(1);
	private WPI_TalonSRX backLeft = new WPI_TalonSRX(2);
	private WPI_TalonSRX backRight = new WPI_TalonSRX(3);
	
	private double pVal = .65;
	private int interval = 10;
	
	public final double INCH_PU_MULT = 215.910640625;
	
	private double velocity1;
	private double velocity2;
	private double velocity3;
	private double velocity4;
	
	private double YValScaler1 = 1;
	private double XValScaler1 = 1;
	private double YValScaler2 = 1;
	private double XValScaler2 = 1;
	private double ZValScaler = 1;
	
	private int velocityInvert1 = 1;
	private int velocityInvert2 = -1;
	private int velocityInvert3 = -1;
	private int velocityInvert4 = 1;
	
	private String mecanumSubtable = "Mecanum";
	
	private String velocity1ID = "Velocity1";
	private String velocity2ID = "Velocity2";
	private String velocity3ID = "Velocity3";
	private String velocity4ID = "Velocity4";
		
	public TalonMecanum() {
		setupTalons();
	}
	
	public void setupTalons() {
		frontLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		frontRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		backLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		backRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		
		frontLeft.setSelectedSensorPosition(0, 0, 0);
		frontRight.setSelectedSensorPosition(0, 0, 0);
		backLeft.setSelectedSensorPosition(0, 0, 0);
		backRight.setSelectedSensorPosition(0, 0, 0);
		
		frontLeft.config_kP(0, pVal, interval);
		frontRight.config_kP(0, pVal, interval);
		backLeft.config_kP(0, pVal, interval);
		backRight.config_kP(0, pVal, interval);
			
		frontLeft.config_kI(0, 0.000, 0);
		frontRight.config_kI(0, 0.000, 0);
		backLeft.config_kI(0, 0.000, 0);
		backRight.config_kI(0, 0.000, 0);
			
		frontLeft.config_kD(0, 0, 0);
		frontRight.config_kD(0, 0, 0);
		backLeft.config_kD(0, 0, 0);
		backRight.config_kD(0, 0, 0);
		
		frontLeft.setSensorPhase(true);
		frontRight.setSensorPhase(true);
		backLeft.setSensorPhase(true);
		backRight.setSensorPhase(true);
		
	}
	
	public void moveBackward(double speed) {
		
	}
	

	public void moveForward(double inches) {
		double pu = inches * INCH_PU_MULT;
		frontLeft.set(ControlMode.MotionMagic, pu);
		frontRight.set(ControlMode.MotionMagic, -pu);
		backLeft.set(ControlMode.MotionMagic, pu);
		backRight.set(ControlMode.MotionMagic, -pu);
	}
	
	public void resetEncoders() {
		frontLeft.setSelectedSensorPosition(0, 0, 0);
		frontRight.setSelectedSensorPosition(0, 0, 0);
		backLeft.setSelectedSensorPosition(0, 0, 0);
		backRight.setSelectedSensorPosition(0, 0, 0);
		System.out.println("Here");
	}
	
	public int getAverageSensorPos() {
		return (Math.abs(frontLeft.getSelectedSensorPosition(0)) + Math.abs(
		frontRight.getSelectedSensorPosition(0)) + Math.abs(
		backLeft.getSelectedSensorPosition(0)) + Math.abs(
		backRight.getSelectedSensorPosition(0)))/4;
	}

	public void moveVelocityAuton(double XVal, double YVal, double ZVal) {
		double velocity1 = 4*(maxRPM * (YVal * YValScaler1 + XVal * XValScaler1 + ZVal * ZValScaler) * (velocityInvert1));
		double velocity2 = 4*(maxRPM * (YVal * YValScaler2 - XVal * XValScaler2 - ZVal * ZValScaler) * (velocityInvert2)); 
		double velocity3 = 4*(maxRPM * (YVal * YValScaler2 + XVal * XValScaler2 - ZVal * ZValScaler) * (velocityInvert3));
		double velocity4 = 4*(maxRPM * (YVal * YValScaler1 - XVal * XValScaler1 + ZVal * ZValScaler) * (velocityInvert4));
		
		
		frontLeft.set(ControlMode.Velocity, velocity1);
		frontRight.set(ControlMode.Velocity, velocity2);
		backLeft.set(ControlMode.Velocity, velocity4);
		backRight.set(ControlMode.Velocity, velocity3);
	}
	
	public void move() {
		double YVal = -OI.generalJoystickFilter(OI.leftJoy.getY()); 
		double XVal = OI.generalJoystickFilter(OI.leftJoy.getX());
		double ZVal = OI.generalJoystickFilter(OI.rightJoy.getX());
	
		velocity1 = 4*(maxRPM * (YVal * YValScaler1 + XVal * XValScaler1 + ZVal * ZValScaler) * (velocityInvert1));
		velocity2 = 4*(maxRPM * (YVal * YValScaler2 - XVal * XValScaler2 - ZVal * ZValScaler) * (velocityInvert2)); 
		velocity3 = 4*(maxRPM * (YVal * YValScaler2 + XVal * XValScaler2 - ZVal * ZValScaler) * (velocityInvert3));
		velocity4 = 4*(maxRPM * (YVal * YValScaler1 - XVal * XValScaler1 + ZVal * ZValScaler) * (velocityInvert4));
		
		frontLeft.set(ControlMode.Velocity, velocity1);
		frontRight.set(ControlMode.Velocity, velocity2);
		backLeft.set(ControlMode.Velocity, velocity4);
		backRight.set(ControlMode.Velocity, velocity3);
		
		
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put(velocity1ID, velocity1);
		values.put(velocity2ID, velocity2);
		values.put(velocity3ID, velocity3);
		values.put(velocity4ID, velocity4);
		NetTableManager.updateValues(mecanumSubtable, values);
		System.out.println(frontLeft.getSelectedSensorPosition(0) + " " + frontRight.getSelectedSensorPosition(0) + " " + backLeft.getSelectedSensorPosition(0) + " " + backRight.getSelectedSensorPosition(0));
	}
	
	public void moveAtSpeeddouble(double speed1, double speed2, double speed3, double speed4) {
		
		frontLeft.set(ControlMode.Velocity, speed1);
		frontRight.set(ControlMode.Velocity, speed2);
		backLeft.set(ControlMode.Velocity, speed4);
		backRight.set(ControlMode.Velocity, speed3);
		
	}
	
	public void rotate(double velocity) {
		frontLeft.set(ControlMode.Velocity, velocity);
		frontRight.set(ControlMode.Velocity, velocity);
		backLeft.set(ControlMode.Velocity, velocity);
		backRight.set(ControlMode.Velocity, velocity);
	}
	private int t = 0;
	protected void initDefaultCommand() {
		Robot.mecanum.setDefaultCommand(new Move(this));
		// ^NORMAL WAY TO DRIVE
	}
	

}