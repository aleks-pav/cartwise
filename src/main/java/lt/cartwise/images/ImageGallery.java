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
}
