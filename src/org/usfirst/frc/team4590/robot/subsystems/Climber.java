package org.usfirst.frc.team4590.robot.subsystems;

import org.usfirst.frc.team4590.robot.RobotMap;
import org.usfirst.frc.team4590.utils.SmartTalon;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climber extends Subsystem {

	private static Climber instance;
	
	private static double defaultPower = 1;
	
	private SmartTalon motor;
	
	public static Climber getInstance() {
		return instance;
	}
	
	public static void init() {
		instance = new Climber();
	}
	
	private Climber() {
		motor = new SmartTalon(RobotMap.CLIMBER_MOTOR_PORT);
		SmartDashboard.putNumber("Climber power", defaultPower);
	}

    public void initDefaultCommand() {}
    
    public void update() {
    	defaultPower = SmartDashboard.getNumber("Climber power", defaultPower);
    }
    
    public static double getDefaultPower() {
    	return defaultPower;
    }
    
    public void setPower(double power) {
    	motor.set(power);
    }
    
    public void stop() {
    	motor.set(0);
    }
}