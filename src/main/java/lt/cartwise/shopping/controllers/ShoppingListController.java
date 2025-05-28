package lt.cartwise.shopping.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lt.cartwise.shopping.dto.ShoppingListDto;
import lt.cartwise.shopping.services.ShoppingListService;

@RestController
@CrossOrigin
@RequestMapping("/api/shopping")
public class ShoppingListController {
	
	private ShoppingListService shoppingListService;

	public ShoppingListController(ShoppingListService shoppingListService) {
		this.shoppingListService = shoppingListService;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ShoppingListDto> getShoppingList(@PathVariable String id){
		return ResponseEntity.of(shoppingListService.getShoppingList(id));
	}
}
