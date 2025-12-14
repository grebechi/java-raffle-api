package br.com.gabrielrebechi.raffle.domain.repository;

import br.com.gabrielrebechi.raffle.domain.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    List<Registration> findAllByKeyword(String keyword);

}