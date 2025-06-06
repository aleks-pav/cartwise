package lt.cartwise.shopping.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lt.cartwise.shopping.dto.ShoppingListDto;
import lt.cartwise.shopping.dto.ShoppingListProductDto;
import lt.cartwise.shopping.services.ShoppingListProductService;
import lt.cartwise.shopping.services.ShoppingListService;

@RestController
@CrossOrigin
@RequestMapping("/api/shopping")
public class ShoppingListController {
	
	private final ShoppingListService shoppingListService;
	private final ShoppingListProductService shoppingListProductService;

	public ShoppingListController(ShoppingListService shoppingListService, ShoppingListProductService shoppingListProductService) {
		this.shoppingListService = shoppingListService;
		this.shoppingListProductService = shoppingListProductService;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ShoppingListDto> getShoppingList(@PathVariable String id){
		return ResponseEntity.of(shoppingListService.getShoppingList(id));
	}
	
	
	@PatchMapping("/{id}")
	public ResponseEntity<ShoppingListProductDto> switchCompleted(@PathVariable String id, @RequestBody ShoppingListProductDto dto){
		return ResponseEntity.ok( shoppingListProductService.switchCompleted(id, dto ));
	}
}
