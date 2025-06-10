package lt.cartwise.images;

import java.util.List;

import jakarta.persistence.*;
import lt.cartwise.enums.Model;

@Entity
@Table(name = "image_galleries"
		, indexes = {@Index(name = "galleries_lookup", columnList = "imageableId, imageableType")}
		, uniqueConstraints = {@UniqueConstraint(name = "galleries_unique", columnNames = {"imageableId", "imageableType", "fieldName"})}
)
public class ImageGallery {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long imageableId;
	
	@Enumerated(EnumType.STRING)
	private Model imageableType;
	private String fieldName;
	
	@OneToMany(mappedBy = "gallery", cascade = CascadeType.ALL)
	List<Image> images;
	

	public ImageGallery() {};
	public ImageGallery(Long id, Long imageableId, Model imageableType, String fieldName, List<Image> images) {
		this.id = id;
		this.imageableId = imageableId;
		this.imageableType = imageableType;
		this.fieldName = fieldName;
		this.images = images;
	}
	
	public void addImage(Image image) {
	    image.setGallery(this);
	    this.images.add(image);
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getImageableId() {
		return imageableId;
	}
	public void setImageableId(Long imageableId) {
		this.imageableId = imageableId;
	}
	public Model getImageableType() {
		return imageableType;
	}
	public void setImageableType(Model imageableType) {
		this.imageableType = imageableType;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public List<Image> getImages() {
		return images;
	}
	public void setImages(List<Image> images) {
		this.images = images;
	}
}
