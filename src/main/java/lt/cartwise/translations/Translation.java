package lt.cartwise.translations;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lt.cartwise.enums.Model;

@Entity
@Table(name = "translations",
	uniqueConstraints = { @UniqueConstraint(name = "translation_unique",
		columnNames = { "translatableId", "translatableType", "language", "field_name" }) },
	indexes = { @Index(name = "translation_lookup",
		columnList = "translatableId, translatableType, language") })
public class Translation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long translatableId;

	@Enumerated(EnumType.STRING)
	private Model translatableType;

	@Size(min = 2, max = 2, message = "Language must be ISO 639-1 (2 letters)")
	private String language;

	@NotNull(message = "fieldName can't be null")
	@NotBlank(message = "fieldName can't be empty")
	private String fieldName;

	@Lob
	@NotBlank(message = "Translation text can't be empty")
	private String value;

	public Translation() {};

	public Translation(Long id, Long translatableId, Model translatableType, String language, String fieldName,
			String value) {
		this.id = id;
		this.translatableId = translatableId;
		this.translatableType = translatableType;
		this.language = language;
		this.fieldName = fieldName;
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTranslatableId() {
		return translatableId;
	}

	public void setTranslatableId(Long translatableId) {
		this.translatableId = translatableId;
	}

	public Model getTranslatableType() {
		return translatableType;
	}

	public void setTranslatableType(Model translatableType) {
		this.translatableType = translatableType;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFiledName(String filedName) {
		this.fieldName = filedName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
