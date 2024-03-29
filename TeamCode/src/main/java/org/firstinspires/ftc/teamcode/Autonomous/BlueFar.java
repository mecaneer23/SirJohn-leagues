package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Base.BaseOpMode;
import org.firstinspires.ftc.teamcode.Base.Robot;
import org.firstinspires.ftc.teamcode.Bots.SirJohn;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.RRMecanum;
import org.firstinspires.ftc.teamcode.VisionProcessors.TeamPropDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

@Autonomous
public class BlueFar extends BaseOpMode {
    public SirJohn robot;
    public RRMecanum drive;
    public TeamPropDetection.PropPosition position = TeamPropDetection.PropPosition.CENTER;

    public Trajectory forward;
    public Trajectory toCenter;
    public Trajectory updateTrajectory;
    private boolean place1 = false;
    public Trajectory extraForward;
    public Trajectory leftInitial;
    public Trajectory leftMiddle;
    public Trajectory strafeRight;
    public Trajectory centerMiddle;
    public Trajectory rightMiddle;

    public Trajectory strafeLeft;
    public Trajectory strafeLeftMore;
    public Trajectory strafeLeftEvenMore;
    public Trajectory leftForward;
    public Trajectory moreForward;

    @Override
    protected Robot setRobot() {
        this.robot = new SirJohn();
        return this.robot;
    }

    @Override
    protected boolean setTeleOp() {
        return false;
    }

