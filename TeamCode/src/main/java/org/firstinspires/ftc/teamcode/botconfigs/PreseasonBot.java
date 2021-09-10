package org.firstinspires.ftc.teamcode.botconfigs;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.hardwaresystems.LinearDriveTrain;
import org.firstinspires.ftc.teamcode.hardwarewrap.DcMotorWrap;

public class PreseasonBot {

    // drive train properties
    public LinearDriveTrain driveTrain;
    public String driveNameL = "l";
    public String driveNameR = "r";
    public double driveLinearSpeed = 1;
    public double driveTurnSpeed = 1;

    // init, get drive train
    public PreseasonBot(HardwareMap map) {
        DcMotorWrap driveL = new DcMotorWrap(map, driveNameL, 3, 1, 1, 1680);
        DcMotorWrap driveR = new DcMotorWrap(map, driveNameR, 3, 1, 1, 1680);
        driveTrain = new LinearDriveTrain(new DcMotorWrap[]{driveL, driveR}, driveLinearSpeed, driveTurnSpeed, 8);
    }
}
