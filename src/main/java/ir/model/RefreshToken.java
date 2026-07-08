package ir.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 1000)
    private String token; // هشِ توکن

    @Column(nullable = false)
    private String username;

    private boolean revoked; // آیا ابطال شده؟
    private LocalDateTime expiryDate;
    @PrePersist
    protected void onCreate() {
        this.revoked = false; // همیشه هنگام ساخت، false است
    }
}

