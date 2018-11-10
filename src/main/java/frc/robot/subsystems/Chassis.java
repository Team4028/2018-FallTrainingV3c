/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.interfaces.ISubsystem;
import frc.robot.models.LogDataBE;

/**
 * Chassis Class
 * 
 * Tank Drive
 *  - 3 Motor Gearbox per Side
 *  - Hi/Low Gear Pneumatic Shifter
 */
public class Chassis extends Subsystem implements ISubsystem 
{
  // define class level working variables
  private TalonSRX _leftMaster;
  private TalonSRX _leftSlaveA;
  private TalonSRX _leftSlaveB;
  private TalonSRX _rightMaster;
  private TalonSRX _rightSlaveA;
  private TalonSRX _rightSlaveB;


	//=====================================================================================
	// Define Singleton Pattern
	//=====================================================================================
	private static Chassis _instance = new Chassis();
	
  public static Chassis getInstance() 
  {
		return _instance;
	}
	
	// private constructor for singleton pattern
  private Chassis()
  {
    // left side
    _leftMaster = new TalonSRX(RobotMap.LEFT_DRIVE_MASTER_CAN_ADDR);
    _leftSlaveA = new TalonSRX(RobotMap.LEFT_DRIVE_SLAVEA_CAN_ADDR);
    //_leftSlaveB = new TalonSRX(RobotMap.LEFT_DRIVE_SLAVEB_CAN_ADDR);

    _leftMaster.setInverted(true);
    _leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    _leftMaster.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled, 0);
    _leftMaster.configReverseLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled, 0);

    _leftSlaveA.setInverted(true);
    _leftSlaveA.follow(_leftMaster);

    //_leftSlaveB.setInverted(true);
    //_leftSlaveB.follow(_leftMaster);

    // right side
    _rightMaster = new TalonSRX(RobotMap.RIGHT_DRIVE_MASTER_CAN_ADDR);
    _rightSlaveA = new TalonSRX(RobotMap.RIGHT_DRIVE_SLAVEA_CAN_ADDR);
    //_rightSlaveB = new TalonSRX(RobotMap.RIGHT_DRIVE_SLAVEB_CAN_ADDR);

    _rightMaster.setInverted(false);
    _rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    _rightMaster.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled, 0);
    _rightMaster.configReverseLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled, 0);

    _rightSlaveA.setInverted(false);
    _rightSlaveA.follow(_rightMaster);

    //_rightSlaveB.setInverted(true);
    //_rightSlaveB.follow(_leftMaster);
  }

  //=====================================================================================
  // Methods for controlling this subsystem Called from Commands.
  //=====================================================================================
  @Override
  public void initDefaultCommand() {

    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void arcadeDrive(double throttleCmdRaw, double turnCmdRaw)
  {
    _leftMaster.set(ControlMode.PercentOutput, throttleCmdRaw + (0.7 * turnCmdRaw));
    _rightMaster.set(ControlMode.PercentOutput, throttleCmdRaw + (0.7 * -1.0 * turnCmdRaw));
  }

  public int get_LeftEncoderNU()
  {
    return _leftMaster.getSelectedSensorPosition(0);
  }

  public int get_RightEncoderNU()
  {
    return _rightMaster.getSelectedSensorPosition(0);
  }

  //=====================================================================================
	// Special Methods for ISubsystem
	//=====================================================================================
  @Override
  public void updateLogData(LogDataBE logData) 
  {

  }

  @Override
  public void updateDashboard() 
  {
    SmartDashboard.putNumber("Carriage:LeftEncoderNU", get_LeftEncoderNU());
    SmartDashboard.putNumber("Carriage:RightEncoderNU", get_RightEncoderNU());
  }
}
