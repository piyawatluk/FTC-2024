package org.firstinspires.ftc.teamcode.calibrator;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="servo calibrate", group="Iterative Opmode")
public class calibrator extends OpMode
{
    Servo sv_1, sv_2, sv_3;
    DcMotor extender_L, extender_R;
    DigitalChannel ls = null;



    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void init() {

        telemetry.addData("Status", "Initialized");

        sv_1 = hardwareMap.get(Servo.class, "sv_1");
        sv_2 = hardwareMap.get(Servo.class, "sv_2");
        sv_3 = hardwareMap.get(Servo.class, "sv_3");

        extender_L = hardwareMap.get(DcMotor.class, "et_1");
        extender_R = hardwareMap.get(DcMotor.class, "et_2");

        ls = hardwareMap.get(DigitalChannel.class, "ls");
        ls.setMode(DigitalChannel.Mode.INPUT);

        extender_L.setDirection(DcMotor.Direction.FORWARD);
        extender_R.setDirection(DcMotor.Direction.REVERSE);
        extender_L.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        extender_R.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);


    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
        runtime.reset();
    }

    public boolean rightmotor = true;

    @Override
    public void loop() {
        DcMotor currentmotor;

        if(rightmotor){
            currentmotor = extender_R;
            telemetry.addData("Active Motor", "Right Motor");
        }
        else{
            currentmotor = extender_L;
            telemetry.addData("Active Motor", "Left Motor");
        }

        double drive = -gamepad1.left_stick_y;


        currentmotor.setPower(drive);


        if(gamepad1.dpad_up){
            sv_1.setPosition(1);
            sv_2.setPosition(1);
            sv_3.setPosition(0);
        }

        if(gamepad1.dpad_down){
            sv_1.setPosition(0);
            sv_2.setPosition(0);
            sv_3.setPosition(1);
        }

        if (!ls.getState()){
            telemetry.addLine("limit switch is press");
        }

        telemetry.addData("sv_1", sv_1.getPosition());
        telemetry.addData("sv_2", sv_2.getPosition());
        telemetry.addData("sv_3", sv_3.getPosition());
        telemetry.addData("Pos_L", extender_L.getCurrentPosition());
        telemetry.addData("Pos_R", extender_R.getCurrentPosition());
        telemetry.update();

    }
    @Override
    public void stop() {
    }

}