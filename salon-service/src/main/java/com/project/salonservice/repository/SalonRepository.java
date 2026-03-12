package com.project.salonservice.repository;

import com.project.salonservice.modal.Salon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SalonRepository extends JpaRepository<Salon, Long> {

    Salon findByOwnerId(Long ownerId);

    List<Salon> findByCityContainingIgnoreCase(String city);

    @Query("SELECT s FROM Salon s WHERE " +
            "lower(s.city) LIKE lower(concat('%', :keyword, '%')) OR " +
            "lower(s.name) LIKE lower(concat('%', :keyword, '%')) OR " +
            "lower(s.address) LIKE lower(concat('%', :keyword, '%'))")
    List<Salon> searchSalon(@Param("keyword") String keyword);
}
