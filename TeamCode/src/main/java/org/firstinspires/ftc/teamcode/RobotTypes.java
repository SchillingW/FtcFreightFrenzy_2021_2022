package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RobotTypes {

    public static Robot newFuji(HardwareMap map, Telemetry tele) {
        return new Robot(map, tele,
            new double[]{Robot.tprHdHex60, Robot.tprHdHex60, Robot.tprHdHex60, Robot.tprHdHex60,
                Robot.tprCoreHex, Robot.tprCoreHex, Robot.tprHdHex, Robot.tprNeveRest, 0},
            new boolean[]{false, false, false, false, false},
            new double[]{0.2, 0.5, 0.55, 0.51},
            true,
            false,
            true,
            false);
    }

    public static Robot newBabi(HardwareMap map, Telemetry tele) {
        return new Robot(map, tele,
            new double[]{Robot.tprHdHex20, Robot.tprHdHex20, Robot.tprHdHex20, Robot.tprHdHex20,
                Robot.tprCoreHex, 0, Robot.tprHdHex, Robot.tprCoreHex, 0},
            new boolean[]{true, true, true, true, false},
            new double[]{0.2, 0.5, 1, 0.51},
            true,
            false,
            true,
            false);
    }

    public static Robot newRob3(HardwareMap map, Telemetry tele) {
        return new Robot(map, tele,
            new double[]{Robot.tprHdHex20 * (30.0 / 36.0), Robot.tprHdHex20, Robot.tprHdHex20 * (30.0 / 36.0), Robot.tprHdHex20,
                Robot.tprHdHex, 0, Robot.tprHdHex, 0, Robot.tprHdHex},
            new boolean[]{false, false, true, true, false},
            new double[]{0.2, 0.5, 1, 0.51},
            false,
            true,
            false,
            false);
    }
}
