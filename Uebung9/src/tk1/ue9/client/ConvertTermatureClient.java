package tk1.ue9.client;

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.text.DecimalFormat;
import java.util.Locale;

import javax.swing.JFrame;


public class ConvertTermatureClient extends JFrame 
{
	private static final long serialVersionUID = 5784201068834350325L;
   
	private javax.swing.JButton convertBtn;
   private javax.swing.JComboBox serviceCombo;
   private javax.swing.JLabel serviceLabel;
   private javax.swing.JComboBox sourceCombo;
   private javax.swing.JLabel sourceLabel;
   private javax.swing.JTextField sourceTextfield;
   private javax.swing.JComboBox targetCombo;
   private javax.swing.JLabel targetLabel;
   private javax.swing.JTextField targetTextfield;
   private javax.swing.JLabel unitLabel;
   private javax.swing.JLabel degreeSign1;
   private javax.swing.JLabel degreeSign2;
	private LocalConvTempProxy localProxy = new LocalConvTempProxy();
	private WebserviceXTemperatureProxy webProxy = new WebserviceXTemperatureProxy();
   private ITemperatureConverterProxy currentProxy;
   private double targetDegree = 0.0;
	
   /**
    * constructor
    */
	public ConvertTermatureClient() 
	{
		initGUI();
	}
	
	/**
	 * initializes the gui components and adds appropriate listeners
	 */
	private void initGUI()
	{
      // initial settings
	   currentProxy = webProxy;
	   
	   setTitle("Convert temperatures");
      setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
      setResizable(false);
      
	   sourceLabel = new javax.swing.JLabel();
      targetLabel = new javax.swing.JLabel();
      sourceTextfield = new javax.swing.JTextField();
      targetTextfield = new javax.swing.JTextField();
      unitLabel = new javax.swing.JLabel();
      sourceCombo = new javax.swing.JComboBox();
      targetCombo = new javax.swing.JComboBox();
      serviceLabel = new javax.swing.JLabel();
      serviceCombo = new javax.swing.JComboBox();
      convertBtn = new javax.swing.JButton();
      degreeSign1 = new javax.swing.JLabel();
      degreeSign2 = new javax.swing.JLabel();
      
      // label captions
      sourceLabel.setText("Source Temperature:");
      targetLabel.setText("Target Temperature:");
      unitLabel.setText("Temperature Unit");
      serviceLabel.setText("Webservice:");
      degreeSign1.setText("\u00B0"); // print degree sign as unicode
      degreeSign2.setText("\u00B0");
      
      // source degree row
      sourceTextfield.setText("0.00");
      sourceTextfield.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
              sourceTextfieldActionPerformed();
          }
      });
      sourceTextfield.addFocusListener(new java.awt.event.FocusListener()
            {
            @Override
            public void focusGained(FocusEvent e)
            {
              // nothing to do 
            }
            @Override
            public void focusLost(FocusEvent e)
            {
               sourceTextfieldActionPerformed();
            }
            });
      sourceCombo.setModel(new javax.swing.DefaultComboBoxModel(currentProxy.getAvailableTemperatures()));
      sourceCombo.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent evt) {
              resetTargetTextfield();
          }
      });
      
      // target degree row
      targetTextfield.setEditable(false);
      targetCombo.setModel(new javax.swing.DefaultComboBoxModel(currentProxy.getAvailableTemperatures()));
      targetCombo.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent evt) {
             resetTargetTextfield();
         }
      });
      
      // webservice row
      serviceCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { webProxy.getName(), localProxy.getName()}));
      serviceCombo.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e)
         {
            serviceComboActionPerformed(e);
         }
      });
      
      // convert row
      convertBtn.setText("Convert");
      convertBtn.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent evt) {
              convertBtnActionPerformed(evt);
          }
      });
      
      // setting layout      
      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
      getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
          layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
              .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                  .addGroup(layout.createSequentialGroup()
                      .addContainerGap()
                      .addComponent(convertBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                  .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                      .addContainerGap()
                      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                          .addComponent(serviceLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                          .addComponent(targetLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                          .addComponent(sourceLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))
                      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                          .addGroup(layout.createSequentialGroup()
                              .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                  .addComponent(sourceTextfield)
                                  .addComponent(targetTextfield, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                              .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                  .addComponent(degreeSign2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                  .addComponent(degreeSign1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                              .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                  .addComponent(targetCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                  .addComponent(sourceCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                  .addComponent(unitLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)))
                          .addComponent(serviceCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
              .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );
      layout.setVerticalGroup(
          layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
              .addContainerGap()
              .addComponent(unitLabel)
              .addGap(14, 14, 14)
              .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                  .addComponent(sourceLabel)
                  .addComponent(sourceTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addComponent(sourceCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addComponent(degreeSign1))
              .addGap(18, 18, 18)
              .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                      .addComponent(targetCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                      .addComponent(targetLabel)
                      .addComponent(targetTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                  .addComponent(degreeSign2))
              .addGap(18, 18, 18)
              .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                  .addComponent(serviceLabel)
                  .addComponent(serviceCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
              .addGap(18, 18, 18)
              .addComponent(convertBtn)
              .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );
      pack();
	}
	
	/**
	 * reads the value of the target degree input field. if the input is a correct double
	 * value, the input is saved. otherwise the last correct input is restored.
	 */
	private void sourceTextfieldActionPerformed() 
   {
      try
      {
         targetDegree = Double.valueOf(sourceTextfield.getText());
      }
      catch (Exception e)
      {
         sourceTextfield.setText(String.valueOf(targetDegree));
      }
      
      // delete the conversion result, since the result may be false now
      resetTargetTextfield();
   }
	
	/**
	 * starts the temperature conversion by calling the current proxies
	 * convert method. afterwards the result is written into the output
	 * text field.
	 * 
	 * @param e
	 */
	private void convertBtnActionPerformed(ActionEvent e) 
	{
	   double conv = currentProxy.convert(
	         targetDegree, 
	         sourceCombo.getSelectedItem().toString(), 
	         targetCombo.getSelectedItem().toString()
	         );
	   targetTextfield.setText(String.valueOf(new DecimalFormat("0.00").format(conv)));
	}
	
	/**
	 * nullifies the target/output text field. usually called, after any
	 * input change.
	 */
	private void resetTargetTextfield()
	{
	   targetTextfield.setText("");
	}
	
	/**
	 * determines the current proxy selected in the accordant combo box.
	 * 
	 * @param e
	 */
	private void serviceComboActionPerformed(ActionEvent e)
	{
	   if (serviceCombo.getSelectedItem().equals(webProxy.getName()))
	   {
	      currentProxy = webProxy;
	   }
	   else if (serviceCombo.getSelectedItem().equals(localProxy.getName()))
	   {
	      currentProxy = localProxy;
	   }
	   
	   resetTargetTextfield();
	   
	   // In theory the source/target combobox values should be updated now,
	   // but since they have equal temperature units for both proxies it is
	   // not necessary to implement it.
	}
	
	/**
	 * client starting method.
	 * 
	 * @param args
	 */
	public static void main(String[] args) 
	{
	   Locale.setDefault(Locale.ENGLISH);
	   new ConvertTermatureClient().setVisible(true);
	}
}
