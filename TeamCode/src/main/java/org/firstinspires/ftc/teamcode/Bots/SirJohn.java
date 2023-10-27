package org.firstinspires.ftc.teamcode.Bots;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Base.Robot;
import org.firstinspires.ftc.teamcode.Components.Camera;
import org.firstinspires.ftc.teamcode.Components.Crossbow;
import org.firstinspires.ftc.teamcode.Components.Hanger;
import org.firstinspires.ftc.teamcode.Components.Intake;
import org.firstinspires.ftc.teamcode.Components.Mecanum;
import org.firstinspires.ftc.teamcode.Components.Outtake;
import org.firstinspires.ftc.teamcode.Components.Slides;

public class SirJohn extends Robot {

    public Camera camera;
    public boolean isTeleOp;
    public Intake intake;
    public Outtake outtake;
    public Mecanum mecanum;
    public Crossbow crossbow;
    public Hanger hanger;
    public Slides slides;





    @Override
    protected void mapHardware(HardwareMap hardwareMap, Telemetry telemetry, LinearOpMode opMode, boolean isTeleOp) {
        this.camera = new Camera("Webcam 1", hardwareMap, telemetry, mecanum);
        this.isTeleOp = isTeleOp;
        //TODO figure out the three values for intake and figure out slides and hanger values
        this.intake = new Intake("arm", "claw", hardwareMap, telemetry, isTeleOp, 0, 0,0);
        this.outtake = new Outtake("pins","spin", hardwareMap, telemetry, 0.5, 0, 0.5, 0);
        this.hanger = new Hanger("hanger", hardwareMap, telemetry, isTeleOp, 0,0,0);
        this.mecanum = new Mecanum(hardwareMap,"frontLeft", "frontRight", "backLeft", "backRight",telemetry);
        this.crossbow = new Crossbow("crossbow", hardwareMap, telemetry, 0.5, 0);
        this.slides = new Slides("slides", hardwareMap, telemetry, isTeleOp, 0, 0, 0,0);
        this.mecanum.fl.setDirection(DcMotorSimple.Direction.REVERSE);
        this.mecanum.fr.setDirection(DcMotorSimple.Direction.FORWARD);
        this.mecanum.bl.setDirection(DcMotorSimple.Direction.REVERSE);
        this.mecanum.br.setDirection(DcMotorSimple.Direction.FORWARD);
        addComponents(mecanum);
    }
}