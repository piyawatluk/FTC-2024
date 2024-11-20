package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Arm", group="Iterative OpMode")
public class test_arm extends OpMode

{
    private Servo sv_1;
    private Servo sv_3;
    private CRServo sv_4;
    private static final double INCREMENT = 0.01;
    private static final double MAX_POSITION = 1.0;
    private static final double MIN_POSITION = 0.0;
    private double servo_pos = 0.5;

    private static final double in_speed = 1.0;
    private static final double out_speed = -1.0;

    @Override
    public void init(){
        sv_1 = hardwareMap.get(Servo.class, "sv_1");
        sv_3 = hardwareMap.get(Servo.class, "sv_3");
        sv_4 = hardwareMap.get(CRServo.class, "sv_4");

        sv_1.setPosition(servo_pos);
        sv_3.setPosition(servo_pos);

        telemetry.addData("Status", "Initialized");
    }
    @Override
    public void loop(){
        if(gamepad1.dpad_up){
            servo_pos += INCREMENT;
        }
        else if(gamepad1.dpad_down){
            servo_pos -= INCREMENT;
        }

        servo_pos = Math.max(MIN_POSITION, Math.min(MAX_POSITION, servo_pos));

        sv_1.setPosition(servo_pos);
        sv_3.setPosition(servo_pos);

        if(gamepad1.left_bumper){
            sv_4.setPower(in_speed);
        }
        else if(gamepad1.left_trigger > 0.1){
            sv_4.setPower(out_speed);
        }
        else{
            sv_4.setPower(0);
        }

        telemetry.addData("Arm Position", servo_pos);
        telemetry.update();

    }
}