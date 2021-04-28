package uz.pdp.appcommunicationcompany.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Turniket {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private Address location;            // Turniket o'rnatilgan joy (Kompanya manzili)

    @CreationTimestamp
    private Timestamp created_at;

}
