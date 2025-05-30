package com.example.IS216_Dlegent.controller;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

import com.example.IS216_Dlegent.utils.CookieUtils;

import com.example.IS216_Dlegent.encoder.Sha256PasswordEncoder;
import com.example.IS216_Dlegent.model.Account;
import com.example.IS216_Dlegent.model.DoiTac;
import com.example.IS216_Dlegent.model.Customer;
import com.example.IS216_Dlegent.payload.request.LoginRequest;
import com.example.IS216_Dlegent.payload.request.UserRegistrationDTO;
import com.example.IS216_Dlegent.payload.respsonse.LoginResponse;
import com.example.IS216_Dlegent.repository.CustomerRepository;
import com.example.IS216_Dlegent.repository.DoiTacRepository;
import com.example.IS216_Dlegent.repository.KhachHangRepository;
import com.example.IS216_Dlegent.repository.UserRepo;
import com.example.IS216_Dlegent.service.AccountService;
import com.example.IS216_Dlegent.service.CustomerService;
import com.example.IS216_Dlegent.service.DoiTacService;
import com.example.IS216_Dlegent.service.UserService;
import com.example.IS216_Dlegent.service.VerifyTokenService;
import com.example.IS216_Dlegent.service.ChiTietDatPhongService;
import com.example.IS216_Dlegent.payload.dto.ChiTietDatPhongDTO;

