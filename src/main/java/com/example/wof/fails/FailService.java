package com.example.wof.fails;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FailService {

    private final FailRepository failRepository;

    public FailEntity createFail(String description) {

        return failRepository.saveAndFlush(FailEntity.builder().description(description).build());
    }

    public List<FailEntity> getAll() {
        return failRepository.findAll();
    }
}
