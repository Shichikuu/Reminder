package Reminder;
import java.io.*;
import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class io {
	  public static ArrayList<kegiatan> CreateFile() { //Create file kalo gk ada filenya, kalo sdh ada, read file, return arrayList untuk reminder nya
		 ArrayList<kegiatan> reminderList = new ArrayList<>();
	    try {
	      File myObj = new File("activity.txt");
	      if (myObj.createNewFile()) {
	        System.out.println("File created: " + myObj.getName());
	      } else {
	        reminderList = ReadFile();
	      }
	    } catch (IOException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	    }
	    return reminderList;
	  }
	  public static LocalDateTime convertDate(String dateString) { // Convert dari String date ke LocalDateTime date
		  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
	      return LocalDateTime.parse(dateString, formatter);
	  }
	  public static ArrayList<kegiatan> ReadFile() {
		  ArrayList<kegiatan> reminderList = new ArrayList<>();
		  String nama;
		  LocalDateTime deadline;
		  String desc;
		  String Type;
		  String location;
		  String flag;
		  try (BufferedReader br = new BufferedReader(new FileReader("activity.txt"))) {
	            String line;
	            while ((line = br.readLine()) != null) {
	                // Split the line by the semicolon
	                String[] parts = line.split(";");
	                nama = parts[0];
	                Type = parts[1];
			        deadline = convertDate(parts[2]);
			        desc = parts[3];
			        location = parts[4];
			        flag = parts[5];
	                if(Type.equals("tugas")) {
	                	tugas tugas = new tugas(nama, deadline, desc,flag);
	                	reminderList.add(tugas);
	                }else if(Type.equals("meeting")) {
	                	meeting meeting = new meeting(nama, deadline, desc, location, flag);
	                	reminderList.add(meeting);
	                }else if(Type.equals("hangout")) {
	                	hangout hangout = new hangout(nama, deadline, desc, location, flag);
	                	reminderList.add(hangout);
	                }else if(Type.equals("lainnya")) {
	                	lainnya lainnya = new lainnya(nama, deadline, desc, flag);
	                	reminderList.add(lainnya);
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		  
		  return reminderList;
	  }
	  public static void DeleteFile() {
		  File myObj = new File("activity.txt"); 
		    if (myObj.delete()) { 
		      System.out.println("Deleted the file: " + myObj.getName());
		    } else {
		      System.out.println("Failed to delete the file.");
		    }
	  }
	      public static void WriteToFile(kegiatan reminder) {
	          try (BufferedWriter writer = new BufferedWriter(new FileWriter("activity.txt", true))) {
	              String reminderString = String.format(
	                      "%s;%s;%s;%s;%s;%s\n",
	                      reminder.nama,
	                      reminder.Type,
	                      reminder.deadline.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")),
	                      reminder.desc,
	                      (reminder instanceof meeting || reminder instanceof hangout) ? ((meeting) reminder).location : "-",
	                      reminder.flag
	              );
	              writer.write(reminderString);
	              System.out.println("Reminder written to file.");
	          } catch (IOException e) {
	              e.printStackTrace();
	          }
	      }
	      public static void DeleteReminderFromFile(kegiatan reminder) {
	          try {
	              List<String> lines = new ArrayList<>();
	              File file = new File("activity.txt");
	              BufferedReader reader = new BufferedReader(new FileReader(file));
	              String lineToRemove = String.format(
	                      "%s;%s;%s;%s;%s;%s",
	                      reminder.nama,
	                      reminder.Type,
	                      reminder.deadline.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")),
	                      reminder.desc,
	                      (reminder instanceof meeting || reminder instanceof hangout) ? ((meeting) reminder).location : "-",
	                      reminder.flag
	              );
	              String currentLine;
	              while ((currentLine = reader.readLine()) != null) {

	                  if (!currentLine.equals(lineToRemove)) {
	                      lines.add(currentLine);
	                  }
	              }
	              reader.close();
	              BufferedWriter writer = new BufferedWriter(new FileWriter(file));
	              for (String line : lines) {
	                  writer.write(line + System.lineSeparator());
	              }
	              writer.close();
	              System.out.println("Reminder deleted from file.");
	          } catch (IOException e) {
	              e.printStackTrace();
	          }
	      }
	      
	      public static void modifyFile(kegiatan reminder, String newVal, int position) {
	          
	          try {
	              List<String> lines = new ArrayList<>();
	              File file = new File("activity.txt");
	              BufferedReader reader = new BufferedReader(new FileReader(file));
	              String lineToEdit = String.format(
	                      "%s;%s;%s;%s;%s;%s",
	                      reminder.nama,
	                      reminder.Type,
	                      reminder.deadline.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")),
	                      reminder.desc,
	                      (reminder instanceof meeting || reminder instanceof hangout) ? ((meeting) reminder).location : "-",
	                      reminder.flag
	              );
	              String[] fields = lineToEdit.split(";");
	              String currentLine;
                  fields[position] = newVal;
                  String editedLine = String.join(";", fields);
	              
	              while ((currentLine = reader.readLine()) != null) {

	                  if (!currentLine.equals(lineToEdit)) {
	                      lines.add(currentLine);
	                  }else {
	                	  lines.add(editedLine);
	                  }
	              }
	              reader.close();
	              BufferedWriter writer = new BufferedWriter(new FileWriter(file));
	              for (String line : lines) {
	                  writer.write(line + System.lineSeparator());
	              }
	              writer.close();
	              System.out.println("Reminder Edited from file.");
	          } catch (IOException e) {
	              e.printStackTrace();
	          }
	      }
}