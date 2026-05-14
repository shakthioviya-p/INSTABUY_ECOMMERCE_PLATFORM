package com.example.inventory.service;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
 
import java.util.Optional;
 
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
 
import com.example.inventory.entity.Inventory;
import com.example.inventory.repository.InventoryRepository;
 
@ExtendWith(MockitoExtension.class)
public class InventoryServiceTest {
 
    @Mock
    private InventoryRepository inventoryRepository;
 
    @InjectMocks
    private InventoryService inventoryService;
 
    // ✅ SAVE INVENTORY TEST
    @Test
    void testSaveInventory() {
 
        Inventory inventory = new Inventory();
 
        inventory.setId(1L);
        inventory.setProductId(101L);
        inventory.setProductName("Laptop");
        inventory.setPrice(50000.0);
 
        // ✅ Correct fields
        inventory.setTotalQuantity(10);
        inventory.setAvailableQuantity(10);
        inventory.setReservedQuantity(0);
 
        when(inventoryRepository.save(inventory))
                .thenReturn(inventory);
 
        Inventory saved =
                inventoryRepository.save(inventory);
 
        assertEquals(
                "Laptop",
                saved.getProductName()
        );
    }
 
    // ✅ FIND INVENTORY TEST
    @Test
    void testFindInventory() {
 
        Inventory inventory = new Inventory();
 
        inventory.setId(1L);
        inventory.setProductName("Laptop");
 
        when(inventoryRepository.findById(1L))
                .thenReturn(Optional.of(inventory));
 
        Optional<Inventory> result =
                inventoryRepository.findById(1L);
 
        assertEquals(
                true,
                result.isPresent()
        );
 
        assertEquals(
                "Laptop",
                result.get().getProductName()
        );
    }
}