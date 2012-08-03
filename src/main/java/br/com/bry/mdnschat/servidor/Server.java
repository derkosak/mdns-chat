package br.com.bry.mdnschat.servidor;

import java.net.InetAddress;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

public class Server {

	public static final String tipoServico = "kas2._bry";

	public static void main(String[] args) throws Exception {

		JmDNS mdnsServer = JmDNS
				.create(InetAddress.getLocalHost(), "localhost");

		// Register a test service.
		ServiceInfo testService = ServiceInfo.create(tipoServico, "Chate",
				6667, "bagulho");

		mdnsServer.registerService(testService);
		mdnsServer.printServices();
		System.out.println("coisa");

	}

}
