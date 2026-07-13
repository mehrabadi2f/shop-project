package ir.model.analytics;
import ir.model.Rule;
import jakarta.persistence.*;

//import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "campaigns")
public class Campaigns {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // نام کمپین، مثلا "کمپین نوروزی"
    private String description;

    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL)
    private List<Rule> rules;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<Rule> getRules() { return rules; }
    public void setRules(List<Rule> rules) { this.rules = rules; }
}
