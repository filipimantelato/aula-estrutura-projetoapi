package com.aula.projeto.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/info")
public class InfoController {

    @Autowired
    private IInfoRepository infoRepository;

    @PostMapping("/criar")
    @ResponseStatus(HttpStatus.CREATED)
    public InfoModel criar(@RequestBody InfoModel infoModel) {
        return this.infoRepository.save(infoModel);
    }
}
