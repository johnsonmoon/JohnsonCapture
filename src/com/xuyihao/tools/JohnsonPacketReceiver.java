package com.xuyihao.tools;

import jpcap.PacketReceiver;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;

/**
 * @author johnson
 * created by xuyihao on 2016/5/27
 * @description a listener for listening package receiving
 * */
public class JohnsonPacketReceiver implements PacketReceiver{

	/**
	 * fields
	 * */
	//for controlling
	private int threadWaitTime = 0;
	private boolean hasDeviceInfomation = false;
	private int deviceNumber = 0;
	private String deviceName = "";
	private String deviceDescription = "";
	
	/**
	 * constructor
	 * */
	public JohnsonPacketReceiver(int waitTime) {
		this.threadWaitTime = waitTime;
		this.hasDeviceInfomation = false;
	}
	
	/**
	 * constructor2
	 * */
	public JohnsonPacketReceiver(int waitTime, int number, String name, String description){
		this.threadWaitTime = waitTime;
		this.hasDeviceInfomation = true;
		this.deviceNumber = number;
		this.deviceName = name;
		this.deviceDescription = description;
	}
	
	@Override
	public void receivePacket(Packet p) {
		
		if(p instanceof jpcap.packet.IPPacket){
			IPPacket packet = (IPPacket)p;
			System.out.println("\n\n\n");
			if(this.hasDeviceInfomation){
				System.out.println("设备: "+this.deviceNumber+"  ||  设备名: "+this.deviceName+"");
				System.out.println("设备描述: "+this.deviceDescription+"");
			}
			System.out.println("版本号(version):           "+packet.version);
			System.out.println("数据包长度(length):         "+packet.length);
			System.out.println("标志位(more_frag):         "+packet.more_frag);
			System.out.println("标志位(dont_frag):         "+packet.dont_frag);
			System.out.println("标志位(rsv_frag):          "+packet.rsv_frag);
			System.out.println("TTL(hop_limit):           "+packet.hop_limit);
			System.out.println("片偏移(Fragment offset):   "+packet.offset);
			System.out.println("协议(protocol):            "+packet.protocol);
			System.out.println("优先级(priority):          "+packet.priority);
			System.out.println("原地址IP(src_ip):          "+packet.src_ip);
			System.out.println("目的地址IP(dst_ip):         "+packet.dst_ip);
		}
		if(this.threadWaitTime > 0){
			try {
				Thread.sleep(this.threadWaitTime * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
