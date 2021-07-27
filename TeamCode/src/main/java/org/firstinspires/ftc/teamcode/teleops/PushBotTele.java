package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.GameController;
import org.firstinspires.ftc.teamcode.botconfigs.PushBot;

// mech drive train tele op for PushBot
@TeleOp(name="PushBotTele", group="FreightFrenzy")
public class PushBotTele extends OpMode {

    // robot reference
    public PushBot bot;

    // controller reference
    public GameController pad;

    // init, get robot and controller
    @Override
    public void init() {
        bot = new PushBot(null, hardwareMap);
        pad = new GameController(new Gamepad[]{gamepad1, gamepad2});
    }

    // loop every frame
    @Override
    public void loop() {

        // update game controller input
        pad.update();

        // set drive train power with controller x, y, and rotational input
        bot.driveTrain.run(pad.doubleInputs[0][pad.stickLX], pad.doubleInputs[0][pad.stickLY], pad.doubleInputs[0][pad.stickRX]);
    }
}
