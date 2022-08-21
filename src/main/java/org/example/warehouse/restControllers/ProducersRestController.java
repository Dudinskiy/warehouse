package org.example.warehouse.restControllers;

import lombok.AllArgsConstructor;
import org.example.warehouse.dto.ProducersDto;
import org.example.warehouse.dto.ProducersDtoRes;
import org.example.warehouse.dto.ProducersFullDto;
import org.example.warehouse.dto.ProducersFullDtoRes;
import org.example.warehouse.services.ProducersService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/rest/producer")
@AllArgsConstructor
public class ProducersRestController {

    private final ProducersService producersService;

    @Secured({"ROLE_Кладовщик", "ROLE_Администратор"})
    @PostMapping(value = "/create")
    public boolean createProducer(@RequestBody ProducersDto producersDto) {
        return producersService.createProducer(producersDto);
    }

    @Secured({"ROLE_Кладовщик", "ROLE_Администратор"})
    @GetMapping(value = "/get-by-id/{id}")
    public ProducersDtoRes getProducerById(@PathVariable int id) {
        return producersService.getProducerById(id);
    }

    @Secured({"ROLE_Кладовщик", "ROLE_Администратор"})
    @PostMapping(value = "/get-by-name")
    public ProducersDtoRes getProducerByName(@RequestBody ProducersDto producersDto) {
        return producersService.getProducerByName(producersDto);
    }

    @Secured({"ROLE_Кладовщик", "ROLE_Администратор"})
    @PostMapping(value = "/get-by-name-full")
    public ProducersFullDtoRes getProducerByNameFull(@RequestBody ProducersFullDto producersFullDto){
        return producersService.getProducerByNameFull(producersFullDto);
    }

    @Secured({"ROLE_Кладовщик", "ROLE_Администратор"})
    @GetMapping(value = "/get-all")
    public List<ProducersFullDtoRes> getAllProducerFull(){
        return producersService.getAllProducerFull();
    }

    @Secured({"ROLE_Кладовщик", "ROLE_Администратор"})
    @DeleteMapping(value = "/delete/{id}")
    public boolean deleteProducerById(@PathVariable int id) {
        return producersService.deleteProducerById(id);
    }
}
