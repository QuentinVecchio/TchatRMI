package server;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import javax.swing.event.ListSelectionListener;

//import oracle.jdeveloper.layout.PaneConstraints;
//import oracle.jdeveloper.layout.PaneLayout;

public class ServerView extends JFrame  {


    private JPanel jPanel1 = new JPanel();
    private JTabbedPane jTabbedPane1 = new JTabbedPane();
    //private PaneLayout paneLayout1 = new PaneLayout();
    private JPanel jPanel2 = new JPanel();
    private JPanel jPanel3 = new JPanel();
    private JList jList1 = new JList();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JLabel jLabel1 = new JLabel();
    private BorderLayout borderLayout2 = new BorderLayout();
    private JList jList2 = new JList();
    private JPanel jPanel4 = new JPanel();

    public ServerView() {
        super();
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        
        String choix[] = {"adrien","pierre","michel"};
        jList1= new JList(choix);
        
        //jPanel1.setLayout(paneLayout1);
        jTabbedPane1.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        jPanel2.setLayout(borderLayout1);
        jPanel3.setLayout(borderLayout2);
        jLabel1.setText("messege en temps directe :");
        jPanel2.add(jList1, BorderLayout.CENTER);
        jPanel2.add(jLabel1, BorderLayout.NORTH);
        jTabbedPane1.addTab("adrien", jPanel2);
        jPanel3.add(jList2, BorderLayout.NORTH);
        jPanel3.add(jPanel4, BorderLayout.CENTER);
        jTabbedPane1.addTab(null, jPanel3);
        /*jPanel1.add(jTabbedPane1,
                    new PaneConstraints("jTabbedPane1", "jTabbedPane1",
                                        PaneConstraints.ROOT, 1.0f));*/
        this.getContentPane().add(jPanel1, null);
        this.setSize(800, 500);
        this.setVisible(true);
    }
}
