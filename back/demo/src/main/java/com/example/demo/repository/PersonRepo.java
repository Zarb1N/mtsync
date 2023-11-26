package com.example.demo.repository;

import com.example.demo.entity.Person;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface PersonRepo extends Repository<Person, Long> {
    Person save(Person person);
    Optional<Person> findById(long id);

    @Query(value = "select * from person p where p.name = :name order by name", nativeQuery = true)
    List<Person> findAllByName(String name);
}
