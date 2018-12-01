package frc.robot.subsystems;

import javax.lang.model.util.ElementScanner6;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.interfaces.ISubsystem;
import frc.robot.models.LogDataBE;

/**
 * An example subsystem.  Use this as a template.
 */
public class Infeed extends Subsystem implements ISubsystem 
{
  // define class level working variables
  private TalonSRX _rightInfeedMotor;
  private DigitalInput _limitSwitch1;
  private boolean _isRightAxisHomed = false;
  private boolean _isLeftAxisHomed = false;
  private TalonSRX _leftInfeedMotor;
  private static final double NU_TO_DEGREES = 4096.0/360.0;
  private static final double INFEED_MOTION_MAGIC_F = 0.3354098361;
  private static final double INFEED_MOTION_MAGIC_P = 1.5;
  private static final double INFEED_MOTION_MAGIC_I = 0; 
  private static final double INFEED_MOTION_MAGIC_D = 0;
  private static final int INFEED_MAX_V = 3000;
  private static final int INFEED_MAX_A = 2000;

  


	//=====================================================================================
	// Define Singleton Pattern
	//=====================================================================================
	private static Infeed _instance = new Infeed();
	
	public static Infeed getInstance() {
		return _instance;
	}
	
	// private constructor for singleton pattern
  private Infeed()
  {
    _rightInfeedMotor = new TalonSRX(RobotMap.RIGHT_INFEED_POS_ARM);
    _rightInfeedMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    _rightInfeedMotor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyClosed, 0);
    
    _rightInfeedMotor.setInverted(true);
    _rightInfeedMotor.configMotionAcceleration(INFEED_MAX_A, 0);
    _rightInfeedMotor.configMotionCruiseVelocity(INFEED_MAX_V, 0);
    _rightInfeedMotor.config_kD(0, INFEED_MOTION_MAGIC_D, 0);
    _rightInfeedMotor.config_kF(0, INFEED_MOTION_MAGIC_F, 0);
    _rightInfeedMotor.config_kI(0, INFEED_MOTION_MAGIC_I, 0);
    _rightInfeedMotor.config_kP(0, INFEED_MOTION_MAGIC_P, 0);


    _leftInfeedMotor= new TalonSRX(RobotMap.LEFT_INFEED_POS_ARM);
    _leftInfeedMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    _leftInfeedMotor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyClosed, 0);
    _leftInfeedMotor.setInverted(false);
    _leftInfeedMotor.configMotionAcceleration(INFEED_MAX_A, 0);
    _leftInfeedMotor.configMotionCruiseVelocity(INFEED_MAX_V, 0);
    _leftInfeedMotor.config_kD(0, INFEED_MOTION_MAGIC_D, 0);
    _leftInfeedMotor.config_kF(0, INFEED_MOTION_MAGIC_F, 0);
    _leftInfeedMotor.config_kI(0, INFEED_MOTION_MAGIC_I, 0);
    _leftInfeedMotor.config_kP(0, INFEED_MOTION_MAGIC_P, 0);
  
  }

  //=====================================================================================
	// Public Methods
	//=====================================================================================
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  /*public boolean getLimitSwitchValue()
    {
      return _limitSwitch1.get();

    }*/
    public void homeEncoderPosInfeed() 
    {
      _rightInfeedMotor.setSelectedSensorPosition(0, 0, 0);
  
    }
    public void infeedPositionWideCube()
    {
      _rightInfeedMotor.setSelectedSensorPosition(50000, 0, 50);
      _leftInfeedMotor.setSelectedSensorPosition(50000, 0, 50);
    }
    public void setArmsFalse()
  {
    _isLeftAxisHomed = false;
    _isRightAxisHomed = false;
  }
  public void homeArms()
  {
      if(_rightInfeedMotor.getSensorCollection().isRevLimitSwitchClosed() == false)
      {
        _rightInfeedMotor.setSelectedSensorPosition(0, 0, 0);
        _rightInfeedMotor.set(ControlMode.PercentOutput, 0.0);
        _isRightAxisHomed = true;
      }
  
    else if(!_isRightAxisHomed )
      {

        _rightInfeedMotor.set(ControlMode.PercentOutput, -0.13);
      }
    
	    if(_leftInfeedMotor.getSensorCollection().isRevLimitSwitchClosed() == false)
      {
        _leftInfeedMotor.set(ControlMode.PercentOutput, 0.0);
        _leftInfeedMotor.setSelectedSensorPosition(0, 0, 0);
        _isLeftAxisHomed = true;
      }
    else if(!_isLeftAxisHomed )
      {

        _leftInfeedMotor.set(ControlMode.PercentOutput, -0.13);
      }
    }
    public void moveArmstoPos(double targetPosInDegrees)
    {
      double targetPosInNU = NU_TO_DEGREES * targetPosInDegrees;
      _leftInfeedMotor.set(ControlMode.MotionMagic, targetPosInNU);
      _rightInfeedMotor.set(ControlMode.MotionMagic, targetPosInNU);
    }
 
    private boolean getIsRightArmHomed()
    {
     return _isRightAxisHomed;
    }
    private boolean getIsLeftArmHomed()
    {
      return _isLeftAxisHomed;
    }
    public boolean getIsArmsHomed()
    {
      return getIsRightArmHomed() && getIsLeftArmHomed();

    }
    public int getRightArmPositionNU()
    {
      return _rightInfeedMotor.getSelectedSensorPosition(0);
    }
    public int getLeftArmPositionNU()
    {
      return _leftInfeedMotor.getSelectedSensorPosition(0);
    }
    
    /*
      if(_leftInfeedMotor.getSensorCollection().isRevLimitSwitchClosed() == false)
    {
      _leftInfeedMotor.set(ControlMode.PercentOutput, 0.0);
    }
    else
    {
      _leftInfeedMotor.set(ControlMode.PercentOutput, -0.1);
    }
*/
    private double convertNUtoDegrees(int nativeUnits)
    {
      return nativeUnits/ NU_TO_DEGREES;
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
    SmartDashboard.putBoolean("Infeed:IsRightArmedHomed", getIsRightArmHomed());
    SmartDashboard.putNumber("Infeed:RightPositionNU", getRightArmPositionNU());
    SmartDashboard.putBoolean("Infeed:IsLeftArmedHomed", getIsLeftArmHomed());
    SmartDashboard.putNumber("Infeed:LeftPositionNU", getLeftArmPositionNU());
    SmartDashboard.putNumber("Infeed:LeftPositionDegrees", convertNUtoDegrees(getLeftArmPositionNU()));
    SmartDashboard.putNumber("Infeed:RightPositionDegrees", convertNUtoDegrees(getRightArmPositionNU()));


  }
}
