package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

// tele OpMode for Rob3
@TeleOp(name="TeleOpRob3", group="UltimateGoal")
public class TeleRob3 extends Tele {

    @Override
    public Robot getRobot() {
        return RobotTypes.newRob3(hardwareMap, telemetry);
    }
}
