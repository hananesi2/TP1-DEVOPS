package com.bus.service;

import com.bus.beans.SocieteBus;
import com.bus.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class BusService {
    @Autowired
    private BusRepository busRepository;

    public List<SocieteBus> findBySocieteId(Long id){
        return busRepository.findBySociete_Id(id);
    }

    public List<SocieteBus> findAll(){
        return busRepository.findAll();
    }
 }
