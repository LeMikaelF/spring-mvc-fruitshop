package com.mikaelfrancoeur.bootstrap;

import com.mikaelfrancoeur.domain.Category;
import com.mikaelfrancoeur.domain.Customer;
import com.mikaelfrancoeur.domain.Vendor;
import com.mikaelfrancoeur.repositories.CategoryRepository;
import com.mikaelfrancoeur.repositories.CustomerRepository;
import com.mikaelfrancoeur.repositories.VendorRepository;
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
    private final VendorRepository vendorRepository;


    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) {
        bootstrapCategories();
        bootstrapCustomers();
        bootstrapVendors();
    }

    private void bootstrapVendors() {
        final Vendor vendor1 = new Vendor();
        vendor1.setName("name of vendor 1");
        final Vendor vendor2 = new Vendor();
        vendor2.setName("name of vendor 2");

        vendorRepository.saveAll(Stream.of(vendor1, vendor2).collect(Collectors.toList()));
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
