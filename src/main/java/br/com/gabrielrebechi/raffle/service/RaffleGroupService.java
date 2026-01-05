package br.com.gabrielrebechi.raffle.service;

import br.com.gabrielrebechi.raffle.model.RaffleGroup;
import br.com.gabrielrebechi.raffle.repository.RaffleGroupRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RaffleGroupService {

    private final RaffleGroupRepository raffleGroupRepository;

    @Transactional(readOnly = true)
    public RaffleGroup getByKeyword(String keyword) {
        return raffleGroupRepository.findByKeywordIgnoreCase(keyword)
                .orElseThrow(() ->
                        new EntityNotFoundException("Raffle group not found")
                );
    }

    @Transactional(readOnly = true)
    public RaffleGroup getById(UUID id) {
        return raffleGroupRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Raffle group not found")
                );
    }

    @Transactional(readOnly = true)
    public List<RaffleGroup> listAll() {
        return raffleGroupRepository.findAll();
    }

    @Transactional
    public RaffleGroup create(String keyword, String description) {

        if (raffleGroupRepository.existsByKeywordIgnoreCase(keyword)) {
            throw new EntityExistsException("Raffle group already exists");
        }

        return raffleGroupRepository.save(
                RaffleGroup.builder()
                        .keyword(keyword)
                        .description(description)
                        .build()
        );
    }
}
