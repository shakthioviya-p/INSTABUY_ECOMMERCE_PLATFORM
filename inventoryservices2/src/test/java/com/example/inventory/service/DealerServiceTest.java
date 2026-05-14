package com.example.inventory.service;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
 
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
 
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
 
import com.example.inventory.entity.Dealer;
import com.example.inventory.repository.DealerRepository;
import com.example.inventory.security.JwtUtil;
 
@ExtendWith(MockitoExtension.class)
public class DealerServiceTest {
 
    @Mock
    private DealerRepository repo;
 
    @Mock
    private JwtUtil jwtUtil;
 
    @Mock
    private PasswordEncoder passwordEncoder;
 
    @Mock
    private EmailService emailService;
 
    @InjectMocks
    private DealerService dealerService;
 
    // ✅ SIGNUP TEST
    @Test
    void testSignup() {
 
        Dealer dealer = new Dealer();
 
        dealer.setDealerId(1L);
        dealer.setName("Dhaarani");
        dealer.setEmail("dealer@gmail.com");
        dealer.setPassword("1234");
 
        // ✅ Encode password mock
        when(passwordEncoder.encode("1234"))
                .thenReturn("encodedPassword");
 
        // ✅ Save dealer mock
        when(repo.save(
                org.mockito.ArgumentMatchers.any(Dealer.class)))
                .thenReturn(dealer);
 
        Dealer saved =
                dealerService.signup(dealer);
 
        assertEquals(
                "Dhaarani",
                saved.getName()
        );
    }
 
    // ✅ LOGIN TEST
    @Test
    void testLogin() {
 
        Dealer dealer = new Dealer();
 
        dealer.setDealerId(1L);
        dealer.setEmail("dealer@gmail.com");
        dealer.setPassword("encodedPassword");
 
        when(repo.findByEmail("dealer@gmail.com"))
                .thenReturn(dealer);
 
        when(passwordEncoder.matches(
                "1234",
                "encodedPassword"))
                .thenReturn(true);
 
        when(jwtUtil.generateToken(
                "dealer@gmail.com",
                1L))
                .thenReturn("mock-token");
 
        String token =
                dealerService.login(
                        "dealer@gmail.com",
                        "1234"
                );
 
        assertEquals(
                "mock-token",
                token
        );
    }
 
    // ✅ GET DEALER TEST
    @Test
    void testGetDealerById() {
 
        Dealer dealer = new Dealer();
 
        dealer.setDealerId(1L);
        dealer.setName("Dhaarani");
 
        when(repo.findById(1L))
                .thenReturn(Optional.of(dealer));
 
        Dealer result =
                dealerService.getDealerById(1L);
 
        assertEquals(
                "Dhaarani",
                result.getName()
        );
    }
 
    // ✅ UPDATE ONBOARDING TEST
    @Test
    void testUpdateOnboardingStep() {
 
        Dealer dealer = new Dealer();
 
        dealer.setDealerId(1L);
 
        when(repo.findById(1L))
                .thenReturn(Optional.of(dealer));
 
        when(repo.save(dealer))
                .thenReturn(dealer);
 
        Map<String, Object> data =
                new HashMap<>();
 
        data.put("step", 1);
        data.put("name", "Dhaarani");
        data.put("phone", "9876543210");
        data.put("pan", "ABCDE1234F");
 
        Dealer updated =
                dealerService.updateOnboardingStep(
                        1L,
                        data
                );
 
        assertEquals(
                "Dhaarani",
                updated.getName()
        );
    }
 
    // ✅ SEND OTP TEST
    @Test
    void testSendOtp() {
 
        Dealer dealer = new Dealer();
 
        dealer.setDealerId(1L);
 
        when(repo.findById(1L))
                .thenReturn(Optional.of(dealer));
 
        when(repo.save(dealer))
                .thenReturn(dealer);
 
        // ✅ FIXED
        doNothing().when(emailService)
                .sendOtp(
                        org.mockito.ArgumentMatchers.anyString(),
                        org.mockito.ArgumentMatchers.anyString()
                );
 
        dealerService.sendOtpToUser(
                1L,
                "dealer@gmail.com"
        );
    }
 
    // ✅ VERIFY OTP TEST
    @Test
    void testVerifyOtp() {
 
        Dealer dealer = new Dealer();
 
        dealer.setDealerId(1L);
        dealer.setEmailOtp("123456");
 
        dealer.setOtpExpiry(
                LocalDateTime.now().plusMinutes(5)
        );
 
        when(repo.findById(1L))
                .thenReturn(Optional.of(dealer));
 
        when(repo.save(dealer))
                .thenReturn(dealer);
 
        dealerService.verifyOtp(
                1L,
                "123456"
        );
 
        assertEquals(
                null,
                dealer.getEmailOtp()
        );
    }
 
    // ✅ INVALID LOGIN TEST
    @Test
    void testInvalidLogin() {
 
        when(repo.findByEmail("wrong@gmail.com"))
                .thenReturn(null);
 
        assertThrows(
                RuntimeException.class,
                () -> dealerService.login(
                        "wrong@gmail.com",
                        "1234"
                )
        );
    }
}