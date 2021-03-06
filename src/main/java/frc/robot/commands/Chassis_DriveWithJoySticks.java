/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Chassis;
import frc.robot.util.BeakXboxController.Thumbstick;

public class Chassis_DriveWithJoySticks extends Command 
{
  // declare working objects
  private Chassis _chassis = Chassis.getInstance();
  private Thumbstick _leftThumbstick;
  private Thumbstick _rightThumbstick;

  public Chassis_DriveWithJoySticks(Thumbstick leftThumbstick, Thumbstick righThumbstick) 
  {
    // Use requires() here to declare subsystem dependencies
    requires(_chassis);
    setInterruptible(true);

    _leftThumbstick = leftThumbstick;
    _rightThumbstick = righThumbstick;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() 
  {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() 
  {
	  _chassis.arcadeDrive(_leftThumbstick.getY(), _rightThumbstick.getX());
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() 
  {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {

  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() 
  {
  }
}
