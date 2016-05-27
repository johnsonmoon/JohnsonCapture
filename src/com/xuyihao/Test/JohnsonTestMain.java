package com.xuyihao.Test;

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
			for(int i = 0; i < devices.length; i ++){
				NetworkInterface nif = devices[i];
				JpcapCaptor jpcap = JpcapCaptor.openDevice(nif, 2000, true, 20);
				startCapThread(jpcap);
				System.out.println("开始抓取第"+i+"个卡口上的数据");
			}
		}catch(Exception e){
			e.printStackTrace();
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
