package com.xuyihao.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.xuyihao.tools.JohnsonPacketReceiver;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;

/**
 * created by xuyihao on 2016/5/26
 * @author Johnson
 * @description java抓包工具测试主入口
 * @description 主方法首先通过JpcapCaptor获取计算机上面的所有网络接口的列表
 * @description 然后将其打印出来，最后根据用户输入的需要监听的接口，对相应的
 * @description 接口设置相应的监听器，并为每个监听开启一个子线程，进行监听
 * */
public class JohnsonTestMain{
	public static void main(String[] args){
		try{
			final NetworkInterface[] devices = JpcapCaptor.getDeviceList();//通过JpcapCaptor获取网络接口设备列表
			for(int j = 0; j < devices.length; j++){//将网络设备打印到控制台
				System.out.println("网口:  ");
				System.out.println("No."+j+":  "+devices[j].name+" | "+devices[j].description+"");
			}
			
			ArrayList<JpcapCaptor> captors = new ArrayList<>();//定义一个JpcapCaptor的列表
			
			System.out.println("请选择需要监听的接口(格式:1&2&3&4):");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));//设置输入流
			String in = reader.readLine();//用户输入需要监听的网络接口编号
			char[] ins = in.toCharArray();
			System.out.println("请输入每条监听线程的线程等待时间(s):");
			int timeIn = Integer.parseInt(reader.readLine());//用户输入每条监听线程的线程等待（阻塞）时间
			for(int i = 0; i < ins.length; i++){//下面根据用户的输入，对相应的接口设置监听并且开启监听线程
				int deviceNum = (int)ins[i] - 48;
				NetworkInterface nif = devices[deviceNum];
				try{
					JpcapCaptor jpcap = JpcapCaptor.openDevice(nif, 2000, true, 20);
					captors.add(jpcap);
					startCapThread(jpcap, timeIn, deviceNum, devices[deviceNum].name, devices[deviceNum].description);
				}catch(Exception e){
					//do nothing
					e.printStackTrace();
				}
				System.out.println("开启第"+ins[i]+"个设备监听");
			}
			String commandIn = "";//监听开启后，定义一个控制变量用来控制子线程是否关闭（子线程停止监听）
			while(!commandIn.equals("exit")){//如果输入了exit则说明用户希望停止所有的监听线程，如果输入不为exit则继续循环
				System.out.println("停止监听输入  exit ");
				commandIn = reader.readLine();//输入指令
			}
			for(int k = 0; k < captors.size(); k++){//exit指令获取后，对接口列表的接口作停止监听操作
				captors.get(k).breakLoop();
			}
			System.out.println("监听结束! 程序结束!");
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("启动失败!");
		}
	}
	
	/**
	 * @author johnson
	 * @method startCapThread
	 * @description 开启线程的方法
	 * */
	public static void startCapThread(final JpcapCaptor jpcap, final int waitTime){
		new Thread(){
			@Override
			public void run(){
				jpcap.loopPacket(-1, new JohnsonPacketReceiver(waitTime));
			}
		}.start();
	}
	
	/**
	 * @author johnson
	 * @method startCapThread
	 * @description 开启线程的方法 
	 * */
	public static void startCapThread(final JpcapCaptor jpcap, final int waitTime, final int deviceNumber, final String deviceName, final String deviceDescription){
		new Thread(){
			@Override
			public void run(){
				jpcap.loopPacket(-1, new JohnsonPacketReceiver(waitTime, deviceNumber, deviceName, deviceDescription));
			}
		}.start();
	}
}
