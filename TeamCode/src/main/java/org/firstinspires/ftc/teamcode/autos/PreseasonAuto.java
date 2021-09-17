package org.firstinspires.ftc.teamcode.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.botconfigs.PreseasonBot;

// linear drive train autonomous for preseason game
@Autonomous(name="PreseasonAuto", group="FreightFrenzy")
public class PreseasonAuto extends LinearOpMode {

    // robot reference
    public PreseasonBot bot;

    // run the autonomous
    @Override
    public void runOpMode() {

        // initialize robot
        bot = new PreseasonBot(this, telemetry, hardwareMap);

        // wait for user to press play
        waitForStart();

        // move forward 3 feet (36 inches)
        bot.driveTrain.moveEncoders(36, 0.3, true);

        // wait 1 second to avoid movement overlap
        sleep(1000);

        // correct angle using gyro sensor to avoid drift
        bot.normalizeGyro(0, 0.2);

        // wait 1 second to avoid movement overlap
        sleep(1000);

        // turn clockwise 90 degrees (a quarter rotation)
        bot.driveTrain.moveEncoders(0.25, 0.3, false);

        // wait 1 second to avoid movement overlap
        sleep(1000);

        // correct angle using gyro sensor to avoid drift
        bot.normalizeGyro(0.25, 0.2);

        // wait 1 second to avoid movement overlap
        sleep(1000);

        // move forward 6 feet (72 inches)
        bot.driveTrain.moveEncoders(72, 0.3, true);

        // wait 1 second to avoid movement overlap
        sleep(1000);

        // correct angle using gyro sensor to avoid drift
        bot.normalizeGyro(0.25, 0.2);

        // wait 1 second to avoid movement overlap
        sleep(1000);
    }
}
