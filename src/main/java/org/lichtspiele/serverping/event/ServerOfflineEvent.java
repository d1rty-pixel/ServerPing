package org.lichtspiele.serverping.event;

import org.lichtspiele.serverping.ServerPingSign;

public class ServerOfflineEvent extends ServerEvent {

	public ServerOfflineEvent(ServerPingSign sps) {
		super(sps);
	}
	
}
