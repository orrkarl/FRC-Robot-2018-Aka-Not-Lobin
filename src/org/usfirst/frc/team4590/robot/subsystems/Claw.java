package org.usfirst.frc.team4590.robot.subsystems;

import org.usfirst.frc.team4590.robot.Robot;
import org.usfirst.frc.team4590.robot.RobotMap;
import org.usfirst.frc.team4590.robot.commands.pitcher.HoldPitcher;
import org.usfirst.frc.team4590.utils.PitcherState;
import org.usfirst.frc.team4590.utils.SmartTalon;

import com.ctre.phoenix.ErrorCode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Claw extends Subsystem {

	private static Claw instance;

	private static double defaultPower = 0.1; //0.13
	public static final int CURRENT_LIMIT = 0, //27
							 VOLTAGE_LIMIT = 2;

	private SmartTalon motor;
	private DigitalInput openMicroswitch, closedMicroswitch;

	public static Claw getInstance() {
		return instance;
	}

	public static void init() {
		instance = new Claw();
	}

	private Claw() {
		motor = new SmartTalon(RobotMap.CLAW_MOTOR_PORT);	
		motor.configVoltageCompSaturation(2, 0);
		motor.enableVoltageCompensation(true);
		openMicroswitch = new DigitalInput(RobotMap.CLAW_OPEN_MICROSWITCH_PORT);
		closedMicroswitch = new DigitalInput(RobotMap.CLAW_CLOSED_MICROSWITCH_PORT);
		 SmartDashboard.putNumber("Claw power", defaultPower);
		// SmartDashboard.putNumber("Claw current limit", currentLimit);
	}

	public void initDefaultCommand() {}
	
	public void update() {
		SmartDashboard.putString("Claw error", motor.getLastError().name());
		SmartDashboard.putNumber("Claw voltage", motor.getMotorOutputVoltage());
		SmartDashboard.putString("Claw current command", getCurrentCommandName());
		defaultPower = SmartDashboard.getNumber("Claw power", defaultPower);
		SmartDashboard.putBoolean("Claw isOpen", isOpen());
		SmartDashboard.putBoolean("Claw isClosed", isClosed());
		SmartDashboard.putNumber("Claw vlotage", motor.getMotorOutputVoltage());
		if(motor.getLastError() != ErrorCode.OK) System.out.println("TALON ERROR: " + motor.getLastError() + " ,value " + motor.getLastValue() + " ,voltage " + motor.getBusVoltage());
	}

	public static double getDefaultPower() {
		return defaultPower;
	}
	
	public void openWings(double power) {
		if (Pitcher.getInstance().getCurrentState() == PitcherState.SWITCH_BACKWARD &&
			Pitcher.getInstance().getCurrentCommand() instanceof HoldPitcher) {
			motor.set(power);
			Robot.getInstance().setEndgame(true);
		}
	}

	public void setPower(double power) {
		motor.set(power);
	}

	public void stop() {
		motor.set(0);
	}
	
	public void setVoltageCompensation(double voltage) {
		motor.configVoltageCompSaturation(voltage, 0);
	}

	public boolean isOpen() {
		return openMicroswitch.get();
	}

	public boolean isClosed() {
		return closedMicroswitch.get();
	}
	
	public SmartTalon getMotor() {
		return motor;
	}

	public boolean isClosing() {
		return motor.getLastValue() > 0;
	}
}