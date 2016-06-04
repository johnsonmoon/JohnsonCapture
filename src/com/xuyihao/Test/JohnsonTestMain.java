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
 * @description java抓包工具测试入口
 * */
public class JohnsonTestMain{
	public static void main(String[] args){
		try{
			final NetworkInterface[] devices = JpcapCaptor.getDeviceList();
			for(int j = 0; j < devices.length; j++){
				//System.out.println("扫描到以下这些网口:  ");
				System.out.println("网口:  ");
				System.out.println("No."+j+":  "+devices[j].name+" | "+devices[j].description+"");
			}
			
			ArrayList<JpcapCaptor> captors = new ArrayList<>();
			
			System.out.println("请选择需要监听的接口(格式:1&2&3&4):");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String in = reader.readLine();
			char[] ins = in.toCharArray();
			System.out.println("请输入每条监听线程的线程等待时间(s):");
			int timeIn = Integer.parseInt(reader.readLine());
			for(int i = 0; i < ins.length; i++){
				if(ins[i] != '&'){
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
			}
			String commandIn = "";
			while(!commandIn.equals("exit")){
				System.out.println("停止监听输入  exit ");
				commandIn = reader.readLine();
			}
			for(int k = 0; k < captors.size(); k++){
				captors.get(k).breakLoop();
			}
			System.out.println("监听结束! 程序结束!");
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("启动失败!");
		}
	}
	
	public static void startCapThread(final JpcapCaptor jpcap, final int waitTime){
		new Thread(){
			@Override
			public void run(){
				jpcap.loopPacket(-1, new JohnsonPacketReceiver(waitTime));
			}
		}.start();
	}
	
	public static void startCapThread(final JpcapCaptor jpcap, final int waitTime, final int deviceNumber, final String deviceName, final String deviceDescription){
		new Thread(){
			@Override
			public void run(){
				jpcap.loopPacket(-1, new JohnsonPacketReceiver(waitTime, deviceNumber, deviceName, deviceDescription));
			}
		}.start();
	}
}
