package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.DriveTrain;
import org.firstinspires.ftc.teamcode.Hardware.Intake;
import org.firstinspires.ftc.teamcode.Hardware.Sensors;

@Autonomous(name ="Basic Blue Green Pathing", group="Auto Basic")
public class GreenPathing_Basic extends LinearOpMode {

    private double driveSpeed = 0.6;
    DriveTrain driveTrain= new DriveTrain();
    Sensors sensors = new Sensors();
    Intake intake = new Intake();
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        driveTrain.initDriveTrain(this);
        driveTrain.resetEncoders();
        waitForStart();

        driveTrain.encoderDrive(driveSpeed,  96,  96, 96, 96, 4.0);
        driveTrain.encoderDrive(driveSpeed, -48, -48, -48, -48, 3.0);
        driveTrain.encoderDrive(driveSpeed,144, -144, -144, 144, 5.0);
        driveTrain.encoderDrive(driveSpeed,-96, 96, 96, -96, 5.0);

        sleep(1000);

        telemetry.addData("Path", "Complete");
        telemetry.update();
    }

}
