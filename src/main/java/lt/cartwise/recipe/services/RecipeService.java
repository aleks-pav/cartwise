package lt.cartwise.recipe.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lt.cartwise.enums.Model;
import lt.cartwise.exceptions.ProductNotFoundException;
import lt.cartwise.product.dto.ProductIngridientDto;
import lt.cartwise.product.entities.Product;
import lt.cartwise.product.repositories.ProductRepository;
import lt.cartwise.recipe.dto.RecipeCategoriesDto;
import lt.cartwise.recipe.dto.RecipeCreateDto;
import lt.cartwise.recipe.dto.RecipeIngridientDto;
import lt.cartwise.recipe.dto.RecipeWithAttributesDto;
import lt.cartwise.recipe.entities.Ingridient;
import lt.cartwise.recipe.entities.Recipe;
import lt.cartwise.recipe.entities.RecipeCategory;
import lt.cartwise.recipe.repositories.RecipeCategoryRepository;
import lt.cartwise.recipe.repositories.RecipeRepository;
import lt.cartwise.translations.TranslationService;
import lt.cartwise.user.entities.User;

@Service
public class RecipeService {
	
	private RecipeRepository recipeRepository;
	private RecipeCategoryRepository recipeCategoryRepository;
	private ProductRepository productRepository;
	private TranslationService translationService;

	public RecipeService(RecipeRepository recipeRepository
			, RecipeCategoryRepository recipeCategoryRepository
			, ProductRepository productRepository
			, TranslationService translationService) {
		this.recipeRepository = recipeRepository;
		this.recipeCategoryRepository = recipeCategoryRepository;
		this.productRepository = productRepository;
		this.translationService = translationService;
	}

	public List<RecipeWithAttributesDto> getAllIsPublic(boolean isPublic) {
		return recipeRepository.findByIsPublic(isPublic).stream().map( this::toRecipeWithAttributesDto ).toList();
	}
	
	public List<RecipeWithAttributesDto> getAllIsPublic(boolean isPublic, Long userId) {
		return recipeRepository.findByIsPublicAndUserId(isPublic, userId).stream().map( this::toRecipeWithAttributesDto ).toList();
	}
	
	public Optional<RecipeWithAttributesDto> getIsPublicById(boolean isPublic, Long id) {
		return recipeRepository.findByIdAndIsPublic(id, true).map( this::toRecipeWithAttributesDto );
	}
	
	public Optional<RecipeWithAttributesDto> getIsPublicById(boolean isPublic, Long id, Long userId) {
		return recipeRepository.findByIdAndIsPublicAndUserId(id, isPublic, userId).map( this::toRecipeWithAttributesDto );
	}
	
	public boolean deleteById(Long id) {
		if( !recipeRepository.existsById(id) )
			return false;
		recipeRepository.deleteById(id);
		return true;
	}
	
	@Transactional
	public RecipeWithAttributesDto createRecipe(@Valid RecipeCreateDto recipeCreate, User user)  {
		Recipe recipe = this.toEntity(recipeCreate);
		recipe.setUser(user);
		recipe.setCategories( recipeCategoryRepository.findAllById( recipeCreate.getCategories().stream().map(cat -> cat.getId()).toList() ) );
		
		List<Ingridient> ingridients = recipeCreate.getIngidients().stream().map( ingridientDto -> {
			Long productId = ingridientDto.getProduct().getId();
			Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product (id: " + productId + ") not found"));
			Ingridient ingridient = this.toIngridientEntity(ingridientDto);
			ingridient.setProduct( product );
			ingridient.setRecipe( recipe );
			return ingridient;
		}).toList();
		
		recipe.setIngidients(ingridients);
		
		Recipe recipeNew = recipeRepository.save( recipe );
		translationService.createTraslations(Model.RECIPE, recipeNew.getId(), recipeCreate.getTranslations() );
		
		return toRecipeWithAttributesDto( recipeNew );
	}
	
	
	
	
	
	
	private Recipe toEntity(RecipeCreateDto dto) {
		Recipe entity = new Recipe();
		entity.setName( dto.getName() );
		entity.setPortions( dto.getPortions() );
		entity.setTimePreparation( dto.getTimePreparation() );
		entity.setTimeCooking( dto.getTimeCooking() );
		entity.setIsPublic( dto.getIsPublic() );
//		TODO handle User, Categories and Ingridients
//		entity.setUser( user );
//		entity.setCategories( dto.getCategories().stream().map( this::toRecipeCategoryEntity ).toList() );
//		entity.setIngidients( dto.getIngidients().stream().map( this::toIngridientEntity ).toList() );
		
		return entity;
	}
	
	private Ingridient toIngridientEntity(RecipeIngridientDto dto) {
		Ingridient entity = new Ingridient();
//		TO DO handle Product
//		entity.setProduct( this.toProductEntity(dto.getProduct()) );
		entity.setUnits( dto.getUnits() );
		entity.setAmount( dto.getAmount() );
		return entity;
	}
	
	private RecipeWithAttributesDto toRecipeWithAttributesDto(Recipe recipe) {
		return new RecipeWithAttributesDto(recipe.getId()
					, recipe.getName()
					, recipe.getPortions()
					, recipe.getTimePreparation()
					, recipe.getTimeCooking()
					, recipe.getIsPublic()
					, translationService.getGroupedTranslations( Model.RECIPE, recipe.getId() )
					, recipe.getIngidients().stream().map( this::toRecipeIngridientsDto ).toList()
					, recipe.getCategories().stream().map( this::toRecipeCategoriesDto ).toList()
					, recipe.getCreatedAt()
					, recipe.getUpdatedAt()
				);
	}
	
	
	private RecipeIngridientDto toRecipeIngridientsDto(Ingridient item) {
		return new RecipeIngridientDto(item.getAmount()
				, item.getUnits()
				, this.toProductIngridientDto(item.getProduct())
			);
	}
	
	private RecipeCategoriesDto toRecipeCategoriesDto(RecipeCategory cat) {
		return new RecipeCategoriesDto(cat.getId()
				, cat.getName()
				, cat.getSlug()
				, cat.getIsActive()
			);
	}
	
	private ProductIngridientDto toProductIngridientDto(Product product) {
		return new ProductIngridientDto(product.getId()
				, product.getName()
				, product.getCalories()
				, translationService.getGroupedTranslations( Model.PRODUCT, product.getId() )
			);
	}

	

		

	
	
	
}
