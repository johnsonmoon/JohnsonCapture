package com.xuyihao.tools;

import jpcap.PacketReceiver;
import jpcap.packet.ARPPacket;
import jpcap.packet.DatalinkPacket;
import jpcap.packet.EthernetPacket;
import jpcap.packet.ICMPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;

/**
 * @author johnson
 * created by xuyihao on 2016/5/27
 * @description a listener for listening package receiving
 * */
public class JohnsonPacketReceiver implements PacketReceiver{

	@Override
	public void receivePacket(Packet p) {
		if(p instanceof jpcap.packet.TCPPacket){
			TCPPacket packet = (TCPPacket)p;
			String s = "TCP Packet : dst_ip "+packet.dst_ip+":"+packet.dst_port+" | src_ip "+packet.src_ip+":"+packet.src_port+" | len: "+packet.len+"";
			System.out.println(s);
		}else if(p instanceof jpcap.packet.UDPPacket){
			UDPPacket packet = (UDPPacket)p;
			String s = "UDP Packet : dst_ip "+packet.dst_ip+":"+packet.dst_port+" | src_ip "+packet.src_ip+":"+packet.src_port+" | len: "+packet.len+"";
			System.out.println(s);
		}else if(p instanceof jpcap.packet.ICMPPacket){
			ICMPPacket packet = (ICMPPacket)p;
			String router_ip = "";
			for(int i = 0; i < packet.router_ip.length; i++){
				router_ip += " " + packet.router_ip[i].getHostAddress();
			}
			String s = "@ @ @ ICMPPacket: | router_ip: "+router_ip+" | redir_ip: "+packet.redir_ip+" | mtu: "+packet.mtu+" | length: "+packet.length+"";
			System.out.println(s);
		}else if(p instanceof jpcap.packet.ARPPacket){
			ARPPacket packet = (ARPPacket)p;
			Object saa = packet.getSenderHardwareAddress();
			Object taa = packet.getTargetHardwareAddress();
			String s = "* * * ARPPacket: | SenderHardwareAddress "+saa+" | TargetHardwareAddress "+taa+" | len: "+packet.len+"";
			System.out.println(s);
		}
		DatalinkPacket datalink = p.datalink;
		if(datalink instanceof jpcap.packet.EthernetPacket){
			EthernetPacket packet = (EthernetPacket)datalink;
			String s = " datalink layer packet: |DestinationAddress: "+packet.getDestinationAddress()+" | SourceAddress: "+packet.getSourceAddress()+"";
			System.out.println(s);
		}
	}
}
