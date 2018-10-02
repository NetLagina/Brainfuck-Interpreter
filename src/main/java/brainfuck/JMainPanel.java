package brainfuck;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.LayoutManager;
import javax.swing.border.TitledBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class JMainPanel extends JPanel {

	private static final long serialVersionUID = 5003754420161917133L;
	
	private JPanel argumentsPanel;
	private JTextArea taCodeEditor;
	private JTextField tfArguments;

	public JMainPanel() {
		initialize();
	}

	public JMainPanel(LayoutManager arg0) {
		super(arg0);
		initialize();
	}

	public JMainPanel(boolean arg0) {
		super(arg0);
		initialize();
	}

	public JMainPanel(LayoutManager arg0, boolean arg1) {
		super(arg0, arg1);
		initialize();
	}
	
	private void initialize() {
		this.setLayout(new BorderLayout(0, 0));
		
		taCodeEditor = new JTextArea();
		taCodeEditor.setFont(new Font("Courier New", Font.PLAIN, 12));
		taCodeEditor.setTabSize(4);
		this.add(taCodeEditor, BorderLayout.CENTER);
		
		argumentsPanel = new JPanel();
		argumentsPanel.setBorder(new TitledBorder(null, "Arguments", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.add(argumentsPanel, BorderLayout.SOUTH);

		tfArguments = new JTextField();
		tfArguments.setFont(new Font("Courier New", Font.PLAIN, 12));
		tfArguments.setColumns(10);
		
		GroupLayout gl_ArgumentsPanel = new GroupLayout(argumentsPanel);
		gl_ArgumentsPanel
			.setHorizontalGroup(gl_ArgumentsPanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_ArgumentsPanel
							.createSequentialGroup()
							.addContainerGap()
							.addComponent(tfArguments, GroupLayout.DEFAULT_SIZE, 740, Short.MAX_VALUE)
							.addContainerGap()));
		gl_ArgumentsPanel
			.setVerticalGroup(gl_ArgumentsPanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_ArgumentsPanel
							.createSequentialGroup()
							.addGap(5)
							.addComponent(tfArguments, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		argumentsPanel.setLayout(gl_ArgumentsPanel);
		
		this.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	}
	
	/**
	 * @return the argumentsPanel
	 */
	public JPanel getArgumentsPanel() {
		return argumentsPanel;
	}

	/**
	 * @return the taCodeEditor
	 */
	public JTextArea getTaCodeEditor() {
		return taCodeEditor;
	}

	/**
	 * @return the tfArguments
	 */
	public JTextField getTfArguments() {
		return tfArguments;
	}

}
