package com.luv2code.ecommerce.dao;

import com.luv2code.ecommerce.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findByCustomerEmailOrderByDateCreatedDesc(@Param("email") String email, Pageable pageable);
    
    // something like this is what Spring Data JPA will do 'auto-magically'
    /*
    SELECT * FROM orders
    LEFT OUTER JOIN customer
    ON orders.customer_id=customer.id
    WHERE customer.email=:email
    ORDER BY orders.date_created DESC
     */
    
    // endpoint will be, for example:
    // http://localhost:8080/api/orders/search/findByCustomerEmailOrderByDateCreatedDesc?email=demo@luv2code.com
}
