package uz.pdp.appcommunicationcompany.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.pdp.appcommunicationcompany.entity.enums.TurniketType;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public class TurniketHistory {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private Turniket turniket;

    @CreatedBy
    private UUID createdBy;             // ishga kiruvchi user

    @Enumerated(EnumType.STRING)
    private TurniketType type;

    @CreationTimestamp
    private Timestamp created_at;
}
