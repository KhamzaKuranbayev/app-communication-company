package uz.pdp.appcommunicationcompany.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public class SimCard {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false, unique = true, length = 13)
    private String phoneNumber;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @Column
    private double balance;                     // hisobidagi mablag'

    @ManyToOne
    private Company company;

    @CreationTimestamp
    private Timestamp created_at;

    @CreatedBy
    private UUID created_by;

}
