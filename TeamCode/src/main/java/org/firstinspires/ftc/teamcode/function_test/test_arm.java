package org.firstinspires.ftc.teamcode.function_test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Arm", group="Iterative OpMode")
public class test_arm extends OpMode

{
    private Servo sv_1;
    private Servo sv_3;
    private CRServo sv_4;
    private static final double in_speed = 1.0;
    private static final double out_speed = -1.0;

    private DcMotor extender_L = null;
    private DcMotor extender_R = null;

    private DigitalChannel ls = null;
    int d_pad_count = 0;
    double pos1 = 45;
    double pos2 = 90;
    double pos3 = 135/180;
    int pos_L = 0;

    @Override
    public void init(){
        sv_1 = hardwareMap.get(Servo.class, "sv_1");
        sv_3 = hardwareMap.get(Servo.class, "sv_3");
        sv_4 = hardwareMap.get(CRServo.class, "sv_4");

        sv_4.setPower(0);

        extender_L = hardwareMap.get(DcMotor.class, "et_1");
        extender_R = hardwareMap.get(DcMotor.class, "et_2");


        ls = hardwareMap.get(DigitalChannel.class, "ls");
        ls.setMode(DigitalChannel.Mode.INPUT);

        extender_L.setDirection(DcMotor.Direction.FORWARD);
        extender_R.setDirection(DcMotor.Direction.REVERSE);

        extender_L.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extender_R.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        extender_L.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extender_R.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Status", "Initialized");
    }

    public void extender_func(){
        double power;
        pos_L = extender_L.getCurrentPosition(); // only ref from left side

        if(gamepad1.x == true){
            extender_L.setTargetPosition(6200);
            extender_R.setTargetPosition(6200);

            extender_L.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            extender_R.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            extender_L.setPower(1);
            extender_R.setPower(1);
        }

        else if (gamepad1.b == true){
            extender_L.setTargetPosition(0);
            extender_R.setTargetPosition(0);

            extender_L.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            extender_R.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            extender_L.setPower(-1);
            extender_R.setPower(-1);

        }

        else if (gamepad1.a == true){
            extender_L.setTargetPosition(3100);
            extender_R.setTargetPosition(3100);

            extender_L.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            extender_R.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            if (pos_L < 3100){
                extender_L.setPower(1);
                extender_R.setPower(1);
            }

            else{

                extender_L.setPower(-1);
                extender_R.setPower(-1);

            }

            extender_L.setPower(-1);
            extender_R.setPower(-1);
        }

        else if (ls.getState() == false){

            extender_L.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            extender_R.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            extender_L.setPower(0);
            extender_R.setPower(0);

        }


    }

    @Override
    public void loop(){

        extender_func();

        boolean wasDpadUpPressed = false;
        boolean wasDpadDownPressed = false;

        // Detect D-pad up button press to increment d_pad_count
        if (gamepad2.dpad_up && !wasDpadUpPressed) {
            d_pad_count++;
        }

        // Detect D-pad down button press to decrement d_pad_count
        if (gamepad2.dpad_down && !wasDpadDownPressed) {
            d_pad_count--;
        }

        // Store the current state of the buttons to track them in the next loop
        wasDpadUpPressed = gamepad2.dpad_up;
        wasDpadDownPressed = gamepad2.dpad_down;

        // Logic to set the servo position based on d_pad_count
        if (d_pad_count == 0) {
            sv_1.setPosition(pos1 / 180);  // First position
        } else if (d_pad_count == 1) {
            sv_1.setPosition(pos2 / 180);  // Second position
        } else if (d_pad_count == 2) {
            sv_1.setPosition(pos3 / 180);  // Third position
        }


        if(gamepad2.left_bumper){
            sv_4.setPower(in_speed);
        }

        else if(gamepad2.left_trigger > 0.1){
            sv_4.setPower(out_speed);
        }

        telemetry.addData("Pos", pos_L);
        telemetry.update();

    }
}