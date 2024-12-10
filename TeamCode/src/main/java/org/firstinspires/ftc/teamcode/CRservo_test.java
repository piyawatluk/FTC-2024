package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
@Disabled
@TeleOp
public class CRservo_test extends OpMode {
    public CRServo sv_4;
    @Override
    public void init(){
        sv_4 = hardwareMap.get(CRServo.class, "sv_4");
    }
    @Override
    public void loop(){
        while (gamepad1.a){
            sv_4.setPower(1);
        }
        while (gamepad1.b){
            sv_4.setPower(-1);
        }
        sv_4.setPower(0);
    }
}
