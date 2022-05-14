package frc.robot.LoggableSmartDashboard;

import edu.wpi.first.util.datalog.DataLog;
import edu.wpi.first.wpilibj.DataLogManager;

public class LogManager {
    private static LogManager instance = new LogManager();

    public static LogManager getInstance() {
        if (instance == null) {
            instance = new LogManager();
        }
        return instance;
    }
    
    public void startLogging(DataLog dataLog, boolean schedule) {
        LoggableSmartDashboard.clearHashMaps();
        LoggableSmartDashboard.dataLog = dataLog;
        LoggableSmartDashboard.currentlyLogging = true;
        if (schedule) {
            LogSmartDashboard log = new LogSmartDashboard();
            log.schedule();
        }
    }

    public void startLogging(boolean schedule) {
        DataLog dataLog = DataLogManager.getLog();
        startLogging(dataLog, schedule);
    }
    
    public void startLogging(DataLog dataLog) {
        startLogging(dataLog, true);
    }

    public void startLogging() {
        DataLog dataLog = DataLogManager.getLog();
        startLogging(dataLog, true);
    }
    
    public void stopLogging() {
        LoggableSmartDashboard.clearHashMaps();
        LoggableSmartDashboard.currentlyLogging = false;
    }
}