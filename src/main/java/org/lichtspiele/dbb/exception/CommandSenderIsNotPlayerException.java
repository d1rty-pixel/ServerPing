package org.lichtspiele.dbb.exception;

public class CommandSenderIsNotPlayerException extends Exception {

	private static final long serialVersionUID = 6376447701991073716L;

	public CommandSenderIsNotPlayerException(String string) {
		super(string);
	}
	
}
