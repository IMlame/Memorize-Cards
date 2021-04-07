import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;




public class CheckboxPanel extends JPanel {
	private boolean[] checkBoxStatus = {true, true, true, true};
	private CheckboxListener handler;

	
	public CheckboxPanel(CheckboxListener handler) {
		setLayout(new FlowLayout());
		
		//add 4 different checkboxes
		add(createCheckbox("Spades"));
		add(createCheckbox("Hearts"));
		add(createCheckbox("Diamonds"));
		add(createCheckbox("Clubs"));

		//store listener
		this.handler = handler;
	}
	
	private JCheckBox createCheckbox(String checkboxLabel) {
		JCheckBox checkbox = new JCheckBox(checkboxLabel, true);
		
		//create listener for checkbox
		checkbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//update checkBoxStatus boolean array with correct values associated with checkboxes
				if(checkboxLabel.equals("Spades")) {
					checkBoxStatus[0] = !checkBoxStatus[0];
				} else if(checkboxLabel.equals("Hearts")) {
					checkBoxStatus[1] = !checkBoxStatus[1];
				} else if(checkboxLabel.equals("Diamonds")) {
					checkBoxStatus[2] = !checkBoxStatus[2];
				} else if(checkboxLabel.equals("Clubs")) {
					checkBoxStatus[3] = !checkBoxStatus[3];
				}
				
				//callback to main with updated info
				handler.action(checkBoxStatus);
			}
		});
		return checkbox;
	}
	

}
