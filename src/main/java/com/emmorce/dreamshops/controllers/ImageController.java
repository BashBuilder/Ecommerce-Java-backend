package com.emmorce.dreamshops.controllers;

import com.emmorce.dreamshops.dto.ImageDto;
import com.emmorce.dreamshops.model.Image;
import com.emmorce.dreamshops.response.ApiResponse;
import com.emmorce.dreamshops.service.image.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {
    private final IImageService imageService;


    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files, @RequestParam Long productId){
        try {
            List<ImageDto> imageDtos = imageService.saveImage(files, productId);
            return  ResponseEntity
                    .ok(new ApiResponse("Images uploaded successfully", imageDtos));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Upload failed", e.getMessage()));
        }

    }

    @GetMapping("/download/{imageId}")
    public ResponseEntity<Resource> downloadImage (@PathVariable Long imageId){
        Image image = imageService.getImageById(imageId);
        try {
            ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));
            return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename:\"" + image.getFileName() + "\"")
                .body(resource);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
//    just some commit message
}
