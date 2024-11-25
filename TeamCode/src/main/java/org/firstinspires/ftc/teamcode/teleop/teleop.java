package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="teleop please run this", group="Iterative OpMode")
public class teleop extends OpMode
{
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor FLM = null;
    private DcMotor BLM = null;
    private DcMotor FRM = null;
    private DcMotor BRM = null;

    // Control variables
    boolean invertX = false;
    boolean invertY = true;
    double deadZone = 0.1;
    double saturation = 1;
    double sensitivity = -0.5;
    double range = 1;

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        // Initialize motors
        FLM = hardwareMap.get(DcMotor.class, "FLM");
        BLM = hardwareMap.get(DcMotor.class, "BLM");
        FRM = hardwareMap.get(DcMotor.class, "FRM");
        BRM = hardwareMap.get(DcMotor.class, "BRM");

        // Set motor directions
        FLM.setDirection(DcMotor.Direction.REVERSE);
        BLM.setDirection(DcMotor.Direction.REVERSE);
        FRM.setDirection(DcMotor.Direction.FORWARD);
        BRM.setDirection(DcMotor.Direction.FORWARD);
    }

    @Override
    public void init_loop() {}

    @Override
    public void start() {
        runtime.reset();
    }

    // This function will calculate the motor powers for mecanum drive.
    public void move_func(){
        // Update joystick inputs in each loop cycle
        double computedX_l = cordinate_converter.computeX(gamepad1.left_stick_x, gamepad1.left_stick_y, deadZone, saturation, sensitivity, range, invertX, invertY);
        double computedY_l = cordinate_converter.computeY(gamepad1.left_stick_x, gamepad1.left_stick_y, deadZone, saturation, sensitivity, range, invertX, invertY);
        double computedX_r = cordinate_converter.computeX(gamepad2.right_stick_x, gamepad2.right_stick_y, deadZone, saturation, sensitivity, range, invertX, invertY);

        // Mecanum drive equations to calculate motor powers
        double frontLeft = computedY_l + computedX_l + computedX_r;
        double frontRight = computedY_l - computedX_l - computedX_r;
        double backLeft = computedY_l - computedX_l + computedX_r;
        double backRight = computedY_l + computedX_l - computedX_r;

        // Apply motor power values
        FLM.setPower(frontLeft);
        FRM.setPower(frontRight);
        BLM.setPower(backLeft);
        BRM.setPower(backRight);
    }

    @Override
    public void loop() {
        move_func();  // Update motor powers in each loop cycle
    }

    @Override
    public void stop() {}
}
