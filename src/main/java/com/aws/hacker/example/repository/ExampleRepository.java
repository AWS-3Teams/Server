package com.aws.hacker.example.repository;

import com.aws.hacker.example.entity.Example;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExampleRepository extends JpaRepository<Example, Long> {
}
