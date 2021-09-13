package org.firstinspires.ftc.teamcode.botconfigs;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.hardwaresystems.ClawedHinge;
import org.firstinspires.ftc.teamcode.hardwaresystems.LinearDriveTrain;
import org.firstinspires.ftc.teamcode.hardwarewrap.DcMotorWrap;
import org.firstinspires.ftc.teamcode.hardwarewrap.GyroWrap;
import org.firstinspires.ftc.teamcode.hardwarewrap.ServoWrap;

public class PreseasonBot {

    // drive train properties
    public LinearDriveTrain driveTrain;
    public String driveNameL = "l";
    public String driveNameR = "r";
    public double driveLinearSpeed = 1;
    public double driveTurnSpeed = 1;

    // arm properties
    public ClawedHinge arm;
    public String hingeName = "hinge";
    public String clawName = "claw";

    // gyro sensor properties
    public GyroWrap gyro;
    public String gyroName = "gyro";

    // init, get hardware devices
    public PreseasonBot(LinearOpMode op, Telemetry tele, HardwareMap map) {

        // init drive train
        DcMotorWrap driveL = new DcMotorWrap(tele, map, driveNameL, 3.5, 1, 1, 288);
        DcMotorWrap driveR = new DcMotorWrap(tele, map, driveNameR, 3.5, 1, 1, 288);
        driveTrain = new LinearDriveTrain(tele, new DcMotorWrap[]{driveL, driveR}, driveLinearSpeed, driveTurnSpeed, 8);

        // init arm
        DcMotorWrap hinge = new DcMotorWrap(tele, map, hingeName, 1, 1, 1, 288);
        ServoWrap claw = new ServoWrap(tele, map, clawName, 0, 1);
        arm = new ClawedHinge(tele, hinge, claw);

        // init gyro
        gyro = new GyroWrap(op, map, gyroName);
    }

    public void normalizeGyro(double target, double speed) {
        driveTrain.moveEncoders(target - (gyro.getAngle() / 360), speed, false);
    }
}
