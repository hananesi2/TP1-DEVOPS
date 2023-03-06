package com.bus.repository;

import com.bus.beans.SocieteBus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusRepository extends JpaRepository<SocieteBus, Long> {
    List<SocieteBus> findBySociete_Id(Long id);
}
