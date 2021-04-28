package uz.pdp.appcommunicationcompany.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Service {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    private Detail detail;

    @Column(nullable = false)
    private String ussd;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private Integer countDateOfExpire;             //  xarid qilingan kundan e’tiboran necha kungacha amal qilish muddati
}
