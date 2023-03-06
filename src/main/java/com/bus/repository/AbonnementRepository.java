package com.bus.repository;

import com.bus.beans.TrajetAbonnement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbonnementRepository extends JpaRepository<TrajetAbonnement, Long> {
}
