package org.example.warehouse.restControllers;

import lombok.AllArgsConstructor;
import org.example.warehouse.dto.CountriesDto;
import org.example.warehouse.dto.CountriesDtoRes;
import org.example.warehouse.services.CountriesService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/rest/country")
@AllArgsConstructor
public class CountriesRestController {

    private final CountriesService countriesService;

    @Secured({"ROLE_Кладовщик", "ROLE_Администратор"})
    @PostMapping(value = "/create")
    public boolean createCountry(@RequestBody CountriesDto countriesDto) {
        return countriesService.createCountry(countriesDto);
    }

    @Secured({"ROLE_Кладовщик", "ROLE_Администратор"})
    @PostMapping(value = "/get-by-id")
    public CountriesDtoRes getCountryById(@RequestBody CountriesDto countriesDto) {
        return countriesService.getCountryById(countriesDto);
    }

    @Secured({"ROLE_Кладовщик", "ROLE_Администратор"})
    @PostMapping(value = "/get-by-name")
    public CountriesDtoRes getCountryByName(@RequestBody CountriesDto countriesDto) {
        return countriesService.getCountryByName(countriesDto);
    }

    @Secured({"ROLE_Кладовщик", "ROLE_Администратор"})
    @PostMapping(value = "/delete")
    public boolean deleteCountryById(@RequestBody CountriesDto countriesDto) {
        return countriesService.deleteCountryById(countriesDto);
    }

    @Secured({"ROLE_Кладовщик", "ROLE_Администратор"})
    @GetMapping("/get-all")
    public List<CountriesDtoRes> getAllCountries(){
        return countriesService.getAllCountries();
    }
}
