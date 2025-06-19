package lt.cartwise.recipe.services;

import java.util.List;

import org.springframework.stereotype.Service;

import lt.cartwise.recipe.dto.RecipeCategoryDto;
import lt.cartwise.recipe.mappers.RecipeMapper;
import lt.cartwise.recipe.repositories.RecipeCategoryRepository;

@Service
public class RecipeCategoryService {

	private final RecipeCategoryRepository recipeCategoryRepository;
	private final RecipeMapper recipeMapper;

	public RecipeCategoryService(RecipeCategoryRepository recipeCategoryRepository, RecipeMapper recipeMapper) {
		this.recipeCategoryRepository = recipeCategoryRepository;
		this.recipeMapper = recipeMapper;
	}

	public List<RecipeCategoryDto> getAllActive(String language) {
		return recipeCategoryRepository.findByIsActive(true).stream()
				.map((c) -> recipeMapper.toRecipeCategoryDto(c, language))
				.toList();
	}

}
