package org.firstinspires.ftc.teamcode.botconfigs;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
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
    public PreseasonBot(Telemetry tele, HardwareMap map) {
        DcMotorWrap driveL = new DcMotorWrap(tele, map, driveNameL, 3.5, 1, 1, 288);
        DcMotorWrap driveR = new DcMotorWrap(tele, map, driveNameR, 3.5, 1, 1, 288);
        driveTrain = new LinearDriveTrain(tele, new DcMotorWrap[]{driveL, driveR}, driveLinearSpeed, driveTurnSpeed, 8);
    }
}
