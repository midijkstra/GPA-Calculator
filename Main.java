import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import java.io.IOException;

public class Main extends Application {

	@Override
	public void start(Stage stage) throws IOException  {

		MainField mainField = new MainField();
		Scene scene = new Scene(mainField, 600, 450);

		stage.getIcons().add(new Image("/zero.png"));
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Zero: GPA Calculator");
		stage.show();
	}	
}