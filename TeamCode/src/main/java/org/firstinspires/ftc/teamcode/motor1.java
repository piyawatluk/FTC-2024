package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "motor1 (Blocks to Java)")
public class motor1 extends LinearOpMode {

    private DcMotor rm;
    private DcMotor lm;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        rm = hardwareMap.get(DcMotor.class, "rm");
        lm = hardwareMap.get(DcMotor.class, "lm");

        // Reverse one of the drive motors.
        // You will have to determine which motor to reverse for your robot.
        // In this example, the right motor was reversed so that positive
        // applied power makes it move the robot in the forward direction.
        rm.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.
            while (opModeIsActive()) {
                // Put loop blocks here.
                // The Y axis of a joystick ranges from -1 in its topmost position
                // to +1 in its bottommost position. We negate this value so that
                // the topmost position corresponds to maximum forward power.
                lm.setPower(-gamepad1.left_stick_y);
                rm.setPower(-gamepad1.right_stick_y);
                telemetry.addData("Left Pow", lm.getPower());
                telemetry.addData("Right Pow", rm.getPower());
                telemetry.update();
            }
        }
    }
}