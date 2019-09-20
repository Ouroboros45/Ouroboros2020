package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Outtake {

    Servo openBasket;
    CRServo rightSideY;
    CRServo leftSideY;

    LinearOpMode opMode;
    ElapsedTime time = new ElapsedTime();

    public boolean initOutPut(OpMode opMode)
    {
        this.opMode = (LinearOpMode) opMode;
        time.reset();

        try
        {
            openBasket = opMode.hardwareMap.servo.get("Open Basket");
            rightSideY = opMode.hardwareMap.crservo.get("Right Outtake");
            leftSideY = opMode.hardwareMap.crservo.get("Left Outtake");

            opMode.telemetry.addData("Success", "Outtake Initialized");
            opMode.telemetry.update();

        } catch (Exception e)
        {
            opMode.telemetry.addData("Failed", "Failed to Map Servos");
            opMode.telemetry.update();

            return false;
        }
        return true;
    }

}
