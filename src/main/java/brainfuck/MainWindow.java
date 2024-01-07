package brainfuck;

import exception.IllegalOperatorException;
import exception.PointerOutOfBoundsException;
import utils.BrainfuckFileFilter;
import utils.FileManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

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
    private JTextArea taResult;
    private JTabbedPane tabbedPane;

    private final FileManager fileManager;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MainWindow window = new MainWindow();
                window.frmBrainfuckInterpreter.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public MainWindow() {
        fileManager = new FileManager();
        initialize();
    }

    private void initialize() {
        frmBrainfuckInterpreter = new JFrame();
        frmBrainfuckInterpreter.setTitle(TITLE);
        frmBrainfuckInterpreter.setBounds(100, 100, 800, 500);
        frmBrainfuckInterpreter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final JMainPanel panel = new JMainPanel();

        /* START Menu Bar */
        JMenuBar menuBar = new JMenuBar();
        frmBrainfuckInterpreter.getContentPane().add(menuBar, BorderLayout.NORTH);

        /* START Menu File */
        JMenu mnFile = new JMenu(MENU_BAR_FILE);
        menuBar.add(mnFile);

        /* START Menu Bar New */
        JMenuItem mntmNew = new JMenuItem(MENU_BAR_NEW);
        mntmNew.addActionListener(arg0 -> {
            JMainPanel panel1 = new JMainPanel();
            panel1.getTaCodeEditor().setText("");
            tabbedPane.addTab(NEW_TAB, panel1);
            tabbedPane.setSelectedComponent(panel1);
        });
        mnFile.add(mntmNew);
        /* END Menu Bar New */

        /* START Menu Bar Open */
        JMenuItem mntmOpen = new JMenuItem(MENU_BAR_OPEN);
        mntmOpen.addActionListener(arg0 -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new BrainfuckFileFilter());
            int returnValue = fileChooser.showOpenDialog(((JMainPanel) tabbedPane.getSelectedComponent()).getTaCodeEditor());
            File file;
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                file = fileChooser.getSelectedFile();
                String code = fileManager.load(file);
                JMainPanel panel12 = new JMainPanel();
                panel12.getTaCodeEditor().setText(code);
                tabbedPane.addTab(file.getAbsolutePath(), panel12);
                tabbedPane.setSelectedComponent(panel12);
                fileManager.add(file, file.getAbsolutePath());
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
        mntmClose.addActionListener(arg0 -> {
            fileManager.remove(tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()));
            tabbedPane.remove(tabbedPane.getComponentAt(tabbedPane.getSelectedIndex()));
            JMainPanel newPanel;
            if (tabbedPane.getTabCount() == 0) {
                newPanel = new JMainPanel();
                tabbedPane.addTab(NEW_TAB, newPanel);
                tabbedPane.setSelectedComponent(newPanel);
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
        mntmSave.addActionListener(arg0 -> {
            File file;
            if (!Objects.equals(tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()), NEW_TAB)) {
                file = fileManager.get(tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()));
                fileManager.save(file, ((JMainPanel) tabbedPane.getSelectedComponent()).getTaCodeEditor().getText());
            } else {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new BrainfuckFileFilter());
                int returnValue = fileChooser.showSaveDialog(((JMainPanel) tabbedPane.getSelectedComponent()).getTaCodeEditor());
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    file = fileChooser.getSelectedFile();
                    fileManager.save(file, ((JMainPanel) tabbedPane.getSelectedComponent()).getTaCodeEditor().getText());
                    tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), file.getName());
                }
            }
        });
        mnFile.add(mntmSave);
        /* END Menu Bar Save */

        /* START Menu Bar Save As */
        JMenuItem mntmSaveAs = new JMenuItem(MENU_BAR_SAVE_AS);
        mntmSaveAs.addActionListener(arg0 -> {
            File file;
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new BrainfuckFileFilter());
            int returnValue = fileChooser.showSaveDialog(((JMainPanel) tabbedPane.getSelectedComponent()).getTaCodeEditor());
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                file = fileChooser.getSelectedFile();
                fileManager.save(file, ((JMainPanel) tabbedPane.getSelectedComponent()).getTaCodeEditor().getText());
                tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), file.getName());
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
        mntmExit.addActionListener(arg0 -> System.exit(0));
        mnFile.add(mntmExit);
        /* END Menu Bar Exit */
        /* END Menu File */

        /* START Menu Bar Run */
        JMenu mnRun = new JMenu(MENU_BAR_RUN);
        menuBar.add(mnRun);

        /* START Menu Bar RunRun */
        JMenuItem mntmRun = new JMenuItem(MENU_BAR_RUN);
        mntmRun.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F11, InputEvent.CTRL_DOWN_MASK));
        mntmRun.addActionListener(e -> {
            String code = ((JMainPanel) tabbedPane.getSelectedComponent()).getTaCodeEditor().getText();
            Brainfuck brainfuck = new Brainfuck(code);
            String input = ((JMainPanel) tabbedPane.getSelectedComponent()).getTfArguments().getText();
            String result;
            try {
                result = brainfuck.process(input);
                taResult.setText(result);
            } catch (PointerOutOfBoundsException | IOException | IllegalOperatorException exception) {
                taResult.setText(exception.toString());
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
