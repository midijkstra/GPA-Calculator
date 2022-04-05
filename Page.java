import java.io.*;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.ComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Page extends Pane {

	private Cell[] cells;

	public Page() {
		cells = new Cell[10];
		for (int i = 0; i < 10; i++) {
			cells[i] = new Cell();
		}
		drawPage();
		drawCells();
	}

	public Page(Cell[] cells) {
		this.cells = cells;
		drawPage();
		drawCells();
	}

	public Cell[] getCells() {
		return cells;
	}

	public void setCells(Cell[] c) {
		for (int i = 0; i < 10; i++) {
			if (!c[i].getGrade().equals("-")) {
				cells[i].setName(c[i].getName());
				cells[i].setCredits(c[i].getCredits());
				cells[i].setGrade(c[i].getGrade());
			}
		}
		cells = c;
	}	

	private void drawPage() {
		setPrefSize(500, 400);
		Rectangle page = new Rectangle(400, 400);
		page.setFill(Color.WHITE);
		page.setStrokeWidth(3);
		page.setStroke(Color.BLACK);
		getChildren().add(page);

		Text label1 = new Text("Subject");
		label1.relocate(10, 5);
		getChildren().add(label1);

		Text label2 = new Text("Credits");
		label2.relocate(265, 5);
		getChildren().add(label2);

		Text label3 = new Text("Grade");
		label3.relocate(325, 5);
		getChildren().add(label3);
	}

	private void drawCells() {

		int y = 30;

		for (int i = 0; i < 10; i++) {
			cells[i].relocate(10, y);
			getChildren().add(cells[i]);
			y+=35;
		}
	}	
}


class Cell extends Pane {

	private TextField tfName;
	private TextField tfCredits;
	private ComboBox<String> tfGrade;

	Cell() {

		setPrefSize(300, 30);

		tfName = new TextField();
		tfName.setPrefSize(250, 30);
		tfName.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.length() > 20) tfName.setText(newValue.substring(0, 20));
		});

		tfCredits = new TextField();
		tfCredits.setPrefSize(50, 30);
		tfCredits.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.length() > 2) tfCredits.setText(newValue.substring(0, 2));
		});

		tfGrade = new ComboBox<>();
		tfGrade.getItems().addAll("-", "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "F");		
		tfGrade.setValue("-");
		tfGrade.setVisibleRowCount(3);

		HBox panel = new HBox(5);
		panel.getChildren().addAll(tfName, tfCredits, tfGrade);
		getChildren().add(panel);

	}

	public void setName(String name) {
		tfName.setText(name);
	}

	public void setCredits(int credits) {
		tfCredits.setText(Integer.toString(credits));
	}

	public void setGrade(String grade) {
		tfGrade.setValue(grade);
	}

	public String getName() {
		return tfName.getText();
	}

	public int getCredits() {
		return Integer.parseInt(tfCredits.getText());
	}

	public String getGrade() {
		return tfGrade.getValue();
	}

}