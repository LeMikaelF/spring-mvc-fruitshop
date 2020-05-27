package com.mikaelfrancoeur.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue
    private Long id;

    public Category(String name) {
        this.name = name;
    }

    private String name;
}
