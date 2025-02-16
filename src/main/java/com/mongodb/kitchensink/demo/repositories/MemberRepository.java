package com.mongodb.kitchensink.demo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mongodb.kitchensink.demo.models.Member;

@Repository
public interface MemberRepository extends MongoRepository<Member, String> {
}