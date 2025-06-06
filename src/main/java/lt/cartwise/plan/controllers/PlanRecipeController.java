package lt.cartwise.plan.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lt.cartwise.plan.dto.PlanRecipeDto;
import lt.cartwise.plan.dto.PlanRecipePostRequest;
import lt.cartwise.plan.services.PlanRecipeService;

@RestController
@CrossOrigin
@RequestMapping("/api/plans/recipes")
public class PlanRecipeController {
	
	private final PlanRecipeService planRecipeService;

	public PlanRecipeController(PlanRecipeService planRecipeService) {
		this.planRecipeService = planRecipeService;
	}
	
		
	@PostMapping
	public ResponseEntity<PlanRecipeDto> createPlanRecipe(@AuthenticationPrincipal UserDetails userDetails
			, @Valid @RequestBody PlanRecipePostRequest dto){
		return ResponseEntity.ok( planRecipeService.createPlanRecipe(userDetails, dto) );
	}
}