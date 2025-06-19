package lt.cartwise.shopping.dto;

import jakarta.validation.constraints.NotNull;

public record ShoppingListPatchRequest(@NotNull(message = "Shopping list product id is required") Long id) {

}
