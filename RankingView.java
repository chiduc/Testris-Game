package Tetris;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;


public class RankingView extends JFrame {

	private static final long serialVersionUID = 6L;
	
	protected Main main;
	static final String DB_URL = "jdbc:sqlserver://localhost:1433;encrypt=true;databaseName=rank;user=sa;password=Chiduc123;encrypt=true;trustServerCertificate=true";
	RankingView() 
	{
		 Object[] columnNames = {"Top","Tên Người Chơi","Điểm"};
	        Object[][] rowData = new Object[10][3];
        JFrame jf = new JFrame();
        JPanel panel = new JPanel(new BorderLayout());
        


        try (Connection con = DriverManager.getConnection(DB_URL); 
        	 Statement stmt = con.createStatement();) 
        {
            String SQL = "SELECT TOP 10 * FROM BXH order by BXH.Diem DESC";
            ResultSet rs = stmt.executeQuery(SQL);
            int i = 0;
            int j = 1;
            while (rs.next()) 
            {                	
                String name = rs.getString("Ten");
                int score = rs.getInt("Diem");
                rowData[i][0] = j;
                rowData[i][1] = name;
                rowData[i][2] = score;                
                i++;j++;              
            }
        }
        catch (SQLException e) {        	
            e.printStackTrace();
        }

        JTable table = new JTable(rowData, columnNames);
        
        panel.add(table.getTableHeader(), BorderLayout.NORTH);
        table.setFont(new Font("Serif", Font.PLAIN, 18));
        panel.add(table, BorderLayout.CENTER);
        
        jf.setContentPane(panel);
        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setSize(1024,768);
        jf.setSize(1024,768);
        
        jf.setVisible(true);
        jf.setLocationRelativeTo(null);
        
        jf.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                	int dialogButton = JOptionPane.YES_NO_OPTION;
                    int dialogResult = JOptionPane.showConfirmDialog (null, "Do you want to exit ?","Notification",dialogButton);
                    if(dialogResult == JOptionPane.YES_OPTION)
                    {
                    	try {
                    		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    		jf.setVisible(false);   						
                    		String a[] = null;
    						Main.main(a);   						   						
    					} catch (Exception e1) { 
    						e1.printStackTrace();
    					}  						
                    }
                    else {
	                	jf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	                }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                
            }		                   
		});

        
	 }
	

	}

