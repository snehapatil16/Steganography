package patilProject4;

import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.PrintWriter;
import java.io.RandomAccessFile;


public class ConvertToBMP {
  
	public static void main(String[] args) {
      Scanner keyboard = new Scanner(System.in);
      System.out.println("Enter filename of image you want to convert (png or jpg): ");
      String fileName = keyboard.nextLine();
      if (fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".png") || fileName.toLowerCase().endsWith(".bmp")) {
         File f = new File(fileName);
         if (f.exists()) {
            try {
               BufferedImage im = ImageIO.read(f);
               System.out.println("Image read frome file, size is " + im.getWidth() + "x" + im.getHeight());
               String outFileName = fileName.substring(0,fileName.lastIndexOf('.'))+".bmp";
               System.out.println("Creating BMP file: " + outFileName);
               writeBMP(im, outFileName);
            } catch (IOException e) {
               System.out.println("Failure reading from file");
               e.printStackTrace();
               System.exit(1);
            }
         } else {
            System.out.println("Can't find file.");
         }
      } else {
         System.out.println("Bad file type, should be a .jpg or .png file");
      }
   }
   
	static void writeBMP(BufferedImage im, String fn) {
      try {
         RandomAccessFile outputFile = new RandomAccessFile(fn, "rw");
         outputFile.setLength(0);
         // bmp format has padding per rows if number of pixels not divisible by 4
         // so I'll simply clip off last pixels if necessary.
         int width = im.getWidth()/4*4;
         int height = im.getHeight();
         outputFile.writeByte('B');
         outputFile.writeByte('M');
         // size of file: header 14 bytes, bitmap header 40 bytes, then 3 bytes per pixel
         outputFile.writeInt(Integer.reverseBytes(14+40+3*width*height));
         outputFile.writeShort(0); // reserved value, ignored
         outputFile.writeShort(0); // reserved value, ignored
         // we start writing pixel data at position 54 (14 for header, 40 for bitmap header)
         outputFile.writeInt(Integer.reverseBytes(54)); 
         // We're done with the 14 bytes of the header
         // Now we'll write the bitmap header
         // We are using BITMAPHEADERINFO version, which has 40 bytes
         outputFile.writeInt(Integer.reverseBytes(40));
         outputFile.writeInt(Integer.reverseBytes(width));
         outputFile.writeInt(Integer.reverseBytes(height));
         // number of color planes, must be 1:
         short colorPlanes = 1;
         outputFile.writeShort(Short.reverseBytes(colorPlanes));
         // we're using one byte for each color, so 24 bits per pixel:
         short bpp = 24;
         outputFile.writeShort(Short.reverseBytes(bpp));
         outputFile.writeInt(0); // compression method, 0 means none
         outputFile.writeInt(0); // size of the raw image. Can be 0 since no compression
         outputFile.writeInt(0); // horiz. resolution
         outputFile.writeInt(0); // vert. resolution
         outputFile.writeInt(0); // number of colors (defaults to 2^24)
         outputFile.writeInt(0); // all colors are important
         // Now write all of the pixel data:
         for (int row = height-1; row >=0; row--) {
            for (int col = 0; col < width; col++) {
               outputFile.writeByte(im.getRGB(col, row) & 255); // blue stored in LSB
               outputFile.writeByte(im.getRGB(col, row)>>8 & 255); // green in middle byte
               outputFile.writeByte(im.getRGB(col, row)>>16 & 255); // red in MSB
            }
         }
         outputFile.close();
      }
      catch (IOException e) {
         System.out.println("Failure writing to file.");
         e.printStackTrace();
         System.exit(1);
      }
   }
}
