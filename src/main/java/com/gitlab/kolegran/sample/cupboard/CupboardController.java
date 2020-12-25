package com.gitlab.kolegran.sample.cupboard;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class CupboardController {

    @GetMapping("/cupboards")
    public List<Cupboard> cupboards() {
        return List.of(
            new Cupboard(1, "physics laboratory"),
            new Cupboard(2, "chemistry laboratory")
        );
    }
}
