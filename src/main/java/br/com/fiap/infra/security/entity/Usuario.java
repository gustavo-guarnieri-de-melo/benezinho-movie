package br.com.fiap.infra.security.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "TB_USER", uniqueConstraints = {
        @UniqueConstraint(name = "UK_USER_EMAIL", columnNames = "USER_EMAIL")
})
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_USER")
    @SequenceGenerator(name = "SQ_USER", sequenceName = "SQ_USER", allocationSize = 1, initialValue = 1)
    @Column(name = "ID_USUARIO")
    private Long id;

    @Email
    @Column(name = "USER_EMAIL", nullable = false)
    private String username;

    @Column(name = "USER_PASSWORD", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    @JoinTable(name = "TB_AUTHORITY_USUARIO",
            joinColumns = {@JoinColumn(name = "USUARIO", referencedColumnName = "ID_USUARIO", foreignKey = @ForeignKey(name = "FK_AUTHORIRY_USER"))},
            inverseJoinColumns = {@JoinColumn(name = "AUTHORITY", referencedColumnName = "ID_AUTHORITY", foreignKey = @ForeignKey(name = "FK_USER_AUTHORITY"))})
    private Set<Authority> authorities = new LinkedHashSet<>();


    public Usuario() {
    }

    public Usuario(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;

    }

    public Usuario(Long id, String username, String password, Set<Authority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = Objects.nonNull( authorities ) ? authorities : new LinkedHashSet<>();
    }

    public Long getId() {
        return id;
    }

    public Usuario setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Usuario setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Usuario setPassword(String password) {
        this.password = password;
        return this;
    }


    public Set<Authority> getAuthorities() {
        return Collections.unmodifiableSet( authorities );
    }


    public Usuario addAuthority(Authority a) {
        this.authorities.add( a );
        return this;
    }

    public Usuario removeAuthority(Authority a) {
        this.authorities.remove( a );
        return this;
    }


    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}
