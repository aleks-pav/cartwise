package lt.cartwise.images;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lt.cartwise.enums.Model;

@Repository
public interface ImageGalleryRepository extends JpaRepository<ImageGallery, Long> {
	List<ImageGallery> findByImageableTypeAndImageableId(Model imageableType, Long imageableId);
}
