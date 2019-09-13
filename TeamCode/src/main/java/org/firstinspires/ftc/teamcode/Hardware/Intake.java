package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Intake {

    //Compliant Wheel Intake
    //-----------------------
    //Detects a block using vision - in OpClass
    //Activates Intake Motors after block found - to save power

    DcMotor rightSide;
    DcMotor leftSide;

    private LinearOpMode opMode;

    public boolean initIntake(LinearOpMode opMode)
    {
        this.opMode = opMode;
        try
        {
            rightSide = opMode.hardwareMap.dcMotor.get("right_motor");
            leftSide = opMode.hardwareMap.dcMotor.get("left_motor");
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
    public void compliantIntake()
    {
        //find out intake mechanism
    }
}
