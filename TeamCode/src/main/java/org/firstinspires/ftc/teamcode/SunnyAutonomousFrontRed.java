package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

@Autonomous(name = "SunnyAutonomousFrontRed (Blocks to Java)", preselectTeleOp = "Sunny-TeleOp")
public class SunnyAutonomousFrontRed extends LinearOpMode {

    private DcMotor arm_L;
    private DcMotor B_L;
    private DcMotor F_L;
    private Servo gripper_L;
    private Servo gripper_R;
    private Servo servo_arm_R;
    private Servo servo_arm_L;
    private DcMotor B_R;
    private DcMotor F_R;
    private DcMotor arm_R;

    List<Recognition> myTfodRecognitions;
    TfodProcessor myTfodProcessor;
    float x;
    VisionPortal myVisionPortal;

    /**
     * Initialize TensorFlow Object Detection.
     */
    private void initTfod() {
        TfodProcessor.Builder myTfodProcessorBuilder;
        VisionPortal.Builder myVisionPortalBuilder;

        // First, create a TfodProcessor.Builder.
        myTfodProcessorBuilder = new TfodProcessor.Builder();
        // Set the name of the file where the model can be found.
        myTfodProcessorBuilder.setModelFileName("TeamPropDetectorModel.tflite");
        // Set the full ordered list of labels the model is trained to recognize.
        myTfodProcessorBuilder.setModelLabels(JavaUtil.createListWith("RedProp", "BlueProp"));
        // Set the maximum number of recognitions the network will return.
        myTfodProcessorBuilder.setMaxNumRecognitions(1);
        // Create a TfodProcessor by calling build.
        myTfodProcessor = myTfodProcessorBuilder.build();
        // Next, create a VisionPortal.Builder and set attributes related to the camera.
        myVisionPortalBuilder = new VisionPortal.Builder();
        // Use a webcam.
        myVisionPortalBuilder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
        // Add myTfodProcessor to the VisionPortal.Builder.
        myVisionPortalBuilder.addProcessor(myTfodProcessor);
        // Create a VisionPortal by calling build.
        myVisionPortal = myVisionPortalBuilder.build();
        // Resume the streaming session if previously stopped.
        myVisionPortal.resumeStreaming();
        // Set the minimum confidence at which to keep recognitions.
        myTfodProcessor.setMinResultConfidence((float) 0.65);
        // Indicate that only the zoomed center area of each image will be passed to
        // the TensorFlow object detector. For no zooming, set magnification to 1.0.
        myTfodProcessor.setZoom(1);
    }

    /**
     * This function is executed when this OpMode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        // TODO: Enter the type for variable named i
        UNKNOWN_TYPE i;

        arm_L = hardwareMap.get(DcMotor.class, "LeftArm");
        B_L = hardwareMap.get(DcMotor.class, "BackL");
        F_L = hardwareMap.get(DcMotor.class, "FrontL");
        gripper_L = hardwareMap.get(Servo.class, "gripperL");
        gripper_R = hardwareMap.get(Servo.class, "gripperR");
        servo_arm_L = hardwareMap.get(Servo.class, "servoarmL");
        servo_arm_R = hardwareMap.get(Servo.class, "servoarmR");
        B_R = hardwareMap.get(DcMotor.class, "BackR");
        F_R = hardwareMap.get(DcMotor.class, "FrontR");
        arm_R = hardwareMap.get(DcMotor.class, "RightArm");

        // This 2023-2024 OpMode illustrates the basics of TensorFlow Object Detection.
        LeftArm.setDirection(DcMotor.Direction.REVERSE);
        BackL.setDirection(DcMotor.Direction.REVERSE);
        FrontL.setDirection(DcMotor.Direction.REVERSE);
        Grab.setPosition(0.3);
        ArmJoint.setPosition(0.35);
        sleep(2000);
        Grab.setPosition(0.5);
        sleep(500);
        ArmJoint.setPosition(1);
        // Initialize TFOD before waitForStart.
        initTfod();
        // Wait for the match to begin.
        waitForStart();
        if (opModeIsActive()) {
            for (i = 1; i <= 1000; i++) {
                telemetryTfod();
                telemetry.update();
                if (JavaUtil.listLength(myTfodRecognitions) == 1) {
                    break;
                }
            }
            // Enable or disable the TensorFlow Object Detection processor.
            myVisionPortal.setProcessorEnabled(myTfodProcessor, false);
            if (x < 400) {
                StraightPath();
            } else if (x > 400) {
                RightPath();
            } else {
                LeftPath();
            }
        }
    }

    /**
     * Describe this function...
     */
    private void forward() {
        BackL.setPower(1);
        BackR.setPower(1);
        FrontL.setPower(1);
        FrontR.setPower(1);
    }

    /**
     * Describe this function...
     */
    private void backward() {
        BackL.setPower(-1);
        BackR.setPower(-1);
        FrontL.setPower(-1);
        FrontR.setPower(-1);
    }

