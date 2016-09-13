package com.mph.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class CheckUserPw {
	
	Socket socket = null;
	DataOutputStream dos = null;
	DataInputStream dis = null;
	boolean beConnected = false;
	boolean connServerState = false;
	boolean checkExistState = true; //�û��������ı�־ 
	boolean checkPasswd = true;     //����Դ�ı�־
	boolean stateOfServer = true;
	int noOfClient;    //�õ��Ĵ�client��Clients�е����

	String[] indexClient = null;
	
	public static void main(String[] args) {
		CheckUserPw cpUserPw = new CheckUserPw();
		cpUserPw.connectServer("111", "11", "127.0.0.1");
	}
	
	public void connectServer(String name, String passwd, String ip) {
		try {
			
			socket = new Socket(ip, 6666);		
			
			//�õ������
			dos = new DataOutputStream(socket.getOutputStream());
			
			//�õ�������
			dis = new DataInputStream(socket.getInputStream());
			
			//���������Ժ�
			beConnected = true;
			
			int checkNum = 1;  //����һ������
			//��ͣ�Ķ�
			if(checkNum == 1) {
				
				dos.writeUTF("Check" + " " +name + " " +passwd);  //����Ҫ�����û��������뵽������
				String info = dis.readUTF();  //����ʽ��
				
				if(info.equals("success")) {
System.out.println("��֤ͨ�������ӵ�����������");
				this.setConnServerState(true);    //���ӳɹ�����connServerState��true
//				indexClient = info.split("\\s+"); //�ҵ�server���ͻ����Ĵ�Client��Clients�е���ţ�
//				                                  //ʹ������UserLogin���õ����Client�Ķ���
//				this.setNoOfClient(Integer.parseInt(indexClient[1]));
				checkNum = 0;	                  //ûʲô�ã���û�кð취��			
				}	
				
				else if(info.equals("user have exist")) {   //����û��Ѿ�����
					checkExistState = false;
				}
				
				else if(info.equals("User error")) {    //����������
					checkPasswd = false;
				}
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.setStateOfServer(false);
			System.out.println("�Ҳ�������");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("���ӳ���");
		}
	}

	public boolean getConnServerState() {
		return connServerState;
	}

	public void setConnServerState(boolean connServerState) {
		this.connServerState = connServerState;
	}
	
	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public boolean getCheckExistState() {
		return checkExistState;
	}

	public void setCheckExistState(boolean checkExistState) {
		this.checkExistState = checkExistState;
	}

	public boolean getCheckPasswd() {
		return checkPasswd;
	}

	public void setCheckPasswd(boolean checkPasswd) {
		this.checkPasswd = checkPasswd;
	}

	public boolean getStateOfServer() {
		return stateOfServer;
	}

	public void setStateOfServer(boolean stateOfServer) {
		this.stateOfServer = stateOfServer;
	}
}
