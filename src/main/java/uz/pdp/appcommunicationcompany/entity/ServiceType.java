package uz.pdp.appcommunicationcompany.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServiceType {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;            // MB, MINUTE, SMS, VOICE_MESSAGE yoki Kongilochar xizmatlar:  GOODok, ob-havo, ...

    private String description;
}
