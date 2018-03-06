package org.usfirst.frc.team4590.robot.subsystems;

import org.usfirst.frc.team4590.robot.OI;
import org.usfirst.frc.team4590.robot.RobotMap;
import org.usfirst.frc.team4590.robot.commands.chassis.ArcadeDriveByJoystick;
import org.usfirst.frc.team4590.utils.CANRobotDrive;
import org.usfirst.frc.team4590.utils.CANRobotDrive.TalonID;
import org.usfirst.frc.team4590.utils.SmartEncoder;

import com.ctre.phoenix.ErrorCode;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//Encoders are about 2600 ticks per meter.
public class Chassis extends Subsystem {

	private static Chassis instance;
	
	private SmartEncoder m_leftEncoder, m_rightEncoder;
	private CANRobotDrive m_robotDrive;
	private AHRS m_navx;
	
	public static Chassis getInstance() {
		return instance;
	}
	
	public static void init() {
		instance = new Chassis();
	}

	private Chassis() {
		m_robotDrive = new CANRobotDrive(RobotMap.CHASSIS_FRONT_LEFT_MOTOR_PORT,
									   RobotMap.CHASSIS_REAR_LEFT_MOTOR_PORT,
									   RobotMap.CHASSIS_FRONT_RIGHT_MOTOR_PORT, 
									   RobotMap.CHASSIS_REAR_RIGHT_MOTOR_PORT);
		m_leftEncoder = new SmartEncoder(m_robotDrive.getTalon(TalonID.REAR_LEFT), 2300);
		m_rightEncoder = new SmartEncoder(m_robotDrive.getTalon(TalonID.REAR_RIGHT), 2300);
		m_navx = new AHRS(RobotMap.CHASSIS_GYRO_PORT);
	}
	
    public void initDefaultCommand() {
    	setDefaultCommand(new ArcadeDriveByJoystick(OI.getInstance().getMainJS()));
    }
    
    public void update() {
    	SmartDashboard.putString("Chassis current command", getCurrentCommandName());
    	SmartDashboard.putNumber("Chassis angle", getAngle());
    	SmartDashboard.putNumber("Chassis Distance", getDistance());
    }
    
    public void arcadeDrive(double moveValue, double rotateValue) {
    	m_robotDrive.arcadeDrive(moveValue, rotateValue);
    }
    
    public void tankDrive(double leftValue, double rightValue) {
    	m_robotDrive.tankDrive(leftValue, rightValue);
    }
    
    public void stop() {
    	tankDrive(0, 0);
    }
    
    public double getDistance() {
    	return m_leftEncoder.getDistance() / 2 - m_rightEncoder.getDistance() / 2;
    }
    
    public double getSpeed() {
    	return (m_leftEncoder.getSpeed() - m_rightEncoder.getSpeed()) / 2;
    }
    
    public double getLeftDistance() {
    	return m_leftEncoder.getDistance();
    }
    
    public double getRightDistance() {
    	return -m_rightEncoder.getDistance();
    }
    
    public int getLeftTicks(){ 
    	return m_leftEncoder.getTicks();
    }
    
    public int getRightTicks() {
    	return -m_rightEncoder.getTicks();
    }
    
    public double getLeftSpeed() {
    	return m_leftEncoder.getSpeed();
    }
    
    public double getRightSpeed() {
    	return m_rightEncoder.getSpeed();
    }
    
    public double getAngle() {
    	return m_navx.getYaw();
    }
    
    public void resetSensors() {
    	resetGyro();
    	resetEncoders();
    }
    
    public void resetGyro() {
    	m_navx.reset();
    }
    
    public ErrorCode resetLeftEncoder() {
    	return m_leftEncoder.reset();
    }
    
    public ErrorCode resetRightEncoder() {
    	return m_rightEncoder.reset();
    }
    
    public void resetEncoders() {
    	resetLeftEncoder();
    	resetRightEncoder();
    }
}