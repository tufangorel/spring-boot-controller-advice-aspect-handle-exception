package com.company.customerinfo.repository;

import com.company.customerinfo.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LogRepository extends JpaRepository<Log, Long> {

}

