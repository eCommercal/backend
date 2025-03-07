package com.viet.service;

import com.viet.config.JwtProvider;
import com.viet.domain.USER_ROLE;
import com.viet.model.Cart;
import com.viet.model.User;
import com.viet.model.VerificationCode;
import com.viet.repository.CartRepository;
import com.viet.repository.UserRepository;
import com.viet.repository.VerificationCodeRepository;
import com.viet.request.LoginRequest;
import com.viet.request.SignupRequest;
import com.viet.response.AuthResponse;
import com.viet.utils.OtpUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    CartRepository cartRepository;
    JwtProvider jwtProvider;
    VerificationCodeRepository verificationCodeRepository;
    EmailService emailService;
    CustomUserServiceImpl customUserService;

    @Override
    public String createUser(SignupRequest req) throws Exception {

        VerificationCode verificationCode = verificationCodeRepository.findByEmail(req.getEmail());

        if(verificationCode == null || !verificationCode.getOtp().equals(req.getOtp())) {
            throw new Exception("wrong otp");
        }

        User existingUser = userRepository.findByEmail(req.getEmail());

        if (existingUser == null) {
            User user = new User();
            user.setEmail(req.getEmail());
            user.setFullName(req.getFullName());
            user.setRole(USER_ROLE.ROLE_CUSTOMER);
            user.setMobile("123456789");
            user.setPassword(passwordEncoder.encode(req.getOtp()));

            user = userRepository.save(user);

            Cart cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);

        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(USER_ROLE.ROLE_CUSTOMER.toString()));

        Authentication auth = new UsernamePasswordAuthenticationToken(req.getEmail(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);

        return jwtProvider.generateToken(auth);
    }

    @Override
    public void sendOtp(String email) throws Exception {
        String SIGNING_PREFIX = "signin_";

        if(email.startsWith(SIGNING_PREFIX)) {
            email = email.substring(SIGNING_PREFIX.length());

            User user = userRepository.findByEmail(email);
            if(user == null) {
                throw new Exception("user not found with this email!");
            }
        }

        VerificationCode isExist = verificationCodeRepository.findByEmail(email);

        if(isExist != null) {
            verificationCodeRepository.delete(isExist);
        }

        String otp = OtpUtil.generateOtp();
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(email);

        verificationCodeRepository.save(verificationCode);

        String subject = "OTP verification";
        String body = "Your verification code is " + otp;

        emailService.sendVerificationOtpEmail(email, otp, subject, body);
    }

    @Override
    public AuthResponse signing(LoginRequest req) throws Exception {
        String username = req.getEmail();
        String otp = req.getOtp();

        Authentication authentication = authenticate(username, otp);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Login successful");

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roleName = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();

        authResponse.setRole(USER_ROLE.valueOf(roleName));

        return authResponse;
    }

    private Authentication authenticate(String username, String otp) {
        UserDetails userDetails = customUserService.loadUserByUsername(username);

        if(userDetails == null) {
            throw new BadCredentialsException("username or password is incorrect");
        }

        VerificationCode verificationCode = verificationCodeRepository.findByEmail(username);

        if(verificationCode == null || !verificationCode.getOtp().equals(otp)) {
            throw new BadCredentialsException("wrong otp");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
