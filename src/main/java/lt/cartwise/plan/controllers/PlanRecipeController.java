package lt.cartwise.plan.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lt.cartwise.plan.dto.PlanRecipeDto;
import lt.cartwise.plan.dto.PlanRecipePatchRequest;
import lt.cartwise.plan.dto.PlanRecipePostRequestList;
import lt.cartwise.plan.services.PlanRecipeService;

@RestController
@RequestMapping("/api/plans/recipes")
public class PlanRecipeController {
	
	private final PlanRecipeService planRecipeService;

	public PlanRecipeController(PlanRecipeService planRecipeService) {
		this.planRecipeService = planRecipeService;
	}
	
	@PostMapping
	public ResponseEntity<Void> createPlanRecipe(@AuthenticationPrincipal UserDetails userDetails
			, @RequestBody @Valid PlanRecipePostRequestList requestList){
		planRecipeService.createPlanRecipe(userDetails, requestList.recipes());
		return ResponseEntity.accepted().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePlanRecipe(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id){
		planRecipeService.deletePlanRecipe(userDetails, id);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping
	public ResponseEntity<PlanRecipeDto> putPlanRecipe(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody PlanRecipePatchRequest dto){
		return ResponseEntity.ok(planRecipeService.putPlanRecipe(userDetails, dto));
	}
}