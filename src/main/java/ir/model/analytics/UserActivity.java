package ir.model.analytics;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_activities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    // برای برخی سرچ‌ها ممکن است محصول خاصی وجود نداشته باشد (Null)
    @Column(name = "product_id")
    private Long productId;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false)
    private ActivityType eventType;

    // ذخیره اطلاعات جانبی (مثل کلمه سرچ شده یا قیمت) به صورت اختیاری
    @Column(name = "metadata")
    private String metadata;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
