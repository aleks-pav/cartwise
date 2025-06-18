package lt.cartwise.admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lt.cartwise.recipe.dto.RecipePatchRequest;
import lt.cartwise.recipe.dto.RecipeResponse;
import lt.cartwise.user.dto.UserPutRequest;
import lt.cartwise.user.dto.UserResponse;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	private final AdminService adminService;

	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<UserResponse>> getUsers(){
		return ResponseEntity.ok( adminService.getUsersList() );
	}
	
	@PutMapping("/users")
	public ResponseEntity<UserResponse> putUser(@RequestBody @Valid UserPutRequest request){
		return ResponseEntity.ok( adminService.putUser(request) );
	}
	
	@GetMapping("/recipes")
	public ResponseEntity<List<RecipeResponse>> getRecipes(){
		return ResponseEntity.ok( adminService.getRecipesList() );
	}
	
	@PatchMapping("/recipes")
	public ResponseEntity<RecipeResponse> patchRecipe(@RequestBody @Valid RecipePatchRequest request){
		return ResponseEntity.ok( adminService.patchRecipe(request) );
	}
}
