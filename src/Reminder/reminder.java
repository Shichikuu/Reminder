
package Reminder;
import java.util.*;
import java.io.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class kegiatan{
	String nama;
	LocalDateTime deadline;
	String desc;
	String Type;
	String flag;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
	public kegiatan(String nama, LocalDateTime deadline, String desc, String Type, String flag) {
		super();
		this.nama = nama;
		this.deadline = deadline;
		this.desc = desc;
		this.Type = Type;
		this.flag = flag;
	}
	
	public String getStringDeadline() {
		return deadline.format(formatter);
	}
	public void setFinished() {
		this.flag = "Selesai";
	}
	public void setNama(String nama) {
		this.nama = nama;
	}
	public void setDeadline(LocalDateTime deadline) {
		this.deadline = deadline;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}

class tugas extends kegiatan{

	public tugas(String nama, LocalDateTime deadline, String desc, String flag) {
		super(nama, deadline, desc, "tugas",flag);
	}
	
	public void printData() {
		String end = deadline.format(formatter);
		System.out.printf("| %15s | %-8s | %-16s | %-30s | %-15s | %-7s |\n",nama,"Tugas",end,desc,"-",flag);
	}
	
	public String[] getData() {
		String end = deadline.format(formatter);
		String[] StringData =  {nama, "Tugas", end, desc, "-", flag};
		return StringData;
	}
	
}

class meeting extends kegiatan{
	String location;

	public meeting(String nama, LocalDateTime deadline, String desc, String location, String flag) {
		super(nama, deadline, desc, "meeting",flag);
		this.location = location;
	}
	public void printData() {
		String end = deadline.format(formatter);
		System.out.printf("| %-15s | %-8s | %-16s | %-30s | %-15s | %-7s |\n",nama,"Meeting",end,desc,location,flag);
	}
	
	public String[] getData() {
		String end = deadline.format(formatter);
		String[] StringData =  {nama, "Meeting", end, desc, location, flag};
		return StringData;
	}
	
}

class hangout extends kegiatan{
	String location;

	public hangout(String nama, LocalDateTime deadline, String desc, String location, String flag) {
		super(nama, deadline, desc, "hangout",flag);
		this.location = location;
	}
	public void printData() {
		String end = deadline.format(formatter);
		System.out.printf("| %-15s | %-8s | %-16s | %-30s | %-15s | %-7s |\n",nama,"Hangout",end,desc,location,flag);
	}
	
	public String[] getData() {
		String end = deadline.format(formatter);
		String[] StringData =  {nama, "Hangout", end, desc, location, flag};
		return StringData;
	}
	
}

class lainnya extends kegiatan{

	public lainnya(String nama, LocalDateTime deadline, String desc, String flag) {
		super(nama, deadline, desc, "lainnya",flag);
	}
	public void printData() {
		String end = deadline.format(formatter);
		System.out.printf("| %-15s | %-8s | %-16s | %-30s | %-15s | %-7s |\n",nama,"Lainnya",end,desc,"-",flag);
	}
	public String[] getData() {
		String end = deadline.format(formatter);
		String[] StringData =  {nama, "Lainnya", end, desc, "-", flag};
		return StringData;
	}
	
	
}


public class reminder{
	
	static ArrayList<kegiatan> reminderList = io.CreateFile();
	static Scanner sc = new Scanner(System.in);
	
	private JFrame mainFrame;
    private JPanel controlPanel;

    public reminder() throws AWTException {
        // Call the prepareGUI method to set up the GUI
    	
        prepareGUI();
    }
	
	public static void main(String[] args) throws AWTException {
		startCountdownThreads();
		
		new reminder();
	}


    public void prepareGUI() throws AWTException{
        // Create the main frame and set its size and layout
        mainFrame = new JFrame("ReminderApplication");
        mainFrame.setSize(800, 600);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());
        GridBagConstraints headerGbc = new GridBagConstraints();
        headerGbc.gridwidth = GridBagConstraints.REMAINDER;
        headerGbc.fill = GridBagConstraints.HORIZONTAL;
        headerGbc.anchor = GridBagConstraints.NORTH;
        controlPanel.setBackground(new Color(169, 169, 169));
        
        
    	// Remainder List Panel
        
        String[] columnNames = { "Nama Kegiatan", "Tipe", "Waktu Tenggat", "Deskripsi", "Lokasi", "Status" };
        JTable reminderListTable = new JTable();
        DefaultTableModel dtm = new DefaultTableModel(0, 0);
        refreshTable(dtm);


        dtm.setColumnIdentifiers(columnNames);
        reminderListTable.setModel(dtm);
        JScrollPane reminderListPanel = new JScrollPane(reminderListTable);
        refreshTable(dtm);
        reminderListTable.repaint();
        Button addButton = new Button("Add Kegiatan");
        Button deleteButton = new Button("Delete Kegiatan");
        Button deleteAllButton = new Button("Delete All Kegiatan");
        Button editButton = new Button("Edit Kegiatan");
        
        addButton.addActionListener(
        	    new ActionListener() {
        	        public void actionPerformed(ActionEvent e) { // ADD REMINDER
        	            
        	            String name;
        	            String type;
        	            String deadlineString;
        	            String desc;
        	            String location;
        	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        	            boolean isValid;
        	            // Validate Name
        	            do {
        	                name = JOptionPane.showInputDialog(null, "Enter Reminder Name (<=15 characters):");
        	                if (name == null) { // User pressed cancel
        	                    return; // Custom exception to indicate cancellation
        	                }

        	            } while (name.isBlank() || name.length() > 15);
        	            // Validate Type
        	            do {
        	                type = JOptionPane.showInputDialog(null, "Enter Reminder Type (tugas/meeting/hangout/lainnya):").toLowerCase();
        	                if (type == null) return; // Handle cancel option
        	            } while (!type.equals("tugas") && !type.equals("meeting") && !type.equals("hangout") && !type.equals("lainnya"));

        	            // Validate Deadline
        	            do {
        	                isValid = true;
        	                deadlineString = JOptionPane.showInputDialog("Enter Deadline (yyyy/MM/dd HH:mm):", "Example : 2024/11/17 15:30");
        	                if (deadlineString == null) return; // Handle cancel option
        	                try {
        	                    LocalDateTime dateTime = LocalDateTime.parse(deadlineString, formatter);
        	                    if (!dateTime.isAfter(LocalDateTime.now())) {
        	                        isValid = false;
        	                        JOptionPane.showMessageDialog(null, "Deadline must be in the future.");
        	                    }
        	                } catch (DateTimeParseException ex) {
        	                    isValid = false;
        	                    JOptionPane.showMessageDialog(null, "Invalid date format.");
        	                }
        	            } while (!isValid);

        	            // Validate Description
        	            do {
        	                desc = JOptionPane.showInputDialog(null, "Enter Description (<=30 characters):");
        	                if (desc == null) return; // Handle cancel option
        	            } while (desc.length() > 30);

        	            // Add reminder after validation
        	            kegiatan newReminder;
        	            switch (type.toLowerCase()) {
        	            case "tugas":
        	                newReminder = new tugas(name, io.convertDate(deadlineString), desc, " ");
        	                break;
        	            case "meeting":
        	            	do {
        	            		location = JOptionPane.showInputDialog(null, "Enter Location (<=15 karakter): ");
        	                    if (location == null) location = "";
        	            	}while(location.length()>15);
        	                
        	                newReminder = new meeting(name, io.convertDate(deadlineString), desc, location, " ");
        	                break;
        	            case "hangout":
        	            	do {
        	            		location = JOptionPane.showInputDialog(null, "Enter Location (<=15 karakter): ");
        	                    if (location == null) location = "";
        	            	}while(location.length()>15);
        	                newReminder = new hangout(name, io.convertDate(deadlineString), desc, location, " ");
        	                break;
        	            case "lainnya":
        	                newReminder = new lainnya(name, io.convertDate(deadlineString), desc, " ");
        	                break;
        	            default:
        	                System.out.println("Invalid reminder type.");
        	                return;
        	        }
        	        reminderList.add(newReminder);
        	        io.WriteToFile(newReminder);
        	        countdown.remind(newReminder.deadline, newReminder);
        	        countdown.remind_OneDay(newReminder.deadline, newReminder);
        	        JOptionPane.showMessageDialog(null, "Reminder added Successfully!");
        	        refreshTable(dtm);
        	        reminderListTable.revalidate();
        	        reminderListTable.repaint();
        	        }
        	    }
        	);

        deleteButton.addActionListener(
            new ActionListener() {
            	public void actionPerformed(ActionEvent e) {
            	   if(reminderList.isEmpty()) {
	           		JOptionPane.showMessageDialog(null, "Data is Empty!");
           			return;
            	   }
        	  String nameToDelete;
        	  do{
        		  nameToDelete = JOptionPane.showInputDialog(null, "Enter Reminder Name to Delete:");
        		  if (nameToDelete == null) return; // Handle cancel option
        	  }while (nameToDelete.isBlank() || nameToDelete.length() > 15);
        	   	if(delReminder(nameToDelete) == 1) {
        	   		JOptionPane.showMessageDialog(null, "Reminder with a name " + nameToDelete + " has been deleted!");
        	   	}else {
        	   		JOptionPane.showMessageDialog(null, "Reminder with a name " + nameToDelete + " is not found!");
        	   	}
        	   	refreshTable(dtm);
        	   	reminderListTable.revalidate();
        	   	reminderListTable.repaint();
               }
            	
            }
        );
        deleteAllButton.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                    	io.DeleteFile();
                    	countdown.stopTimerForAllReminder(reminderList);
	                    reminderList.clear();
	                    JTabbedPane tabbedPane = new JTabbedPane();
	                    tabbedPane.add(null, "All Reminder have been Deleted.");
	                    refreshTable(dtm);
	                    reminderListTable.revalidate();
	                    reminderListTable.repaint();
                    }
                }
            );
        editButton.addActionListener(
                new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                 	   if(reminderList.isEmpty()) {
     	           		JOptionPane.showMessageDialog(null, "Data is Empty!");
                			return;
                 	   }
	            		boolean flag = false;
	            		String nameToEdit;
	            		kegiatan reminder = null;
                		do{
                  		  	nameToEdit = JOptionPane.showInputDialog(null, "Enter reminder name: ");
                  		  	if (nameToEdit == null) return; // Handle cancel option
                  	  	}while (nameToEdit.isBlank() || nameToEdit.length() > 15);
                		Iterator<kegiatan> iterator = reminderList.iterator();
                        while (iterator.hasNext()) {
                            reminder = iterator.next();
                            if (reminder.nama.equalsIgnoreCase(nameToEdit)) {
                            	flag = true;
                                break;
                            }
                        }
	            		if(flag==true) {
	            			editGUI(reminder,dtm,reminderListTable);
	            		}else {
		                    JOptionPane.showMessageDialog(null, "Reminder with a name " + nameToEdit + " is not found!");
	            		}
                	} 
                	
                }
        );
        // Add buttons to the control panel
        controlPanel.add(addButton);  
        controlPanel.add(deleteButton);
        controlPanel.add(deleteAllButton);
        controlPanel.add(editButton);
        mainFrame.add(controlPanel, BorderLayout.NORTH);
        mainFrame.add(reminderListPanel, BorderLayout.CENTER);
        // Set the visibility of the main frame to true
        mainFrame.setVisible(true);
    }
    
    private static void refreshTable(DefaultTableModel dtm) {
    	dtm.setRowCount(0);
    	for(kegiatan data : reminderList) {
			if(data instanceof tugas) {
				dtm.addRow(((tugas) data).getData());
			}else if(data instanceof meeting) {
				dtm.addRow(((meeting) data).getData());
			}else if(data instanceof hangout) {
				dtm.addRow(((hangout) data).getData());
			}else {
				dtm.addRow(((lainnya) data).getData());
			}
		}
    }
    
    private static void editGUI(kegiatan reminder, DefaultTableModel dtm, JTable reminderListTable) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JFrame frame = new JFrame();
        frame.setSize(300, 200);
        // Create buttons for choices
        Button option1Button = new Button("Name");
        Button option2Button = new Button("Deadline");
        Button option3Button = new Button("Description");
        Button option4Button = new Button("Mark as Finished");
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        option1Button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String name;
        		do {
	                name = JOptionPane.showInputDialog(null, "Enter New Reminder Name (<=15 characters):");
	                if (name == null) { // User pressed cancel
	                    return;
	                }

	            } while (name.isBlank() || name.length() > 15);
        		io.modifyFile(reminder, name, 0);
            	reminder.setNama(name);
            	JOptionPane.showMessageDialog(null, "Name has been edited");
                frame.dispose();
                refreshTable(dtm);
        		reminderListTable.revalidate();
                reminderListTable.repaint();
            }
        });
        option2Button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		LocalDateTime dateTime = null;
        		boolean isValid = false;
        		String deadlineString;
        		do {
	                isValid = true;
	                deadlineString = JOptionPane.showInputDialog("Enter New Deadline (yyyy/MM/dd HH:mm):", "Example : 2024/11/17 15:30");
	                if (deadlineString == null) return; // Handle cancel option
	                try {
	                    dateTime = LocalDateTime.parse(deadlineString, formatter);
	                    if (!dateTime.isAfter(LocalDateTime.now())) {
	                        isValid = false;
	                        JOptionPane.showMessageDialog(null, "Deadline must be in the future.");
	                    }
	                } catch (DateTimeParseException ex) {
	                    isValid = false;
	                    JOptionPane.showMessageDialog(null, "Invalid date format.");
	                }
	            } while (!isValid);
        		countdown.stopTimerForReminder(reminder);
        		io.modifyFile(reminder, deadlineString, 2);
            	reminder.setDeadline(dateTime);
            	countdown.remind(reminder.deadline, reminder);
    	        countdown.remind_OneDay(reminder.deadline, reminder);
            	JOptionPane.showMessageDialog(null, "Deadline has been edited!");
                frame.dispose(); 
                refreshTable(dtm);
        		reminderListTable.revalidate();
                reminderListTable.repaint();
            }
        });
        option3Button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String desc;
        		do {
	                desc = JOptionPane.showInputDialog(null, "Enter New Description (<=30 characters):");
	                if (desc == null) return; // Handle cancel option
	            } while (desc.length() > 30);
        		io.modifyFile(reminder, desc, 3);
            	reminder.setDesc(desc);
            	JOptionPane.showMessageDialog(null, "Description has been edited!");
            	frame.dispose(); 
            	refreshTable(dtm);
        		reminderListTable.revalidate();
                reminderListTable.repaint();
        	}
        });
        option4Button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		io.modifyFile(reminder, "Selesai", 5);
            	reminder.setFinished();
            	JOptionPane.showMessageDialog(null, "Reminder " + reminder.nama + " has been marked as Finished!");
            	frame.dispose();
            	refreshTable(dtm);
        		reminderListTable.revalidate();
                reminderListTable.repaint();
        	}
        });

        // Add buttons to the frame
        panel.add(option1Button);
        panel.add(option2Button);
        panel.add(option3Button);
        panel.add(option4Button);
        
        frame.add(panel,BorderLayout.CENTER);

        // Display the frame
        frame.setVisible(true);
    }
    
    static public void displayTray(String line) throws AWTException {
        //Obtain only one instance of the SystemTray object
        SystemTray tray = SystemTray.getSystemTray();

        //If the icon is a file
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        //Alternative (if the icon is on the classpath):
        //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));

        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
        //Let the system resize the image if needed
        trayIcon.setImageAutoSize(true);
        //Set tooltip text for the tray icon
        trayIcon.setToolTip("Reminder");
        tray.add(trayIcon);

        trayIcon.displayMessage("Reminder", line,  TrayIcon.MessageType.INFO);
    }
    
	
	private static void startCountdownThreads() {
        for (kegiatan reminder : reminderList) {
            countdown.remind(reminder.deadline, reminder);
            countdown.remind_OneDay(reminder.deadline, reminder);
        }
    }
	
	public static int delReminder(String nameToDelete) {
		//Buat delete 1 reminder

	        Iterator<kegiatan> iterator = reminderList.iterator();
	        while (iterator.hasNext()) {
	            kegiatan reminder = iterator.next();
	            if (reminder.nama.equalsIgnoreCase(nameToDelete)) {
	                iterator.remove();
	                io.DeleteReminderFromFile(reminder);
	                countdown.stopTimerForReminder(reminder);
	                System.out.println("Reminder deleted successfully.");
	                return 1;
	            }
	        }
	        System.out.println("Reminder not found.");
	        return 0;
	}
}


