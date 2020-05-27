package com.mikaelfrancoeur.bootstrap;

import com.mikaelfrancoeur.domain.Category;
import com.mikaelfrancoeur.domain.Customer;
import com.mikaelfrancoeur.repositories.CategoryRepository;
import com.mikaelfrancoeur.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class Bootstrap implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        bootstrapCategories();
        bootstrapCustomers();
    }

    private void bootstrapCustomers() {
        final Customer mikael = new Customer(1L, "Mikael", "Francoeur");
        final Customer darth = new Customer(2L, "Darth", "Vader");
        final Customer luke = new Customer(3L, "Luke", "Skywalker");
        final Customer frodo = new Customer(4L, "Frodo", "Baggins");

        customerRepository.saveAll(Stream.of(mikael, darth, luke, frodo).collect(Collectors.toList()));

        log.debug("Saved {} customers to repository.", customerRepository.count());
    }

    private void bootstrapCategories() {
        final Category fruits = new Category("Fruits");
        final Category dried = new Category("Dried");
        final Category fresh = new Category("Fresh");
        final Category exotic = new Category("Exotic");
        final Category nuts = new Category("Nuts");

        categoryRepository.saveAll(
                Stream.of(fruits, dried, fresh, exotic, nuts)
                        .collect(Collectors.toList()));

        log.debug("Saved {} categories to repository.", categoryRepository.count());
    }
}
