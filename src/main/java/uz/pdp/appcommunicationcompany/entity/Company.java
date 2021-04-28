package uz.pdp.appcommunicationcompany.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Company {

    /*
        Ushbu class Kompanya va Filiallar uchun umumiy class hisoblanadi
        Kompanya uchun alohida, Filial uchun alohida ochmadim.
     */

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @ManyToOne
    private Company parentCompany;            // agar filial bo'lsa, qaysi kompanya filiali ekanligi

    @ToString.Exclude
    @OrderBy(value = "city asc, street desc")
    @OneToOne(mappedBy = "company", cascade = CascadeType.ALL)
    private Address address;

}
