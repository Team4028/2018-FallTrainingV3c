/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import org.omg.CosNaming._BindingIteratorImplBase;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Infeed;


public class Motion_Magic_Infeed_Pos extends Command {
    
    private Infeed _infeed = Infeed.getInstance();
    private double _targetPosInDegrees = 0;


  public Motion_Magic_Infeed_Pos(double targetPosInDegrees) {
    _targetPosInDegrees = targetPosInDegrees;
    requires(_infeed);
  }

  public Motion_Magic_Infeed_Pos(int targetPosInDegrees) {
    _targetPosInDegrees = targetPosInDegrees;
    requires(_infeed);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    _infeed.moveArmstoPos(_targetPosInDegrees);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }
  
}
