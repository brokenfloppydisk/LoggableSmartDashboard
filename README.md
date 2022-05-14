# LoggableSmartDashboard

This library acts as a wrapper around the SmartDashboard class in WPILib, allowing for the logging of all values.

## How to use:

1. Add `/src/main/java/frc/robot/LoggableSmartDashboard` to your own project.

2. Replace SmartDashboard put calls with LoggableSmartDashboard calls

3. Call `LogManager.getInstance.startLogging()` and `LogManager.getInstance.stopLogging()` to start and stop logging

4. Use DataLog Tool to open the data log and export to csv as table format

5. Use consolidate.py to consolidate the rows, if needed
