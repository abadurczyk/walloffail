package com.example.wof.fails;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("fails/")
public class FailController {

    private final FailService failService;

    @GetMapping("all")
    public List<FailEntity> getFails() {
       return failService.getAll();
    }

    @PutMapping("{description}")
    public FailEntity createFail(@PathVariable String description){
        return failService.createFail(description);
    }
}
