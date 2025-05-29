package lt.cartwise.plan.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lt.cartwise.plan.dto.PlanCreateDto;
import lt.cartwise.plan.dto.PlanDeleteDto;
import lt.cartwise.plan.dto.PlanDto;
import lt.cartwise.plan.dto.PlanWithAttributesDto;
import lt.cartwise.plan.services.PlanService;

@RestController
@CrossOrigin
@RequestMapping("/api/plans")
public class PlanController {
	
	private final PlanService planService;

	public PlanController(PlanService planService) {
		this.planService = planService;
	}
	
	
	@GetMapping
	public ResponseEntity<List<PlanDto>> getAll(@RequestParam Long uid){
		return ResponseEntity.ok( planService.getAllByUser(uid) );
	}
	
	// TODO get with full data (recipes and products)
	@GetMapping("/{id}")
	public ResponseEntity<PlanWithAttributesDto> getById(@PathVariable Long id, @RequestParam Long uid){
		return ResponseEntity.of( planService.getByIdByUser(id, uid) );
	}
	//TODO @Valid must be in Controller, not Service!!!
	@PostMapping
	public ResponseEntity<PlanDto> createPlan(@Valid @RequestBody PlanCreateDto planCreate){
		return ResponseEntity.ok( planService.createPlan(planCreate) );
	}
	
	@DeleteMapping
	public ResponseEntity<Void> createPlan(@Valid @RequestBody PlanDeleteDto planDelete){
		planService.deletePlan(planDelete);
		return ResponseEntity.ok().build();
	}
	
	
	
}
