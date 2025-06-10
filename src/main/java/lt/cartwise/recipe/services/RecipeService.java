package lt.cartwise.recipe.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lt.cartwise.enums.Model;
import lt.cartwise.exceptions.NotFoundException;
import lt.cartwise.images.ImageGalleryService;
import lt.cartwise.product.dto.ProductIngridientDto;
import lt.cartwise.product.entities.Product;
import lt.cartwise.product.repositories.ProductRepository;
import lt.cartwise.recipe.dto.RecipeCategoriesDto;
import lt.cartwise.recipe.dto.RecipeIngridientDto;
import lt.cartwise.recipe.dto.RecipePostRequest;
import lt.cartwise.recipe.dto.RecipeWithAttributesDto;
import lt.cartwise.recipe.entities.Ingridient;
import lt.cartwise.recipe.entities.Recipe;
import lt.cartwise.recipe.entities.RecipeCategory;
import lt.cartwise.recipe.mappers.RecipeMapper;
import lt.cartwise.recipe.repositories.RecipeCategoryRepository;
import lt.cartwise.recipe.repositories.RecipeRepository;
import lt.cartwise.translations.TranslationService;
import lt.cartwise.user.entities.User;

@Service
public class RecipeService {
	
	private final RecipeRepository recipeRepository;
	private final RecipeCategoryRepository recipeCategoryRepository;
	private final ProductRepository productRepository;
	private final RecipeMapper recipeMapper;
	private final TranslationService translationService;
	private final ImageGalleryService imageGalleryService;



	public RecipeService(RecipeRepository recipeRepository
			, RecipeCategoryRepository recipeCategoryRepository
			, ProductRepository productRepository
			, RecipeMapper recipeMapper
			, TranslationService translationService
			, ImageGalleryService imageGalleryService) {
		this.recipeRepository = recipeRepository;
		this.recipeCategoryRepository = recipeCategoryRepository;
		this.productRepository = productRepository;
		this.recipeMapper = recipeMapper;
		this.translationService = translationService;
		this.imageGalleryService = imageGalleryService;
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
	public Recipe createRecipe(@Valid RecipePostRequest recipeCreate, User user)  {
		Recipe recipe = recipeMapper.toEntity(recipeCreate);
		recipe.setUser(user);
		recipe.setCategories( recipeCategoryRepository.findAllById(recipeCreate.categories()) );
		
		List<Ingridient> ingridients = recipeCreate.ingridients().stream().map( ingridientDto -> {
			Long productId = ingridientDto.productId();
			Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product (id: " + productId + ") not found"));
			return new Ingridient(null, ingridientDto.amount(), ingridientDto.units(), recipe, product);
		}).toList();
		
		recipe.setIngidients(ingridients);
		
		Recipe recipeNew = recipeRepository.save( recipe );
		translationService.createTraslations(Model.RECIPE, recipeNew.getId(), recipeCreate.translations() );
		
		return recipeNew;
	}
	
	public Optional<Recipe> getRecipeOptional(Long id){
		return recipeRepository.findById(id);
	}
	
	
	
	
	private RecipeWithAttributesDto toRecipeWithAttributesDto(Recipe recipe) {
		return new RecipeWithAttributesDto(recipe.getId()
					, recipe.getName()
					, recipe.getPortions()
					, recipe.getTimePreparation()
					, recipe.getTimeCooking()
					, recipe.getIsPublic()
					, translationService.getGroupedTranslations( Model.RECIPE, recipe.getId() )
					, recipe.getCategories().stream().map( this::toRecipeCategoriesDto ).toList()
					, recipe.getIngidients().stream().map( this::toRecipeIngridientsDto ).toList()
					, imageGalleryService.getActiveByType( Model.RECIPE, recipe.getId() )
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
