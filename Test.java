import java.io.*;
import java.util.Scanner;

public class Test {
	public static void main(String[] args) throws IOException, ClassNotFoundException {

		try ( 
			ObjectInputStream input =
 			new ObjectInputStream(new FileInputStream("data.bin"));
 		) {
			boolean continueInput = true;
			Semester p = new Semester();

			while (continueInput) {

				try {
					p = (Semester)input.readObject();
				} catch (EOFException ex) {
					continueInput = false;
					break;
				}

				Subject[] c = p.getSubjects();
				for (int i = 0; i < 10; i++) { 
					System.out.println(c[i].getName() + " " + c[i].getCredits() + " " + c[i].getGrade());
				}
			}
 		}
	}
}