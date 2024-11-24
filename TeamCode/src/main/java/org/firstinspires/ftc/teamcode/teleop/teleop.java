package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="teleop please run this", group="Iterative OpMode")
public class teleop extends OpMode
{
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor FLM = null;
    private DcMotor BLM = null;
    private DcMotor FRM = null;
    private DcMotor BRM = null;
    boolean invertX = false;
    boolean invertY = false;
    double computedX = 0;
    double computedY = 0;
    double deadZone = 0;
    double saturation = 0;
    double sensitivity = 0;
    double range = 0;

    @Override
    public void init() {

        telemetry.addData("Status", "Initialized");
        FLM = hardwareMap.get(DcMotor.class, "FLM");
        BLM = hardwareMap.get(DcMotor.class, "BLM");
        FRM = hardwareMap.get(DcMotor.class, "FRM");
        BRM = hardwareMap.get(DcMotor.class, "BRM");


        FLM.setDirection(DcMotor.Direction.FORWARD);
        BLM.setDirection(DcMotor.Direction.FORWARD);
        FRM.setDirection(DcMotor.Direction.REVERSE);
        BRM.setDirection(DcMotor.Direction.REVERSE);



    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
        runtime.reset();
    }

    private void computeCoordinates() {

        // Convert to polar coordinates
        double r = coerceValue(Math.sqrt((gamepad1.left_stick_x * gamepad1.left_stick_x) + (gamepad1.left_stick_y * gamepad1.left_stick_y)), -1.0, 1.0); // Radius
        double a = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x); // Angle (in radians)

        // Apply modifiers
        double value = computeModifiers(r);

        // Convert back to Cartesian coordinates
        double x = value * Math.cos(a);
        double y = value * Math.sin(a);

        // Apply axis-independent modifiers
        if (invertX) x = -x;
        if (invertY) y = -y;

        // Store the computed values
        computedX = x;
        computedY = y;
    }

    private double computeModifiers(double value) {
        // Apply dead-zone and saturation
        if (deadZone > 0.0 || saturation < 1.0) {
            double edgeSpace = (1 - saturation) + deadZone;
            if (edgeSpace < 1.0) {
                double multiplier = 1.0 / (1.0 - edgeSpace);
                value = (value - deadZone) * multiplier;
                value = coerceValue(value, 0.0, 1.0);
            } else {
                value = Math.round(value);
            }
        }

        // Apply sensitivity
        if (sensitivity != 0.0) {
            value = value + ((value - Math.sin(value * (Math.PI / 2))) * (sensitivity * 2));
            value = coerceValue(value, 0.0, 1.0);
        }

        // Apply range
        if (range < 1.0) {
            value = value * range;
        }

        // Return the calculated value
        return coerceValue(value, -1.0, 1.0);
    }

    private double coerceValue(double value, double min, double max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }




    public void move_func(){
        double y = -gamepad1.left_stick_y; // Remember, Y stick is reversed!
        double x = gamepad1.left_stick_x;
        double rx = gamepad1.right_stick_x;

        FLM.setPower(y - x - rx);
        BLM.setPower(y + x - rx);
        FRM.setPower(y + x + rx);
        BRM.setPower(y - x + rx);

    }

    @Override
    public void loop() {
        move_func();
    }
    @Override
    public void stop() {
    }

}