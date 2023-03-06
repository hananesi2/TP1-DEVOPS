package com.bus.beans;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Trajet {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String depart;

    private String arrive;

    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Double prix;

    @ManyToMany
    private List<Customer> customers;

    @OneToMany(mappedBy="trajet")
    private List<TrajetAbonnement> abonnements;

}
