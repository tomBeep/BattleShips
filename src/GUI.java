import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import javax.swing.*;
import javax.swing.border.Border;

public class GUI {

	private final int BUTTON_WIDTH = 90;
	private final int BUTTON_HEIGHT = 30;

	JFrame mainFrame;
	JComponent drawing2;
	JComponent drawing;

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
		mainFrame = new JFrame();
		mainFrame.setLayout(new BorderLayout(2, 1));
		mainFrame.setSize(400, 400);
	}

	public void makeMenuArea() {
		JPanel menuArea = new JPanel();
		BoxLayout layout = new BoxLayout(menuArea, BoxLayout.LINE_AXIS);
		menuArea.setLayout(layout);
		for (JButton button : makeButtons()) {
			button.setMaximumSize(new Dimension(100, 50));
			button.setMinimumSize(new Dimension(100, 50));
			button.setAlignmentX(Component.CENTER_ALIGNMENT);// makes Sure all buttons are the same width and height and
																// centred
			menuArea.add(button);
		}
		mainFrame.add(menuArea, BorderLayout.NORTH);
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

		buttons.add(startButton);
		buttons.add(quitButton);
		return buttons;
	}

	/**
	 * Repaints both graphics frames
	 */
	public void redraw() {
		mainFrame.repaint();
	}

	public void makeGraphics2Area() {//the right graphics area
		drawing2 = new JComponent() {
			protected void paintComponent(Graphics g) {
				mainClass.redrawBoard1(g);
			}
		};
		Border edge = BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK);
		drawing2.setBorder(edge);
		drawing2.setLayout(new BorderLayout(1, 1));
		drawing2.setPreferredSize(new Dimension(400, 400));
		mainFrame.add(drawing2, BorderLayout.LINE_START);
	}

	public void makeGraphicsArea() {//the left graphics area
		drawing = new JComponent() {
			protected void paintComponent(Graphics g) {
				mainClass.redrawBoard2(g);
			}
		};
		Border edge = BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK);
		drawing.setBorder(edge);
		drawing.setLayout(new BorderLayout(1, 1));
		drawing.setPreferredSize(new Dimension(400, 400));
		mainFrame.add(drawing, BorderLayout.EAST);
	}

	public void Finnalise() {
		mainFrame.pack();
		mainFrame.setVisible(true);
	}

}
