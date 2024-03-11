package patilProject4;

public class BMPIOException extends Exception {
	
	public BMPIOException() {
		
		super("Error with .bmp file");
	} 
	
	public BMPIOException(String s) {
		
		super(s);
	}

}
