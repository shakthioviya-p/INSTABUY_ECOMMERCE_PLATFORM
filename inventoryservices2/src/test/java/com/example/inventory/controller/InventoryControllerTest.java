package com.example.inventory.controller;
 
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
 
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
 
import com.example.inventory.security.JwtFilter;
import com.example.inventory.security.JwtUtil;
import com.example.inventory.service.InventoryService;
 
@WebMvcTest(InventoryController.class)
@AutoConfigureMockMvc(addFilters = false)
public class InventoryControllerTest {
 
    @Autowired
    private MockMvc mockMvc;
 
    // ✅ Mock InventoryService
    @MockBean
    private InventoryService inventoryService;
 
    // ✅ Mock Security Beans
    @MockBean
    private JwtUtil jwtUtil;
 
    @MockBean
    private JwtFilter jwtFilter;
 
    // ✅ TEST CHECK INVENTORY
    @Test
    void testCheckInventory() throws Exception {
 
        when(inventoryService.checkStock(1L, 2))
                .thenReturn("IN STOCK");
 
        String requestBody = """
        {
          "productId":1,
          "quantity":2
        }
        """;
 
        mockMvc.perform(post("/api/inventory/check")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk());
    }
}