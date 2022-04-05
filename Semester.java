import java.io.Serializable;

public class Semester implements Serializable {
		
	private Subject[] subjects;

	public Semester() {
		subjects = new Subject[10];
	}

	public Subject[] getSubjects() {
		return subjects;
	}

	public void setSubjects(Subject[] subjects) {
		this.subjects = subjects;
	}
}

class Subject implements Serializable {

	private String name;
	private int credits;
	private String grade;

	public Subject() {}

	public Subject(String name, int credits, String grade) {
		this.name = name;
		this.credits = credits;
		this.grade = grade;
	}

	public void setName(String name) {
		this.name = name;
	}	

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getName() {
		return name;
	}

	public int getCredits() {
		return credits;
	}

	public String getGrade() {
		return grade;
	}

}