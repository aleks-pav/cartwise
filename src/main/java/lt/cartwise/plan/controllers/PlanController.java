package lt.cartwise.plan.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lt.cartwise.plan.dto.PlanCreateDto;
import lt.cartwise.plan.dto.PlanDto;
import lt.cartwise.plan.dto.PlanWithAttributesDto;
import lt.cartwise.plan.services.PlanService;
import lt.cartwise.user.dto.UserDto;

@RestController
@CrossOrigin
@RequestMapping("/api/plans")
public class PlanController {
	
	private PlanService planService;

	public PlanController(PlanService planService) {
		this.planService = planService;
	}
	
	
	@GetMapping
	public ResponseEntity<List<PlanDto>> getAll(@RequestParam Long uid){
		return ResponseEntity.ok( planService.getAllByUser(uid) );
	}
	
	// TODO get with full data (recipes and products)
	@GetMapping("/{id}")
	public ResponseEntity<PlanWithAttributesDto> getById(@PathVariable Long id, @RequestBody UserDto userDto){
		return ResponseEntity.of( planService.getByIdByUser(id, userDto) );
	}
	
	@PostMapping
	public ResponseEntity<PlanDto> createPlan(@RequestBody PlanCreateDto planCreate, @RequestBody UserDto userDto){
		return ResponseEntity.ok( planService.createPlan(planCreate, userDto) );
	}
	
}
