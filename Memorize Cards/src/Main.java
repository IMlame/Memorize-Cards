import javax.swing.JFrame;

public class Main {
	static JFrame main;
	final static int HEIGHT = 600;
	final static int WIDTH = 800;
	
	public static void main (String[] args) {
		main = new JFrame();
		main.setSize(WIDTH, HEIGHT);
		main.setResizable(false);
		main.setFocusable(true);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.add(new CardPanel());
		main.setVisible(true);
		main.requestFocus();

	}
}
