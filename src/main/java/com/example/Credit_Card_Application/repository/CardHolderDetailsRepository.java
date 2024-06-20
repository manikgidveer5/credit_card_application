package com.example.Credit_Card_Application.repository;

import com.example.Credit_Card_Application.model.CardHolderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CardHolderDetailsRepository extends JpaRepository<CardHolderDetails, Long> {
}
