package com.viet.controller;

import com.viet.config.JwtProvider;
import com.viet.domain.AccountStatus;
import com.viet.model.Seller;
import com.viet.model.SellerReport;
import com.viet.model.VerificationCode;
import com.viet.repository.VerificationCodeRepository;
import com.viet.request.LoginRequest;
import com.viet.response.AuthResponse;
import com.viet.service.Impl.AuthService;
import com.viet.service.EmailService;
import com.viet.service.Impl.SellerReportService;
import com.viet.service.Impl.SellerService;
import com.viet.utils.OtpUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sellers")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SellerController {

    SellerService sellerService;
    VerificationCodeRepository verificationCodeRepository;
    AuthService authService;
    EmailService emailService;
    JwtProvider jwtProvider;
    SellerReportService sellerReportService;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> sellerLogin(@RequestBody LoginRequest req) throws Exception {
        String otp = req.getOtp();

        String email = req.getEmail();

        req.setEmail("seller_"+email);
        AuthResponse authResponse = authService.signing(req);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PatchMapping("/verify/{otp}")
    public ResponseEntity<Seller> verifySellerEmail(@PathVariable("otp") String otp) throws Exception {
        VerificationCode verificationCode = verificationCodeRepository.findByOtp(otp);

        if(verificationCode == null || !verificationCode.getOtp().equals(otp)) {
            throw new Exception("wrong otp");

        }

        Seller seller = sellerService.verifyEmail(verificationCode.getEmail(), otp);
        return new ResponseEntity<>(seller, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<Seller> createSeller(@RequestBody Seller seller) throws Exception {

        Seller savedSeller = sellerService.createSeller(seller);

        String otp = OtpUtil.generateOtp();

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(seller.getEmail());

        verificationCodeRepository.save(verificationCode);

        String subject = "Email verification";
        String body = "Welcome to V, verify your account using this link";

        String fe_url = "http://localhost:3000/verify-seller/";

        emailService.sendVerificationOtpEmail(seller.getEmail(), verificationCode.getOtp(), subject, body + fe_url);

        return new ResponseEntity<>((savedSeller), HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Seller> getSellerById(@PathVariable("id") Long id) throws Exception {
        Seller seller = sellerService.getSellerById(id);
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<Seller> getSellerProfile(@RequestHeader("Authorization") String jwt) throws Exception {

        Seller seller = sellerService.getSellerProfile(jwt);


        return new ResponseEntity<>(seller, HttpStatus.OK);
    }

    @GetMapping("/report")
    public ResponseEntity<SellerReport> getSellerReport(@RequestHeader("Authorization") String jwt) throws Exception {

//        String email = jwtProvider.getEmailFromJwtToken(jwt);
//        Seller seller = sellerService.getSellerByEmail(email);
        Seller seller = sellerService.getSellerProfile(jwt);
        SellerReport sellerReport = sellerReportService.getSellerReport(seller);

        return new ResponseEntity<>(sellerReport, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<Seller>> getSellerList(@RequestParam(required = false)AccountStatus status) throws Exception {
        List<Seller> sellers = sellerService.findAll(status);
        return new ResponseEntity<>(sellers, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<Seller> updateSeller(@RequestHeader("Authorization") String jwt,
                                               @RequestBody Seller seller) throws Exception {
        Seller profile = sellerService.getSellerProfile(jwt);
        Seller updatedSeller = sellerService.updateSeller(profile.getId(), seller);
        return new ResponseEntity<>(updatedSeller, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable("id") Long id) throws Exception {
        sellerService.deleteSeller(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
