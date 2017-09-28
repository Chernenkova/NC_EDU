package Elena.Chernenkova.controller;

import Elena.Chernenkova.Service;

import Elena.Chernenkova.entity.Director;
import Elena.Chernenkova.repository.DirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by 123 on 29.09.2017.
 */
@RestController
@RequestMapping("/directors")
public class DirectorController {
    private final DirectorRepository directorRepository;

    @Autowired
    DirectorController(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{directorId}")
    ResponseEntity<Director> getDirector(@PathVariable Integer directorId){
        return new ResponseEntity<Director>(this.directorRepository.findOne(directorId), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity getDirectors(){
        return new ResponseEntity<>(this.directorRepository.
                findAll(new Sort("directorName")), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<Director> createDirector(@RequestBody String json){
        String directorName = Service.findJson("directorName", json);
        String directorNumber = Service.findJson("directorNumber", json);
        Director newDirector = new Director(directorName, directorNumber);
        this.directorRepository.save(newDirector);
        return new ResponseEntity<Director>(newDirector, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{directorId}")
    ResponseEntity<Director> updateDirector(@RequestBody String json, @PathVariable Integer directorId){
        String directorName = Service.findJson("directorName", json);
        String directorNumber = Service.findJson("directorNumber", json);
        Director currentDirector = this.directorRepository.findOne(directorId);
        currentDirector.setDirectorName(directorName);
        currentDirector.setDirectorNumber(directorNumber);
        this.directorRepository.save(currentDirector);
        return new ResponseEntity<Director>(currentDirector, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{directorId}")
    ResponseEntity deleteDirector(@PathVariable Integer directorId){
        this.directorRepository.delete(directorId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    ResponseEntity deleteAllDirectors(){
        this.directorRepository.deleteAll();
        return new ResponseEntity(HttpStatus.OK);
    }
}
