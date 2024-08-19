package com.demo1.demo.repository;

import com.demo1.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByMail(String mail);


    //Bazı hazır methotarı elde ettik herhangi bir soru yapmadan database işlemlerini yapabiliyoruz UserReporsitoy interface si yüzünden

}
