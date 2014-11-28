package org.lichtspiele.serverping.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class PingUtil {

	@SuppressWarnings("resource")
	public static boolean ping(String address, int port) {
		InetSocketAddress isa 	= new InetSocketAddress(address, port); 
		Socket connection 		= new Socket();
		try {
			connection.connect(isa, 5*1000);
		} catch (IOException e) {
			return false;
		}
		
		return true;
	}
	
}
