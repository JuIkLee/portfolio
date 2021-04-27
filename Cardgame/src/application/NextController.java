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

public class NextController implements Initializable {

	@FXML ImageView next_card0, next_card1, next_card2, next_card3,
					next_card4, next_card5, next_card6, next_card7,
					next_card8, next_card9, next_card10, next_card11,
					next_card12, next_card13, next_card14, next_card15;
	@FXML Label lbl_title;
	int m_column, m_row;
	ImageView[][] image_obj = new ImageView[4][4];
	
	public static final int STATE_READY = 0;
	public static final int STATE_GAME = 1;
	public static final int STATE_END = 2;
	
	int game_state = STATE_READY;
	
	Card m_Card[][];
	Card m_Shuffle1, m_Shuffle2;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		image_obj[0][0] = next_card0;
		image_obj[1][0] = next_card1;
		image_obj[2][0] = next_card2;
		image_obj[3][0] = next_card3;
		image_obj[0][1] = next_card4;
		image_obj[1][1] = next_card5;
		image_obj[2][1] = next_card6;
		image_obj[3][1] = next_card7;
		image_obj[0][2] = next_card8;
		image_obj[1][2] = next_card9;
		image_obj[2][2] = next_card10;
		image_obj[3][2] = next_card11;
		image_obj[0][3] = next_card12;
		image_obj[1][3] = next_card13;
		image_obj[2][3] = next_card14;
		image_obj[3][3] = next_card15;
		m_column = 4;
		m_row = 4;
		m_Card = new Card[m_column][m_row];
		Card_Shuffle(m_column, m_row);
		OnDraw();
		NextThread thread = new NextThread(this);
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
		//if(m_state == STATE_READY) {
		//	All_Card_View();
		//} else if(m_state == STATE_GAME){
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
		//}
	}
	
	public void View_Front_Image(int x, int y){
		switch(m_Card[x][y].m_image) {
		case Card.NEXT_IMG_0 : image_obj[x][y].setImage(
				new Image(getClass().getResource(
						"images/black_window2.jpg").toString()));
			break;
		case Card.NEXT_IMG_1 : image_obj[x][y].setImage(
				new Image(getClass().getResource(
						"images/captain_america2.jpg").toString()));
			break;
		case Card.NEXT_IMG_2 : image_obj[x][y].setImage(
				new Image(getClass().getResource(
						"images/captain_marvel2.jpg").toString()));
			break;
		case Card.NEXT_IMG_3 : image_obj[x][y].setImage(
				new Image(getClass().getResource(
						"images/hawkeye2.jpg").toString()));
			break;
		case Card.NEXT_IMG_4 : image_obj[x][y].setImage(
				new Image(getClass().getResource(
						"images/hulk2.jpg").toString()));
			break;
		case Card.NEXT_IMG_5 : image_obj[x][y].setImage(
				new Image(getClass().getResource(
						"images/ironman2.jpg").toString()));
			break;
		case Card.NEXT_IMG_6 : image_obj[x][y].setImage(
				new Image(getClass().getResource(
						"images/thor2.jpg").toString()));
			break;
		case Card.NEXT_IMG_7 : image_obj[x][y].setImage(
				new Image(getClass().getResource(
						"images/wong2.jpg").toString()));
			break;
		}
	}
	public void all_card_close() {
		for(int y=0;y<m_row;y++)
			for(int x=0;x<m_column;x++) {
				m_Card[x][y].m_state = Card.CARD_CLOSE;
			}
	}
	public void OnMouseClickedRoot(MouseEvent event) {
		
		if(game_state == STATE_READY) {
			game_state = STATE_GAME;
			all_card_close();
			OnDraw();
		}else if (game_state == STATE_END) {
			
			lbl_title.setText("Game Over");
		}
	}
	public void OnMouseClickedImageView(MouseEvent event) {
		if(m_Shuffle1 != null && m_Shuffle2 != null)return;
		for(int y=0;y<m_row;y++)
			for(int x=0;x<m_column;x++) {
				if( image_obj[x][y] == event.getSource()) {
					if(m_Card[x][y].m_state == Card.CARD_CLOSE) {
						m_Card[x][y].m_state = Card.CARD_PLAYEROPEN;
						if(m_Shuffle1 == null) {
							m_Shuffle1 = m_Card[x][y];
						}
						else {
							m_Shuffle2 = m_Card[x][y];
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
			game_state = STATE_END;
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







