package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.FileChooser.ExtensionFilter;

public class RootController implements Initializable {

	@FXML TextArea text_area;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}
	
	private Stage primaryStage;
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	public void handleNew(ActionEvent event) {
		text_area.setText("");
	}
	
	public void handleOpen(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("Text Files", "*.txt")
				);
		File selectedFile = fileChooser.showOpenDialog(primaryStage);
		if (selectedFile != null) {
			text_area.setText(getTextFromFile(selectedFile));
		}
	}
	
	public String getTextFromFile(File txtFile) {
		String text = "";
		BufferedReader br = null;
		try {
			br = new BufferedReader((
					new InputStreamReader(new FileInputStream
									(txtFile))));
			String line;
			while((line = br.readLine()) != null) {
				text = text + (line + "\n");
			}
			 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text;
	}
	
	
	public void handleSave(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(
				new ExtensionFilter("All Files",  "*.*")
				);
		File selectedFile = fileChooser.showSaveDialog(primaryStage);
		if (selectedFile != null) {
			saveFile(selectedFile);
		}
	}
	
	private void saveFile(File file) {
		
		try {
			FileWriter writer = null;
			writer = new FileWriter(file);
			writer.write(text_area.getText()
					.replaceAll("\n", "\r\n"));
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void handleQuit(ActionEvent event) {
		Platform.exit();
	}
	
	public void handleAbout(ActionEvent e) {
		Stage dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initOwner(primaryStage);
		dialog.setTitle("°æ°í");
		
		try {
			Parent parent = FXMLLoader.load(
					getClass().getResource("about.fxml"));
			Button btnOK = (Button)parent.lookup("#btn_ok");
			btnOK.setOnAction(event->dialog.close());
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.setResizable(false);
			dialog.setTitle("My Note Pad 1.0");
			dialog.show();
		} catch (IOException x) {
			// TODO Auto-generated catch block
			x.printStackTrace();
		}
	}
	
	
	
	

}
