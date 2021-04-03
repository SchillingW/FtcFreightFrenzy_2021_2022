package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

// tele OpMode for Fuji
@TeleOp(name="TeleOpFuji", group="UltimateGoal")
public class TeleFuji extends Tele {

    @Override
    public Robot getRobot() {
        return RobotTypes.newFuji(hardwareMap, telemetry);
    }
}
