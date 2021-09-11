package org.firstinspires.ftc.teamcode.hardwarewrap;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

// base wrapper class for CrServo device
public class CrServoWrap {

    // telemtry device for debugging
    public Telemetry tele;

    // servo reference
    public CRServo servo;

    // name in hardwareMap
    public String name;

    // base servo speed
    public double speed;

    // init, get servo from HardwareMap
    public CrServoWrap(Telemetry tele, HardwareMap map, String name, double speed) {

        this.tele = tele;

        servo = map.get(CRServo.class, name);
        this.name = name;
        this.speed = speed;
    }

    // run at constant power
    public void run(double speed) {
        servo.setPower(speed * this.speed);
    }
}