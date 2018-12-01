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
import frc.robot.subsystems.Infeed;
import frc.robot.util.DataLogger;

/**
 * An example command.  You can replace me with your own command.
 */
public class 
Infeed_Homing extends Command 
{
    private Infeed _infeed = Infeed.getInstance();
    private Boolean _isForceZero;


  public Infeed_Homing(boolean isForceZero) 
  {
      
    // Use requires() here to declare subsystem dependencies
    //requires(Robot.m_subsystem);
    requires(_infeed);
    setInterruptible(false);
    _isForceZero = isForceZero;
  }
  // Called just before this Command runs the first time
  @Override
  protected void initialize() 
  {
    if(_isForceZero == true)
    {
      _infeed.setArmsFalse();
    }
    

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() 
  {
    _infeed.homeArms();
    System.out.println("command is running");
    

    
      
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() 
    {
    return _infeed.getIsArmsHomed();
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
