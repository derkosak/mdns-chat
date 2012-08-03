package br.com.bry.mdnschat.servidor;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import javax.jmdns.ServiceTypeListener;

public class Cliente {

	public static final String tipoServico_Chat = "_bry._tcp.local.";

	public static void main(String[] args) throws Exception {
		final JmDNS mdnsService = JmDNS.create();
		ServiceListener mdnsServiceListener = new ServiceListener() {

			@Override
			public void serviceAdded(ServiceEvent serviceEvent) {
				System.out.println("serviceadded");
				// Test service is discovered. requestServiceInfo() will trigger
				// serviceResolved() callback.
				mdnsService.requestServiceInfo(tipoServico_Chat,
						serviceEvent.getName());
			}

			@Override
			public void serviceRemoved(ServiceEvent serviceEvent) {
				System.out.println("serviceRemoved");
				// Test service is disappeared.
			}

			@Override
			public void serviceResolved(ServiceEvent serviceEvent) {
				System.out.println("serviceResolved");
				// Test service info is resolved.
				String serviceUrl = serviceEvent.getInfo().getURL();
				System.out.println(serviceUrl);
				// serviceURL is usually something like
				// http://192.168.11.2:6666/my-service-name
			}
		};

		ServiceTypeListener serviceTypeListener = new ServiceTypeListener() {

			@Override
			public void subTypeForServiceTypeAdded(ServiceEvent event) {
				System.out.println("subtype added: " + event.getType());
			}

			@Override
			public void serviceTypeAdded(ServiceEvent event) {
				System.out.println("serviceType added: " + event.getType());

			}
		};

		System.out.println("Buscando serviço " + tipoServico_Chat);
		mdnsService.addServiceListener(tipoServico_Chat, mdnsServiceListener);
		mdnsService.addServiceTypeListener(serviceTypeListener);
		ServiceInfo[] infos = mdnsService.list(tipoServico_Chat, 15000);
		for (ServiceInfo info : infos) {
			System.out.println("Serviço encontrado: " + info.getApplication());
		}

		mdnsService.printServices();
		// Retrieve service info from either ServiceInfo[] returned here or
		// listener callback method above.
		mdnsService
				.removeServiceListener(tipoServico_Chat, mdnsServiceListener);
		mdnsService.removeServiceTypeListener(serviceTypeListener);
		mdnsService.close();

	}
}
