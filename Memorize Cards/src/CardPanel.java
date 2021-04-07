import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CardPanel extends JPanel{
	private String[] cardNameArr = new String[3];
	private boolean[] cardShown = new boolean[3];
	private JLabel[] cardArr = {new JLabel(), new JLabel(), new JLabel()};
	private int leftCardNum = 1;

	
	public CardPanel() {
		setLayout(new FlowLayout());
		
		createListeners();
		
		//setBackground(Color.pink);
		
	}
	 
	public void setCards(String[] cardarr, int leftCardNum) {
		//clear jpanel
		clearJPanel();
		
		//update string array
		cardNameArr = cardarr;
		
		//set card nums
		this.leftCardNum = leftCardNum;
		
		//set icons
		updateCards();
		
	}
	
	private ImageIcon setImageIcon(String cardName) {
		URL url = Main.class.getResource("/cards/" + cardName + ".png");
		ImageIcon imageHolder = new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(691/5, 1056/5, Image.SCALE_SMOOTH));

		return imageHolder;
	}
	
	private void updateCards() {
		for(int i = 0; i < cardNameArr.length; i++) {
			//create new icons for cards
			cardArr[i].setIcon(cardShown[i] == true ? setImageIcon(cardNameArr[i]) : setImageIcon("red_back"));
		}
		
		//redraw ards
		redrawCards();
	}
	
	private void createListeners() {
		for(int i = 0; i < cardArr.length; i++) {
			final int index = i;
			System.out.println(index);
			//toggle card shown and not shown when clicked
			cardArr[i].addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent arg0) {

				}

				@Override
				public void mouseEntered(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseExited(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mousePressed(MouseEvent arg0) {
					cardShown[index] = !cardShown[index];
					
					System.out.println("card clicked");
					updateCards();

					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseReleased(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
			});
		}
	}
	
	private void redrawCards() {
		//clear JPanel
		clearJPanel();
		
		for(int i = 0; i < cardArr.length; i++) {
			//create jpanel that combines card number with card image
			JPanel cardWithNum = new JPanel();
			cardWithNum.setLayout(new BoxLayout(cardWithNum, BoxLayout.Y_AXIS));
			cardWithNum.add(new JLabel("" + (leftCardNum + i)));
			cardWithNum.add(cardArr[i]);
			add(cardWithNum);
		}
	}
	private void clearJPanel() {
		removeAll();
		revalidate();
		repaint();
	}
}
