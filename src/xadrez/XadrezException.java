package xadrez;

import campodejogo.TabuException;

public class XadrezException extends TabuException{
	private static final long serialVersionUID = 1L;
	
	public XadrezException(String msg) {
		super(msg);
	}

}
