/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.commands.Chassis_DriveWithJoySticks;
import frc.robot.commands.Infeed_Homing;
import frc.robot.commands.Motion_Magic_Infeed_Pos;
import frc.robot.util.BeakXboxController;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI 
{
  // working object
  BeakXboxController _driverGamePad;
  BeakXboxController _operatorGamepad;

	//=====================================================================================
	// Define Singleton Pattern
	//=====================================================================================
	private static OI _instance = new OI();
	
	public static OI getInstance() {
		return _instance;
	}
	
	// private constructor for singleton pattern
  public OI() {
    _driverGamePad = new BeakXboxController(RobotMap.DRIVERS_STATION_DRIVER_GAMEPAD_USB_PORT);
		_operatorGamepad = new BeakXboxController(RobotMap.DRIVERS_STATION_OPERATOR_GAMEPAD_USB_PORT);
		
		// ================================
		// Driver GamePad Commands
		// ================================
		_driverGamePad.leftStick.whileActive(new Chassis_DriveWithJoySticks(_driverGamePad.leftStick, _driverGamePad.rightStick));
		_driverGamePad.rightStick.whileActive(new Chassis_DriveWithJoySticks(_driverGamePad.leftStick, _driverGamePad.rightStick));
 
		_driverGamePad.leftStick.whenReleased(new Chassis_DriveWithJoySticks(_driverGamePad.leftStick, _driverGamePad.rightStick));
		_driverGamePad.rightStick.whenReleased(new Chassis_DriveWithJoySticks(_driverGamePad.leftStick, _driverGamePad.rightStick));

		_driverGamePad.a.whenPressed(new Infeed_Homing(true));
		_driverGamePad.b.whenPressed(new Motion_Magic_Infeed_Pos(90));
		_driverGamePad.x.whenPressed(new Motion_Magic_Infeed_Pos(45));
		_driverGamePad.y.whenPressed(new Motion_Magic_Infeed_Pos(180));
		
	
		// ================================
		// Operator GamePad Commands
		// ================================
	}
}
