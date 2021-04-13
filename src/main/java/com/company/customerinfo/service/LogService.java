package com.company.customerinfo.service;


import com.company.customerinfo.model.Log;
import com.company.customerinfo.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    public Log save(Log log){
        return logRepository.save(log);
    }

    public List<Log> findAll(){
        return logRepository.findAll();
    }

}