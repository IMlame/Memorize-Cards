import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;

//interface to notify main when checkbox is clicked in checkbox panel
interface CheckboxListener {
	public void action(boolean[] checkboxInfo);
}

interface ControlPanelListener {
	public void generate();
	
	public void delete();
}

public class Main {
	// callback to pass checkbox info back to main

	static JFrame main;
	final static int HEIGHT = 600;
	final static int WIDTH = 800;
	final static String[] cardNums = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A" };
	final static String[] cardSuits = { "S", "H", "D", "C"};

	private static CardPanel cardPanel = new CardPanel();
	
	//array that mirrors selected/unselected suits
	private static boolean[] checkboxInfo = {true, true, true, true};
	
	//array that is linked to displayed cards
	private static String[] cardArr = { "unknown", "unknown", "unknown" };

	// deck number variable for jpanel
	private static int deckNumber = 0;

	// card number variable for jpanel
	private static int cardNumber = 0;

	// card increment variable for jpanel
	private static int incrementValue = 3;

	private static ArrayList<ArrayList<String>> deckList = new ArrayList<ArrayList<String>>();

	private static FileWriter fileWriter;

	private static Scanner fileReader;

	public static void main(String[] args) {
		setUpTextFile();

		retrieveTextFileInfo();

		setUpJPanel();

	}

	private static void setUpTextFile() {
		File deckInfoFile = new File("deckInfoFile.txt");

		// create deckInfoFile if not created already
		if (!deckInfoFile.exists()) {
			try {
				deckInfoFile.createNewFile();
			} catch (IOException e) {
				System.out.println("file already exists");
				e.printStackTrace();
			}
		} else {
			System.out.println("deckInfoFile found");
		}


		try {
			fileReader = new Scanner(deckInfoFile);
		} catch (FileNotFoundException e) {
			System.out.println("unable to find deckInfoFile.txt for scanner");
			e.printStackTrace();
		}
	}

	private static void retrieveTextFileInfo() {
		while (fileReader.hasNextLine()) {
			// store individual deck in string
			String deckString = fileReader.nextLine();
			// create scanner for string
			Scanner deckReader = new Scanner(deckString);
			// create array to put into deckList
			ArrayList<String> deck = new ArrayList<String>();
			while (deckReader.hasNext()) {
				deck.add(deckReader.next());
			}
			deckList.add(deck);
			deckReader.close();
		}
		updateCardPanel();
	}

	private static void setUpJPanel() {
		main = new JFrame();
		main.addKeyListener(new KeyboardListener());
		main.setSize(WIDTH, HEIGHT);
		main.setResizable(false);
		main.setFocusable(true);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setLayout(new BoxLayout(main.getContentPane(), BoxLayout.Y_AXIS));

		// create panel with 3 cards
		cardPanel.setCards(cardArr, cardNumber + 1);
		main.add(cardPanel);

		// create checkbox panel with listener
		main.add(new CheckboxPanel(new CheckboxHandler()));
		
		//create cardcontrol panel with listener
		main.add(new CardControlPanel(new ControlPanelHandler()));

		main.setVisible(true);
		main.setFocusable(true);
		main.requestFocus();
		main.pack();
	}

	private static void updateTextFile() {
		try {
			fileWriter = new FileWriter("deckInfoFile.txt", false);
		} catch (IOException e) {
			System.out.println("unable to find deckInfoFile.txt for printWriter");
			e.printStackTrace();
		}
		
		for (ArrayList<String> deck : deckList) {
			String deckToString = new String();
			for (String card : deck) {
				deckToString += card + " ";
			}

			try {
				fileWriter.write(deckToString + "\n");
				fileWriter.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private static void updateCardPanel() {
		String[] fillerArr = {"unknown","unknown","unknown"};
		cardArr = fillerArr;
		for(int i = 0; i < cardArr.length; i++) {
			if(deckNumber < deckList.size() && cardNumber + i < deckList.get(deckNumber).size()) {
				cardArr[i] = deckList.get(deckNumber).get(cardNumber + i);
			}
		}
		cardPanel.setCards(cardArr, cardNumber + 1);
	}
	
	private static void generateDeck() {
		//list of suits needed to generate
		ArrayList<String> suitsNeeded = new ArrayList<String>();
		
		//list of card numbers needed to generate
		ArrayList<ArrayList<String>> cardsNeeded = new ArrayList<>();
		
		for(int i = 0; i < 4; i++) {
			if(checkboxInfo[i]) {
				suitsNeeded.add(cardSuits[i]);
				cardsNeeded.add(new ArrayList<>(Arrays.asList(cardNums)));
			}
		}
		//new cards will be added to newDeckList
		ArrayList<String> newDeck = new ArrayList<String>();
		
		Random rand = new Random();
		while(suitsNeeded.size() != 0) {
			//select random suit and card
			int suit = rand.nextInt(suitsNeeded.size());
			int card = rand.nextInt(cardsNeeded.get(suit).size());
			
			newDeck.add(cardsNeeded.get(suit).get(card) + suitsNeeded.get(suit));
			
			//remove added card
			cardsNeeded.get(suit).remove(card);
			if(cardsNeeded.get(suit).size() == 0) {
				cardsNeeded.remove(suit);
				suitsNeeded.remove(suit);
			}
		}
		
		//if at the end of decklist, just add it to the list instead of setting
		if(deckList.size() != deckNumber) {
			deckList.set(deckNumber, newDeck);
		} else {
			deckList.add(newDeck);
		}
		
		updateTextFile();
	}
	
	private static class KeyboardListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			switch (keyCode) {
			case KeyEvent.VK_UP:
				//upper limit
				if (deckNumber < deckList.size()) {
					deckNumber++;
					cardNumber = 0;
				}
				break;
			case KeyEvent.VK_DOWN:
				//lower limit
				if (deckNumber != 0) {
					deckNumber--;
					cardNumber = 0;
				}
				break;
			case KeyEvent.VK_LEFT:
				if(cardNumber - incrementValue >= 0) {
					cardNumber -= incrementValue;
				} else {
					cardNumber = 0;
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(deckNumber < deckList.size() && cardNumber + incrementValue < deckList.get(deckNumber).size()) {
					cardNumber += incrementValue;
				} else if(deckNumber < deckList.size()){
					cardNumber = deckList.get(deckNumber).size()-1;
				}
				break;
			}
			updateCardPanel();
			//System.out.println("cardNumber: " + cardNumber + " deckNumber: " + deckNumber);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

	}

	private static class CheckboxHandler implements CheckboxListener {

		public void action(boolean[] checkboxInfo) {
			Main.checkboxInfo = checkboxInfo;
		}

	}
	
	private static class ControlPanelHandler implements ControlPanelListener {

		@Override
		public void generate() {
			generateDeck();
			updateCardPanel();
		}

		@Override
		public void delete() {
			deckList.remove(deckNumber);
			updateTextFile();
			updateCardPanel();
			
		}

	}

}
