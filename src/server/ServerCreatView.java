package server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.JTextField;

import oracle.jdeveloper.layout.VerticalFlowLayout;

public class ServerCreatView extends JFrame {
    private ServerController servercont;
        
    private JPanel jPanel1 = new JPanel();
    private VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
    private JPanel jPanel2 = new JPanel();
    private JLabel jLabel1 = new JLabel();
    private JTextField jTextField1 = new JTextField();
    private JButton jButton1 = new JButton();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JLabel jLabel2 = new JLabel();

    public ServerCreatView(ServerController aServercont ) {
        servercont=aServercont;
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        jPanel1.setLayout(verticalFlowLayout1);
        jPanel2.setLayout(borderLayout1);
        jLabel1.setText("insserez le numero de port: ");
        jTextField1.setSize(new Dimension(70,19));
        //jTextField1.setMinimumSize(new Dimension(30, 19));
        jButton1.setText("Ok");
        jButton1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButton1_actionPerformed(e);
                }
            });
        jLabel2.setForeground(Color.red);
        jLabel2.setFont(new Font("Tahoma", 1, 12));
        jPanel2.add(jLabel1, BorderLayout.WEST);
        jPanel2.add(jTextField1, BorderLayout.CENTER);
        jPanel2.add(jButton1, BorderLayout.EAST);
        jPanel1.add(jPanel2, null);
        jPanel1.add(jLabel2, null);
        this.getContentPane().add(jPanel1, null);
        this.setSize(400, 100);
        this.setVisible(true);
    }

    private void jButton1_actionPerformed(ActionEvent e) {
        try {
            servercont.InitServeur(Integer.parseInt(jTextField1.getText()));
        }
        catch(NumberFormatException nfe)
        {
            jLabel2.setText("inserte a number");
        }
    }
}
