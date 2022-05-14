package frc.robot.LoggableSmartDashboard;

import edu.wpi.first.util.WPIUtilJNI;
import edu.wpi.first.util.datalog.DataLog;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * The {@link SmartDashboardLogSender} class can be used to enable 
 * and disable logging on the {@link LoggableSmartDashboard} class.
 */
public class SmartDashboardLogSender {
    private boolean currentlyLogging = false;
    private DataLog dataLog;

    public void startLogging(DataLog dataLog, boolean schedule) {
        LoggableSmartDashboard.clearHashMaps();
        this.dataLog = dataLog;
        currentlyLogging = true;
        if (schedule) {
            Command log = new Log();
            log.schedule();
        }
    }

    public void startLogging(DataLog dataLog) {
        startLogging(dataLog, true);
    }

    public void stopLogging() {
        LoggableSmartDashboard.clearHashMaps();
        currentlyLogging = false;
    }

    /**
     * Schedule this command to log the values currently on SmartDashboard every 20 ms.
     */
    public class Log extends CommandBase {

        public Log() {}

        @Override
        public void initialize() {}

        @Override
        public void execute() {
            if (!currentlyLogging) {return;}

            long timestamp = WPIUtilJNI.now();
    
            // Alternative implementation (less readable):
            // for (Entry<Integer, Double> entry : doubleEntries.entrySet()) {
            //     dataLog.appendDouble(entry.getKey(), entry.getValue(), timestamp);
            // }
    
            for (Integer id : LoggableSmartDashboard.doubleEntries.keySet()) {
                dataLog.appendDouble(id, LoggableSmartDashboard.doubleEntries.get(id), timestamp);
            }
            for (Integer id : LoggableSmartDashboard.stringEntries.keySet()) {
                dataLog.appendString(id, LoggableSmartDashboard.stringEntries.get(id), timestamp);
            }
            for (Integer id : LoggableSmartDashboard.booleanEntries.keySet()) {
                dataLog.appendBoolean(id, LoggableSmartDashboard.booleanEntries.get(id), timestamp);
            }
        }

        /**
         * Flush the log values, finish, and close the log.
         */
        @Override
        public void end(boolean interrupted) {
            dataLog.flush();
            for (int logEntry : LoggableSmartDashboard.logEntryIDs.values()) {
                dataLog.finish(logEntry);
            }
            dataLog.close();
        }

        /**
         * Returns true when the command is finished.
         */
        @Override
        public boolean isFinished() {
            return !currentlyLogging;
        }
    }
}