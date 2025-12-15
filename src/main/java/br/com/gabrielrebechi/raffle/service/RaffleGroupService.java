package br.com.gabrielrebechi.raffle.service;

import br.com.gabrielrebechi.raffle.domain.dto.CreateRaffleGroupRequest;
import br.com.gabrielrebechi.raffle.domain.model.RaffleGroup;
import br.com.gabrielrebechi.raffle.repository.RaffleGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RaffleGroupService {

    private final RaffleGroupRepository raffleGroupRepository;

    public List<RaffleGroup> findAll() {
        return raffleGroupRepository.findAll();
    }

    public RaffleGroup findById(Long id) {
        return raffleGroupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Grupo não encontrado com id: " + id));
    }

    @Transactional
    public RaffleGroup create(CreateRaffleGroupRequest request) {
        if (raffleGroupRepository.existsByKeywordIgnoreCase(request.keyword())) {
            throw new IllegalArgumentException("Já existe um grupo com essa palavra-chave");
        }

        RaffleGroup group = RaffleGroup.builder()
                .keyword(request.keyword())
                .build();

        return raffleGroupRepository.save(group);
    }

    @Transactional
    public RaffleGroup update(Long id, CreateRaffleGroupRequest request) {
        RaffleGroup group = findById(id);

        if (!group.getKeyword().equalsIgnoreCase(request.keyword())
                && raffleGroupRepository.existsByKeywordIgnoreCase(request.keyword())) {
            throw new IllegalArgumentException("Já existe um grupo com essa palavra-chave");
        }

        group.setKeyword(request.keyword());
        return raffleGroupRepository.save(group);
    }

    @Transactional
    public void delete(Long id) {
        RaffleGroup group = findById(id);
        raffleGroupRepository.delete(group);
    }

    @Transactional
    public RaffleGroup findOrCreateByKeyword(String keyword) {
        return raffleGroupRepository.findByKeywordIgnoreCase(keyword)
                .orElseGet(() -> create(new CreateRaffleGroupRequest(keyword)));
    }
}
