package com.bus.service;

import com.bus.beans.Societe;
import com.bus.repository.SocieteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SocietyService {

    @Autowired
    private SocieteRepository societeRepository;

    public List<Societe> findAll(){
        return societeRepository.findAll();
    }
}
