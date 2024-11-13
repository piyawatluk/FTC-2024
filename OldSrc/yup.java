package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "yup (Blocks to Java)", group = "")
public class yup extends LinearOpMode {

  private DcMotor lm;
  private DcMotor rm;

  /**
   * This function is executed when this Op Mode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {
    lm = hardwareMap.dcMotor.get("lm");
    rm = hardwareMap.dcMotor.get("rm");

    // Put initialization blocks here.
    waitForStart();
    while (opModeIsActive()) {
      if (gamepad1.dpad_up) {
        lm.setPower(1);
        rm.setPower(-1);
      } else if (gamepad1.dpad_left) {
        lm.setPower(1);
        rm.setPower(1);
      } else if (gamepad1.dpad_right) {
        lm.setPower(-1);
        rm.setPower(-1);
      } else if (gamepad1.dpad_right) {
        lm.setPower(-1);
        rm.setPower(1);
      } else {
        lm.setPower(0);
        rm.setPower(0);
      }
    }
  }
}
