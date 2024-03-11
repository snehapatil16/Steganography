package patilProject4;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.awt.Color;

public class BMPIO {
	
		public static Color[][] readBMPFile(String fileName) throws IOException, BMPIOException {
			
			byte char1, char2;
			int offset;
			RandomAccessFile raf = new RandomAccessFile(fileName, "r");
			char1 = raf.readByte();
			char2 = raf.readByte();
			
			if (char1 != 'B' || char2 != 'M') {
				raf.close();
				throw new BMPIOException("BMPIO: Unsupported first characters");
			}
			
			raf.seek(10);
			offset = raf.readInt();
			offset = Integer.reverseBytes(offset);
			if (offset != 54) {
				raf.close();
				throw new BMPIOException("BMPIO: Offset unsupported");
			}
			
			raf.seek(14);
			int header = raf.readInt();
			header = Integer.reverseBytes(header);
			if (header != 40) {
				raf.close();
				throw new BMPIOException("BMPIO: Header unsupported");
			}
			
			raf.seek(18);
			int width = raf.readInt();
			width = Integer.reverseBytes(width);
			if (width % 4 != 0) {
				raf.close();
				throw new BMPIOException("BMPIO: Width unsupported");
			}
			
			int height;
			raf.seek(22);
            height = raf.readInt();
			height = Integer.reverseBytes(height);
			
			raf.seek(28);
			short short1;
			short1 = raf.readShort();
			short1 = Short.reverseBytes(short1);
			if (short1 != 24) {
				raf.close();
				throw new BMPIOException("BMPIO: Bits per pixel unsupported");
			}
			
			int red, green, blue;
			Color[][] colors = new Color[height][width];
			raf.seek(54);
			for (int row = 0; row < height; row++) {
				for (int col = 0; col < width; col++) {
					blue = raf.readUnsignedByte();
					green = raf.readUnsignedByte();
					red = raf.readUnsignedByte();
					colors[row][col] = new Color(red, green, blue);
				}
			}
			
			raf.close();
			return colors;
		}
}
