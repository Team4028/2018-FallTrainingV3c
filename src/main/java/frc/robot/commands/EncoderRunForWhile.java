/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import javax.lang.model.util.ElementScanner6;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Chassis;
import frc.robot.util.DataLogger;

/**
 * An example command.  You can replace me with your own command.
 */
public class EncoderRunForWhile extends Command 
{
    private Chassis _chassis = Chassis.getInstance();
    

  public EncoderRunForWhile() 
  {
      
    // Use requires() here to declare subsystem dependencies
    //requires(Robot.m_subsystem);
    requires(_chassis);
    
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
      _chassis.arcadeDrive(0.7,0);
      
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() 
    {
        int currentpos = _chassis.get_LeftEncoderNU();
    if(currentpos >= 100000)
    {
       _chassis.arcadeDrive(0.0,0);
       return true; 
    }
    else
    {
        
        return false;
    }
       
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
