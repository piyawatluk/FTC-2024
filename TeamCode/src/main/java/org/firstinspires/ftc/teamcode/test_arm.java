package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

@Disabled
@TeleOp(name = "Arm", group = "Iterative OpMode")
public class test_arm extends OpMode

{
    //hardware name declaration :)
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
    double pos2 = (double) 15 / 200;
    double pos1 = (double) 0 / 180;

    @Override
    public void init() {

        //hardware pin name declaration
        sv_1 = hardwareMap.get(Servo.class, "sv_1");
        sv_2 = hardwareMap.get(Servo.class, "sv_2");
        sv_3 = hardwareMap.get(Servo.class, "sv_3");
        sv_4 = hardwareMap.get(CRServo.class, "sv_4");
        extender_L = hardwareMap.get(DcMotor.class, "et_1");
        extender_R = hardwareMap.get(DcMotor.class, "et_2");
        ls = hardwareMap.get(DigitalChannel.class, "ls");

        //initially stop the intake motor
        sv_4.setPower(0);

        //hardware behavior declaration
        ls.setMode(DigitalChannel.Mode.INPUT);

        extender_L.setDirection(DcMotor.Direction.FORWARD);
        extender_R.setDirection(DcMotor.Direction.REVERSE);

        extender_L.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extender_R.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        extender_L.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extender_R.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Status", "Initialized");
    }

    public int extender_func(int targetPosition) { // Takes targetPosition as input
        int pos_L = extender_L.getCurrentPosition(); // References encoder position from the left side

        // Set target positions for both motors
        extender_L.setTargetPosition(targetPosition);
        extender_R.setTargetPosition(targetPosition);

        extender_L.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extender_R.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Adjust power based on current position relative to the target
        if (pos_L < targetPosition) {
            extender_L.setPower(1);
            extender_R.setPower(1);
        } else if (pos_L > targetPosition) {
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



    private int dPadCount = 0;
    private boolean wasDpadUpPressed = false;
    private boolean wasDpadDownPressed = false;
    private void movement_presets() {
        // Handle D-pad up and down for servo position control
        if (gamepad2.dpad_up && !wasDpadUpPressed) {
            dPadCount = Math.min(dPadCount + 1, 3); // Cap at 1
        } else if (gamepad2.dpad_down && !wasDpadDownPressed) {
            dPadCount = Math.max(dPadCount - 1, 0); // Cap at 0
        }

        // Store button states for next loop
        wasDpadUpPressed = gamepad2.dpad_up;
        wasDpadDownPressed = gamepad2.dpad_down;

        // Set servo position based on dPadCount
        if (dPadCount == 2) {
            extender_func(50);
            sv_1.setPosition(pos1);
            sv_3.setPosition(1.0 - pos1);
        } else if (dPadCount == 1) {
            extender_func(6700);
            sv_1.setPosition(pos2);
            sv_3.setPosition(1.0 - pos2);
        } else if (dPadCount == 3) {
            extender_func(0);
            sv_1.setPosition(pos3);
            sv_3.setPosition(1.0 - pos3);
        } else if (dPadCount == 0) {
            extender_func(2200);
            sv_1.setPosition(pos4);
            sv_3.setPosition(1.0 - pos4);
        }

        telemetry.addData("dPadCount", dPadCount);
    }

    private void controlCRServo() {
        if (gamepad2.left_bumper) {
            sv_4.setPower(in_speed);
        } else if (gamepad2.left_trigger > 0.1) {
            sv_4.setPower(out_speed);
        } else {
            sv_4.setPower(0); // Stop servo if no input
        }
    }

    private void gripper(){
        if (gamepad2.right_bumper == true){
            sv_2.setPosition(0);
        } else if (gamepad2.right_trigger > 0.5) {
            sv_2.setPosition(0.3);
        }
    }

    @Override
    public void loop() {

        movement_presets();
        controlCRServo();
        gripper();

        telemetry.addData("Pos_L", extender_L.getCurrentPosition());
        telemetry.addData("Pos_R", extender_R.getCurrentPosition());
        telemetry.update();

    }
}