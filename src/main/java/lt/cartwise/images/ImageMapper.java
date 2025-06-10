package lt.cartwise.images;

import org.springframework.stereotype.Component;

@Component
public class ImageMapper {

	
	public ImageResponse toResponse(Image image) {
		return new ImageResponse(image.getId(), image.getSrc(), image.getPosition());
	}

}
