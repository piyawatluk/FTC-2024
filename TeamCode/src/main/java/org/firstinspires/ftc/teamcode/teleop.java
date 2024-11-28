package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.cordinate_converter;

@TeleOp(name="teleop please run this", group="Iterative OpMode")
public class teleop extends OpMode
{
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor FLM = null;
    private DcMotor BLM = null;
    private DcMotor FRM = null;
    private DcMotor BRM = null;

    private Servo sv_1, sv_3, sv_2;
    private CRServo sv_4;
    private DcMotor extender_L = null;
    private DcMotor extender_R = null;
    private DigitalChannel ls = null;

    //setting intake speed
    private double in_speed = 1.0;
    private double out_speed = -1.0;

    //setting arm servo position in degree
    double pos4 = (double) 180 / 180;
    double pos3 = (double) 90 / 180;
    double pos2 = (double) 15 / 180;
    double pos1 = (double) 0 / 180;

    // Control variables
    boolean invertX = false;
    boolean invertY = true;
    double deadZone = 0.1;
    double saturation = 1;
    double sensitivity = 0.5;
    double range = 1;

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        // Initialize motors
        FLM = hardwareMap.get(DcMotor.class, "FLM");
        BLM = hardwareMap.get(DcMotor.class, "BLM");
        FRM = hardwareMap.get(DcMotor.class, "FRM");
        BRM = hardwareMap.get(DcMotor.class, "BRM");

        //arm section
        sv_1 = hardwareMap.get(Servo.class, "sv_1");
        sv_2 = hardwareMap.get(Servo.class, "sv_2");
        sv_3 = hardwareMap.get(Servo.class, "sv_3");
        sv_4 = hardwareMap.get(CRServo.class, "sv_4");

        extender_L = hardwareMap.get(DcMotor.class, "et_1");
        extender_R = hardwareMap.get(DcMotor.class, "et_2");
        ls = hardwareMap.get(DigitalChannel.class, "ls");

        //initially stop the intake motor
        sv_4.setPower(0);

        //hardware behavior declaration for lift system
        ls.setMode(DigitalChannel.Mode.INPUT);

        extender_L.setDirection(DcMotor.Direction.FORWARD);
        extender_R.setDirection(DcMotor.Direction.REVERSE);

        extender_L.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extender_R.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        extender_L.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extender_R.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Set motor directions
        FLM.setDirection(DcMotor.Direction.REVERSE);
        BLM.setDirection(DcMotor.Direction.REVERSE);
        FRM.setDirection(DcMotor.Direction.FORWARD);
        BRM.setDirection(DcMotor.Direction.FORWARD);
        extender_func(2200);
        sv_1.setPosition(pos4);
        sv_3.setPosition(1 - pos4);
    }

    @Override
    public void init_loop() {}

    @Override
    public void start() {
        runtime.reset();

    }

    public int extender_func(int targetPosition) { // Takes targetPosition as input
        int pos_L = extender_L.getCurrentPosition(); // References encoder position from the left side
        int pos_R = extender_R.getCurrentPosition(); // References encoder position from the left side
        int pos = ((pos_L + pos_R)/2);

        // Set target positions for both motors
        extender_L.setTargetPosition(targetPosition);
        extender_R.setTargetPosition(targetPosition);

        extender_L.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extender_R.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Adjust power based on current position relative to the target
        if (pos < targetPosition) {
            extender_L.setPower(1);
            extender_R.setPower(1);
        } else if (pos > targetPosition) {
            extender_L.setPower(-1);
            extender_R.setPower(-1);
        } else { // Target reached
            extender_L.setPower(0);
            extender_R.setPower(0);
        }

        // Handle limit switch: stop the motors if the limit switch is pressed
        if (!ls.getState()) {
            extender_L.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            extender_R.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            extender_L.setPower(0);
            extender_R.setPower(0);
        }

        return pos_L; // Return the current position of extender_L
    }

    private void movement_presets() {

        if(gamepad2.x){
            extender_func(2200);
            sv_1.setPosition(pos4);
            sv_3.setPosition(1.0 - pos4); //start
        }
        else if(gamepad2.y){
            extender_func(0);
            sv_1.setPosition(pos3);
            sv_3.setPosition(1.0 - pos3); //sample collect
        }
        else if(gamepad2.b){
            extender_func(50);
            sv_1.setPosition(pos1);
            sv_3.setPosition(1.0 - pos1); //specimen collect
        }
        else if(gamepad2.a){
            extender_func(6200);
            sv_1.setPosition(pos2);
            sv_3.setPosition(1.0 - pos2); //high basket
        }
        else if(gamepad2.dpad_down){
            extender_func(1000);
            sv_1.setPosition(pos2);
            sv_3.setPosition(1.0 - pos2); //low basket
        }
        else if(gamepad2.dpad_left){
            extender_func(1000);
            sv_1.setPosition(pos1);
            sv_3.setPosition(1.0 - pos1); //high chamber
        }
        else if(gamepad2.dpad_right){
            extender_func(1000);
            sv_1.setPosition(pos1);
            sv_3.setPosition(1.0 - pos1); //low chamber
        }
        else if(gamepad2.dpad_up){
            extender_func(1000);
            sv_1.setPosition(pos1);
            sv_3.setPosition(1.0 - pos1); //ascend
        }
    }

    private void controlCRServo() {
        if (gamepad2.left_bumper) {
            sv_4.setPower(1);
        } else if (gamepad2.left_trigger > 0.1) {
            sv_4.setPower(-1);
        } else {
            sv_4.setPower(0); // Stop servo if no input
            telemetry.addLine("nuh uh");
        }
    }

    private void gripper(){
        if (gamepad2.right_bumper){
            sv_2.setPosition(0);
        } else if (gamepad2.right_trigger > 0.5) {
            sv_2.setPosition(0.3);
        }
    }

    public void move_func(){
        // Update joystick inputs in each loop cycle
        double computedX_l = cordinate_converter.computeX(gamepad1.left_stick_x, gamepad1.left_stick_y, deadZone, saturation, sensitivity, range, invertX, invertY);
        double computedY_l = cordinate_converter.computeY(gamepad1.left_stick_x, gamepad1.left_stick_y, deadZone, saturation, sensitivity, range, invertX, invertY);
        double computedX_r = cordinate_converter.computeX(gamepad1.right_stick_x, gamepad1.right_stick_y, deadZone, saturation, sensitivity, range, invertX, invertY);

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
        movement_presets();
        controlCRServo();
        gripper();

        telemetry.addData("Pos_L", extender_L.getCurrentPosition());
        telemetry.addData("Pos_R", extender_R.getCurrentPosition());
        telemetry.update();
    }

    @Override
    public void stop() {}
}
