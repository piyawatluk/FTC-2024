package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class Test_path_for_multispec {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(25, 25, Math.toRadians(90), Math.toRadians(90), 10.11)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(25, -60, Math.toRadians(90)))
                        .strafeTo(new Vector2d(4,-27))
                        .back(10)
                        .lineToLinearHeading(new Pose2d(53,-60,Math.toRadians(270)))
                        .lineToLinearHeading(new Pose2d(4,-27,Math.toRadians(90)))
                        .back(10)
                        .lineTo(new Vector2d(53,-60))
                        .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}