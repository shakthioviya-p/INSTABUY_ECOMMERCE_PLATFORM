package com.example.inventory.service;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
 
import java.util.Optional;
 
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
 
import com.example.inventory.entity.Product;
import com.example.inventory.repository.ProductRepository;
 
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
 
    @Mock
    private ProductRepository productRepo;
 
    @Mock
    private InventoryService inventoryService;
 
    @InjectMocks
    private ProductService productService;
 
    // ✅ ADD PRODUCT TEST
    @Test
    void testAddProduct() {
 
        Product product = new Product();
 
        product.setProductId(1L);
        product.setProductName("Laptop");
        product.setDealerId(100L);
        product.setPrice(50000.0);
        product.setQuantity(5);
 
        // ✅ Product does not exist
        when(productRepo.findByProductNameAndDealerId(
                "Laptop",
                100L))
                .thenReturn(null);
 
        // ✅ Save mock
        when(productRepo.save(product))
                .thenReturn(product);
 
        // ✅ Inventory mock
        when(inventoryService.addProduct(
                1L,
                "Laptop",
                5,
                50000.0))
                .thenReturn("Added");
 
        String result =
                productService.addProduct(product);
 
        assertEquals(
                "Added in product + inventory",
                result
        );
    }
 
    // ✅ DELETE PRODUCT TEST
    @Test
    void testDeleteProduct() {
 
        Product product = new Product();
 
        product.setProductId(1L);
        product.setProductName("Laptop");
 
        when(productRepo.findById(1L))
                .thenReturn(Optional.of(product));
 
        doNothing().when(productRepo)
                .deleteById(1L);
 
        when(inventoryService.deleteProductFromInventory(1L))
                .thenReturn("Deleted");
 
        String result =
                productService.deleteProduct(1L);
 
        assertEquals(
                "Product deleted from product and inventory",
                result
        );
    }
 
    // ✅ ADD STOCK TEST
    @Test
    void testAddStock() {
 
        Product product = new Product();
 
        product.setProductId(1L);
        product.setProductName("Laptop");
        product.setDealerId(100L);
        product.setPrice(50000.0);
        product.setQuantity(5);
 
        when(productRepo.findByProductNameAndDealerId(
                "Laptop",
                100L))
                .thenReturn(product);
 
        when(productRepo.save(product))
                .thenReturn(product);
 
        when(inventoryService.addProduct(
                1L,
                "Laptop",
                2,
                50000.0))
                .thenReturn("Added");
 
        String result =
                productService.addStock(
                        "Laptop",
                        100L,
                        2
                );
 
        assertEquals(
                "Stock added successfully",
                result
        );
    }
 
    // ✅ REDUCE STOCK TEST
    @Test
    void testReduceStock() {
 
        Product product = new Product();
 
        product.setProductId(1L);
        product.setProductName("Laptop");
        product.setDealerId(100L);
        product.setPrice(50000.0);
        product.setQuantity(10);
 
        when(productRepo.findByProductNameAndDealerId(
                "Laptop",
                100L))
                .thenReturn(product);
 
        when(productRepo.save(product))
                .thenReturn(product);
 
        // ✅ void method
        doNothing().when(inventoryService)
                .reduceStock(1L, 2);
 
        String result =
                productService.reduceStock(
                        "Laptop",
                        100L,
                        2
                );
 
        assertEquals(
                "Stock reduced successfully",
                result
        );
    }
}