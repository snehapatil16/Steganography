package patilProject4;

import java.awt.Color;
import java.io.IOException;
import java.io.RandomAccessFile;

//value % 16           // Gives the value of the lowest 4 bits of value
//value * 16           // Shifts the lowest 4 bits to the highest 4 bits, leaving 0s for the lowest 4 bits
//value / 16           // Shifts the highest 4 bits to the lowest 4 bits, leaving 0s for the highest 4 bits
//value - (value % 16) // Clears the lowest 4 bits, making them all 0
//value + otherValue   // Obviously, returns the sum of the two values. If value holds just the highest 4 bits
//                     // and otherValue just the lowest, then by adding the byte holds the information 
//                     // for both, without them interfering with each other.

public class Steganography {
	
	static Color[][] newColors;
	
	public static Color[][] extractSecretImage(String name) throws IOException, BMPIOException {
				
		Color[][] colors = null;
			
		colors = BMPIO.readBMPFile(name);
			
		int red, green, blue;
		for (int row = 0; row < colors.length; row++) {
			for (int col = 0; col < colors[row].length; col++) {
				red = colors[row][col].getRed();
				green = colors[row][col].getGreen();
				blue = colors[row][col].getBlue();
				
				red = red % 16; //lowest 4 bits
				green = green % 16;
				blue = blue % 16;
				
				red = red * 16; //shifts lowest 4 bits to highest 4 bits
				green = green * 16;
				blue = blue * 16;
				
				colors[row][col] = new Color(red, green, blue);

			}
		}
	
		return colors;
	}
	
	public static Color[][] embedSecretImage(String name, String secretName) throws IOException, BMPIOException {
		
		RandomAccessFile write = new RandomAccessFile(name, "rw");
        write.seek(54);
		Color[][] colors = null;
		Color[][] secretColors = null;
		Color[][] embedColors = null;
		int red, green, blue;
		int sRed, sGreen, sBlue;
        int finalRed = 0; 
        int finalBlue = 0;
        int finalGreen = 0;
        int origRed = 0;
        int origBlue = 0;
        int origGreen = 0;
		
        try {
			
			colors = BMPIO.readBMPFile(name);
			embedColors = BMPIO.readBMPFile(name);
			secretColors = BMPIO.readBMPFile(secretName);
			
		} catch (IOException e){
			
			System.out.println(e.getMessage());
			System.exit(0);
		}
        
        for (int row = 0; row < colors.length; row++) {
			for (int col = 0; col < colors[row].length; col++) {
				
				origRed = colors[row][col].getRed();
				origGreen = colors[row][col].getGreen();
				origBlue = colors[row][col].getBlue();
				
				red = colors[row][col].getRed();
				green = colors[row][col].getGreen();
				blue = colors[row][col].getBlue();
				
				red -= (red % 16); //Clears the lowest 4 bits, making them all 0
				green -= (green % 16);
				blue -= (blue % 16);
				
				colors[row][col] = new Color(red, green, blue);
				
				if (row + 1 <= secretColors.length && col + 1 <= secretColors[row].length) {
					sRed = secretColors[row][col].getRed();
					sGreen = secretColors[row][col].getGreen();
					sBlue = secretColors[row][col].getBlue();
						
					sRed /= 16; //Shifts the highest 4 bits to the lowest 4 bits, leaving 0s for the highest 4 bits
					sGreen /= 16;
					sBlue /= 16;
						
					finalRed = red + sRed;
					finalGreen = green + sGreen;
					finalBlue = blue + sBlue;
						
					embedColors[row][col] = new Color(finalRed, finalGreen, finalBlue);
					
					write.write(finalBlue);
					write.write(finalGreen);
					write.write(finalRed);
					
				
				} else {
					
					write.write(origBlue);
					write.write(origGreen);
					write.write(origRed);

				}
			}
		}
        
        write.close();
		return embedColors;
	}

}
