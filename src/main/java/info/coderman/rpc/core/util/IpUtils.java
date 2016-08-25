package info.coderman.rpc.core.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
/**
 * 
 * @author yuezixin 2016-8-22 15:35:01
 *
 */
public class IpUtils {
	public static String getBindIp(){
		String ip="";
        try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			ip=inetAddress.getHostAddress();
		} catch (UnknownHostException e) {
 			e.printStackTrace();
		}
		return ip; 
	}
}
