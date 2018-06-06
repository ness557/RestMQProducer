package ness.controller;

import ness.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class RestMessageService {

    private ProducerService service;

    @Autowired
    public RestMessageService(ProducerService producerService){
        this.service = producerService;
    }

    @RequestMapping(value = "/write", method = RequestMethod.GET)
    public ResponseEntity write(@RequestParam(value = "message") String message){
        if(service.produce(message) == 1)
            return new ResponseEntity(HttpStatus.ACCEPTED);
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
