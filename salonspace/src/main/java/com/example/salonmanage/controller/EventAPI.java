package com.example.salonmanage.controller;

import com.example.salonmanage.Entities.event;
import com.example.salonmanage.repository.EventRepository;
import com.example.salonmanage.service.FileStorageService;
import com.example.salonmanage.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/event")
@CrossOrigin(origins = "*")
public class EventAPI {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private ServiceService serviceService;

    @PostMapping
//    @RolesAllowed("ROLE_ADMIN")
    public void create(@RequestBody @Valid event event) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        String dateTimeString = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String day = event.getDate().substring(8,10);
        String month =event.getDate().substring(5,7);
        String year =event.getDate().substring(0,4);
        String date= day+"/"+month+"/"+year;

        event.setDate(date);
        String[] strings = event.getImg().split(",");
        String extension;
        switch (strings[0]) {//check image's extension
            case "data:image/jpeg;base64":
                extension = "jpeg";
                break;
            case "data:image/png;base64":
                extension = "png";
                break;
            default://should write cases for more images types
                extension = "jpg";
                break;
        }
        byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
        String uniqueImageName = UUID.randomUUID().toString();
        String pathfile = uniqueImageName + dateTimeString + "." + extension;
        fileStorageService.storeFile(new ByteArrayInputStream(data), pathfile);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/img/")
                .path(pathfile)
                .toUriString();
        event.setImg(fileDownloadUri);
        eventRepository.save(event);
    }

    @GetMapping("/detail/{id}")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<?> listId(@PathVariable Integer id) {
        if (!eventRepository.existsById(id) || eventRepository.findById(id).get().getStatus() == 3) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok().body(eventRepository.findById(id).get());
        }
    }

    @PutMapping("{id}")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody event event) {
        if (eventRepository.existsById(id) == true) {
            String date ;
            event newEvent = eventRepository.findById(id).get();
            System.out.println(event.getDate());
            if(event.getDate().equals(newEvent.getDate())==false){
            String day = event.getDate().substring(8,10);
            String month =event.getDate().substring(5,7);
            String year =event.getDate().substring(0,4);
           date= day+"/"+month+"/"+year;
            System.out.println(date);}
            else {
                date=newEvent.getDate();
            }
            if (!newEvent.getImg().equals(event.getImg())) {
                serviceService.removeImg(event.getImg());
                String pathfile = serviceService.saveImg(event.getImg());
                String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/img/")
                        .path(pathfile)
                        .toUriString();
                newEvent.setImg(fileDownloadUri);
            }
            newEvent.setContent(event.getContent());
            newEvent.setDiscount(event.getDiscount());
            newEvent.setStatus(event.getStatus());
            newEvent.setDate(date);
            event savedEvent = eventRepository.save(newEvent);
            URI productURI = URI.create("/event/" + savedEvent.getId());
            return ResponseEntity.created(productURI).body(savedEvent);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("{id}")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        if (eventRepository.existsById(id) == false) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            event update = eventRepository.findById(id).get();
            update.setStatus(3);
            eventRepository.save(update);
            return ResponseEntity.ok(id);
        }
    }

    @GetMapping()
    @RolesAllowed("ROLE_ADMIN")
    public List<event> get() {
        return eventRepository.getList();
    }
}
