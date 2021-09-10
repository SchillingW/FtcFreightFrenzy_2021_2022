package org.firstinspires.ftc.teamcode.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.GameController;
import org.firstinspires.ftc.teamcode.botconfigs.PreseasonBot;

// linear drive train autonomous for preseason game
@Autonomous(name="PreseasonTele", group="FreightFrenzy")
public class PreseasonAuto extends LinearOpMode {

    // robot reference
    public PreseasonBot bot;

    // controller reference
    public GameController pad;

    // run the autonomous
    @Override
    public void runOpMode() {

        // initialize robot and controllers
        bot = new PreseasonBot(hardwareMap);
        pad = new GameController(new Gamepad[]{gamepad1, gamepad2});

        // wait for user to press play
        waitForStart();

        // move forward 3 feet (36 inches)
        bot.driveTrain.moveEncoders(36, 1, true);

        // turn clockwise 90 degrees (a quarter rotation)
        bot.driveTrain.moveEncoders(0.25, 1, false);

        // move forward 6 feet (72 inches)
        bot.driveTrain.moveEncoders(72, 1, true);
    }
}
