package com.xuyihao.tools;

import jpcap.PacketReceiver;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;

/**
 * @author johnson
 * created by xuyihao on 2016/5/27
 * @description a listener for listening package receiving
 * @description 继承自PacketReceiver 的工具类，用来监听并抓取特定网络接口的数据报发送 
 * */
public class JohnsonPacketReceiver implements PacketReceiver{

	/**
	 * fields
	 * */
	//for controlling
	private int threadWaitTime = 0;//线程等待时间，即抓包监听线程手动阻塞的时间，便于观察控制台下的抓包情况，在等待时间之内，线程是无法获取到监听到的数据报的
	private boolean hasDeviceInformation = false;//判断是否有此网络接口信息的一个布尔值，用构造器进行初始化
	private int deviceNumber = 0;//网络接口信息，接口号（程序定），由构造器初始化
	private String deviceName = "";//网络接口信息，接口名（由Jpcap通过操作系统获得），由构造器初始化
	private String deviceDescription = "";//网络接口的描述信息，（由Jpcap通过操作系统获取），由构造器初始化
	
	/**
	 * constructor
	 * @description 默认构造器
	 * @param waitTime 单个抓包线程需要等待（手动阻塞）的时间
	 * */
	public JohnsonPacketReceiver(int waitTime) {
		this.threadWaitTime = waitTime;
		this.hasDeviceInformation = false;
	}
	
	/**
	 * constructor2
	 * @author johnson
	 * @description 构造器2
	 * @param waitTime 单个抓包线程需要等待（手动阻塞）的时间
	 * @param number 初始化传入的设备编号
	 * @param name 初始化传入的设备名
	 * @param description 初始化传入的设备描述
	 * */
	public JohnsonPacketReceiver(int waitTime, int number, String name, String description){
		this.threadWaitTime = waitTime;
		this.hasDeviceInformation = true;
		this.deviceNumber = number;
		this.deviceName = name;
		this.deviceDescription = description;
	}
	
	/**
	 * @author johnson
	 * @method receivePacket
	 * @description 抓包之后的核心方法，主要功能都在其中实现，重写自几类PacketReceiver
	 * @description 抓包之后，系统自动执行该方法，需要执行的操作就在方法体内完成
	 * */
	@Override
	public void receivePacket(Packet p) {
		
		if(p instanceof jpcap.packet.IPPacket){//判断抓取的数据报p是否为IP数据报
			IPPacket packet = (IPPacket)p;
			//下面一系列操作讲IP数据报的各项参数打印到控制台
			System.out.println("\n\n\n");
			if(this.hasDeviceInformation){
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
		if(this.threadWaitTime > 0){//如果设置的线程等待时间大于零，即需要等待，则手动设置线程睡眠设置的时间
			try {
				Thread.sleep(this.threadWaitTime * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
