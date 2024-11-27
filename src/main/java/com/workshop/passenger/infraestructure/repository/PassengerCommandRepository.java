package com.workshop.passenger.infraestructure.repository;

import com.workshop.passenger.domain.model.aggregates.Passenger;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerCommandRepository extends ReactiveMongoRepository<Passenger, ObjectId> {
}
