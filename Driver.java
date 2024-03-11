package patilProject4;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import edu.du.dudraw.DUDraw;

public class Driver {

	public static void main(String[] args) {
		
		String embedExtract;
		String name;
		String secretName;
		Color[][] colors = null;
		Color[][] colors2 = null;
		
		Scanner input = new Scanner(System.in);
		System.out.println("Extract or Embed image: ");
		embedExtract = input.nextLine();
		
		// for embed method
		if (embedExtract.equals("Embed") || embedExtract.equals("embed")) {
			
			System.out.print("Enter the file name you want to embed: ");
			name = input.nextLine();
			System.out.print("Enter the secret file name you want embeded: ");
			secretName = input.nextLine();
			input.close();
			
			try {
					
				colors = Steganography.embedSecretImage(name, secretName);
					
			} catch (IOException e) {
					
				System.out.println(e.getMessage());
				System.exit(0);
			
			} catch (BMPIOException e) { 
				
				//also handles FileNotFoundException
				System.out.println(e.getMessage());
				System.exit(0);
			} 
			
			if (colors != null) {
				
				DUDraw.enableDoubleBuffering();
				DUDraw.setCanvasSize(colors[0].length, colors.length);
				DUDraw.setXscale(0, colors[0].length);
				DUDraw.setYscale(0, colors.length);
				int width = colors[0].length;
				int height = colors.length;
				
				for(int row = 0; row < height; row++) {
					for (int col = 0; col < width; col++) {
						
						DUDraw.setPenColor(colors[row][col]);
						DUDraw.point(col, row);
					}
				}
				
				DUDraw.show();
			}
		
		//for extract method
		} else if (embedExtract.equals("Extract") || embedExtract.equals("extract")) {
			
			System.out.print("Enter your file name you want to extract: ");
			name = input.nextLine();
			input.close();
			
			try {
					
				colors = BMPIO.readBMPFile(name);
				colors2 = Steganography.extractSecretImage(name);
					
			} catch (IOException e) {
					
				System.out.println(e.getMessage());
				System.exit(0);
			
			} catch (BMPIOException e) {
				
				System.out.println(e.getMessage());
				System.exit(0);
			}
			
			if (colors != null) {
				
				DUDraw.enableDoubleBuffering();
				DUDraw.setCanvasSize(colors[0].length * 2, colors.length);
				DUDraw.setXscale(0, colors[0].length * 2);
				DUDraw.setYscale(0, colors.length);
				int width = colors[0].length;
				int height = colors.length;
				
				for(int row = 0; row < height; row++) {
					for (int col = 0; col < width; col++) {
						
						DUDraw.setPenColor(colors[row][col]);
						DUDraw.point(col, row);
					}
				}
				
				for (int row = 0; row < height; row++) {
					for (int col = 0; col < width; col++) {
						
						DUDraw.setPenColor(colors2[row][col]);
						DUDraw.point(col + colors[row].length, row);
					}
				}
					
				DUDraw.show();
			
			} else {
				
				System.out.println("File reading failed");
			}
		}
	}
}
