package ir.service;

import ir.dto.AuthResponse;
import ir.dto.LoginRequestref;
import ir.model.RefreshToken;
import ir.repository.RefreshTokenRepository;
import ir.security.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {
    @Autowired private JwtService jwt;
    @Autowired private RefreshTokenRepository rep;
    @Autowired private AuthenticationManager authManager;

    @Transactional
    public AuthResponse login(LoginRequestref req)
    {
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken((req.getUsername()), req.getPassword()));
            String accessToken = jwt.generateToken(req.getUsername(), 15 * 60 * 1000);
            String refreshToken = jwt.generateToken(req.getUsername(), 7 * 24 * 60 * 60 * 1000);

            // حتماً رفرش توکن را ذخیره کن تا در متد رفرش قابل شناسایی باشد
            saveRefreshtoken(refreshToken, req.getUsername());
            return new AuthResponse(accessToken, refreshToken);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    @Transactional
    public AuthResponse refreshToken(String oldToken)
    {
        RefreshToken refreshToken = rep.findByToken(oldToken).orElseThrow(()->new RuntimeException("Refresh token not found"));
        if(refreshToken.isRevoked())
        {
            rep.deleteByUsername(refreshToken.getUsername());
            throw  new RuntimeException("Refresh token revoked");
        }
        refreshToken.setRevoked(true);
        rep.save(refreshToken);
        String username=refreshToken.getUsername();
        String newAccessToken=jwt.generateToken(username,15*60*1000);
        String newRefreshToken=jwt.generateToken(username,7*24*60*60*1000);
        saveRefreshtoken(newRefreshToken, username);
        return new AuthResponse(newAccessToken,newRefreshToken);
    }
    @Transactional
    public  void  saveRefreshtoken (String  token , String username)
    {
        // ابتدا توکن‌های قبلی این کاربر را پاک کن
        rep.deleteByUsername(username);


        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(token);
        refreshToken.setUsername(username);
        refreshToken.setExpiryDate(LocalDateTime.now().plusDays(7));
        rep.save(refreshToken);
    }

}
