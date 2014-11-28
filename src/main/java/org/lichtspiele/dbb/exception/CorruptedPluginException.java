package org.lichtspiele.dbb.exception;

public class CorruptedPluginException extends Exception {

	private static final long serialVersionUID = 5017410043519573015L;
	
	public CorruptedPluginException(ReflectiveOperationException e) {
		super(e);
	}

}
