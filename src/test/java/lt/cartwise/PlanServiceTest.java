package lt.cartwise;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import lt.cartwise.enums.Unit;
import lt.cartwise.plan.entities.Plan;
import lt.cartwise.plan.entities.PlanRecipe;
import lt.cartwise.plan.repositories.PlanRepository;
import lt.cartwise.plan.services.PlanService;
import lt.cartwise.product.entities.Product;
import lt.cartwise.recipe.entities.Ingridient;
import lt.cartwise.recipe.entities.Recipe;

@ExtendWith(MockitoExtension.class)
class PlanServiceTest {

    @Mock
    private PlanRepository planRepository;

    @InjectMocks
    private PlanService planService;

    @Test
    void calculateProducts_shouldReturnCorrectAggregatedProductAmounts() {
        // Arrange
        Long planId = 1L;

        // Create ingredients
        Product product = new Product(); // assume it has proper equals/hashCode
        Unit unit = Unit.g;
        Ingridient ingredient = new Ingridient();
        ingredient.setProduct(product);
        ingredient.setUnits(unit);
        ingredient.setAmount(100.0);

        Recipe recipe = new Recipe();
        recipe.setPortions(2);
        recipe.setIngidients(List.of(ingredient));

        PlanRecipe planRecipe = new PlanRecipe();
        planRecipe.setPortions(4.0); // implies ratio 2x
        planRecipe.setRecipe(recipe);

        Plan plan = new Plan();
        plan.setRecipes(List.of(planRecipe));

        when(planRepository.findById(planId)).thenReturn(Optional.of(plan));

        // Act
        Map<Product, Map<Unit, Double>> result = planService.calculateProducts(planId);

        // Assert
        assertEquals(1, result.size());
        Map<Unit, Double> unitMap = result.get(product);
        assertNotNull(unitMap);
        assertEquals(1, unitMap.size());
        assertEquals(200.0, unitMap.get(Unit.g)); // 100 * 2 (due to ratio)
    }
}

