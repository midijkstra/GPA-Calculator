import java.util.ArrayList;
import java.io.*;

import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.FontWeight; 
import javafx.scene.text.FontPosture;

public class MainField extends Pane {

	private Rectangle mainField;
	private ArrayList<Page> pages;
	private BorderPane mainPain;
	private Circle circleGpa;
	private Text gradeText;
	private Circle circleSpa;
	private Text semesterText;

	public MainField() throws IOException {
	 	
		pages = new ArrayList<>();
		drawMainField();
		drawCircle();
		
		File file = new File("data.bin");
		try {
			setPages();
		} catch (EOFException | FileNotFoundException ex) {
			if (file.exists()) {
				file.delete();
				file.createNewFile();
			} else {
				file.createNewFile();
			}

			if (pages.size() == 0) {
				Page page = new Page();
 				mainPain.setCenter(page);
				pages.add(page);
			}
		}
	}	

	private void drawMainField() {
		mainField = new Rectangle(8, 5, 595, 445);
		mainField.setStroke(Color.BLACK);
		mainField.setStrokeWidth(3);
		mainField.setFill(Color.WHITE);
		getChildren().add(mainField);

		mainPain = new BorderPane();
		mainPain.setPrefSize(500, 400);
		mainPain.setPadding(new Insets(0, 0, 0, 0));
		mainPain.relocate(8, 5);
		getChildren().add(mainPain);

		Button btPrev = new Button("<");
		btPrev.setPrefSize(90, 15);
		btPrev.setStyle("-fx-background-color: #C0C0C0;");
		btPrev.setPadding(new Insets(2, 2, 2, 2));
		btPrev.setOnMouseEntered(event -> {
			btPrev.setStyle("-fx-background-color: #949494;");
		});
		btPrev.setOnMouseExited(event -> {
			btPrev.setStyle("-fx-background-color: #C0C0C0;");
		});


		Button add = new Button("Add");
		add.setPrefSize(50, 15);
		add.setStyle("-fx-background-color: #C0C0C0;");
		add.setPadding(new Insets(2, 2, 2, 2));
		add.setOnMouseEntered(event -> {
			add.setStyle("-fx-background-color: #949494;");
		});
		add.setOnMouseExited(event -> {
			add.setStyle("-fx-background-color: #C0C0C0;");
		});


		Button delete = new Button("Delete");
		delete.setPrefSize(50, 15);
		delete.setStyle("-fx-background-color: #C0C0C0;");
		delete.setPadding(new Insets(2, 2, 2, 2));
		delete.setOnMouseEntered(event -> {
			delete.setStyle("-fx-background-color: #949494;");
		});
		delete.setOnMouseExited(event -> {
			delete.setStyle("-fx-background-color: #C0C0C0;");
		});


		Button btNext = new Button(">");
		btNext.setPrefSize(90, 15);
		btNext.setStyle("-fx-background-color: #C0C0C0;");
		btNext.setPadding(new Insets(2, 2, 2, 2));
		btNext.setOnMouseEntered(event -> {
			btNext.setStyle("-fx-background-color: #949494;");
		});
		btNext.setOnMouseExited(event -> {
			btNext.setStyle("-fx-background-color: #C0C0C0;");
		});

		//Bottom Panel
		HBox bottomPanel = new HBox(30);
		bottomPanel.setMaxHeight(20);
		bottomPanel.relocate(20, 415);
		bottomPanel.setPadding(new Insets(0, 0, 0, 0));
		bottomPanel.getChildren().addAll(btPrev, add, delete, btNext);
		getChildren().add(bottomPanel);

		//Line 
		Line line = new Line(408, 405, 408, 448);
		line.setStrokeWidth(3);
		getChildren().add(line);

		// Save Button
		Button save = new Button("Save/Calculate");
		save.setPrefSize(150, 30);
		save.setStyle("-fx-background-color: #C0C0C0;");
		save.relocate(430, 410);
		getChildren().add(save);
		save.setOnMouseEntered(event -> {
			save.setStyle("-fx-background-color: #949494;");
		});
		save.setOnMouseExited(event -> {
			save.setStyle("-fx-background-color: #C0C0C0;");
		});


		add.setOnAction(event -> {
			pages.add(new Page());
			mainPain.setCenter(pages.get(pages.indexOf(mainPain.getCenter()) + 1));
		});

		delete.setOnAction(event -> {
			if (pages.size() > 0) {
				mainPain.setCenter(pages.get(pages.indexOf(mainPain.getCenter()) - 1));
				pages.remove(pages.indexOf(mainPain.getCenter()) + 1);
			}
		});

		btPrev.setOnAction(event -> {
			try {
				mainPain.setCenter(pages.get(pages.indexOf(mainPain.getCenter()) - 1));
				getSpa();
			} catch (IndexOutOfBoundsException ex) {}
		});

		btNext.setOnAction(event -> {
			try {
				mainPain.setCenter(pages.get(pages.indexOf(mainPain.getCenter()) + 1));
				getSpa();
			} catch (IndexOutOfBoundsException ex) {}
		});

		save.setOnAction(event -> {

			try (
				ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("data.bin"));
			) {
				for (Page page : pages) {

					Semester semester = new Semester();
					Cell[] tempCell = page.getCells();
					Subject[] tempSub = new Subject[10];
					for (int i = 0; i < 10; i++) {
						if (!tempCell[i].getGrade().equals("-")) {
							tempSub[i] = new Subject(tempCell[i].getName(), tempCell[i].getCredits(), tempCell[i].getGrade());
						} else {
							tempSub[i] = new Subject("-", 0, "-");
						}
					}
					semester.setSubjects(tempSub);
					output.writeObject(semester);
				}
			} catch (IOException ex) {}
			calculate();
		});
	}

	private void setPages() throws IOException {
		
		try (
			ObjectInputStream input = new ObjectInputStream(new FileInputStream("data.bin"));
		) {
			boolean continueInput = true;
			Semester s = new Semester();

			while (continueInput) {

				try {
					s = (Semester)input.readObject();
				} catch (EOFException | ClassNotFoundException ex) {
					continueInput = false;
					break;
				}

				Subject[] tempSub = s.getSubjects();
				Cell[] tempCell = new Cell[10];

				for (int i = 0; i < 10; i++) {
					tempCell[i] = new Cell();
					if (!tempSub[i].getGrade().equals("-")) {
						tempCell[i].setName(tempSub[i].getName());
						tempCell[i].setCredits(tempSub[i].getCredits());
						tempCell[i].setGrade(tempSub[i].getGrade());
					}
				}
				Page page = new Page(tempCell);
				mainPain.setCenter(page);
				pages.add(page);
				calculate();
				getSpa();
			}
		}
	}

	private void drawCircle() {

		circleGpa = new Circle(85);
		circleGpa.relocate(420, 30);
		circleGpa.setFill(Color.GRAY);

		Text label = new Text("GPA");
		label.setFont(Font.font("Times", FontWeight.NORMAL, FontPosture.REGULAR, 30));
		label.setFill(Color.WHITE);
		label.relocate(475, 45);

		gradeText = new Text();
		gradeText.setFont(Font.font("Times", FontWeight.BOLD, FontPosture.REGULAR, 50));
		gradeText.setFill(Color.WHITE);
		gradeText.relocate(455, 80);

		getChildren().addAll(circleGpa, label, gradeText);

		circleSpa = new Circle(55);
		circleSpa.relocate(450, 250);
		circleSpa.setFill(Color.GRAY);

		Text label2 = new Text("SPA");
		label2.setFont(Font.font("Times", FontWeight.NORMAL, FontPosture.REGULAR, 25));
		label2.setFill(Color.WHITE);
		label2.relocate(482, 255);

		semesterText = new Text();
		semesterText.setFont(Font.font("Times", FontWeight.BOLD, FontPosture.REGULAR, 35));
		semesterText.setFill(Color.WHITE);
		semesterText.relocate(470, 280);

		getChildren().addAll(circleSpa, label2, semesterText);
	}

	private void calculate() {

		int credCount = 0;
		double cred_points = 0;
		double gpa = 0;
		double temp = 0;

		for (Page page : pages) {
			Cell[] c = page.getCells();
			for (int i = 0; i < 10; i++) {
				if (!c[i].getGrade().equals("-")) {
					temp = c[i].getCredits() * getPointsForGrade(c[i].getGrade());
					cred_points += temp;
					credCount += c[i].getCredits();
					gpa = cred_points / credCount;

					circleGpa.setFill(Color.hsb( (int)(gpa * 100 / 4), 1, 0.7));
					gradeText.setText(format(gpa));
				}
			}
		}
		getSpa();
	}

	private void getSpa() {
		Page p = pages.get(pages.indexOf(mainPain.getCenter()));
		Cell[] sc = p.getCells();
		int creditsSpa = 0;
		double pointSpa = 0;
		double spa = 0;
		double tempSpa = 0;

		for (int i = 0; i < 10; i++) {
			if (!sc[i].getGrade().equals("-")) {
				tempSpa = sc[i].getCredits() * getPointsForGrade(sc[i].getGrade());
				pointSpa += tempSpa;
				creditsSpa += sc[i].getCredits();
				spa = pointSpa / creditsSpa;

				circleSpa.setFill(Color.hsb( (int)(spa * 100 / 4), 1, 0.7));
				semesterText.setText(format(spa));
			}
		}
	}

	private double getPointsForGrade(String s) {

		double result = 0;

		switch (s) {
			case "A": result = 4.0; break;
			case "A-": result = 3.67; break;
			case "B+": result = 3.33; break;
			case "B": result = 3.0; break;
			case "B-": result = 2.67; break;
			case "C+": result = 2.33; break;
			case "C": result = 2.0; break;
			case "C-": result = 1.67; break;
			case "D+": result = 1.33; break;
			case "D": result = 1.0; break;
			case "F": result = 0.0;
		}
		return result;
	}

	private String format(double d) {
		String s = Double.toString(d);
		if (s.length() > 4) {
			return s.substring(0, 4);
		} else if (s.length() < 4) {
			return s + "0";
		} 
		return s;
	}
}