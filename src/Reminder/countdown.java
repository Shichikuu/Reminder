package Reminder;

import java.awt.TrayIcon.MessageType;
import java.time.*;
import java.util.*;

import javax.swing.JOptionPane;
import java.awt.*;

public class countdown {
	static Map<kegiatan, ArrayList<Timer>> timers = new HashMap<>();
    public static void remind(LocalDateTime targetDateTime, kegiatan reminder) {
    	
    	Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                LocalDateTime now = LocalDateTime.now();
                if (now.isAfter(targetDateTime)) {
                	try {
						Reminder.reminder.displayTray("Reminder '" + reminder.nama + "' has reached its deadline!");
					} catch (AWTException e) {
						e.printStackTrace();
					}
                    timer.cancel();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
        timers.computeIfAbsent(reminder, k -> new ArrayList<>()).add(timer);
    }
	public static void remind_OneDay(LocalDateTime targetDateTime, kegiatan reminder) {
	    	
	    	Timer timer = new Timer();
	        TimerTask task = new TimerTask() {
	            @Override
	            public void run() {
	            	LocalDateTime now = LocalDateTime.now();
                	Duration duration = Duration.between(now, targetDateTime);
                	Integer a = (int) duration.toDays();
                    if (duration.toDays() <= 1 && now.isBefore(targetDateTime)) {
	                    try {
	                        Reminder.reminder.displayTray("Less than a day remains before the deadline for '" + reminder.nama + "'");                        } catch (AWTException e) {
                            e.printStackTrace();
                        }
	                    timer.cancel();
                    }
                }
	        };
	        timer.scheduleAtFixedRate(task, 0, 60000);
	        timers.computeIfAbsent(reminder, k -> new ArrayList<>()).add(timer);
	}
	public static void stopTimerForReminder(kegiatan reminder) {
		ArrayList<Timer> timerList = timers.get(reminder);
	    if (timerList != null) {
	    	for (Timer timer : timerList) {
	            timer.cancel(); // Cancel each timer
	        }
	    	timerList.clear(); // Clear the list of timers
	        timers.remove(reminder); // Remove the association from the map
	    }
	}
	public static void stopTimerForAllReminder(ArrayList<kegiatan> reminderList) {
		for(kegiatan reminder : reminderList) {
			stopTimerForReminder(reminder);
		}
	    
	}
}
