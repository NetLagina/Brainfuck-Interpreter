package brainfuck;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import javax.swing.border.TitledBorder;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import java.util.ArrayList;
import java.util.List;

import exception.IllegalOperatorException;
import exception.PointerOutOfBoundsException;
import utils.BrainfuckFileFilter;
import utils.FileManager;

public class MainWindow {

	public final static String TITLE = "Brainfuck Interpreter";
	
	private final static String NEW_TAB = "New Tab";
	private final static String MENU_BAR_FILE = "File";
	private final static String MENU_BAR_NEW = "New";
	private final static String MENU_BAR_OPEN = "Open";
	private final static String MENU_BAR_CLOSE = "Close";
	private final static String MENU_BAR_SAVE = "Save";
	private final static String MENU_BAR_SAVE_AS = "Save As...";
	private final static String MENU_BAR_EXIT = "Exit";
	private final static String MENU_BAR_RUN = "Run";
	private final static String RESULT_PANEL = "Result";
	
	private JFrame frmBrainfuckInterpreter;
	private List<JMainPanel> tabs;
	private JTextArea taResult;
	private JTabbedPane tabbedPane;

	private final FileManager fileManager;

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
		fileManager = new FileManager();
		tabs = new ArrayList<>();
		initialize();
	}

	private void initialize() {
		frmBrainfuckInterpreter = new JFrame();
		frmBrainfuckInterpreter.setTitle(TITLE);
		frmBrainfuckInterpreter.setBounds(100, 100, 800, 500);
		frmBrainfuckInterpreter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final JMainPanel panel = new JMainPanel();
		tabs.add(panel);
		
		/* START Menu Bar */
		JMenuBar menuBar = new JMenuBar();
		frmBrainfuckInterpreter.getContentPane().add(menuBar, BorderLayout.NORTH);

		/* START Menu File */
		JMenu mnFile = new JMenu(MENU_BAR_FILE);
		menuBar.add(mnFile);

		/* START Menu Bar New */
		JMenuItem mntmNew = new JMenuItem(MENU_BAR_NEW);
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JMainPanel panel = new JMainPanel();
				panel.getTaCodeEditor().setText("");
				tabs.add(panel);
				tabbedPane.addTab(NEW_TAB, panel);
				tabbedPane.setSelectedComponent(panel);
			}
		});
		mnFile.add(mntmNew);
		/* END Menu Bar New */
		
		/* START Menu Bar Open */
		JMenuItem mntmOpen = new JMenuItem(MENU_BAR_OPEN);
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new BrainfuckFileFilter());
				int returnValue = fileChooser.showOpenDialog(tabs.get(tabbedPane.getSelectedIndex()).getTaCodeEditor());
				File file = null;
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					file = fileChooser.getSelectedFile();
					String code = fileManager.load(file);
					JMainPanel panel = new JMainPanel();
					panel.getTaCodeEditor().setText(code);
					tabs.add(panel);
					tabbedPane.addTab(file.getName(), panel);
					tabbedPane.setSelectedComponent(panel);
					fileManager.add(file, tabbedPane.getSelectedIndex());
				}
			}
		});
		mnFile.add(mntmOpen);
		/* END Menu Bar Open */

		/* START Menu Bar Separator 1 */
		JSeparator separator1 = new JSeparator();
		mnFile.add(separator1);
		/* END Menu Bar Separator 1 */
		
		/* START Menu Bar Close */
		JMenuItem mntmClose = new JMenuItem(MENU_BAR_CLOSE);
		mntmClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK));
		mntmClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JMainPanel newPanel = null;
				if (tabbedPane.getTabCount() == 1) {
					newPanel = new JMainPanel();
					tabbedPane.addTab(NEW_TAB, newPanel);
				}
				fileManager.remove(tabbedPane.getSelectedIndex());
				tabbedPane.remove(tabbedPane.getComponentAt(tabbedPane.getSelectedIndex()));
				if (tabbedPane.getTabCount() == 1) {
					tabbedPane.setSelectedComponent(newPanel);
				}
			}
		});
		mnFile.add(mntmClose);
		/* END Menu Bar Close */
		
		/* START Menu Bar Separator 2 */
		JSeparator separator2 = new JSeparator();
		mnFile.add(separator2);
		/* END Menu Bar Separator 2 */
		
		/* START Menu Bar Save */
		JMenuItem mntmSave = new JMenuItem(MENU_BAR_SAVE);
		mntmSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				File file = null;
				if (tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()) != NEW_TAB) {
					file = fileManager.get(tabbedPane.getSelectedIndex());
					fileManager.save(file, tabs.get(tabbedPane.getSelectedIndex()).getTaCodeEditor().getText());
				} else {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setFileFilter(new BrainfuckFileFilter());
					int returnValue = fileChooser.showSaveDialog(tabs.get(tabbedPane.getSelectedIndex()).getTaCodeEditor());
					if (returnValue == JFileChooser.APPROVE_OPTION) {
						file = fileChooser.getSelectedFile();
						fileManager.save(file, tabs.get(tabbedPane.getSelectedIndex()).getTaCodeEditor().getText());
						tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), file.getName());
					}
				}
			}
		});
		mnFile.add(mntmSave);
		/* END Menu Bar Save */
		
		/* START Menu Bar Save As */
		JMenuItem mntmSaveAs = new JMenuItem(MENU_BAR_SAVE_AS);
		mntmSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				File file = null;
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new BrainfuckFileFilter());
				int returnValue = fileChooser.showSaveDialog(tabs.get(tabbedPane.getSelectedIndex()).getTaCodeEditor());
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					file = fileChooser.getSelectedFile();
					fileManager.save(file, tabs.get(tabbedPane.getSelectedIndex()).getTaCodeEditor().getText());
					tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), file.getName());
				}
			}
		});
		mnFile.add(mntmSaveAs);
		/* END Menu Bar Save As */
		
		/* START Menu Bar Separator 2 */
		JSeparator separator3 = new JSeparator();
		mnFile.add(separator3);
		/* END Menu Bar Separator 2 */
		
		/* START Menu Bar Exit */
		JMenuItem mntmExit = new JMenuItem(MENU_BAR_EXIT);
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);
		/* END Menu Bar Exit */
		/* END Menu File */
		
		/* START Menu Bar Run */
		JMenu mnRun = new JMenu(MENU_BAR_RUN);
		menuBar.add(mnRun);

		/* START Menu Bar RunRun */
		JMenuItem mntmRun = new JMenuItem(MENU_BAR_RUN);
		mntmRun.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F11, InputEvent.CTRL_DOWN_MASK));
		mntmRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String code = tabs.get(tabbedPane.getSelectedIndex()).getTaCodeEditor().getText();
				Brainfuck brainfuck = new Brainfuck(code);
				String input = tabs.get(tabbedPane.getSelectedIndex()).getTfArguments().getText();
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
		/* END Menu Bar RunRun */
		/* END Menu Bar Run */
		/* END Menu Bar */

		/* START TabbedPane */
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab(NEW_TAB, panel);
		frmBrainfuckInterpreter.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		/* END TabbedPane */
		
		/* START ResultPanel */
		JPanel resultPanel = new JPanel();
		resultPanel.setBorder(new TitledBorder(null, RESULT_PANEL, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frmBrainfuckInterpreter.getContentPane().add(resultPanel, BorderLayout.SOUTH);
		resultPanel.setLayout(new BorderLayout(0, 0));
		
		/* START taResult */
		taResult = new JTextArea();
		taResult.setEditable(false);
		taResult.setTabSize(4);
		taResult.setFont(new Font("Courier New", Font.PLAIN, 12));
		resultPanel.add(taResult, BorderLayout.CENTER);
		/* END taResult */
		/* END ResultPanel */
	}
}
