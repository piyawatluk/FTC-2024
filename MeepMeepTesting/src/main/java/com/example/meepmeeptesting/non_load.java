package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class non_load {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(25, 25, Math.toRadians(90), Math.toRadians(90), 10.11)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(25, -60, Math.toRadians(90)))
                        .strafeRight(10)
                        .forward(55)
                        .turn(Math.toRadians(180))
                        .strafeLeft(10)
                        .forward(45)
                        .back(45)
                        .strafeLeft(10)
                        .forward(45)
                        .back(45)
                        .strafeLeft(7)
                        .forward(45)
                        .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}