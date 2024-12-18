package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous(name = "left basket")
public class auto_left_basket extends LinearOpMode {
    private Servo sv_1, sv_3, sv_2;
    private CRServo INTAKE_SERVO;
    private DcMotor extender_L = null;
    private DcMotor extender_R = null;
    private DigitalChannel ls = null;
    DcMotor currentmotor;
    boolean rightmotor = false;
    double pos3 = (double) 100 / 180;


    public void encoder_reset(){
        sv_1.setPosition(0);
        sv_3.setPosition(1);
        sleep(700);
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
        INTAKE_SERVO = hardwareMap.get(CRServo.class, "sv_4");
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

        Pose2d startPose = new Pose2d(-37, -60, Math.toRadians(90));

        drive.setPoseEstimate(startPose);

        TrajectorySequence trajSeq = drive.trajectorySequenceBuilder(startPose)

                .addSpatialMarker(new Vector2d(-46, -45), () -> {
                    sv_2.setPosition(0);
                })


                .waitSeconds(1.5)

                .UNSTABLE_addTemporalMarkerOffset(-0.5, () -> {
                    currentmotor.setTargetPosition(4000);
                    currentmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    currentmotor.setPower(1);
                })


                .strafeTo(new Vector2d(-4,-23))

                .addDisplacementMarker(() -> {
                    currentmotor.setTargetPosition(3000);
                    currentmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    currentmotor.setPower(-1);
                })

                .waitSeconds(0.5)

                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> sv_2.setPosition(0.4))

                .back(10)
                .strafeLeft(30)
                .addDisplacementMarker(() -> {
                    currentmotor.setTargetPosition(660);
                    currentmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    currentmotor.setPower(-1);
                    sv_1.setPosition(0.5);
                    sv_3.setPosition(0.5);
                })

                .turn(Math.toRadians(90))

                .addDisplacementMarker(() ->{
                    INTAKE_SERVO.setPower(1);
                    sv_1.setPosition(pos3);
                    sv_3.setPosition(1-pos3);
                    currentmotor.setTargetPosition(0);
                    currentmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                })

                .strafeRight(12)
                .addDisplacementMarker(() -> {
                    sv_1.setPosition(15/180);
                    sv_3.setPosition(1-(15/180));
                    INTAKE_SERVO.setPower(0);
                    currentmotor.setTargetPosition(6200);
                    currentmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                })

                .lineToLinearHeading(new Pose2d(-56,-56,Math.toRadians(225)))
                .addDisplacementMarker(() -> {
                    INTAKE_SERVO.setPower(-1);
                })
                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                    INTAKE_SERVO.setPower(1);
                    sv_1.setPosition(pos3);
                    sv_3.setPosition(1-pos3);
                    currentmotor.setTargetPosition(0);
                    currentmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                })
                .lineToLinearHeading(new Pose2d(-34,-25,Math.toRadians(180)))
                .forward(8)
                .lineToLinearHeading(new Pose2d(-56,-56,Math.toRadians(225)))
                .lineToLinearHeading(new Pose2d(-42,-25,Math.toRadians(180)))
                .lineTo(new Vector2d(-24,0))

                .build();

        waitForStart();

        if (!isStopRequested()){
            drive.followTrajectorySequence(trajSeq);
        }
    }
}