package org.firstinspires.ftc.teamcode.calibrator;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="servo calibrate", group="Iterative Opmode")
public class calibrator extends OpMode
{
    Servo sv_1, sv_2, sv_3;
    CRServo sv_4;



    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void init() {

        telemetry.addData("Status", "Initialized");

        sv_1 = hardwareMap.get(Servo.class, "sv_1");
        sv_2 = hardwareMap.get(Servo.class, "sv_2");
        sv_3 = hardwareMap.get(Servo.class, "sv_3");
        sv_4 = hardwareMap.get(CRServo.class, "sv_4");

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
        sv_1.setPosition(1);
        sv_2.setPosition(1);
        sv_3.setPosition(1);
        sv_4.setPower(0);


    }
    @Override
    public void stop() {
    }

}