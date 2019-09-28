
package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.DriveTrain;
import org.firstinspires.ftc.teamcode.Hardware.Intake;
import org.firstinspires.ftc.teamcode.Hardware.Outtake;
import org.firstinspires.ftc.teamcode.Hardware.Sensors;
import org.firstinspires.ftc.teamcode.Hardware.Vuforia;

@Autonomous(name ="Basic Blue Green Path", group="Auto Basic")
public class GreenPathing_Basic extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private double driveSpeed = 0.6;

    DriveTrain driveTrain= new DriveTrain();
    Sensors sensors = new Sensors();
    Intake intake = new Intake();
    Outtake outtake = new Outtake();
    Vuforia vuf = new Vuforia();

    @Override
    public void runOpMode() {

        driveTrain.initDriveTrain(this);
        driveTrain.resetEncoders();

        intake.initIntake(this);
        outtake.initOuttake(this);
        vuf.initVuforia(this);
        vuf.initVuforia(this);
        waitForStart();

        driveTrain.encoderDrive(driveSpeed,  10,  10, 10);

        switch (vuf.senseSkystone(this)) {
            case 1:
                driveTrain.encoderDrive(driveSpeed,  5,  5, 5);
                intake.compliantIntake_Auto(1, true);
                //rest of code for EVERYTHING
                break;
            case 2:
                driveTrain.encoderDrive(driveSpeed,  5,  5, 5);
                intake.compliantIntake_Auto(1, true);

                //rest of code for EVERYTHING

                break;
            case 3:
                driveTrain.encoderDrive(driveSpeed,  5,  5, 5);
                intake.compliantIntake_Auto(1, true);

                //rest of code for EVERYTHING

                break;
        }

        intake.compliantIntake_Auto(1, true);

        driveTrain.encoderDrive(driveSpeed, -48, -48, -48);
        driveTrain.encoderDrive(driveSpeed,144, -144, -144);
        driveTrain.encoderDrive(driveSpeed,-96, 96, 96);

        sleep(1000);

        telemetry.addData("Path", "Complete");
        telemetry.update();
    }

}
