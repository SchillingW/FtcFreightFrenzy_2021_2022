package org.firstinspires.ftc.teamcode.hardwaresystems;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.hardwarewrap.DcMotorWrap;
import org.firstinspires.ftc.teamcode.hardwarewrap.ServoWrap;

public class ClawedHinge {

    // telemetry device for debugging
    public Telemetry tele;

    // motor references
    public DcMotorWrap hinge;
    public ServoWrap claw;

    // init, get hardware devices
    public ClawedHinge(Telemetry tele, DcMotorWrap hinge, ServoWrap claw) {
        this.tele = tele;
        this.hinge = hinge;
        this.claw = claw;
    }
}
