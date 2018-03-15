package org.usfirst.frc.team4590.robot.commands.autonomous;

import org.usfirst.frc.team4590.robot.commands.autonomous.drives.AutoReverseDriveLeft;
import org.usfirst.frc.team4590.robot.commands.claw.GrabCube;
import org.usfirst.frc.team4590.robot.commands.intake.Collect;
import org.usfirst.frc.team4590.robot.commands.intake.ShootToSwitch;
import org.usfirst.frc.team4590.robot.commands.pitcher.MovePitcherToState;
import org.usfirst.frc.team4590.utils.CommandChain;
import org.usfirst.frc.team4590.utils.PitcherState;

import edu.wpi.first.wpilibj.command.Command;

public class AutoSwitchLeftReverse extends CommandChain {
	
	public AutoSwitchLeftReverse() {
		Command closeClaw = new GrabCube(),
				collect = new Collect(500);
		
		addCommand(closeClaw);
		addCommand(collect);
	}
	
	@Override
	public void onFirstRun() {
		Command driveToSwitch = new AutoReverseDriveLeft(),
				movePitcher = new MovePitcherToState(PitcherState.SWITCH_BACKWARD),
				throwCube = new ShootToSwitch(1000);
		
		addCommand(driveToSwitch);
		addCommand(movePitcher);
		addCommand(throwCube);
	}
}