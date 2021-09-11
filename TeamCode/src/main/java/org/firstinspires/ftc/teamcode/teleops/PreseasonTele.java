package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.GameController;
import org.firstinspires.ftc.teamcode.botconfigs.PreseasonBot;

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

        // output input telemetry
        telemetry.addData("left input", pad.doubleInputs[0][pad.stickLY]);
        telemetry.addData("right input", pad.doubleInputs[0][pad.stickRY]);

        // set drive train power with controller x, y, and rotational input
        bot.driveTrain.run(pad.doubleInputs[0][pad.stickLY], pad.doubleInputs[0][pad.stickRY]);

        // push telemetry debugging
        telemetry.update();
    }
}
