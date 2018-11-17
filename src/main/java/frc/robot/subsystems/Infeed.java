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
  private Button _homeButton;
  


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


    _leftInfeedMotor= new TalonSRX(RobotMap.LEFT_INFEED_POS_ARM);
    _leftInfeedMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    _leftInfeedMotor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyClosed, 0);
    _leftInfeedMotor.setInverted(false);
  
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
