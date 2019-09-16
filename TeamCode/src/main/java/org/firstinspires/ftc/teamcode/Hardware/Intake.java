package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
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

    private static final double PICKUP = 1;
    private static final double IDLE = 0;

    public boolean initIntake(OpMode opMode)
    {
        this.opMode = (LinearOpMode) opMode;
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

        rightSide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        leftSide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        return true;
    }

    //is to be called in OpMode
    //once activated moves toward block and activates compliant wheels
    //blockFound set by vision
    public void compliantIntake_Auto(boolean blockFound, double x_distance, double y_distance)
    {
        if(blockFound)
        {

            //activate intake
            rightSide.setPower(PICKUP);
            leftSide.setPower(PICKUP);

            //move toward block (x_distance, y_distance) - using vision
            //uses encoder drive

            opMode.telemetry.addData("Active", "Picking Up Block");
            opMode.telemetry.update();


        }else{
            rightSide.setPower(IDLE);
            leftSide.setPower(IDLE);
        }
        //find out intake mechanism
    }

    public void compliantIntake_TeleOp()
    {
        if(opMode.gamepad1.x) //set gamepade button to x, could change
        {
            rightSide.setPower(PICKUP);
            leftSide.setPower(PICKUP);

            opMode.telemetry.addData("Active", "Intake Running");
            opMode.telemetry.update();
        }else
        {
            rightSide.setPower(IDLE);
            leftSide.setPower(IDLE);
        }

    }
}
