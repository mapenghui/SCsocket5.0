package com.mph.view;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import com.mph.chat.ChatClient;
import com.mph.chat.CheckUserPw;
import com.mph.tools.*;

public class UserLogin extends JDialog implements ActionListener{

	JLabel jl1, jl2, jl3, jl4;
	JTextField jtf, jtfIP;
	JPasswordField jpf;
	JButton jb1, jb2;
	JMIPV4AddressField ipFiled;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UserLogin us = new UserLogin();
	}

	public UserLogin() {
		Container ct = this.getContentPane();
		//空布局
		this.setLayout(null);
		
		jl1 = new JLabel("用户名：");
		jl1.setFont(MyTools.font1);
		jl1.setBounds(155, 130, 70, 30);
		ct.add(jl1);		
		
		jl3 = new JLabel("密  码：");
		jl3.setFont(MyTools.font1);
		jl3.setBounds(155, 162, 70, 30);
		ct.add(jl3);
		
		jl4 = new JLabel("   IP： ");
		jl4.setFont(MyTools.font1);
		jl4.setBounds(155, 192, 70, 30);
		ct.add(jl4);
		
		jtf = new JTextField(30);
		jtf.setBounds(250, 130, 140, 28);
		//设置下凹感觉
		jtf.setBorder(BorderFactory.createLoweredBevelBorder());
		ct.add(jtf);
		
		jpf = new JPasswordField(30);
		jpf.setBounds(250, 162, 140, 28);
		//设置下凹感觉
		jpf.setBorder(BorderFactory.createLoweredBevelBorder());
		ct.add(jpf);
		
		ipFiled = new JMIPV4AddressField();
		ipFiled.setIpAddress("192.168.1.100");
		ipFiled.setBounds(250, 192, 140, 28);
		ipFiled.setBorder(BorderFactory.createLoweredBevelBorder());
		ct.add(ipFiled);		
		
		jb1 = new JButton("确定");
		jb1.addActionListener(this);
		jb1.setBounds(200, 240, 70, 30);
		ct.add(jb1);
		
		jb2 = new JButton("取消");
		jb2.addActionListener(this);
		jb2.setBounds(280, 240, 70, 30);
		ct.add(jb2);
		
		//创建一个BackImage对象
		BackImage bi = new BackImage();
		//把位置确定
		bi.setBounds(0, 0, 630, 380);

		ct.add(bi);
		
		//不使用上下边框
		this.setUndecorated(true);
		this.setSize(630, 380);
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation(width/2-300, height/2-200);
		this.setVisible(true);
	}
	
	//内部类	
	class BackImage extends JPanel{
	     
	    Image image=null;
	     
	    public void paint(Graphics g){
	        try {
	            image=ImageIO.read(new File("images\\userLogin15.jpg"));
	            g.drawImage(image, 0, 0, 630, 380, null);
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == jb1){
			
			String uName =  this.jtf.getText().trim();
			String pw = new String(this.jpf.getPassword());
			String getIP = ipFiled.toIPString(ipFiled.getIPValue());
			System.out.println(uName + pw + getIP);
			
			if( uName.equals("") || pw.equals("") || getIP.equals("")) {
				JOptionPane.showMessageDialog(null, "用户名、密码、IP不能为空！", "数据格式警告",
						JOptionPane.WARNING_MESSAGE);
			}
			else {
				CheckUserPw cup = new CheckUserPw();
				cup.connectServer(uName, pw, getIP);
				//ChatClient c = new ChatClient(); 
				if(cup.getStateOfServer() == true) {     //如果服务器开启
					if(cup.getCheckExistState() == false) {   //如果用户已经存在
						JOptionPane.showMessageDialog(null, "用户已存在，请不要重复连接！", "登陆警告",
								JOptionPane.WARNING_MESSAGE);
					}
					
					if(cup.getCheckPasswd() == false) {      //如果用户密码错误
						JOptionPane.showMessageDialog(null, "用户名或密码错误！", "登陆警告",
								JOptionPane.WARNING_MESSAGE);
					}
							
					if(cup.getConnServerState() == true) {
						
						//关闭登陆界面
						this.dispose();
						
						ChatClient client = new ChatClient();
						client.connServer(cup.getSocket());
					}
				}
				else {   //服务器未开启
					JOptionPane.showMessageDialog(null, "服务器未开启！", "网络警告",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		}
		else if(e.getSource() == jb2) {
			this.dispose();
		}
	}
}
