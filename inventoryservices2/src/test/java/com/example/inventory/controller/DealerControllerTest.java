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
 
import com.example.inventory.entity.Dealer;
import com.example.inventory.security.JwtFilter;
import com.example.inventory.security.JwtUtil;
import com.example.inventory.service.DealerService;
 
@WebMvcTest(DealerController.class)
@AutoConfigureMockMvc(addFilters = false)
public class DealerControllerTest {
 
    @Autowired
    private MockMvc mockMvc;
 
    // ✅ Mock Dealer Service
    @MockBean
    private DealerService dealerService;
 
    // ✅ Mock Security
    @MockBean
    private JwtUtil jwtUtil;
 
    @MockBean
    private JwtFilter jwtFilter;
 
    // ✅ LOGIN TEST
    @Test
    void testDealerLogin() throws Exception {
 
        Dealer dealer = new Dealer();
 
        dealer.setDealerId(1L);
        dealer.setEmail("dealer@gmail.com");
        dealer.setPassword("1234");
 
        // ✅ Mock methods
        when(dealerService.login(
                "dealer@gmail.com",
                "1234"))
                .thenReturn("mock-token");
 
        when(dealerService.findByEmail(
                "dealer@gmail.com"))
                .thenReturn(dealer);
 
        String requestBody = """
        {
          "email":"dealer@gmail.com",
          "password":"1234"
        }
        """;
 
        mockMvc.perform(post("/api/dealer/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk());
    }
}