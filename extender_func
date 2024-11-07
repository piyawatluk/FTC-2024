package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="DC_exteder_test", group="Iterative OpMode")
public class TEST2 extends OpMode
{
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor extender = null;
    @Override
    public void init() {
        
        telemetry.addData("Status", "Initialized");
        extender = hardwareMap.get(DcMotor.class, "et");
        extender.setDirection(DcMotor.Direction.FORWARD);
        telemetry.addData("Status", "Initialized");
        
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
        runtime.reset();
    }
    
    public void extender_func(){
        int max_pos = 6200;
        int min_pos = 0;
        double power;
        double drive = -gamepad1.left_stick_y;
        int pos = extender.getCurrentPosition();
        double target_pos;
        target_pos = Math.max(min_pos, Math.min(target_pos, max_pos));
        double distance = target_pos - pos;
        double nd = distance / (max_pos - min_pos);
        double slow_factor = 1 - Math.pow(Math.abs(nd), 2) * 10;
        power = nd * slow_factor;
        power = Math.max(-1, Math.min(power, 1));
        
        
                
        if ((power > 0 && pos < max_pos) || (power < 0 && pos > min_pos)) {
            
            extender.setPower(power);
            
        } 
        
        else {
            
            extender.setPower(0);
        }

        
        //extender.setPower(power);
        
        
        telemetry.addData("Motors", "Power (%.2f)", power);
        telemetry.addData("Pos", pos);
        telemetry.update();
    }

    @Override
    public void loop() {
        extender_func();
    }
    @Override
    public void stop() {
    }

}
