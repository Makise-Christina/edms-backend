package com.m.edmsbackend.dao;

import java.util.List;

import com.m.edmsbackend.model.Contact;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface IContactRepository extends JpaRepository<Contact, Long> {
    @Query(value = "select * from elder_contact where elder_id = ?1", nativeQuery = true)
    List<Contact> findContactsByElderId(Long elderId);

    @Modifying
    @Transactional
    @Query(value = "delete from elder_contact where elder_id = ?1", nativeQuery = true)
    void deleteContactsByElderId(Long elderId);
}
