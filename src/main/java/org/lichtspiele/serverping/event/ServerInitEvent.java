package org.lichtspiele.serverping.event;

import org.lichtspiele.serverping.ServerPingSign;

public class ServerInitEvent extends ServerEvent {

	public ServerInitEvent(ServerPingSign sps) {
		super(sps);
	}
 
}