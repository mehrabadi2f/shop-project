package ir.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "users")
public class user {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true, length = 100)
    private String username;

    private String password;

    private boolean enabled = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id")
    )
    private Set<role> roles;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Account account;



    public Long getId() { return id; }
    public String getUsername() { return username; }
    public void setUsername(String u) { this.username = u; }

    public String getPassword() { return password; }
    public void setPassword(String p) { this.password = p; }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean e) { this.enabled = e; }

    public Set<role> getRoles() { return roles; }
    public void setRoles(Set<role> roles) { this.roles = roles; }

    // getters setters
}
