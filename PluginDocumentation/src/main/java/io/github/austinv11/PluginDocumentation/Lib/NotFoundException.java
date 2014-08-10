package io.github.austinv11.PluginDocumentation.Lib;

public class NotFoundException extends Exception{
	
	public NotFoundException(){
		super();
    }

	public NotFoundException(String message){
		super(message);
    }

	public NotFoundException(Throwable cause){
		super(cause);
    }

	public NotFoundException(String message, Throwable cause){
		super(message, cause);
    }
}
