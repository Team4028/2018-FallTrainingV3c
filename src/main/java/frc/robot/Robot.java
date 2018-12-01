/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.StartCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.EncoderRunForWhile;
import frc.robot.commands.Infeed_Homing;
import frc.robot.models.LogDataBE;
import frc.robot.subsystems.Chassis;
import frc.robot.subsystems.Infeed;
import frc.robot.util.BeakUtilities;
import frc.robot.util.DataLogger;
import frc.robot.util.MovingAverage;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot 
{
  // create instance of singelton Subsystems
  private static final String ROBOT_NAME = "2019-FallTrainingV3-TestEncoder-PAJ";

  private Chassis _chassis;
  private Infeed _infeed;
  private OI _oi;

	// class level working variables
	private DataLogger _dataLogger = null;
	private String _buildMsg = "?";
 	long _lastScanEndTimeInMSec;
 	long _lastDashboardWriteTimeMSec;
 	MovingAverage _scanTimeSamples;

  // ==============================================================================================
  // Robot StartUp
  // ==============================================================================================

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() 
  {
    _buildMsg = BeakUtilities.WriteBuildInfoToDashboard(ROBOT_NAME);
    _scanTimeSamples = new MovingAverage(50);
    _chassis = Chassis.getInstance();
    _infeed = Infeed.getInstance();
    _oi = OI.getInstance();
  }

  // ==============================================================================================
  // Robot Disabled
  // ==============================================================================================

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() 
  {
  }

  @Override
  public void disabledPeriodic() 
  {
    Scheduler.getInstance().run();
  }

  // ==============================================================================================
  // Autonomous Mode
  // ==============================================================================================

   /**
   * This method run 1x when the robot is enabled in auton mode
   */
  @Override
  public void autonomousInit() 
  {

    //_chassis.homeEncoderPos();
   // m_autonomousCommand = m_chooser.getSelected();

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    /*EncoderRunForWhile auton = new EncoderRunForWhile();
    auton.start();
    _scanTimeSamples = new MovingAverage(50);
    */
    Infeed_Homing auton = new Infeed_Homing(false);
    auton.start();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() 
  {
    Scheduler.getInstance().run();

    
    // ============= Refresh Dashboard =============
		outputAllToDashboard();
		
		// ============= Optionally Log Data =============
		logAllData();
  }

  // ==============================================================================================
  // Telop Mode
  // ==============================================================================================
  
  /**
  * This method run 1x when the robot is enabled in telop mode
  */
  @Override
  public void teleopInit() 
  {
    
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    _scanTimeSamples = new MovingAverage(50);
    Infeed_Homing teleop = new Infeed_Homing(false);
    teleop.start();

  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() 

  {
    Scheduler.getInstance().run();
  
  

    // ============= Refresh Dashboard =============
		outputAllToDashboard();
		
		// ============= Optionally Log Data =============
		logAllData();
  }

  // ==============================================================================================
  // Test Mode
  // ==============================================================================================
  
  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() 
  {
  }

  // ==============================================================================================
  // Special Methods
  // ==============================================================================================
  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() 
  {
    outputAllToDashboard();
  }

	/** Method to Push Data to ShuffleBoard */
	private void outputAllToDashboard() {
		// limit spamming
    	long scanCycleDeltaInMSecs = new Date().getTime() - _lastScanEndTimeInMSec;
    	// add scan time sample to calc scan time rolling average
    	_scanTimeSamples.add(new BigDecimal(scanCycleDeltaInMSecs));
    	
    	if((new Date().getTime() - _lastDashboardWriteTimeMSec) > 100) {
    		// each subsystem should add a call to a outputToSmartDashboard method
    		// to push its data out to the dashboard

        _chassis.updateDashboard(); 
        _infeed.updateDashboard();
	    	
    		// write the overall robot dashboard info
	    	SmartDashboard.putString("Robot Build", _buildMsg);
	    	
	    	BigDecimal movingAvg = _scanTimeSamples.getAverage();
	    	DecimalFormat df = new DecimalFormat("####");
	    	SmartDashboard.putString("Scan Time (2 sec roll avg)", df.format(movingAvg) + " mSec");
    		// snapshot last time
    		_lastDashboardWriteTimeMSec = new Date().getTime();
    	}
    	
    	// snapshot when this scan ended
      _lastScanEndTimeInMSec = new Date().getTime();
      
	}

  	/** Method for Logging Data to the USB Stick plugged into the RoboRio */
	private void logAllData() { 
		// always call this 1st to calc drive metrics
    	if(_dataLogger != null) {    	
	    	// create a new, empty logging class
        	LogDataBE logData = new LogDataBE();
	    	
	    	// ask each subsystem that exists to add its data
	    	_chassis.updateLogData(logData);
			
	    	_dataLogger.WriteDataLine(logData);
    	}
	}
}