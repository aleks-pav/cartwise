package lt.cartwise.shopping.controllers;

import org.springframework.web.bind.annotation.*;

import lt.cartwise.shopping.services.ShoppingListProductService;

@RestController
@CrossOrigin
@RequestMapping("/api/shopping/product")
public class ShoppingListProductController {
	
	private final ShoppingListProductService shoppingListProductService;

	public ShoppingListProductController(ShoppingListProductService shoppingListProductService) {
		this.shoppingListProductService = shoppingListProductService;
	}
	
}

// TODO is it needed ???