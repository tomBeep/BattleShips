import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import javax.swing.*;
import javax.swing.border.Border;

public class GUI {

	private final int BUTTON_WIDTH = 120;
	private final int BUTTON_HEIGHT = 50;

	JFrame mainFrame;
	JComponent drawing2;
	JComponent drawing;
	JTextArea text;

	Graphics g1;

	main mainClass;

	public GUI(main MainClass) {
		this.mainClass = MainClass;
		setup();
		makeMenuArea();
		makeGraphics2Area();
		makeGraphicsArea();
		Finnalise();
	}

	public void setup() {
		mainFrame = new TomJFrame();
		mainFrame.setLayout(new BorderLayout(2, 1));
		mainFrame.setSize(600, 600);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void makeMenuArea() {
		JPanel menuArea = new JPanel();
		BoxLayout layout = new BoxLayout(menuArea, BoxLayout.LINE_AXIS);
		menuArea.setLayout(layout);
		for (JButton button : makeButtons()) {
			button.setFocusable(false);
			button.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
			button.setMinimumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
			button.setAlignmentX(Component.CENTER_ALIGNMENT);// makes Sure all buttons are the same width and height and
																// centred
			menuArea.add(button);
		}
		mainFrame.add(menuArea, BorderLayout.NORTH);
		text = new JTextArea();
		mainFrame.add(text, BorderLayout.SOUTH);
	}

	public LinkedList<JButton> makeButtons() {
		LinkedList<JButton> buttons = new LinkedList<JButton>();

		JButton quitButton = new JButton("Quit");
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				System.exit(0); // cleanly end the program.
			}
		});

		JButton startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				mainClass.start();
			}
		});

		JButton rulesButton = new JButton("Rules");
		rulesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				displayRules();
			}
		});

		buttons.add(startButton);
		buttons.add(quitButton);
		buttons.add(rulesButton);
		return buttons;
	}

	public void displayRules() {
		JFrame j = new TomJFrame();
		j.setLayout(new BorderLayout(2, 1));
		j.setSize(600, 600);
		JTextArea rules = new JTextArea();
		String text = "BattleShip Rules:\n\n";
		text += "This game is a two player implementation where each player takes turn shooting at the other player's board\n"
				+ "The goal of the game is too destroy all of your opponents ships.\n\n";
		text += "The first stage of the game involves each player placing his or her \n"
				+ "ships in the position they would like their ships hidden at,\nShip "
				+ "locations are hidden from your opponent.\n\n"
				+ "The second Stage of the game involves taking turns shooting at your \n"
				+ "opponents ships (the second grid is used for this), move the pointer \n"
				+ "around the grid and press 'enter' to take a shot.\n\n"
				+ "You have two grids. The left grid shows the position of your SHIPS and\n"
				+ "also the opponents hits/misses, the grid on the right shows you your \n"
				+ "HITS and MISSES. Black means miss, red means hit.\n\n"
				+ "The game ends when all of one player's ships are destroyed.\n\n"
				+ "To move the point and ships use the arrow keys, press spacebar to flip\n"
				+ "a ship from vertical to horizontal during the ship placing stage, press\n"
				+ "'Enter' to confirm placement/confirm shot.\n\n" + "Colors:\n" + "Black is a shot that missed\n"
				+ "Red is a shot that hit\n" + "Green is a ship\n"
				+ "Yellow is an opponent's shot that hit one of your ship\n" + "Grey is a ship that is totally dead\n";

		rules.setText(text);
		j.add(rules);
		j.pack();
		j.setVisible(true);
	}

	/**
	 * Repaints both graphics frames
	 */
	public void redraw() {
		drawing.repaint();
		drawing2.repaint();
		mainFrame.repaint();
		if (main.mainGameStarted == false)
			return;
		if (mainClass.player1Turn) {
			text.setText("Player 1's turn");
		} else {
			text.setText("Player 2's turn");
		}
	}

	public void makeGraphics2Area() {// the right graphics area
		drawing2 = new JComponent() {
			protected void paintComponent(Graphics g) {
				if (main.hideBoard) {
					g.clearRect(0, 0, 1080, 1920);
					g.drawString(main.message, 50, 50);
				} else
					mainClass.redrawBoard1(g);
			}
		};
		Border edge = BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK);
		drawing2.setBorder(edge);
		drawing2.setLayout(new BorderLayout(1, 1));
		drawing2.setPreferredSize(
				new Dimension(main.BOARD_UNITS * main.UNIT_SIZE + 50, main.BOARD_UNITS * main.UNIT_SIZE + 50));
		mainFrame.add(drawing2, BorderLayout.LINE_START);
	}

	public void makeGraphicsArea() {// the left graphics area
		drawing = new JComponent() {
			protected void paintComponent(Graphics g) {
				if (main.hideBoard) {
					g.clearRect(0, 0, 1080, 1920);
					g.drawString(main.message, 50, 50);
				} else
					mainClass.redrawBoard2(g);
			}
		};
		Border edge = BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK);
		drawing.setBorder(edge);
		drawing.setLayout(new BorderLayout(1, 1));
		drawing.setPreferredSize(
				new Dimension(main.BOARD_UNITS * main.UNIT_SIZE + 50, main.BOARD_UNITS * main.UNIT_SIZE + 50));
		mainFrame.add(drawing, BorderLayout.EAST);
	}

	public void Finnalise() {
		mainFrame.pack();
		mainFrame.setVisible(true);
	}

	public void sleep(int duration) {
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private class TomJFrame extends JFrame implements KeyListener {

		public TomJFrame() {
			super();
			addKeyListener(this);
			setFocusable(true);
		}

		@Override
		public void keyPressed(KeyEvent arg0) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (mainClass.highlightedArea == null)
				return;
			if (main.hideBoard) {
				main.hideBoard = false;
				drawing.setVisible(true);
				drawing2.setVisible(true);
				redraw();
				return;
			}
			int keyCode = e.getKeyCode();
			switch (keyCode) {
			case KeyEvent.VK_W:
				mainClass.highlightedArea.moveArea(0, -1);
				break;
			case KeyEvent.VK_S:
				mainClass.highlightedArea.moveArea(0, 1);
				break;
			case KeyEvent.VK_A:
				mainClass.highlightedArea.moveArea(-1, 0);
				break;
			case KeyEvent.VK_D:
				mainClass.highlightedArea.moveArea(1, 0);
				break;
			case KeyEvent.VK_UP:
				mainClass.highlightedArea.moveArea(0, -1);
				break;
			case KeyEvent.VK_DOWN:
				mainClass.highlightedArea.moveArea(0, 1);
				break;
			case KeyEvent.VK_LEFT:
				mainClass.highlightedArea.moveArea(-1, 0);
				break;
			case KeyEvent.VK_RIGHT:
				mainClass.highlightedArea.moveArea(1, 0);
				break;
			case KeyEvent.VK_SPACE:
				if (mainClass.highlightedArea.rotateShip())
					mainClass.addedShipIsVertical = !mainClass.addedShipIsVertical;
				break;
			case KeyEvent.VK_ENTER:
				if (!main.mainGameStarted)
					mainClass.addShip();
				else {
					mainClass.fireShot();
				}
				break;
			}
			redraw();
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}

	}
}
