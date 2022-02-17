package com.odap.aplazoApi.repository;

import com.odap.aplazoApi.entity.CreditDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditDetailsRepository extends JpaRepository<CreditDetails,Long> {


    List<CreditDetails> findAllByRequestId(Long requestId);
}
