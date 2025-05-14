package com.example.salonmanage.service;

import com.example.salonmanage.File.FileStorageException;
import com.example.salonmanage.File.FileStorageProperties;
import com.example.salonmanage.File.MyFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public void storeFile(InputStream file, String fileName) {
        try {

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file, targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public void removeFile( String fileName) {
        // Resolve the file path
        Path targetLocation = this.fileStorageLocation.resolve(fileName);

        // Check if the file exists before attempting to delete
        if (Files.exists(targetLocation)) {
            try {
                // Delete the file from the target location
                Files.delete(targetLocation);
                System.out.println("File removed successfully.");
            } catch (IOException e) {
                System.out.println("Error while deleting the file: " + e.getMessage());
                // You may throw a specific exception here or handle the error accordingly
            }
        } else {
            System.out.println("File not found: " + fileName);
            // Handle the case when the file does not exist
            // For example, you can throw a custom exception or log the error.
        }
    }
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }
}
