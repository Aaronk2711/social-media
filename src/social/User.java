package social;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

public class User {
	
	private int user_id;
	private String firstName;
	private String lastName;
	private String college;
	private String school;
	private String hometown;
	private LocalDate birthday;
	private String email;
	private long phone;
	private Instant lastMod;
	private Instant lastLogin;
	private String password;
	private byte[] profilePic;
	private String encPic;
	private String pfp64;
	
	public User(int user_id, String firstName, String lastName, String college, String school, String hometown, LocalDate birthday, String email, long phone, Instant lastMod, Instant lastLogin, String password, byte[] profilePic) {
		this.user_id = user_id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.college = college;
		this.school = school;
		this.hometown = hometown;
		this.birthday = birthday;
		this.email = email;
		this.phone = phone;
		this.lastMod = lastMod;
		this.lastLogin = lastLogin;
		this.password = password;
		this.profilePic = profilePic;
		
		setPfp64(Base64.getEncoder().encodeToString(profilePic));
	}
	
	public int getId() {
		return user_id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getCollege() {
		return college;
	}
	
	public void setCollege(String college) {
		this.college = college;
	}
	
	public String getSchool() {
		return school;
	}
	
	public void setSchool(String school) {
		this.school = school;
	}
	
	public String getHometown() {
		return hometown;
	}
	
	public void setHometown(String hometown) {
		this.hometown = hometown;
	}
	
	public LocalDate getBirthday() {
		return birthday;
	}
	
	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Long getPhone() {
		return phone;
	}
	
	public void setPhone(Long phone) {
		this.phone = phone;
	}
	
	public Instant getLastMod() {
		return lastMod;
	}
	
	public void setLastMod(Instant lastMod) {
		this.lastMod = lastMod;
	}
	
	public Instant getLastLogin() {
		return lastLogin;
	}
	
	public void setLastLogin(Instant lastLogin) {
		this.lastLogin = lastLogin;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public byte[] getProfilePic() {
		return profilePic;
	}
	
	public void setProfilePic(byte[] profilePic) {
		this.profilePic = profilePic;
		this.setEncPic(Base64.getEncoder().encodeToString(profilePic));
	}
	
	public String getEncPic() {
		return encPic;
	}
	
	public void setEncPic(String encPic) {
		this.encPic = encPic;
	}
	
	public String formatInstant(Instant instant) {
	    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());

	    return DATE_TIME_FORMATTER.format(instant);
	}

	public String getPfp64() {
		return pfp64;
	}

	public void setPfp64(String pfp64) {
		this.pfp64 = pfp64;
	}

}
