import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class CardControlPanel extends JPanel{
	//callback with info of button presses
	private ControlPanelListener handler;
	private JButton delete, generate;
	
	public CardControlPanel(ControlPanelListener handler) {
		this.handler = handler;
		
		setLayout(new FlowLayout());
		
		createButtons();
		
		createButtonListeners();
	}
	
	private void createButtons() {
		delete = new JButton("Delete");
		generate = new JButton("Generate");
		
		delete.setFocusable(false);
		generate.setFocusable(false);
		
		add(generate);
		add(delete);
	}
	
	private void createButtonListeners() {
		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				handler.delete();
			}
			
		});
		
		generate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				handler.generate();
			}
			
		});
	}
}
