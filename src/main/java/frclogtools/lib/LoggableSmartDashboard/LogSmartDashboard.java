package frclogtools.lib.LoggableSmartDashboard;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Schedule this command to log the values currently on SmartDashboard every 20 ms.
 */
public class LogSmartDashboard extends CommandBase {

    public LogSmartDashboard() {}

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        LoggableSmartDashboard.appendEntries();
    }

    /**
     * Flush the log values, finish, and close the log.
     */
    @Override
    public void end(boolean interrupted) {
        LoggableSmartDashboard.dataLog.flush();
        for (int logEntry : LoggableSmartDashboard.logEntryIDs.values()) {
            LoggableSmartDashboard.dataLog.finish(logEntry);
        }
        LoggableSmartDashboard.dataLog.close();
    }

    /**
     * Returns true when the command is finished.
     */
    @Override
    public boolean isFinished() {
        return !LoggableSmartDashboard.currentlyLogging;
    }
}