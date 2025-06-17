package lt.cartwise.images;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lt.cartwise.enums.Model;

@Service
public class ImageGalleryService {

	private final ImageGalleryRepository imageGalleryRepository;
	private final ImageMapper imageMapper;

	public ImageGalleryService(ImageGalleryRepository imageGalleryRepository, ImageMapper imageMapper) {
		this.imageGalleryRepository = imageGalleryRepository;
		this.imageMapper = imageMapper;
	}

	@Transactional
	public void createGallery(Model model, Long imageableId, List<String> uploadedUrls) {
		ImageGallery gallery = new ImageGallery(null, imageableId, model, "main", new ArrayList<>());
		uploadedUrls.stream().map(url -> {
			Image image = new Image(null, url, null, true, null);
			gallery.addImage(image);
			return image;
		}).toList();
		imageGalleryRepository.save(gallery);
	}

	public Map<String, List<ImageResponse>> getActiveByType(Model imageableType, Long imageableId) {
		List<ImageGallery> galleries = imageGalleryRepository.findByImageableTypeAndImageableId(imageableType,
				imageableId);
		return galleries.stream().collect(Collectors.toMap(ImageGallery::getFieldName, (gallery) -> gallery.getImages()
				.stream().filter(Image::getIsActive).map(imageMapper::toResponse).toList()));
	}

}
