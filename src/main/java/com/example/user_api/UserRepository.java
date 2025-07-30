package com.example.user_api;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Sort;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //JPQL query
//    @Query("Select u from User u WHERE u.name LIKE %:keyword%")
//    List<User> searchByName(@Param("keyword") String keyword);

    //Pagination using Built-in JPA method
    Page<User> findAll(Pageable pagination);

    List<User> findByName(String name, Sort sort);

    List<User> findByNameStartingWith(String prefix);
    List<User> findByEmailContaining(String domain);
    List<User> findByPhoneNumberIsNotNull();

    @Query(value = "SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<User> searchByNameIgnoreCase(@Param("keyword") String keyword);

    @Query("SELECT u FROM User u WHERE u.phoneNumber = :phone")
    Optional<User> findByPhoneNumber(@Param("phone") String phone);
    //Update email
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.email = :email WHERE u.id=:id")
    int updateEmail(@Param("id")Long id, @Param("email") String email);
}
