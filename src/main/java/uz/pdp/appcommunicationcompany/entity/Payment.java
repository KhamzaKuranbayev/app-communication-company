package uz.pdp.appcommunicationcompany.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Payment {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private SimCard simCard;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private String paymentType;             // qaysi to’lov turi orqali tushganligi

    @Column(nullable = false)
    private String owner;                   // to'lov kim tomonidan amalga oshirilgani

    @CreationTimestamp
    private Timestamp created_at;

}
