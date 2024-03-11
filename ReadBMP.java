package patilProject4;


import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import edu.du.dudraw.DUDraw;


public class ReadBMP {

		// TODO: Find out the name of the file from the user
		// TODO: Declare a 2D array of Color objects
		// (don't instantiate it here, as must wait until we know the size of the image)
		// TODO: in a try/catch block, call your BMPIO's readBMPfile() method, passing
	    // it the file name
		// Note that the method should return a reference to an array, so store that
		// reference in your array variable
        // TODO: if the array of Color objects that was returned is not null, then
		// output the array to a DUDraw canvas
		// If the returned array is null, it means the reading of the file failed.
		// Output an error message
		
	public static void main(String[] args) throws IOException, BMPIOException {

		Scanner input = new Scanner(System.in);
		System.out.print("Enter your file name: ");
		String fileName = input.nextLine();
		input.close();
		Color[][] colors;
			
		try {				
			
			colors = BMPIO.readBMPFile(fileName);
			DUDraw.enableDoubleBuffering();
			DUDraw.setCanvasSize(colors[0].length, colors.length);
			DUDraw.setXscale(0, colors[0].length);
			DUDraw.setYscale(0, colors.length);
			int wid = colors[0].length;
			int hgt = colors.length;
			
			for(int row = 0; row < hgt; row++) {
				for (int col = 0; col < wid; col++) {
					
					DUDraw.setPenColor(colors[row][col]);
					DUDraw.point(col, row);
				}
			}
			
			DUDraw.show();
			
		} catch (FileNotFoundException e) {
				
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
	}	

}
