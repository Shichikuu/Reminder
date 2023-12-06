
package Reminder;
import java.util.*;

class kegiatan{
	String nama;
	int dateStart;
	int deadline;
	String desc;
	String Type;
	public kegiatan(String nama, int dateStart, int deadline, String desc, String type) {
		super();
		this.nama = nama;
		this.dateStart = dateStart;
		this.deadline = deadline;
		this.desc = desc;
		Type = type;
	}
	
	
}

class tugas extends kegiatan{
	String namaTugas;

	public tugas(String nama, int dateStart, int deadline, String desc, String namaTugas) {
		super(nama, dateStart, deadline, desc, "tugas");
		this.namaTugas = namaTugas;
	}
	
}

class meeting extends kegiatan{
	String namaMeeting;
	String location;

	public meeting(String nama, int dateStart, int deadline, String desc, String namaMeeting, String location) {
		super(nama, dateStart, deadline, desc, "meeting");
		this.namaMeeting = namaMeeting;
		this.location = location;
	}
	
}

class hangout extends kegiatan{
	String namaHangout;
	String location;

	public hangout(String nama, int dateStart, int deadline, String desc, String namaHangout, String location) {
		super(nama, dateStart, deadline, desc, "hangout");
		this.namaHangout = namaHangout;
		this.location = location;
	}
	
}

class lainnya extends kegiatan{
	String namaKegiatan;

	public lainnya(String nama, int dateStart, int deadline, String desc, String namaKegiatan) {
		super(nama, dateStart, deadline, desc, "lainnya");
		this.namaKegiatan = namaKegiatan;
	}
	
}


public class reminder {
	static ArrayList<kegiatan> reminderList = new ArrayList<>();
	public static void main(String[] args) {
		
	}

}
