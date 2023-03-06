package com.bus.service;

import com.bus.beans.Trajet;
import com.bus.beans.TrajetAbonnement;
import com.bus.repository.TrajetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TrajetService {
    @Autowired
    private TrajetRepository trajetRepository;

    public List<Trajet> getAllTrajets(){
        return trajetRepository.findAll();
    }

    public List<TrajetAbonnement> getSubscribesById(Long id){
        return trajetRepository.findById(id).get().getAbonnements();
    }

    public Trajet findById(Long id){
        return trajetRepository.findById(id).get();
    }
}
