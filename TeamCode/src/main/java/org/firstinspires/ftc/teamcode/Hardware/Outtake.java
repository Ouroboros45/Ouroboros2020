package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Outtake {

    Servo openBasket;
    CRServo rightSideY;
    CRServo leftSideY;
    DcMotor liftRight;
    DcMotor liftLeft;

    LinearOpMode opMode;
    ElapsedTime time = new ElapsedTime();

    boolean blockInLift;

    public boolean initOuttake(OpMode opMode)
    {
        this.opMode = (LinearOpMode) opMode;
        time.reset();

        blockInLift = false;

        try
        {
            openBasket = opMode.hardwareMap.servo.get("Open Basket");
            rightSideY = opMode.hardwareMap.crservo.get("Right Outtake");
            leftSideY = opMode.hardwareMap.crservo.get("Left Outtake");
            liftLeft = opMode.hardwareMap.dcMotor.get("Left Lift");
            liftRight = opMode.hardwareMap.dcMotor.get("Right Lift");

            liftLeft.setDirection(DcMotorSimple.Direction.REVERSE);

            opMode.telemetry.addData("Success", "Outtake Initialized");
            opMode.telemetry.update();

        } catch (Exception e)
        {
            opMode.telemetry.addData("Failed", "Failed to Map");
            opMode.telemetry.update();

            return false;
        }
        return true;
    }



}
