package ir.model.analytics;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_product_scores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProductScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "score", nullable = false)
    private Double score;

    @Column(name = "last_interaction_at")
    private LocalDateTime lastInteractionAt;
}
