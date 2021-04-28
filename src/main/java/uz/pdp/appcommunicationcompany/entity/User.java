package uz.pdp.appcommunicationcompany.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 50)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true)
    private String username;                    //  Tizimda xodim uchun takrorlanmas username beriladi

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updateAt;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            mappedBy = "user")
    @PrimaryKeyJoinColumn
    private Passport passport;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;


    private boolean isPhysicalPerson;       // true - Jismoniy shaxs   false - Yuridik shaxs


    private boolean accountNonExpired = true;      // bu user ning accountining amal qilish muddati o'tmagan

    private boolean accountNonLocked = true;       // bu user ning accounti qulflanmagan

    private boolean credentialsNonExpired = true;  // bu user ning ishonchliligining muddati o'tmagan

    private boolean enabled;                       // bu user

    private String emailCode;


    //---------- USERDETAILS ning METHODLARi--------------///

    // getAuthorities - tizimdagi huqulari (role va priviligiya lar bo'lishi mumkin)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    // User ning username ini qaytaradi
    @Override
    public String getUsername() {
        return this.email;
    }


    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}


