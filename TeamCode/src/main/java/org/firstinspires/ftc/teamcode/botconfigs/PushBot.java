package org.firstinspires.ftc.teamcode.botconfigs;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.hardwaresystems.MechDriveTrain;
import org.firstinspires.ftc.teamcode.hardwarewrap.DcMotorWrap;

// simple push bot with mechanum drive train
public class PushBot {

    // drive train properties
    public MechDriveTrain driveTrain;
    public String[] driveTrainNames = {"rf", "rb", "lf", "lb"};
    public double driveLinearSpeed = 1;
    public double driveTurnSpeed = 1;

    // init, get drive train
    public PushBot(HardwareMap map) {
        DcMotorWrap[] motors = new DcMotorWrap[4];
        for (int i = 0; i < motors.length; i++) {motors[i] = new DcMotorWrap(map, driveTrainNames[i], 1);}
        driveTrain = new MechDriveTrain(motors, driveLinearSpeed, driveTurnSpeed);
    }
}
