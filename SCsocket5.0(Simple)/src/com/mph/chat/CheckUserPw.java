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
	boolean checkExistState = true; //用户存在与否的标志 
	boolean checkPasswd = true;     //密码对错的标志
	boolean stateOfServer = true;
	int noOfClient;    //得到的此client在Clients中的序号

	String[] indexClient = null;
	
	public static void main(String[] args) {
		CheckUserPw cpUserPw = new CheckUserPw();
		cpUserPw.connectServer("111", "11", "127.0.0.1");
	}
	
	public void connectServer(String name, String passwd, String ip) {
		try {
			
			socket = new Socket(ip, 6666);		
			
			//得到输出流
			dos = new DataOutputStream(socket.getOutputStream());
			
			//得到输入流
			dis = new DataInputStream(socket.getInputStream());
			
			//连接上了以后
			beConnected = true;
			
			int checkNum = 1;  //进行一次连接
			//不停的读
			if(checkNum == 1) {
				
				dos.writeUTF("Check" + " " +name + " " +passwd);  //发送要检测的用户名和密码到服务器
				String info = dis.readUTF();  //阻塞式的
				
				if(info.equals("success")) {
System.out.println("验证通过，连接到服务器！！");
				this.setConnServerState(true);    //连接成功，将connServerState置true
//				indexClient = info.split("\\s+"); //找到server发送回来的此Client在Clients中的序号，
//				                                  //使可以在UserLogin中拿到这个Client的对象。
//				this.setNoOfClient(Integer.parseInt(indexClient[1]));
				checkNum = 0;	                  //没什么用，但没有好办法了			
				}	
				
				else if(info.equals("user have exist")) {   //如果用户已经存在
					checkExistState = false;
				}
				
				else if(info.equals("User error")) {    //如果密码错误
					checkPasswd = false;
				}
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.setStateOfServer(false);
			System.out.println("找不到主机");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("连接出错");
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
