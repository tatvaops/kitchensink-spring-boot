package com.mongodb.kitchensink.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.mongodb.kitchensink.demo.models.Member;
import com.mongodb.kitchensink.demo.repositories.MemberRepository;

import jakarta.validation.Valid;

// import com.example.migration.models.MemberResourceREST;
// import com.example.migration.repositories.MemberResourceRESTRepository;

@Service
@Transactional
@Validated
public class MemberService {
    @Autowired
    private MemberRepository repository;
    
    public List<Member> findAll() {
        return repository.findAll();
    }
    
    public Optional<Member> findById(String id) {
        return repository.findById(id);
    }
    
    public Member save(@Valid Member entity) {
        return repository.save(entity);
    }
    
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}