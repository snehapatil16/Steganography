## Steganography

Steganography is a close cousin of cryptography. Rather than changing the contents of a message by encrypting it, the unmodified information is disguised or hidden in some way.

## Classes:

Steganography: Has two methods, extractSecretImage() to extract an image from a .bmp file and embedSecretImage() to embed an image.

Driver: Its main method handles interaction with the user. It asks the user if they want to embed or extract an image.
- If they choose to extract an image: It asks the user for the file name of the .bmp file with an embedded image. Using the data from that file, it displays both the original public image on the left side of the window, as well as the extracted secret image on the right side of the window.
- If they choose to embed an image: It asks the user for the file names of the public image and the secret image, and then uses the embedSecretImage() method to alter the contents of the public image file. It also displays the original public image on the left side of the window and the altered version on the right. The two images will look very similar (though not exactly the same!). 

BMPIO: Has a static method called readBMPFile() that takes a String parameter giving the file name. It opens a RandomAccessFile for that file name, reads all data from the .bmp file, and if the reading process was successful, returns a 2D array of Color objects with the image data. The method throws any IOException encountered and returns null if any other error occurred.
