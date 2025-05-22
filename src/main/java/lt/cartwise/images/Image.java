package lt.cartwise.images;

import jakarta.persistence.*;
import lt.cartwise.Timestampable;

@Entity
@Table(name = "images")
public class Image extends Timestampable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String src;
	private Integer position;
	private Boolean isActive;
	
	@ManyToOne
	@JoinColumn(name = "gallery_id")
	private ImageGallery gallery;

	
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public ImageGallery getGallery() {
		return gallery;
	}

	public void setGallery(ImageGallery gallery) {
		this.gallery = gallery;
	}

	
	

}
