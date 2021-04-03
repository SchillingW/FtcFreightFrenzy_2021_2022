package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

// autonomous OpMode for Rob3
@Autonomous(name="AutonomousRob3", group="UltimateGoal")
public class AutoRob3 extends Auto {

    @Override
    public Robot getRobot() {
        return RobotTypes.newRob3(hardwareMap, telemetry);
    }

    @Override
    public void shootAllRings() {
        encoderShoot(2);
        encoderShoot(2);
        encoderShoot(2);
    }
}
