/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
//correct
package frc.robot.commands;

import javax.lang.model.util.ElementScanner6;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.subsystems.Chassis;
import frc.robot.subsystems.Infeed;
import frc.robot.util.DataLogger;

/**
 * An example command.  You can replace me with your own command.
 */
public class 
Homing extends Command 
{
    private Infeed _infeed = Infeed.getInstance();

  public Homing() 
  {
      
    // Use requires() here to declare subsystem dependencies
    //requires(Robot.m_subsystem);
    requires(_infeed);
  }
  // Called just before this Command runs the first time
  @Override
  protected void initialize() 
  {
     // _infeed.getLimitSwitchValue();
    

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() 
  {
    _infeed.homingMotor();
    System.out.println("command is running");
    

    
      
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() 
    {
    return _infeed.getIsRightArmHomed();
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
