package tk1.ue9.client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ConvertTermatureClient extends JFrame {
	
	JComboBox cbFromUnit;
	JComboBox cbToUnit;
	JComboBox cbWebService;
	JTextField tfTemperatureFrom;
	JTextField tfTemperatureTo;
	JButton btnConvert;
	JButton btnClose;
	
	public ConvertTermatureClient() {
		initGUI();
	}
	
	private void initGUI(){
		
		cbFromUnit = new JComboBox();
		cbFromUnit.addItem("Select webservice");
		cbFromUnit.setSelectedIndex(0);		
		
		cbToUnit = new JComboBox();
		cbToUnit.addItem("Select webservice");
		cbToUnit.setSelectedIndex(0);
		
		
		tfTemperatureFrom = new JTextField();
		tfTemperatureFrom.setColumns(10);
		tfTemperatureFrom.setText(Double.toString(0.0));
		
		tfTemperatureTo = new JTextField();
		tfTemperatureTo.setColumns(10);
		tfTemperatureTo.setEditable(false);
		tfTemperatureFrom.setText(Double.toString(0.0));
		
		JPanel fromPanel = new JPanel(new GridLayout(1, 3));
		fromPanel.add(new Label("From: "));
		fromPanel.add(tfTemperatureFrom);
		fromPanel.add(cbFromUnit);
		
		JPanel toPanel = new JPanel(new GridLayout(1, 3));
		toPanel.add(new Label("To: "));
		toPanel.add(tfTemperatureTo);
		toPanel.add(cbToUnit);
		
		JPanel valuesPanel = new JPanel();
		BoxLayout valuePanelLayout = new BoxLayout(valuesPanel, BoxLayout.Y_AXIS);
		valuesPanel.setLayout(valuePanelLayout);
		valuesPanel.add(fromPanel);
		valuesPanel.add(toPanel);
		
				
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(BorderLayout.CENTER, valuesPanel);
		
		
		setSize(300, 200);
		setTitle("Convert temperatures");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				super.windowClosed(e);
				ConvertTermatureClient.this.setVisible(false);
				System.exit(0);
			}
		});
		pack();
	}
	
	public static void main(String[] args) {
		new ConvertTermatureClient().setVisible(true);
	}
}
