package com.example.salonmanage.controller;


import com.example.salonmanage.Entities.Branch;
import com.example.salonmanage.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;



@RestController
@RequestMapping("/branch")
@CrossOrigin(origins = "*")
public class BranchApi {

    @Autowired
    BranchRepository branchRepo;

    @PostMapping
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<Branch> create(@RequestBody @Valid Branch branch) {
        Branch savedSBranch = branchRepo.save(branch);
        URI productURI = URI.create("/branch/" + savedSBranch.getId());
        return ResponseEntity.created(productURI).body(savedSBranch);
    }

    @GetMapping
    public List<Branch> list() {
        return branchRepo.findAllWithNotRemove();
    }

    @GetMapping("/admin")
    @RolesAllowed("ROLE_ADMIN")
    public List<Branch> listAdmin() {
        return branchRepo.findAllWithNotRemoveAdmin();
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?>  listId(@PathVariable Integer id) {
//        return branchRepo.findById(id).orElse(null);
        if (!branchRepo.existsById(id) || branchRepo.findById(id).get().getStatus() ==3){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            return ResponseEntity.ok().body(branchRepo.findById(id).get());
        }
    }

    @PutMapping("{id}")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<?> update(@PathVariable Integer id,@RequestBody @Valid Branch branch){
        if ( !branchRepo.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            Branch update = branchRepo.findById(id).orElse(null);;

            update.setAddress(branch.getAddress());
            update.setLat(branch.getLat());
            update.setLng(branch.getLng());
            update.setPhone(branch.getPhone());
            update.setStatus(branch.getStatus());
            branchRepo.save(update);
            return ResponseEntity.ok(update);
        }
    }

    @DeleteMapping("{id}")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        if ( !branchRepo.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            Branch update = branchRepo.findById(id).orElse(null);;
            update.setStatus(3);
            branchRepo.save(update);
            return ResponseEntity.ok(id);
        }
    }
}
