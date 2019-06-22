package eu.vdmr.dspgui.util;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;



public class ImageUtil {

	private static final String IMAGE_LOCATION = "images";
	
	/**
	 * Load an Image from a file
	 * @param name file name (must reside in image directory)
	 * @return an image (or null if any error occurred during file access.
	 */
	public Image loadImage(String name){
		Image image = null;
		String iconfile = System.getProperty("user.dir") + "\\"+IMAGE_LOCATION+"\\" + name;
		try {
			File input = new File(iconfile);
			image = ImageIO.read(input);
		} catch (IOException ie) {
			System.out.println("Error for file " + iconfile + ":" + ie.getMessage());
		}
		return image;
	}
	
	
	public ImageIcon loadImageIcon(String name){
		Image image = loadImage(name);
		if (image == null){
			return null;
		}
		return  new ImageIcon(image, name); 
	}

}
