package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

// autonomous OpMode for Fuji
@Autonomous(name="AutonomousFujiNoWobble", group="UltimateGoal")
public class AutoFujiNoWobble extends AutoNoWobble {

    @Override
    public Robot getRobot() {
        return RobotTypes.newFuji(hardwareMap, telemetry);
    }

    @Override
    public void shootAllRings() {
        encoderShoot(2);
        if (robot.collector != null) {robot.collector.setPower(robot.collectorPolarity ? 1 : -1);}
        encoderShoot(2);
        if (robot.collector != null) {robot.collector.setPower(0);}
    }
}
