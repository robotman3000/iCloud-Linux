package client;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class GraphicTestClient extends TestClient{

	private JFrame frame;
	private String username;
	private String password;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		GraphicTestClient window = new GraphicTestClient();
		window.frame.setVisible(true);
		
		boolean debugEnabled = true;
		boolean announceConnections = true;

		if (args.length != 2) {
			System.exit(1);
		}
		
		window.username = args[0];
		window.password = args[1];

		
		
	}

	/**
	 * Create the application.
	 */
	public GraphicTestClient() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton connectButton = new JButton();
		connectButton.setBounds(10, 10, 120, 30);
		connectButton.setText("Connect");
		connectButton.setToolTipText("Opens Connection To Server");

		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					openConnection(username, password, false, true, true);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		frame.getContentPane().setLayout(null);

		frame.getContentPane().add(connectButton);

		JButton disconnectButton = new JButton();
		disconnectButton.setBounds(10, 50, 120, 30);
		disconnectButton.setText("Disconnect");
		disconnectButton.setToolTipText("Closes Connection To Server");

		disconnectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					closeConnection();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		frame.getContentPane().add(disconnectButton);
		
		JButton printUserPropsButton = new JButton();
		printUserPropsButton.setBounds(10, 90, 120, 30);
		printUserPropsButton.setText("Print Properties");
		printUserPropsButton.setToolTipText("Gets User Properties");

		printUserPropsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printUserProps();
			}
		});
		
		frame.getContentPane().add(printUserPropsButton);
		
		JButton getNoteManButton = new JButton();
		getNoteManButton.setBounds(140, 10, 120, 30);
		getNoteManButton.setText("Create Note Manager");
		getNoteManButton.setToolTipText("Gets User Note Manager");

		getNoteManButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					createNoteManager();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		frame.getContentPane().add(getNoteManButton);
		
		JButton printNotesButton = new JButton();
		printNotesButton.setBounds(140, 50, 120, 30);
		printNotesButton.setText("Print Notes");
		printNotesButton.setToolTipText("Prints User Notes");

		printNotesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					printNotes(noteMgr);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		frame.getContentPane().add(printNotesButton);
		
		JButton deleteNoteButton = new JButton();
		deleteNoteButton.setBounds(140, 90, 120, 30);
		deleteNoteButton.setText("Delete Note");
		deleteNoteButton.setToolTipText("Deletes a Note");

		deleteNoteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					deleteNote();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		frame.getContentPane().add(deleteNoteButton);
		
		JButton noteChangesetButton = new JButton();
		noteChangesetButton.setBounds(140, 130, 120, 30);
		noteChangesetButton.setText("Note Changeset");
		noteChangesetButton.setToolTipText("");

		noteChangesetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					noteMgr.getChanges();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		frame.getContentPane().add(noteChangesetButton);
	}
}
