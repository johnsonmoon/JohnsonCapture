package com.xuyihao.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

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
				System.out.println("扫描到以下这些网口:  ");
				System.out.println("No."+j+":  "+devices[j].name+" | "+devices[j].description+"");
			}
			System.out.println("请选择需要监听的接口:");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String in = reader.readLine();
			int selected = Integer.parseInt(in);
			NetworkInterface nif = devices[selected];
			JpcapCaptor jpcap = JpcapCaptor.openDevice(nif, 2000, true, 20);
			startCapThread(jpcap);
			System.out.println("开始抓取第"+selected+"个卡口上的数据");
			
		}catch(Exception e){
			//e.printStackTrace();
			System.out.println("启动失败!");
		}
	}
	
	public static void startCapThread(final JpcapCaptor jpcap){
		new Thread(){
			@Override
			public void run(){
				jpcap.loopPacket(-1, new JohnsonPacketReceiver());
			}
		}.start();
	}
}
