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
			System.out.println("数据包长度(len):            "+packet.len);
			System.out.println("标志位(r_flag):            "+packet.r_flag);
			System.out.println("标志位(more_frag):         "+packet.more_frag);
			System.out.println("标志位(rsv_frag):          "+packet.rsv_frag);
			System.out.println("片偏移(Fragment offset):   "+packet.offset);
			System.out.println("协议(protocol):            "+packet.protocol);
			System.out.println("原地址IP(src_ip):          "+packet.src_ip);
			System.out.println("目的地址IP(dst_ip):         "+packet.dst_ip);
			/*
			System.out.println("caplen:   "+packet.caplen);
			System.out.println("d_flag:   "+packet.d_flag);
			System.out.println("dont_frag:   "+packet.dont_frag);
			System.out.println("flow_label:   "+packet.flow_label);
			System.out.println("ident:   "+packet.ident);
			System.out.println("more_frag:   "+packet.more_frag);
			System.out.println("rsv_frag:   "+packet.rsv_frag);
			System.out.println("sec:   "+packet.sec);
			System.out.println("t_flag:   "+packet.t_flag);
			System.out.println("usec:   "+packet.usec);
			System.out.println("toString:   "+packet.toString());
			System.out.println("data:   "+packet.data);
			System.out.println("header:   "+packet.header);
			System.out.println("hop_limit:   "+packet.hop_limit);
			System.out.println("length:   "+packet.length);
			System.out.println("option:   "+packet.option);
			System.out.println("priority:   "+packet.priority);
			System.out.println("rsv_tos:   "+packet.rsv_tos);
			*/
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