    @Override
    public void onInit(){
        robot.intake.closeClaw();
        drive = new RRMecanum(hardwareMap);
        Pose2d startPose = new Pose2d();
        drive.setPoseEstimate(startPose);

        robot.camera.setIsBlue(true);
        robot.outtake.toMiddle();


        forward = drive.trajectoryBuilder(startPose)
                .splineToConstantHeading(new Vector2d(44,-14), 0,RRMecanum.getVelocityConstraint(20, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        RRMecanum.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .addDisplacementMarker( () -> {
                    robot.intake.setAutoPos();
                })

                .build();

        leftForward = drive.trajectoryBuilder(startPose)
                .lineToConstantHeading(new Vector2d(78,-2),RRMecanum.getVelocityConstraint(15, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        RRMecanum.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
        moreForward = drive.trajectoryBuilder(startPose)
                .lineToConstantHeading(new Vector2d(85,-2),RRMecanum.getVelocityConstraint(15, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        RRMecanum.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
        extraForward = drive.trajectoryBuilder(startPose)
                .splineTo(new Vector2d(47.5,-6), Math.toRadians(0),RRMecanum.getVelocityConstraint(20, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        RRMecanum.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .addDisplacementMarker( () -> {
                    robot.intake.setAutoPos();
                })
                .build();
        leftInitial = drive.trajectoryBuilder(startPose)
                .lineToConstantHeading(new Vector2d(30,-1), RRMecanum.getVelocityConstraint(20, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        RRMecanum.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .addDisplacementMarker( () -> {
                    robot.intake.setAutoPos();
                })
                .build();
        strafeRight = drive.trajectoryBuilder(startPose)
                .lineToConstantHeading(new Vector2d(1,-5), RRMecanum.getVelocityConstraint(20, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        RRMecanum.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
        rightMiddle = drive.trajectoryBuilder(new Pose2d())
                .splineToConstantHeading(new Vector2d(6,-4),Math.toRadians(0), RRMecanum.getVelocityConstraint(20, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH), RRMecanum.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
        centerMiddle = drive.trajectoryBuilder(new Pose2d())
                .splineToConstantHeading(new Vector2d(5,-5),Math.toRadians(0), RRMecanum.getVelocityConstraint(20, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH), RRMecanum.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
        leftMiddle = drive.trajectoryBuilder(new Pose2d())
                .lineTo(new Vector2d(22,20), RRMecanum.getVelocityConstraint(20, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH), RRMecanum.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
        strafeLeftEvenMore = drive.trajectoryBuilder(startPose)
                .lineToConstantHeading(new Vector2d(2,44),RRMecanum.getVelocityConstraint(35, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        RRMecanum.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
        strafeLeftMore = drive.trajectoryBuilder(startPose)
                .lineToConstantHeading(new Vector2d(2,42),RRMecanum.getVelocityConstraint(35, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        RRMecanum.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();

        strafeLeft = drive.trajectoryBuilder(startPose)
                .lineToConstantHeading(new Vector2d(2,30),RRMecanum.getVelocityConstraint(35, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        RRMecanum.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();


        robot.camera.init();




    }
    @Override
    public void onStart() throws InterruptedException {
        position = robot.camera.getPosition();
        robot.intake.setAutoPos();
        drive.waitForIdle();
        sleep(1000);


        if(position == TeamPropDetection.PropPosition.LEFT) {
            drive.waitForIdle();
            drive.followTrajectoryAsync(leftInitial);
            robot.intake.setAutoPos();
            drive.waitForIdle();
            drive.turnAsync(Math.toRadians(-84));
            drive.waitForIdle();
            robot.intake.setAutoPos();
            robot.intake.toggleClaw();
            drive.waitForIdle();
            drive.setPoseEstimate(new Pose2d());
            drive.followTrajectory(leftMiddle);
            drive.waitForIdle();
            drive.turnAsync(Math.toRadians(155));
            drive.waitForIdle();
            drive.setPoseEstimate(new Pose2d());
            drive.followTrajectoryAsync(moreForward);
            drive.setPoseEstimate(new Pose2d());
            drive.waitForIdle();
            drive.setPoseEstimate(new Pose2d());
            drive.waitForIdle();
            drive.followTrajectory(strafeLeftEvenMore);
            drive.waitForIdle();
            drive.setPoseEstimate(new Pose2d());
            drive.setPoseEstimate(new Pose2d());
            drive.waitForIdle();
            drive.waitForIdle();
            sleep(500);
            place1 = true;
            drive.waitForIdle();
        }


        if(position == TeamPropDetection.PropPosition.CENTER){
            drive.waitForIdle();
            drive.followTrajectoryAsync(extraForward);
            robot.intake.setAutoPos();
            drive.waitForIdle();
            robot.intake.setAutoPos();
            robot.intake.toggleClaw();
            drive.waitForIdle();
            drive.setPoseEstimate(new Pose2d());
            drive.followTrajectory(centerMiddle);
            drive.turnAsync(Math.toRadians(74));
            drive.waitForIdle();
            drive.setPoseEstimate(new Pose2d());
            drive.waitForIdle();
            drive.waitForIdle();
            drive.followTrajectoryAsync(leftForward);
            drive.waitForIdle();
            drive.setPoseEstimate(new Pose2d());
            drive.followTrajectoryAsync(strafeLeftMore);
            drive.setPoseEstimate(new Pose2d());
            drive.waitForIdle();
            sleep(500);
            place1 = true;
            drive.waitForIdle();
        }
        if(position == TeamPropDetection.PropPosition.RIGHT){
            drive.followTrajectoryAsync(forward);
            robot.intake.setAutoPos();
            drive.waitForIdle();
            robot.intake.setAutoPos();
            robot.intake.toggleClaw();
            drive.waitForIdle();
            drive.setPoseEstimate(new Pose2d());
            drive.waitForIdle();
            drive.followTrajectory(rightMiddle);
            drive.setPoseEstimate(new Pose2d());
            drive.waitForIdle();
            drive.turnAsync(Math.toRadians(72));
            drive.waitForIdle();
            drive.followTrajectoryAsync(leftForward);
            drive.setPoseEstimate(new Pose2d());
            drive.waitForIdle();
            drive.followTrajectoryAsync(strafeLeft);
            drive.setPoseEstimate(new Pose2d());
            drive.waitForIdle();
            sleep(500);
//            sleep(1000);
            place1 = true;
            drive.waitForIdle();


        }
        robot.intake.setAutoPos();




    }
    @Override
    public void onUpdate(){
        drive.update();
        robot.camera.update();
        List<AprilTagDetection> currentDetections = robot.camera.aprilTag.getDetections();
        if(place1) {
            for (AprilTagDetection detection : currentDetections) {
                if (detection.metadata != null) {

                    if (detection.id == 1 && position == TeamPropDetection.PropPosition.LEFT) {
                        drive.setPoseEstimate(new Pose2d());
                        updateTrajectory = drive.trajectoryBuilder(new Pose2d())
                                .splineToConstantHeading(new Vector2d(detection.ftcPose.y-3, -detection.ftcPose.x+1), 0,  RRMecanum.getVelocityConstraint(15, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                                        RRMecanum.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                                .addDisplacementMarker(15,() -> {
                                    robot.slides.move(760,1);
                                    robot.intake.claw.close();
                                    robot.outtake.unFlip();
                                })
                                .splineToConstantHeading(new Vector2d(detection.ftcPose.y-5, -detection.ftcPose.x+1),0, RRMecanum.getVelocityConstraint(1, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                                        RRMecanum.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                                .addDisplacementMarker(() -> {
                                    robot.slides.waitForIdle();
                                    robot.slides.move(20,1);
                                    robot.outtake.flip();
                                    robot.intake.setAutoPos();
                                })
                                .splineToConstantHeading(new Vector2d(detection.ftcPose.y-6, -detection.ftcPose.x+1),0, RRMecanum.getVelocityConstraint(15, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                                        RRMecanum.getAccelerationConstraint(DriveConstants.MAX_ACCEL))

                                .build();
                        drive.turn(Math.toRadians(-detection.ftcPose.roll));

                    }
                    else if (detection.id == 2 && position == TeamPropDetection.PropPosition.CENTER) {
                        drive.setPoseEstimate(new Pose2d());
                        updateTrajectory = drive.trajectoryBuilder(new Pose2d())
                                .splineToConstantHeading(new Vector2d(detection.ftcPose.y-3, -detection.ftcPose.x-2),0, RRMecanum.getVelocityConstraint(15, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                                        RRMecanum.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                                .addDisplacementMarker(15,() -> {
                                    robot.slides.move(760,1);
                                    robot.intake.claw.close();
                                    robot.outtake.unFlip();
                                })
                                .splineToConstantHeading(new Vector2d(detection.ftcPose.y-5, -detection.ftcPose.x),0, RRMecanum.getVelocityConstraint(1, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                                        RRMecanum.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                                .addDisplacementMarker(() -> {
                                    robot.slides.waitForIdle();
                                    robot.slides.move(20,1);
                                    robot.outtake.flip();
                                    robot.intake.setAutoPos();
                                })
                                .splineToConstantHeading(new Vector2d(detection.ftcPose.y-6, -detection.ftcPose.x),0, RRMecanum.getVelocityConstraint(15, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                                        RRMecanum.getAccelerationConstraint(DriveConstants.MAX_ACCEL))

                                .build();
                        drive.turn(Math.toRadians(-detection.ftcPose.roll));

                    }
                    else if (detection.id == 3 && position == TeamPropDetection.PropPosition.RIGHT) {
                        drive.setPoseEstimate(new Pose2d());
                        updateTrajectory = drive.trajectoryBuilder(new Pose2d())
                                .splineToConstantHeading(new Vector2d(detection.ftcPose.y-3, -detection.ftcPose.x-2), 0, RRMecanum.getVelocityConstraint(15, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                                        RRMecanum.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                                .addDisplacementMarker(18,() -> {
                                    robot.slides.move(760,1);
                                    robot.intake.claw.close();
                                    robot.outtake.unFlip();
                                })
                                .splineToConstantHeading(new Vector2d(detection.ftcPose.y-5.5, -detection.ftcPose.x),0, RRMecanum.getVelocityConstraint(1, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                                        RRMecanum.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                                .addDisplacementMarker(() -> {
                                    robot.slides.waitForIdle();
                                    robot.slides.move(20,1);
                                    robot.outtake.flip();
                                    robot.intake.setAutoPos();
                                })
                                .splineToConstantHeading(new Vector2d(detection.ftcPose.y-7, -detection.ftcPose.x),0, RRMecanum.getVelocityConstraint(15, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                                        RRMecanum.getAccelerationConstraint(DriveConstants.MAX_ACCEL))

                                .build();
                        drive.turn(Math.toRadians(-detection.ftcPose.roll));

                    }
                    if(updateTrajectory != null) {
                        drive.followTrajectoryAsync(updateTrajectory);
                    }

                }
            }
            place1 = false;
        }

    }
}
