package com.odap.aplazoApi.repository;

import com.odap.aplazoApi.entity.Requests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  RequestRepository extends JpaRepository<Requests,Long> {
}
