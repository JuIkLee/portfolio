package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class RootController implements Initializable {

	
	@FXML Label lbl_result;
	
	private String operator = "";
	private Double number1 = 0.0;
	private boolean first_input_flag = true;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
	
	@FXML
	public void handleBtnNumbers(ActionEvent event) {
		
		String value = ((Button)event.getSource()).getText();
		if(first_input_flag) {
			first_input_flag = false;
			lbl_result.setText("");
		}
		lbl_result.setText(lbl_result.getText() + value);
	}
	@FXML
	public void handleBtnOperator(ActionEvent event) {
		String opcode = ((Button)event.getSource()).getText();
		
		if(operator.equals("") || operator.equals("=")) {
			number1 = Double.parseDouble(lbl_result.getText()); 
		}
		else {
			if(first_input_flag) {
				operator = opcode;
			}
			else {
				number1 = calculator(number1, 
						Double.parseDouble(lbl_result.getText()));
				lbl_result.setText(number1.toString());
			}
			
		}
		operator = opcode;
		first_input_flag = true;
	}
	public void handleBtnClear(ActionEvent event) {
		number1 = 0.0;
		operator = "";
		lbl_result.setText("0");
		first_input_flag = true;
	}
	
	public double calculator(double number1, double number2) {
		switch(operator) {
		case "+": return number1 + number2;
		case "-": return number1 - number2;
		case "*": return number1 * number2;
		case "/": return number1 / number2;
		default : return 0.0;
		}
	}

}


		
		
		