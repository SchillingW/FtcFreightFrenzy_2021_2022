package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

// autonomous OpMode for Babi
@Autonomous(name="AutonomousBabiNoWobble", group="UltimateGoal")
public class AutoBabiNoWobble extends AutoNoWobble {

    @Override
    public Robot getRobot() {
        return RobotTypes.newBabi(hardwareMap, telemetry);
    }

    @Override
    public void shootAllRings() {
        encoderShoot(2);
        encoderShoot(2);
        encoderShoot(2);
    }
}
