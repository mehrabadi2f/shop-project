package ir.repository;

import ir.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    @Override
    Optional<RefreshToken> findById(Long aLong);
    Optional<RefreshToken>findByToken(String token);
    void deleteByUsername(String username);
}
