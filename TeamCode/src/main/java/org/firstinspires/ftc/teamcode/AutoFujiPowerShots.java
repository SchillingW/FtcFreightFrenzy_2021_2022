package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

// autonomous OpMode for Fuji
@Autonomous(name="AutonomousFujiPowerShots", group="UltimateGoal")
public class AutoFujiPowerShots extends AutoPowerShots {

    @Override
    public Robot getRobot() {
        return RobotTypes.newFuji(hardwareMap, telemetry);
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
