package com.localeneterprise.file.repo;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.localeneterprise.file.model.Photo;

public interface PhotoRepository extends MongoRepository<Photo, String> {

}