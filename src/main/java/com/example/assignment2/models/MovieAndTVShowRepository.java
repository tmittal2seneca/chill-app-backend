package com.example.assignment2.models;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieAndTVShowRepository extends MongoRepository<MovieAndTVShow, String> {

}
