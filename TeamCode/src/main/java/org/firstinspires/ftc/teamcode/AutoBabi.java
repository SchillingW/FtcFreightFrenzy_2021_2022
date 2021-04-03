package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

// autonomous OpMode for Babi
@Autonomous(name="AutonomousBabi", group="UltimateGoal")
public class AutoBabi extends Auto {

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
