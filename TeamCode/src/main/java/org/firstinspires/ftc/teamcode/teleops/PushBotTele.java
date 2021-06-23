package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.GameController;
import org.firstinspires.ftc.teamcode.botconfigs.PushBot;

@TeleOp(name="PushBotTele", group="FreightFrenzy")
public class PushBotTele extends OpMode {

    public PushBot bot;
    public GameController pad;

    @Override
    public void init() {
        bot = new PushBot(hardwareMap);
        pad = new GameController(new Gamepad[]{gamepad1, gamepad2});
    }

    @Override
    public void loop() {
        pad.update();
        bot.driveTrain.run(pad.doubleInputs[0][pad.stickLX], pad.doubleInputs[0][pad.stickLY], pad.doubleInputs[0][pad.stickRX]);
    }
}
