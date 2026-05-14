package com.example.inventory.controller;
 
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
 
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
 
import com.example.inventory.repository.ProductRepository;
import com.example.inventory.security.JwtFilter;
import com.example.inventory.security.JwtUtil;
import com.example.inventory.service.InventoryService;
import com.example.inventory.service.ProductService;
 
@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProductControllerTest {
 
    @Autowired
    private MockMvc mockMvc;
 
    // ✅ Mock Services
    @MockBean
    private ProductService productService;
 
    @MockBean
    private InventoryService inventoryService;
 
    // ✅ Mock Repository
    @MockBean
    private ProductRepository productRepo;
 
    // ✅ Mock Security
    @MockBean
    private JwtUtil jwtUtil;
 
    @MockBean
    private JwtFilter jwtFilter;
 
    // ✅ TEST API
    @Test
    void testGetProducts() throws Exception {
 
        mockMvc.perform(get("/api/product/all"))
                .andExpect(status().isOk());
    }
}