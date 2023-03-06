package com.bus.beans;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "abonnement")
public class TrajetAbonnement {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateDepart;
    private LocalDate dateFin;
    private Double prix;

    @ManyToOne(fetch = FetchType.EAGER)
    private Trajet trajet;
}