    /**
     * Describe this function...
     */
    private void StraightPath() {
        right();
        sleep(350);
        forward();
        sleep(1000);
        stop2();
        sleep(500);
        ArmJoint.setPosition(0.365);
        sleep(1500);
        Grab.setPosition(0.3);
        sleep(1000);
        backward();
        sleep(30);
        stop2();
        ArmJoint.setPosition(0.35);
        sleep(300);
        Grab.setPosition(0.5);
        sleep(500);
        ArmJoint.setPosition(1);
        sleep(1000);
        turnright();
        sleep(750);
        stop2();
        sleep(300);
        forward();
        sleep(2500);
        stop2();
        backward();
        sleep(150);
        stop2();
        sleep(300);
        left();
        sleep(500);
        forward();
        sleep(300);
        stop2();
        armup();
        sleep(1200);
        armstop();
        ArmJoint.setPosition(0.375);
        sleep(1500);
        Grab.setPosition(0.3);
        ArmJoint.setPosition(1);
        backward();
        sleep(300);
        right();
        sleep(1500);
        stop2();
        sleep(300);
        forward();
        sleep(700);
        stop2();
        sleep(300);
    }

    /**
     * Describe this function...
     */
    private void RightPath() {
        right();
        sleep(650);
        stop2();
        forward();
        sleep(550);
        stop2();
        sleep(500);
        ArmJoint.setPosition(0.365);
        sleep(1500);
        Grab.setPosition(0.3);
        sleep(1000);
        backward();
        sleep(30);
        stop2();
        ArmJoint.setPosition(0.35);
        sleep(300);
        Grab.setPosition(0.5);
        sleep(500);
        ArmJoint.setPosition(1);
        sleep(1000);
        turnright();
        sleep(710);
        stop2();
        sleep(300);
        forward();
        sleep(1000);
        stop2();
        sleep(300);
        left();
        sleep(400);
        stop2();
        forward();
        sleep(1000);
        stop2();
        sleep(200);
        armup();
        sleep(1100);
        armstop();
        ArmJoint.setPosition(0.375);
        sleep(1500);
        Grab.setPosition(0.3);
        ArmJoint.setPosition(1);
        backward();
        sleep(300);
        right();
        sleep(1500);
        stop2();
        sleep(300);
        forward();
        sleep(700);
        stop2();
        sleep(300);
    }

    /**
     * Describe this function...
     */
    private void LeftPath() {
        forward();
        sleep(1290);
        stop2();
        sleep(300);
        turnleft();
        sleep(750);
        stop2();
        sleep(300);
        ArmJoint.setPosition(0.365);
        sleep(1000);
        Grab.setPosition(0.3);
        backward();
        sleep(50);
        stop2();
        ArmJoint.setPosition(0.35);
        sleep(300);
        Grab.setPosition(0.5);
        sleep(500);
        ArmJoint.setPosition(1);
        sleep(1000);
        turnright();
        sleep(1690);
        stop2();
        sleep(300);
        left();
        sleep(180);
        stop2();
        sleep(200);
        forward();
        sleep(2500);
        stop2();
        sleep(300);
        armup();
        sleep(1100);
        armstop();
        ArmJoint.setPosition(0.375);
        sleep(1500);
        Grab.setPosition(0.3);
        ArmJoint.setPosition(1);
        backward();
        sleep(300);
        right();
        sleep(2000);
        stop2();
        sleep(300);
        forward();
        sleep(700);
        stop2();
        sleep(300);
    }

    /**
     * Describe this function...
     */
    private void left() {
        BackL.setPower(1);
        BackR.setPower(-1);
        FrontL.setPower(-1);
        FrontR.setPower(1);
    }

    /**
     * Describe this function...
     */
    private void right() {
        BackL.setPower(-1);
        BackR.setPower(1);
        FrontL.setPower(1);
        FrontR.setPower(-1);
    }

    /**
     * Display info (using telemetry) for a detected object
     */
    private void telemetryTfod() {
        Recognition myTfodRecognition;
        float y;

        // Get a list of recognitions from TFOD.
        myTfodRecognitions = myTfodProcessor.getRecognitions();
        // Display the label and confidence for the recognition.
        telemetry.addData("# Objects Detected", JavaUtil.listLength(myTfodRecognitions));
        // Iterate through list and call a function to display info for each recognized object.
        for (Recognition myTfodRecognition_item : myTfodRecognitions) {
            myTfodRecognition = myTfodRecognition_item;
            // Display label and confidence.
            // Display the label and confidence for the recognition.
            telemetry.addData("Image", myTfodRecognition.getLabel() + " (" + JavaUtil.formatNumber(myTfodRecognition.getConfidence() * 100, 0) + " % Conf.)");
            // Display position.
            x = (myTfodRecognition.getLeft() + myTfodRecognition.getRight()) / 2;
            y = (myTfodRecognition.getTop() + myTfodRecognition.getBottom()) / 2;
            // Display the position of the center of the detection boundary for the recognition
            telemetry.addData("- Position", JavaUtil.formatNumber(x, 0) + "," + JavaUtil.formatNumber(y, 0));
        }
    }

    /**
     * Describe this function...
     */
    private void turnleft() {
        BackL.setPower(-1);
        BackR.setPower(1);
        FrontL.setPower(-1);
        FrontR.setPower(1);
    }

    /**
     * Describe this function...
     */
    private void turnright() {
        BackL.setPower(1);
        BackR.setPower(-1);
        FrontL.setPower(1);
        FrontR.setPower(-1);
    }

    /**
     * Describe this function...
     */
    private void stop2() {
        BackL.setPower(0);
        BackR.setPower(0);
        FrontL.setPower(0);
        FrontR.setPower(0);
    }

    /**
     * Describe this function...
     */
    private void armup() {
        LeftArm.setPower(-0.5);
        RightArm.setPower(-0.5);
    }

    /**
     * Describe this function...
     */
    private void armdown() {
        LeftArm.setPower(0.5);
        RightArm.setPower(0.5);
    }
