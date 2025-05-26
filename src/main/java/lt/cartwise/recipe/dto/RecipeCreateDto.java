package lt.cartwise.recipe.dto;

import java.util.List;

import jakarta.validation.constraints.*;
import lt.cartwise.translations.Translation;
import lt.cartwise.user.dto.UserDto;

public class RecipeCreateDto {
	
	@Size(min = 2, message = "Name can't be shorter than 2 symbols")
	private String name;
	
	@Min(1)
	@Max(12)
	@NotNull
	private Integer portions;
	
	@Positive(message = "Preparation time (min) can't be negative")
	private Integer timePreparation;
	
	@Positive(message = "Cooking time (min) can't be negative")
	private Integer timeCooking;
	
	@NotNull
	private Boolean isPublic;
	
	@NotNull
	private UserDto user;
	
	
//	private List<TranslationByLanguageDto> translations;
	
	@NotEmpty(message = "Categories can't be empty")
	private List<RecipeCategoryDto> categories;
	
	@NotEmpty(message = "Ingridients can't be empty")
	private List<RecipeIngridientDto> ingidients;

	@NotNull(message = "Translations can't be null")
	private List<Translation> translations;
	
	
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPortions() {
		return portions;
	}

	public void setPortions(Integer portions) {
		this.portions = portions;
	}

	public Integer getTimePreparation() {
		return timePreparation;
	}

	public void setTimePreparation(Integer timePreparation) {
		this.timePreparation = timePreparation;
	}

	public Integer getTimeCooking() {
		return timeCooking;
	}

	public void setTimeCooking(Integer timeCooking) {
		this.timeCooking = timeCooking;
	}

	public Boolean getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public List<RecipeCategoryDto> getCategories() {
		return categories;
	}

	public void setCategories(List<RecipeCategoryDto> categories) {
		this.categories = categories;
	}

	public List<RecipeIngridientDto> getIngidients() {
		return ingidients;
	}

	public void setIngidients(List<RecipeIngridientDto> ingidients) {
		this.ingidients = ingidients;
	}

	public List<Translation> getTranslations() {
		return translations;
	}

	public void setTranslations(List<Translation> translations) {
		this.translations = translations;
	}
	
	
	
}
