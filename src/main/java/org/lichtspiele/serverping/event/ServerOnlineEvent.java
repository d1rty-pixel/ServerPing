package org.lichtspiele.serverping.event;

import org.lichtspiele.serverping.ServerPingSign;

public class ServerOnlineEvent extends ServerEvent {

	public ServerOnlineEvent(ServerPingSign sps) {
		super(sps);
	}

}
