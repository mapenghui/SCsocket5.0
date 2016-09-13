package com.mph.chat;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ChatClient extends JFrame implements ActionListener{
	JTextArea jta, inputTextArea, jsendState;
	JScrollPane jsp, jsp1, jsp2;
	JTextField jtf;
	JPanel jPanel, jpanel1C, jpanel1E, jPanel1CC, jPanel1CS, jPanel2C, jPanel2, jbuttonPanel, jPanel3;
	JButton jb, jbZXWH, jbSAT2, jbYZYH, jbYZEH, jbMXEH;
	JTabbedPane jtbPane;
	DataOutputStream dos;
	DataInputStream dis;
	JFrame frame;
	
	Socket socket;
	boolean beConnected;
	String info1;
	
	MemoryUsageDemo memoryusagedemo; 

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ChatClient client = new ChatClient();
	}
	
	public ChatClient() {
		this.initWindow();
		//this.connServer(socket); 
	}

	public void initWindow() {			
		frame = new JFrame("�ͻ���");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(100, 20);
		frame.setLayout(new BorderLayout());

		jtbPane = new JTabbedPane(JTabbedPane.TOP);
		jtbPane.setPreferredSize(new Dimension(750, 650));
		jtbPane.setFont(new Font("����", 0, 18));
		
		jPanel2C = new JPanel();
		jPanel2C.setPreferredSize(new Dimension(750, 650));
		jPanel2C.setLayout(new BorderLayout());
		
		jta = new JTextArea();
		jta.setLineWrap(true);                     //�����Զ����й��� 
		jta.setWrapStyleWord(true); 
		 //�������Զ�����
		jta.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				jta.setCaretPosition(jta.getText().length());
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
			}  	
        	});
		
		jsp = new JScrollPane(jta);
		
		inputTextArea = new JTextArea(10, 50);
		inputTextArea.setLineWrap(true);                     //�����Զ����й��� 
        inputTextArea.setWrapStyleWord(true); 
        //�������Զ�����
        inputTextArea.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				inputTextArea.setCaretPosition(inputTextArea.getText().length());
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
			}  	
        	});
        
		jsp1 = new JScrollPane(inputTextArea);
		jb = new JButton("����");
		jb.addActionListener(this);
		jPanel2 = new JPanel();
		jPanel2.setPreferredSize(new Dimension(750, 200));
		jPanel2.add(jsp1);
		jPanel2.add(jb); 
		
		jPanel2C.add(jsp, "Center");
		jPanel2C.add(jPanel2, "South");
		
		//��JPanel1C�л�ͼ
		//��ͼ��
		memoryusagedemo = new MemoryUsageDemo(30000);
		memoryusagedemo.setPreferredSize(new Dimension(750, 495));
        //memoryusagedemo.setBounds(3, 3, 750, 500);
        (memoryusagedemo.new DataGenerator(100)).start();
		
		jpanel1C = new JPanel();
		jpanel1C.setPreferredSize(new Dimension(750, 650));
		
		//��jpanel1E����Ӱ���
		jbZXWH = new JButton("�������");// 118.56  58.33
		jbZXWH.setBounds(25, 30, 150, 30);
		jbZXWH.addActionListener(this);
		jbSAT2 = new JButton("SAT-2");  // 199.31  57.16
		jbSAT2.setBounds(25, 90, 150, 30);
		jbSAT2.addActionListener(this);
		jbYZYH = new JButton("����һ��");// 209.06  55.10
		jbYZYH.setBounds(25, 150, 150, 30);
		jbYZYH.addActionListener(this);
		jbYZEH = new JButton("���޶���");// 248.32  34.26
		jbYZEH.setBounds(25, 210, 150, 30);
		jbYZEH.addActionListener(this);
		jbMXEH = new JButton("���Ƕ���");//133.47   41.28
		jbMXEH.setBounds(25, 270, 150, 30);
		jbMXEH.addActionListener(this);
		
		jbuttonPanel = new JPanel(null);
		jbuttonPanel.setSize(new Dimension(200, 450));
		jbuttonPanel.add(jbZXWH);
		jbuttonPanel.add(jbSAT2);
		jbuttonPanel.add(jbYZYH);
		jbuttonPanel.add(jbYZEH);
		jbuttonPanel.add(jbMXEH);
		
		jPanel3 = new JPanel();
		jPanel3.setPreferredSize(new Dimension(200, 150));
		
		jpanel1E = new JPanel();
		jpanel1E.setPreferredSize(new Dimension(200, 650));
		jpanel1E.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		jpanel1E.setLayout(new BorderLayout());
		jpanel1E.add(jbuttonPanel,"Center");
		jpanel1E.add(jPanel3, "South");

		jpanel1C.setLayout(new BorderLayout());
		jPanel1CC = new JPanel();
		jPanel1CC.setPreferredSize(new Dimension(750, 500));
		//jPanel1CC.setBorder(BorderFactory.createLineBorder(Color.RED));
		jPanel1CC.add(memoryusagedemo);

		//��jpanelCS�����һ��JtextArea
		jsendState = new JTextArea();
		//jsendState.setPreferredSize(new Dimension(745, 140));
		jsendState.setLineWrap(true); 
		jsendState.setWrapStyleWord(true);
		jsp2 = new JScrollPane(jsendState);
		
		jPanel1CS = new JPanel();
		jPanel1CS.setPreferredSize(new Dimension(744, 148));
		//jPanel1CS.setBackground(Color.GREEN);
		jPanel1CS.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		jPanel1CS.setLayout(new BorderLayout());
		jPanel1CS.add(jsp2, "Center");

		jpanel1C.add(jPanel1CC, BorderLayout.CENTER);
		jpanel1C.add(jPanel1CS, BorderLayout.SOUTH);
		
		jtbPane.add("Dialogue", jPanel2C);
		jtbPane.add("Graphic", jpanel1C);

		frame.add(jtbPane, BorderLayout.CENTER);
		frame.add(jpanel1E, BorderLayout.EAST);

		frame.setVisible(true);
		frame.pack();

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			});
	}
	
	public void connServer(Socket socket) {
		try {
			//socket = new Socket("127.0.0.1", 6666);
			
			//�õ������
			dos = new DataOutputStream(socket.getOutputStream());
			
			//�õ�������
			dis = new DataInputStream(socket.getInputStream());
			
			//���������Ժ�
			beConnected = true;
					
			new Thread(new RecThread()).start();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("�Ҳ�������");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("���ӳ���");
		}
	}
	
	//�ͻ��˷�������
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == jb) {
			String info = inputTextArea.getText().trim();  //trimȥ�����߿ո�
			try {
				dos.writeUTF(info);
				jta.append("�����������"+ info + "\r\n");
				inputTextArea.setText("");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
				System.out.println("�������ݳ�����");
			}
		}
		
		if(e.getSource() == jbZXWH) {
			try {
				info1 = "satellite 118.56 058.33e";
				dos.writeUTF(info1);
				jta.append("�����������������ţ� "+ info1 + "\r\n");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
				System.out.println("��������������ݳ�����");
				JOptionPane.showMessageDialog(null, "δ���ӵ�����������", "��������", JOptionPane.WARNING_MESSAGE);
			}
		}
		
		if(e.getSource() == jbSAT2) {
			try {
				info1 = "satellite 199.31 057.16e";
				dos.writeUTF(info1);
				jta.append("�����������SAT-2�� "+ info1 + "\r\n");
			//} catch (Exception e1) {
			  } catch (Exception e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
				System.out.println("����SAT-2���ݳ�����");
				JOptionPane.showMessageDialog(null, "δ���ӵ�����������", "��������", JOptionPane.WARNING_MESSAGE);
			}
		}
		//jbYZYH = new JButton("����һ��");// 209.06  55.10
		if(e.getSource() == jbYZYH) {
			try {
				info1 = "satellite 209.06 055.10e";
				dos.writeUTF(info1);
				jta.append("���������������һ�ţ� "+ info1 + "\r\n");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
				System.out.println("��������һ�����ݳ�����");
				JOptionPane.showMessageDialog(null, "δ���ӵ�����������", "��������", JOptionPane.WARNING_MESSAGE);
			}
		}
		//jbYZEH = new JButton("���޶���");// 248.32  34.26
		if(e.getSource() == jbYZEH) {
			try {
				info1 = "satellite 248.32 034.26e";
				dos.writeUTF(info1);
				jta.append("��������������޶��ţ� "+ info1 + "\r\n");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
				System.out.println("�������޶������ݳ�����");
				JOptionPane.showMessageDialog(null, "δ���ӵ�����������", "��������", JOptionPane.WARNING_MESSAGE);
			}
		}
		//jbMXEH = new JButton("���Ƕ���");//133.47   41.28
		if(e.getSource() == jbMXEH) {
			try {
				info1 = "satellite 133.47 041.28e";
				dos.writeUTF(info1);
				jta.append("��������������Ƕ��ţ� "+ info1 + "\r\n");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
				System.out.println("�������Ƕ������ݳ�����");
				JOptionPane.showMessageDialog(null, "δ���ӵ�����������", "��������", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	class RecThread implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
				//��ͣ�Ķ�
				try {
					while(beConnected) {					
						String info;
						info = dis.readUTF();//����ʽ��
						jta.append("��������" + info + "\r\n");
					} 
				}  catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			} finally {
				try {
					if(socket != null) socket.close();
					if(dis != null) dis.close();
					if(dos != null) dos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
}
