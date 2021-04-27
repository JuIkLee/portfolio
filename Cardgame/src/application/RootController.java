package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RootController implements Initializable {

	@FXML ImageView card0, card1, card2, 
					card3, card4, card5;
	@FXML Label lbl_title;
	ImageView[][] image_obj = new ImageView[3][2];
	
	public static final int STATE_READY = 0;
	public static final int STATE_GAME = 1;
	public static final int STATE_END = 2;
	
	public int m_state = STATE_READY;
	Card m_Shuffle1=null, m_Shuffle2=null;
	Card m_Card[][] = new Card[3][2];
	int m_column=3, m_row=2;
	
	
	private Stage primaryStage;
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		image_obj[0][0] = card0;
		image_obj[1][0] = card1;
		image_obj[2][0] = card2;
		image_obj[0][1] = card3;
		image_obj[1][1] = card4;
		image_obj[2][1] = card5;
		Card_Shuffle(m_column, m_row);
		OnDraw();
		CardGameThread thread = new CardGameThread(this);
		thread.start();
	}
	public void Card_Shuffle(int column, int row) {
		for(int y=0;y<row;y++)
			for(int x=0;x<column;x++) {
				m_Card[x][y] = new Card((y*column+x)/2);
			}
		for(int i=0;i<100;i++) {
			int x = (int)(Math.random() * column);
			int y = (int)(Math.random() * row);
			m_Shuffle1 = m_Card[x][y];
			x = (int)(Math.random() * column);
			y = (int)(Math.random() * row);
			m_Shuffle2 = m_Card[x][y];
			int temp = m_Shuffle1.m_image;
			m_Shuffle1.m_image = m_Shuffle2.m_image;
			m_Shuffle2.m_image = temp;
		}
		m_Shuffle1 = null;
		m_Shuffle2 = null;
	}
	public void OnDraw() {
		if(m_state == STATE_READY) {
			All_Card_View();
		} else if(m_state == STATE_GAME){
			for(int y=0;y<m_row;y++)
				for(int x=0;x<m_column;x++) {
					
					if(m_Card[x][y].m_state == Card.CARD_CLOSE) {
						image_obj[x][y].setImage(
								new Image(getClass().getResource(
										"images/backside.jpg").toString()));
					}else {
						View_Front_Image(x, y);
					}
				}
		}
	}
	public void All_Card_View() {
		for(int y=0;y<m_row;y++)
			for(int x=0;x<m_column;x++) {
				View_Front_Image(x, y);
			}
	}
	public void View_Front_Image(int x, int y){
		switch(m_Card[x][y].m_image) {
		case Card.IMG_0 : image_obj[x][y].setImage(
				new Image(getClass().getResource(
						"images/cap.jpg").toString()));
			break;
		case Card.IMG_1 : image_obj[x][y].setImage(
				new Image(getClass().getResource(
						"images/hulk.jpg").toString()));
			break;
		case Card.IMG_2 : image_obj[x][y].setImage(
				new Image(getClass().getResource(
						"images/ironman.jpg").toString()));
			break;
		}
	}
	
	public void OnMouseClickedStart(MouseEvent event) {
		/*int px = (int)event.getX();
		int py = (int)event.getY();
		lbl_title.setText("x : " + px + "\n" + "y : " + py);*/
		if(m_state == STATE_READY) {
			m_state = STATE_GAME;
			all_card_close();
			OnDraw();
		}else if (m_state == STATE_END) {
			
			try {
				FXMLLoader loader = new FXMLLoader(
						getClass().getResource("next_stage.fxml"));
				Parent root;
				root = loader.load();
				Scene scene = new Scene(root);
								
				primaryStage.setScene(scene);
				primaryStage.setTitle("Pair Game - Stage2");
				primaryStage.setResizable(false);
				primaryStage.show();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	public void all_card_close() {
		for(int y=0;y<m_row;y++)
			for(int x=0;x<m_column;x++) {
				m_Card[x][y].m_state = Card.CARD_CLOSE;
			}
	}
	public void OnMouseClickedImageView(MouseEvent event) {
		//if(m_Shuffle1 != null && m_Shuffle2 != null)return;
		for(int y=0;y<m_row;y++)
			for(int x=0;x<m_column;x++) {
				//if( (image_obj[x][y] == event.getSource()) && (m_Shuffle2 == null) ) {
				if(image_obj[x][y] == event.getSource()) {	
					if(m_Card[x][y].m_state == Card.CARD_CLOSE) {
						
						if(m_Shuffle1 == null) {
							m_Shuffle1 = m_Card[x][y];
							m_Card[x][y].m_state = Card.CARD_PLAYEROPEN;
						}
						/*else if(m_Shuffle1 != null 
								&& m_Shuffle2 != null) {
							m_Card[x][y].m_state = Card.CARD_CLOSE;
						}*/
						else if(m_Shuffle2 == null){
							m_Shuffle2 = m_Card[x][y];
							m_Card[x][y].m_state = Card.CARD_PLAYEROPEN;
						}
						OnDraw();
					}
					return;
				}
			}
	}
	public void checkMatch() {
		if(m_Shuffle1 == null || m_Shuffle2 == null)return;
		if(m_Shuffle1.m_image == m_Shuffle2.m_image) {
			m_Shuffle1.m_state = Card.CARD_MATCHED;
			m_Shuffle2.m_state = Card.CARD_MATCHED;
			m_Shuffle1 = null;
			m_Shuffle2 = null;
			for(int y=0;y<m_row;y++)
				for(int x=0;x<m_column;x++) {
					if(m_Card[x][y].m_state != Card.CARD_MATCHED) {
						OnDraw();
						return;
					}
				}
			m_state = STATE_END;
		}else {
			try {
				Thread.sleep(800);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			m_Shuffle1.m_state = Card.CARD_CLOSE;
			m_Shuffle2.m_state = Card.CARD_CLOSE;
			m_Shuffle1 = null;
			m_Shuffle2 = null;
		}
		OnDraw();
	}
}






