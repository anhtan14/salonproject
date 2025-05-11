package com.example.salonmanage.controller;

import com.example.salonmanage.DTO.ServiceDTO;
import com.example.salonmanage.Entities.ImgDetail;
import com.example.salonmanage.Entities.Service;
import com.example.salonmanage.repository.ImgDetailReponsitory;
import com.example.salonmanage.repository.ServiceRepository;
import com.example.salonmanage.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/service")
@CrossOrigin(origins = "*")
public class ServiceApi {
    @Autowired
    private ServiceRepository serviceRepo;

    @Autowired
    private ServiceService serviceService;


    @Autowired
    ImgDetailReponsitory imgDetailReponsitory;

    @PostMapping
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<?> create(@RequestBody @Valid ServiceDTO service) {
        String pathfile = serviceService.saveImg(service.getImg());
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/img/")
                .path(pathfile)
                .toUriString();
        Service newService = new Service();
        newService.setName(service.getName());
        newService.setPrice(service.getPrice());
        newService.setImg(fileDownloadUri);
        newService.setDescription(service.getDescription());
        newService.setStatus(1);
        Service savedService = serviceRepo.save(newService);
        String pathfileImg;
        String fileDownloadUriImg;
        String idService = String.valueOf(savedService.getId());
        for (int i = 0; i < service.getImgList().length; i++) {
            pathfileImg = serviceService.saveImgDetail(service.getImgList()[i], idService, i);
            fileDownloadUriImg = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/img/")
                    .path(pathfileImg)
                    .toUriString();
            ImgDetail imgDetail = new ImgDetail(fileDownloadUriImg, savedService);
            imgDetailReponsitory.save(imgDetail);
        }
        URI productURI = URI.create("/service/" + savedService.getId());
        return ResponseEntity.created(productURI).body(savedService);
    }

    @GetMapping
    public List<Service> list() {
        return serviceRepo.findAllWithNotRemove();
    }
    @GetMapping("/admin")
    @RolesAllowed("ROLE_ADMIN")
    public List<Service> listAdmin() {
        return serviceRepo.findAllWithNotRemoveAdmin();
    }

    @GetMapping("/search")
    public List<Service> list(@RequestParam(required = false) String search) {
        if (search != null && !search.isEmpty()) {
            return serviceRepo.findAllWithNotRemoveByName(search);
        } else {
            return serviceRepo.findAllWithNotRemove();
        }
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> listId(@PathVariable Integer id) {
//        return branchRepo.findById(id).orElse(null);
        if (!serviceRepo.existsById(id) || serviceRepo.findById(id).get().getStatus() ==3) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok().body(serviceRepo.findById(id).get());
        }
    }

    @PutMapping("{id}")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody ServiceDTO service) {
        if (serviceRepo.existsById(id) == true ) {
            Service newService = serviceRepo.findById(id).get();
            if (!newService.getImg().equals(service.getImg())) {
                serviceService.removeImg(newService.getImg());
                String pathfile = serviceService.saveImg(service.getImg());
                String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/img/")
                        .path(pathfile)
                        .toUriString();
                newService.setImg(fileDownloadUri);
            }
            serviceService.updateImgDetail(service.getImgList(), newService);
            newService.setName(service.getName());
            newService.setPrice(service.getPrice());
            newService.setDescription(service.getDescription());
            newService.setStatus(service.getStatus());
            List<String> listNameImg = serviceService.updateImgDetail(service.getImgList(), newService);
            String fileDownloadUriImg;
            for (int i = 0; i < listNameImg.size(); i++) {
                fileDownloadUriImg = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/img/")
                        .path(listNameImg.get(i))
                        .toUriString();
                ImgDetail imgDetail = new ImgDetail(fileDownloadUriImg, newService);
                imgDetailReponsitory.save(imgDetail);
            }
            Service savedService = serviceRepo.save(newService);
            URI productURI = URI.create("/service/" + savedService.getId());
            return ResponseEntity.created(productURI).body(savedService);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("{id}")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        if (serviceRepo.existsById(id) == false) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            Service update = serviceRepo.findById(id).get();
            update.setStatus(3);
            serviceRepo.save(update);
            return ResponseEntity.ok(id);
        }
    }

    @GetMapping("/most-booked")
    public  List<Object[]> getMostBookedServices() {
        return serviceRepo.findMostBookedServices();
    }
}