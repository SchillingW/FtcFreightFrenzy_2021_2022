package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

// tele OpMode for Babi
@TeleOp(name="TeleOpBabi", group="UltimateGoal")
public class TeleBabi extends Tele {

    @Override
    public Robot getRobot() {
        return RobotTypes.newBabi(hardwareMap, telemetry);
    }
}
