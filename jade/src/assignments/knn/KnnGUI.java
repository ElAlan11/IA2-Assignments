package assignments.knn;

import jade.core.AID;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


class KnnGUI extends JFrame {
    private KNN ag = new KNN();
    private JTextField x1Field, x2Field;
    

    KnnGUI(KNN a){
      super(a.getLocalName());
      ag = a;
      
      JPanel p = new JPanel();
      p.setLayout(new GridLayout(3, 2));

      p.add(new JLabel("X1:"));
      x1Field = new JTextField(15);
      p.add(x1Field);

      p.add(new JLabel("X2:"));
      x2Field = new JTextField(15);
      p.add(x2Field);

      getContentPane().add(p, BorderLayout.CENTER);
      
      JButton addButton = new JButton("Submit");
      addButton.addActionListener( new ActionListener() {
        public void actionPerformed(ActionEvent ev) {
          try {
            String x1 = x1Field.getText().trim();
            String x2 = x2Field.getText().trim();
            ag.updateX(Double.parseDouble(x1), Double.parseDouble(x2));
            x1Field.setText("");
            x2Field.setText("");
          }
          catch (Exception e) {
            JOptionPane.showMessageDialog(KnnGUI.this, "Invalid values. "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
          }
        }
      } );
      p = new JPanel();
      p.add(addButton);
      getContentPane().add(p, BorderLayout.SOUTH);
      
      // Make the agent terminate when the user closes 
      // the GUI using the button on the upper right corner 
      addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
          ag.doDelete();
          //ag.super.onEnd();
        }
      } );
      
      setResizable(false);
    }
    
    public void showGui() {
      pack();
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      int centerX = (int)screenSize.getWidth() / 2;
      int centerY = (int)screenSize.getHeight() / 2;
      setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
      super.setVisible(true);
    }
 }