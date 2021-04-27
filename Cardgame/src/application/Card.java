package application;

public class Card {

	public static final int IMG_0 = 0;
	public static final int IMG_1 = 1;
	public static final int IMG_2 = 2;
	
	public static final int NEXT_IMG_0 = 0;
	public static final int NEXT_IMG_1 = 1;
	public static final int NEXT_IMG_2 = 2;
	public static final int NEXT_IMG_3 = 3;
	public static final int NEXT_IMG_4 = 4;
	public static final int NEXT_IMG_5 = 5;
	public static final int NEXT_IMG_6 = 6;
	public static final int NEXT_IMG_7 = 7;
		
	public static final int CARD_SHOW = 0;
	public static final int CARD_CLOSE = 1;
	public static final int CARD_PLAYEROPEN = 2;
	public static final int CARD_MATCHED = 3;
	
	public int m_state;
	public int m_image;
	
	public Card(int _image) {
		m_state = CARD_SHOW;
		m_image = _image;
	}
}




