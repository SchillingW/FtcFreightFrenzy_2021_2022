package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

// autonomous OpMode for Rob3
@Autonomous(name="AutonomousRob3PowerShots", group="UltimateGoal")
public class AutoRob3PowerShots extends AutoPowerShots {

    @Override
    public Robot getRobot() {
        return RobotTypes.newRob3(hardwareMap, telemetry);
    }

    @Override
    public void shootAllRings() {
        encoderShoot(2);
        encoderDriveHorizontal(targetSpacing);
        encoderShoot(2);
        encoderDriveHorizontal(targetSpacing);
        encoderShoot(2);
    }
}
