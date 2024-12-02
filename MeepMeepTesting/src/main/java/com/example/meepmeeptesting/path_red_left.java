package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class path_red_left {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(25, 25, Math.toRadians(90), Math.toRadians(90), 10.11)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(-37, -60, Math.toRadians(90)))
                        .strafeTo(new Vector2d(-10,-33))
                        .strafeLeft(15)
                        .splineToConstantHeading(new Vector2d(-38, -6),Math.toRadians(90))
                        .splineToConstantHeading(new Vector2d(-48,-55),Math.toRadians(270))
                        .splineToConstantHeading(new Vector2d(-48,-6),Math.toRadians(90))
                        .splineToConstantHeading(new Vector2d(-55,-55),Math.toRadians(270))
                        .splineToConstantHeading(new Vector2d(-55,-6),Math.toRadians(90))
                        .strafeLeft(7)
                        .back(49)
                        .splineToConstantHeading(new Vector2d(-23,0),Math.toRadians(0))

                        .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}