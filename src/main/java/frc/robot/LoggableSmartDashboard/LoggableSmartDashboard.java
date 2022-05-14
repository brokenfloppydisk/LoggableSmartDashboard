package frc.robot.LoggableSmartDashboard;

import java.util.HashMap;

import edu.wpi.first.util.WPIUtilJNI;
import edu.wpi.first.util.datalog.DataLog;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The {@link LoggableSmartDashboard} class is a wrapper around the 
 * {@link SmartDashboard} class that allows it to be logged.
 * <p>
 * 
 */
public class LoggableSmartDashboard {
    protected static HashMap<String, Integer> logEntryIDs = new HashMap<String, Integer>();
    protected static HashMap<Integer, Double> doubleEntries = new HashMap<Integer, Double>();
    protected static HashMap<Integer, String> stringEntries = new HashMap<Integer, String>();
    protected static HashMap<Integer, Boolean> booleanEntries = new HashMap<Integer, Boolean>();

    protected static boolean currentlyLogging = false;
    protected static DataLog dataLog;

    protected static void clearHashMaps() {
        logEntryIDs.clear();
        doubleEntries.clear();
        stringEntries.clear();
        booleanEntries.clear();
    }

    protected static void appendEntries() {
        if (!currentlyLogging) {return;}
        
        long timestamp = WPIUtilJNI.now();

        // Alternative implementation (less readable):
        // for (Entry<Integer, Double> entry : doubleEntries.entrySet()) {
        //     dataLog.appendDouble(entry.getKey(), entry.getValue(), timestamp);
        // }
        for (Integer id : LoggableSmartDashboard.doubleEntries.keySet()) {
            dataLog.appendDouble(id, doubleEntries.get(id), timestamp);
        }
        for (Integer id : LoggableSmartDashboard.stringEntries.keySet()) {
            dataLog.appendString(id, stringEntries.get(id), timestamp);
        }
        for (Integer id : LoggableSmartDashboard.booleanEntries.keySet()) {
            dataLog.appendBoolean(id, booleanEntries.get(id), timestamp);
        }
    }

    private static int getID(String key, String type) {
        if (!logEntryIDs.containsKey(key)) {
            int id = dataLog.start(key, type);
            logEntryIDs.put(key, id);
            return id;
        } else {
            return logEntryIDs.get(key);
        }
    }
    
    public static void putNumber(String key, double value) {
        SmartDashboard.putNumber(key, value);
        if (!currentlyLogging) {return;}

        int id = getID(key, "double");
        doubleEntries.put(id, value);
    }

    public static void putString(String key, String value) {
        SmartDashboard.putString(key, value);
        if (!currentlyLogging) {return;}

        int id = getID(key, "string");
        stringEntries.put(id, value);
    }

    public static void putBoolean(String key, boolean value) {
        SmartDashboard.putBoolean(key, value);
        if (!currentlyLogging) {return;}

        int id = getID(key, "boolean");
        booleanEntries.put(id, value);
    }
}