@RestController
public class SignInAPIController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private DoiTacRepository doiTacRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private VerifyTokenService verifyTokenService;
    @Autowired
    private ChiTietDatPhongService chiTietDatPhongService;

    private final Logger logger = LoggerFactory.getLogger(SignInController.class);

    @PostMapping("/api/signin")
    public ResponseEntity<?> submitUsernamePassword(@RequestBody LoginRequest userLogin) {
        logger.info("Received request: {}", userLogin);
        try {
            logger.info("Login attempt with username: {}", userLogin.getUsername());

            boolean isValid = accountService.verifyAccount(userLogin);
            logger.info("Authentication result: {}", isValid);

            Account account = accountService.getAccountByUsername(userLogin.getUsername()).get();
            Optional<Customer> checkKhach = customerRepository.findByAccount(account);

            if (isValid && checkKhach.isPresent()) {
                Customer khachHang = checkKhach.get();
                if (!khachHang.getTinhTrang().equals("ACTIVE")) {
                    return ResponseEntity.status(406).body(new LoginResponse("Your account is not active", null, null));
                }

                // Create new token valid for 10 minutes and save to database
                Date currentTime = new Date(System.currentTimeMillis());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
                String formattedDate = dateFormat.format(currentTime);
                String token = userLogin.getUsername() + '_' + formattedDate;
                logger.info("Generated token for userc: {}", userLogin.getUsername());

                try {
                    accountService.saveToken(userLogin.getUsername(), token, Duration.ofMinutes(10));
                    logger.info("Token saved successfully");
                } catch (Exception e) {
                    logger.error("Error saving token: {}", e.getMessage(), e);
                    return ResponseEntity.status(500).body(new LoginResponse("Error creating session", null, null));
                }

                // Create auth token cookie
                ResponseCookie authCookie = CookieUtils.createSecureCookie("auth_token", token, 10);

                // Create user ID cookie
                ResponseCookie userIdCookie = CookieUtils.createSecureCookie("user_id", khachHang.getId().toString(),
                        10);

                // Create user role cookie
                ResponseCookie roleCookie = CookieUtils.createSecureCookie("user_role", "CUSTOMER", 10);

                // Create Return success API and set cookies with the token and user info
                return ResponseEntity.ok()
                        .header(HttpHeaders.SET_COOKIE, authCookie.toString())
                        .header(HttpHeaders.SET_COOKIE, userIdCookie.toString())
                        .header(HttpHeaders.SET_COOKIE, roleCookie.toString())
                        .body(new LoginResponse("Login successful", token, khachHang.getId()));
            }

            return ResponseEntity.status(401).body(new LoginResponse("Invalid username or password", null, null));
        } catch (Exception e) {
            logger.error("Error during login process: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(new LoginResponse("Server error during login", null, null));
        }
    }

    @PostMapping("/api/signup")
    public ResponseEntity<?> submitUserRegistrationRequire(@RequestBody UserRegistrationDTO info) {
        try {
            userService.SignUp(info);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error during User Sign Up process: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(new LoginResponse("Server error during login", null, null));
        }

    }

    @PostMapping("/api/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        try {
            // Get auth token from cookie
            String authToken = CookieUtils.getCookieValue(request, "auth_token");

            if (authToken != null) {
                // Revoke the token in database
                accountService.revokeToken(authToken);
                logger.info("Token revoked successfully for logout");
            }

            // Create expired cookies to clear them
            ResponseCookie expiredAuthCookie = CookieUtils.createExpiredCookie("auth_token");
            ResponseCookie expiredUserIdCookie = CookieUtils.createExpiredCookie("user_id");
            ResponseCookie expiredRoleCookie = CookieUtils.createExpiredCookie("user_role");

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, expiredAuthCookie.toString())
                    .header(HttpHeaders.SET_COOKIE, expiredUserIdCookie.toString())
                    .header(HttpHeaders.SET_COOKIE, expiredRoleCookie.toString())
                    .body(new LoginResponse("Logout successful", null, null));

        } catch (Exception e) {
            logger.error("Error during logout process: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(new LoginResponse("Server error during logout", null, null));
        }
    }

    @GetMapping("/api/cart/count")
    public ResponseEntity<?> getCartCount(HttpServletRequest request) {
        try {
            // Check authentication status
            String authToken = CookieUtils.getCookieValue(request, "auth_token");
            boolean isLoggedIn = authToken != null && verifyTokenService.isValidToken(authToken);

            if (!isLoggedIn) {
                return ResponseEntity.ok().body(Map.of("count", 0));
            }

            // Get cart count for logged in user
            Long userId = CookieUtils.getUserIdFromCookie(request);
            if (userId != null) {
                List<ChiTietDatPhongDTO> cartItems = chiTietDatPhongService.getChiTietDatPhongByDatPhongId(userId);
                int cartCount = cartItems != null ? cartItems.size() : 0;
                return ResponseEntity.ok().body(Map.of("count", cartCount));
            } else {
                return ResponseEntity.ok().body(Map.of("count", 0));
            }

        } catch (Exception e) {
            logger.error("Error getting cart count: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(Map.of("error", "Server error getting cart count"));
        }
    }

    @PostMapping("/api/partner-signin")
    public ResponseEntity<?> partnerSignIn(@RequestBody LoginRequest userLogin) {
        logger.info("Received request: {}", userLogin);
        try {
            logger.info("Login attempt with username: {}", userLogin.getUsername());

            boolean isValid = accountService.verifyAccount(userLogin);
            logger.info("Authentication result: {}", isValid);
            Account account = accountService.getAccountByUsername(userLogin.getUsername()).get();

            Optional<DoiTac> checkDoiTac = doiTacRepository.findByAccount(account);
            if (isValid && checkDoiTac.isPresent()) {
                DoiTac doiTac = checkDoiTac.get();
                System.out.println(doiTac.getAccount().getUsername());
                if (!doiTac.getTinhTrang().equals("ACTIVE")) {
                    return ResponseEntity.status(401).body(new LoginResponse("Your account is not active", null, null));
                }
                // Create new token valid for 10 minutes and save to database
                Date currentTime = new Date(System.currentTimeMillis());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
                String formattedDate = dateFormat.format(currentTime);
                String token = userLogin.getUsername() + '_' + formattedDate;
                logger.info("Generated token for userc: {}", userLogin.getUsername());

                try {
                    accountService.saveToken(userLogin.getUsername(), token, Duration.ofMinutes(10));
                    logger.info("Token saved successfully");
                } catch (Exception e) {
                    logger.error("Error saving token: {}", e.getMessage(), e);
                    return ResponseEntity.status(500).body(new LoginResponse("Error creating session", null, null));
                }

                // Create auth token cookie
                ResponseCookie authCookie = CookieUtils.createSecureCookie("auth_token", token, 10);

                // Create user ID cookie
                ResponseCookie userIdCookie = CookieUtils.createSecureCookie("user_id", doiTac.getId().toString(), 10);

                // Create user role cookie
                ResponseCookie roleCookie = CookieUtils.createSecureCookie("user_role", "PARTNER", 10);

                // Create Return success API and set cookies with the token and user info
                return ResponseEntity.ok()
                        .header(HttpHeaders.SET_COOKIE, authCookie.toString())
                        .header(HttpHeaders.SET_COOKIE, userIdCookie.toString())
                        .header(HttpHeaders.SET_COOKIE, roleCookie.toString())
                        .body(new LoginResponse("Login successful", token, doiTac.getId()));
            }

            return ResponseEntity.status(401).body(new LoginResponse("Invalid username or password", null, null));
        } catch (Exception e) {
            logger.error("Error during login process: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(new LoginResponse("Server error during login", null, null));
        }
    }

}
