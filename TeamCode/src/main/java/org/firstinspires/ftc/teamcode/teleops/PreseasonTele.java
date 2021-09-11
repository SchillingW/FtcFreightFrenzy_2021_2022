package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.GameController;
import org.firstinspires.ftc.teamcode.botconfigs.PreseasonBot;
import org.firstinspires.ftc.teamcode.hardwaresystems.ClawedHinge;

// linear drive train tele op for preseason game
@TeleOp(name="PreseasonTele", group="FreightFrenzy")
public class PreseasonTele extends OpMode {

    // robot reference
    public PreseasonBot bot;

    // controller reference
    public GameController pad;

    // init, get robot and controller
    @Override
    public void init() {
        bot = new PreseasonBot(telemetry, hardwareMap);
        pad = new GameController(new Gamepad[]{gamepad1, gamepad2});
    }

    // loop every frame
    @Override
    public void loop() {

        // update game controller input
        pad.update();

        // get input
        double driveInputL = pad.doubleInputs[0][pad.stickLY];
        double driveInputR = pad.doubleInputs[0][pad.stickRY];
        double armHinge = pad.doubleInputs[1][pad.stickLY];
        double armClaw = pad.doubleInputs[1][pad.stickRY];

        // output input telemetry
        telemetry.addData("left input", driveInputL);
        telemetry.addData("right input", driveInputR);
        telemetry.addData("hinge input", armHinge);
        telemetry.addData("arm input", armClaw);

        // set drive train power with controller 1 y input
        bot.driveTrain.run(driveInputL, driveInputR);

        // set hinge power with controller 2 left stick y input
        bot.arm.hinge.run(armHinge);

        // set claw position with controller 2 right stick y input
        if (armClaw > 0.5) {
            bot.arm.claw.run(1);
        }
        else if(armClaw < -0.5) {
            bot.arm.claw.run(0);
        }

        // push telemetry debugging
        telemetry.update();
    }
}
