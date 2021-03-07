import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CardPanel extends JPanel{
	public CardPanel() {
		setLayout(new FlowLayout());
		add(createJLabel("2C"));
		add(createJLabel("10D"));
		add(createJLabel("KH"));
	}
	
	private JLabel createJLabel(String cardName) {
		JLabel imageHolder = new JLabel(new ImageIcon(new ImageIcon("cards/"+cardName+".png").getImage().getScaledInstance(691/5, 1056/5, Image.SCALE_SMOOTH)));
		return imageHolder;
	}
}
