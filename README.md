# LoggableSmartDashboard

This library acts as a wrapper around the [SmartDashboard](https://first.wpi.edu/wpilib/allwpilib/docs/release/java/edu/wpi/first/wpilibj/smartdashboard/SmartDashboard.html) class in WPILib, allowing for the logging of SmartDashboard values.

## Installation:

### Manual:

Add `frclogtools.jar` to a lib folder at the root folder of your repository.

### With Gradle:

In `build.gradle`, add:

```gradle
repositories {
    maven {
        url "https://jitpack.io"
    }
}

dependencies {
    implementation 'com.github.brokenfloppydisk:LoggableSmartDashboard:main-SNAPSHOT'
}
```

`main-SNAPSHOT` can be replaced with the commit hash for a specific version.

Then, run Build Robot Code or `./gradlew build`.

## Usage:

Use `LoggableSmartDashboard.putNumber()`, `.putBoolean()`, or `.putString()` in place of `SmartDashboard` to enable logging for these values.

Use `LogManager.getInstance()` to get an instance of the LogManager, and call `.startLogging()` and `.stopLogging()` to start and stop logging.

DataLog from WPILib is used to log values, so only WPILib 2022.4.1 and up is supported.

If no DataLog is passed to `.startLogging()`, the data will be written to the default log in `DataLogManager.getLog()` instead.

`LogSmartDashboard` is a command that automatically gets scheduled every 20 ms by `LogManager` to log the current SmartDashboard values.

To export the logs, use DataLog Tool (included in the [installer](https://github.com/wpilibsuite/allwpilib/releases) for WPILib 2022.4.1)

The logs can also be processed with [`consolidate.py`](https://github.com/brokenfloppydisk/LoggableSmartDashboard/blob/main/consolidate.py) 
to consolidate the rows, as table exporting to CSV in DataLog Tool does not combine rows with the same timestamp.

## Example Code:

#### **`Robot.java`**
```java
import edu.wpi.first.util.WPIUtilJNI;
import edu.wpi.first.util.datalog.DataLog;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frclogtools.lib.LogManager;
import frclogtools.lib.LoggableSmartDashboard;

public class Robot extends TimedRobot {
  private DataLog dataLog;

  @Override
  public void robotInit() {
    CommandScheduler.getInstance().cancelAll();
    dataLog = new DataLog();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {
    LogManager.getInstance().stopLogging();
  }

  @Override
  public void disabledPeriodic() {}

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    LogManager.getInstance().startLogging(dataLog);
  }

  @Override
  public void teleopPeriodic() {
    long timestamp = WPIUtilJNI.now();
    LoggableSmartDashboard.putNumber("time", timestamp);
    LoggableSmartDashboard.putString("testString", "testString" + timestamp);
    LoggableSmartDashboard.putBoolean("testBoolean", timestamp % 2 == 0);
  }

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}
}
```
