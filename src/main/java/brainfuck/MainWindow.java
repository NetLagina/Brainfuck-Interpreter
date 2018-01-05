package brainfuck;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

import exception.IllegalOperatorException;
import exception.PointerOutOfBoundsException;

public class MainWindow {

	private JFrame frmBrainfuckInterpreter;
	private JTextField tfArguments;
	private JTextArea taCodeEditor;
	private JTextArea taResult;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmBrainfuckInterpreter.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainWindow() {
		initialize();
	}

	private void initialize() {
		frmBrainfuckInterpreter = new JFrame();
		frmBrainfuckInterpreter.setTitle("Brainfuck Interpreter");
		frmBrainfuckInterpreter.setBounds(100, 100, 800, 500);
		frmBrainfuckInterpreter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frmBrainfuckInterpreter.getContentPane().add(menuBar, BorderLayout.NORTH);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);

		JMenu mnRun = new JMenu("Run");
		menuBar.add(mnRun);

		JMenuItem mntmRun = new JMenuItem("Run");
		mntmRun.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F11, InputEvent.CTRL_MASK));
		mntmRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String code = taCodeEditor.getText();
				Brainfuck brainfuck = new Brainfuck(code);
				String input = tfArguments.getText();
				String result;
				try {
					result = brainfuck.process(input);
					taResult.setText(result);
				} catch (PointerOutOfBoundsException | IOException | IllegalOperatorException exception) {
					taResult.setText(exception.toString());
				}
			}
		});
		mnRun.add(mntmRun);

		JPanel editorPanel = new JPanel();
		editorPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Code Editor",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		taCodeEditor = new JTextArea();
		taCodeEditor.setFont(new Font("Courier New", Font.PLAIN, 12));
		taCodeEditor.setTabSize(4);

		taResult = new JTextArea();
		taResult.setEditable(false);
		taResult.setTabSize(4);
		taResult.setFont(new Font("Courier New", Font.PLAIN, 12));

		frmBrainfuckInterpreter.getContentPane().add(editorPanel, BorderLayout.CENTER);
		editorPanel.setLayout(new BorderLayout(0, 0));

		JPanel ArgumentsPanel = new JPanel();
		ArgumentsPanel
				.setBorder(new TitledBorder(null, "Arguments", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		editorPanel.add(ArgumentsPanel, BorderLayout.SOUTH);

		tfArguments = new JTextField();
		tfArguments.setFont(new Font("Courier New", Font.PLAIN, 12));
		tfArguments.setColumns(10);
		editorPanel.add(ArgumentsPanel, BorderLayout.SOUTH);
		GroupLayout gl_ArgumentsPanel = new GroupLayout(ArgumentsPanel);
		gl_ArgumentsPanel.setHorizontalGroup(gl_ArgumentsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_ArgumentsPanel.createSequentialGroup().addContainerGap()
						.addComponent(tfArguments, GroupLayout.DEFAULT_SIZE, 740, Short.MAX_VALUE).addContainerGap()));
		gl_ArgumentsPanel.setVerticalGroup(gl_ArgumentsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_ArgumentsPanel.createSequentialGroup().addGap(5)
						.addComponent(tfArguments, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		ArgumentsPanel.setLayout(gl_ArgumentsPanel);
		editorPanel.add(taCodeEditor);

		JPanel resultPanel = new JPanel();
		resultPanel.setBorder(new TitledBorder(null, "Result", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		frmBrainfuckInterpreter.getContentPane().add(resultPanel, BorderLayout.SOUTH);
		resultPanel.setLayout(new BorderLayout(0, 0));
		resultPanel.add(taResult, BorderLayout.CENTER);
	}
}
