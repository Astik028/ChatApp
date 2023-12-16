package chatapp;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.Socket;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;


  public class Client implements ActionListener {
	static DataOutputStream dout;
	static JFrame f=new JFrame();
	JTextField text1;
    static JPanel a1;
	static Box vertical = Box.createVerticalBox();
	
	 Client() {
		 
		f.setLayout(null);
	   JPanel p1=new JPanel();
		p1.setBackground(new Color(8,90,80));
		p1.setBounds(0,0,450,70);
		f.add(p1);
		
		//String impath="C:\\Users\\user\\Downloads\\profile-pic.png";
		ImageIcon image1=new ImageIcon(ClassLoader.getSystemResource(" "));
		Image im1=image1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon im2=new ImageIcon(im1); 
		JLabel back=new JLabel(im2);
		back.setBounds(5,20,30,30);
		p1.add(back);
		
		back.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				System.exit(0);
			}
		});
		
		//add profile pic
		ImageIcon im3=new ImageIcon(ClassLoader.getSystemResource(" "));
		Image im4=im3.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon im5=new ImageIcon(im4); 
		JLabel profile=new JLabel(im5);
		profile.setBounds(300,20,30,30);
		p1.add(profile);
		
		
		//for vedio
		ImageIcon im6=new ImageIcon(ClassLoader.getSystemResource(" "));
		Image im7=im6.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT);
		ImageIcon im8=new ImageIcon(im7); 
		JLabel vedio=new JLabel(im8);
		profile.setBounds(45,10,40,40);
		
		//for call
		ImageIcon im9=new ImageIcon(ClassLoader.getSystemResource(" "));
		Image im10=im9.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT);
		ImageIcon im11=new ImageIcon(im10); 
		JLabel call=new JLabel(im11);
		profile.setBounds(360,20,40,40);
		
		//for more options
		ImageIcon im12=new ImageIcon(ClassLoader.getSystemResource(" "));
		Image im13=im12.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT);
		ImageIcon im14=new ImageIcon(im13); 
		JLabel more=new JLabel(im14);
		profile.setBounds(420,20,40,40);
		
		JLabel name=new JLabel("Client");
		name.setBounds(20,40,100,20);
		name.setForeground(Color.WHITE);
		name.setFont(new Font("SAN_SERIF",Font.BOLD,20));
		p1.add(name);
		
		//status
		JLabel status=new JLabel("Active Now");
		status.setBounds(20,75,100,20);
		status.setForeground(Color.WHITE);
		status.setFont(new Font("SAN_SERIF",Font.ITALIC,10));
		p1.add(status);
		
		
		//new panel for text
	    a1=new JPanel();
		a1.setBounds(5,75,420,510);
		f.add(a1);
		
		// textfield to send messages
		
		text1=new JTextField();
		text1.setBounds(5,600,340,30);
		name.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
		f.add(text1);
		
		//Button to send 
		JButton send=new JButton("send");
		send.setBounds(350,600,80,30);
		send.addActionListener(this);
		f.add(send);
		
		p1.add(profile);
		f.setSize (450,700); 
		f.setLocation(200,50);
		f.getContentPane().setBackground(Color.WHITE);
		f.setVisible(true);
		
	}

	public void actionPerformed(ActionEvent ae) {
		try {
		String output=text1.getText();
		//JLabel out=new JLabel(output);
		JPanel p2=formatLabel(output);
		//p2.add(out);
		a1.setLayout(new BorderLayout());
        JPanel right=new JPanel(new BorderLayout());
        right.add(p2,BorderLayout.LINE_END);
        
        vertical.add(right);
        vertical.add(Box.createVerticalStrut(15));
        a1.add(vertical,BorderLayout.PAGE_START);
        
        //send to server
        dout.writeUTF(output);
        
        //set the textfield empty after text is sent
        text1.setText("");
        
        //reload frame
        f.repaint();
        f.invalidate();
        f.validate();
		}catch(Exception e) {
			e.printStackTrace();
		}
        
	}
	
	//function to format msgs
	public static JPanel formatLabel(String output) {
		 JPanel panel=new JPanel();
		 panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		 
		 
		 JLabel out=new JLabel("<html><p style=\"width:150px\">"  + output + "</p></html>");
		 out.setFont(new Font("Tahoma",Font.PLAIN,20));
		 out.setBackground(new Color(36,210,100));
		 out.setOpaque(true);
		 out.setBorder(new EmptyBorder(15,15,15,50));
		 panel.add(out);
		 
		 //for time
		 Calendar cal=Calendar.getInstance();
		 SimpleDateFormat sdf=new  SimpleDateFormat("HH:mm");
		 JLabel time=new JLabel();
		 time.setText(sdf.format(cal.getTime()));
		 panel.add(time);
		 return panel;
	}
	
	public static void main(String []args) {
		new Client();
		
		try {
			Socket s=new Socket("127.0.0.1",3001);
			
			DataInputStream din=new DataInputStream(s.getInputStream() );
		    dout=new DataOutputStream(s.getOutputStream());
		    
		    while(true) {
		    	a1.setLayout(new BorderLayout());
				String msg=din.readUTF();
				JPanel panel=formatLabel(msg);
				
				JPanel left=new JPanel(new BorderLayout());
				left.add(panel,BorderLayout.LINE_START);
				vertical.add(left);
				vertical.add(Box.createVerticalStrut(18));
				a1.add(vertical,BorderLayout.PAGE_START);
				f.validate();
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
