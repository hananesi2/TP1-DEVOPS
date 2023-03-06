package com.bus.beans;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Societe {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @OneToMany(mappedBy = "societe")
    private List<SocieteBus> buss;

}
