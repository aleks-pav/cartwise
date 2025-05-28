package lt.cartwise.plan.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lt.cartwise.plan.dto.PlanRecipeCreateDto;
import lt.cartwise.plan.dto.PlanRecipeDto;
import lt.cartwise.plan.services.PlanRecipeService;

@RestController
@CrossOrigin
@RequestMapping("/api/plans/recipes")
public class PlanRecipeController {
	
	private PlanRecipeService planRecipeService;

	public PlanRecipeController(PlanRecipeService planRecipeService) {
		this.planRecipeService = planRecipeService;
	}
	
	
	@PostMapping
	public ResponseEntity<PlanRecipeDto> createPlanRecipe(@RequestBody PlanRecipeCreateDto dto){
		return ResponseEntity.ok( planRecipeService.createPlanRecipe(dto) );
	}
}