package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous(name = "red_right")
public class auto_red_right_2 extends LinearOpMode {
    private Servo sv_1, sv_3, sv_2;
    private DcMotor extender_L = null;
    private DcMotor extender_R = null;
    private DigitalChannel ls = null;
    DcMotor currentmotor;
    boolean rightmotor = false;


    public void encoder_reset(){
        sv_1.setPosition(0);
        sv_3.setPosition(1);
        if (!ls.getState()){
            currentmotor.setPower(0);
            currentmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            currentmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        } else {
            currentmotor.setPower(-1);
        }
        telemetry.addData("current_pos", currentmotor.getCurrentPosition());
    }


    @Override
    public void runOpMode() throws InterruptedException {
        sv_1 = hardwareMap.get(Servo.class, "sv_1");
        sv_2 = hardwareMap.get(Servo.class, "sv_2");
        sv_3 = hardwareMap.get(Servo.class, "sv_3");
        extender_L = hardwareMap.get(DcMotor.class, "et_1");
        extender_R = hardwareMap.get(DcMotor.class,  "et_2");
        extender_L.setDirection(DcMotor.Direction.FORWARD);
        extender_R.setDirection(DcMotor.Direction.REVERSE);
        ls = hardwareMap.get(DigitalChannel.class, "ls");
        ls.setMode(DigitalChannel.Mode.INPUT);



        if(rightmotor){
            currentmotor = extender_R;
            telemetry.addData("Active Motor", "Right Motor");
        }
        else{
            currentmotor = extender_L;
            telemetry.addData("Active Motor", "Left Motor");
        }




        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Pose2d startPose = new Pose2d(25, -60, Math.toRadians(90));

        drive.setPoseEstimate(startPose);

        TrajectorySequence trajSeq = drive.trajectorySequenceBuilder(startPose)


                .addSpatialMarker(new Vector2d(46, -45), () -> {
                    sv_2.setPosition(0);
                })


                .waitSeconds(1.5)

                .UNSTABLE_addTemporalMarkerOffset(-0.5, () -> {
                    currentmotor.setTargetPosition(4000);
                    currentmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    currentmotor.setPower(1);
                })


                .strafeTo(new Vector2d(4,-23))

                .addDisplacementMarker(() -> {
                    currentmotor.setTargetPosition(3000);
                    currentmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    currentmotor.setPower(-1);
                })

                .waitSeconds(0.5)

                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> sv_2.setPosition(0.4))

                .back(10)
                .strafeRight(25)
                .splineToLinearHeading(new Pose2d(50, -6,Math.toRadians(270)),Math.toRadians(320))
                .forward(50)
                .back(50)
                .strafeLeft(10)
                .forward(50)
                .back(50)
                .strafeLeft(10)
                .forward(50)
                .build();

        waitForStart();

        if (!isStopRequested())
            encoder_reset();
            drive.followTrajectorySequence(trajSeq);

    }
}