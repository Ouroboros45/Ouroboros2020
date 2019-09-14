package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Intake {

    //Compliant Wheel Intake
    //-----------------------
    //Detects a block using vision - in OpClass
    //Activates Intake Motors after block found - to save power


    // intake planetary motors
    DcMotor rightSide;
    DcMotor leftSide;

    private LinearOpMode opMode;

    public boolean initIntake(LinearOpMode opMode)
    {
        this.opMode = opMode;
        try
        {
            rightSide = opMode.hardwareMap.dcMotor.get("Right Intake");
            leftSide = opMode.hardwareMap.dcMotor.get("Left Intake");
            opMode.telemetry.addData("Success", "Intake Initialized");
            opMode.telemetry.update();
        } catch (Exception e)
        {
            opMode.telemetry.addData("Failed", "Failed to Map Motors");
            opMode.telemetry.update();
            return false;
        }
        return true;
    }

    //is to be called in OpMode
    public void compliantIntake()
    {
        //find out intake mechanism
    }
}
