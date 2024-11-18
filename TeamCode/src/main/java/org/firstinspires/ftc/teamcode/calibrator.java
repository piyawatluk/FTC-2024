package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

@TeleOp(name="servo calibrate", group="Iterative Opmode")
public class calibrator extends OpMode
{
    Servo servo_1, servo_2, servo_3, servo_4;



    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void init() {

        telemetry.addData("Status", "Initialized");

        servo_1 = hardwareMap.get(Servo.class, "sv_1");
        servo_2 = hardwareMap.get(Servo.class, "sv_2");
        servo_3 = hardwareMap.get(Servo.class, "sv_3");
        servo_4 = hardwareMap.get(Servo.class, "sv_4");

    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
        runtime.reset();
    }


    @Override
    public void loop() {
        servo_1.setPosition(1);
        servo_2.setPosition(1);
        servo_3.setPosition(1);
        servo_4.setPosition(1);


    }
    @Override
    public void stop() {
    }

}