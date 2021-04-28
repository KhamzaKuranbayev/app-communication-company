package uz.pdp.appcommunicationcompany.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Passport {

    @Id
    @GeneratedValue
    private Integer id;

    private String number;          // passport number

    private String givenBy;        // kim tomonidan berilgani

    private Date dateOfIssue;         // qachon berilgani

    private Date dateOfExpire;        // almashtirish vaqti

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
