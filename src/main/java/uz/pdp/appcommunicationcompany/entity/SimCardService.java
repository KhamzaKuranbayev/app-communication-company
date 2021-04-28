package uz.pdp.appcommunicationcompany.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SimCardService {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private SimCard simCard;

    @ManyToOne
    private Service service;

    private boolean status = true;               // true - active  false - not active

    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp dateOfPurchase;           // Paket xarid qilingan sana va vaqt

    @Transient
    private Timestamp expireDate;

    // Paketning Tugash sana va vaqtini ko'rish uchun
    public Timestamp getExpireDate() {
        LocalDateTime localDateTimeOfPurchase = dateOfPurchase.toLocalDateTime();
        return Timestamp.valueOf(localDateTimeOfPurchase.plusDays((long) service.getCountDateOfExpire()));
    }
}
