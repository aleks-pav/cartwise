package lt.cartwise.images;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.springframework.http.MediaType;


//TODO investigate this class
public class ImageEditor {

	public static byte[] convertToPng(byte[] source) throws IOException {
		BufferedImage sourceImage = ImageIO.read( new ByteArrayInputStream(source) );
		
		if(sourceImage == null)
			throw new IOException("Unsupported image format or corrupted image");
		
		ByteArrayOutputStream pngBytes = new ByteArrayOutputStream();
		if( ! ImageIO.write(sourceImage, "png", pngBytes) )
			throw new IOException("Failed to encode image as PNG");
		
		return pngBytes.toByteArray();
	}
	
	public static String detectImageFormat(byte[] imageData) throws IOException {
        try (ImageInputStream iis = ImageIO.createImageInputStream(new ByteArrayInputStream(imageData))) {
            Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
            if (readers.hasNext()) {
                ImageReader reader = readers.next();
                return reader.getFormatName();
            }
        } catch (Exception e) {
        	throw new IOException("Failed detecting image format");
        }
        return null;
    }

    public static MediaType getMediaTypeForFormat(String formatName) {
        if (formatName == null) return MediaType.APPLICATION_OCTET_STREAM;

        return switch (formatName.toLowerCase()) {
            case "png" -> MediaType.IMAGE_PNG;
            case "jpeg" -> MediaType.IMAGE_JPEG;
            case "jpg" -> MediaType.IMAGE_JPEG;
            case "gif" -> MediaType.IMAGE_GIF;
            default -> MediaType.APPLICATION_OCTET_STREAM;
        };
    }
}
