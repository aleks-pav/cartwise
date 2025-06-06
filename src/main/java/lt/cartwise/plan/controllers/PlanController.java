package lt.cartwise.plan.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lt.cartwise.plan.dto.PlanDto;
import lt.cartwise.plan.dto.PlanPostRequest;
import lt.cartwise.plan.dto.PlanWithAttributesDto;
import lt.cartwise.plan.services.PlanService;

@RestController
@RequestMapping("/api/plans")
public class PlanController {
	
	private final PlanService planService;

	public PlanController(PlanService planService) {
		this.planService = planService;
	}


	@GetMapping
	public ResponseEntity<List<PlanDto>> getAll(@AuthenticationPrincipal UserDetails userDetails){
		return ResponseEntity.ok( planService.getAllByUser(userDetails) );
	}
	
	@GetMapping("/active")
	public ResponseEntity<Long> getActive(@AuthenticationPrincipal UserDetails userDetails){
		return ResponseEntity.of( planService.getActivePlanIdByUser(userDetails) );
	}
	
	// TODO get with full data (recipes and products)
	@GetMapping("/{id}")
	public ResponseEntity<PlanWithAttributesDto> getById(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id){
		return ResponseEntity.of( planService.getByIdByUser(userDetails, id) );
	}
	//TODO @Valid must be in Controller, not Service!!!
	@PostMapping
	public ResponseEntity<PlanDto> createPlan(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody PlanPostRequest planCreate){
		return ResponseEntity.ok( planService.createPlan(userDetails, planCreate) );
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePlan(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id){
		planService.deletePlan(userDetails, id);
		return ResponseEntity.ok().build();
	}
	
	
	@PostMapping("/{id}/shopping")
	public ResponseEntity<String> createShoppingList(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id){
		return ResponseEntity.ok( planService.createShoppingList(userDetails, id) );
	}
	
}
