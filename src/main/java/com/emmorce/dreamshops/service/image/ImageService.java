package com.emmorce.dreamshops.service.image;

import com.emmorce.dreamshops.dto.ImageDto;
import com.emmorce.dreamshops.model.Image;
import com.emmorce.dreamshops.model.Product;
import com.emmorce.dreamshops.repositoty.ImageRepository;
import com.emmorce.dreamshops.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

    private final ImageRepository imageRepository;
    private IProductService productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository
                .findById(id)
                .orElseThrow(() -> new ResourceAccessException("No image found for id " + id));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete,
                () -> {
                    throw new ResourceAccessException("No image found for id " + id);
                });
    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);
        List<ImageDto> savedImageDtos = new ArrayList<>();

        for (MultipartFile file : files) {

            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadPath = "/api/v1/images/download/";
                String downloadUrl = buildDownloadPath + image.getId();
                image.setDownloadUrl(downloadUrl);

                Image savedImage = imageRepository.save(image);

                savedImage.setDownloadUrl(buildDownloadPath + savedImage.getId());
                imageRepository.save(image);

                ImageDto imageDto = new ImageDto();
                imageDto.setImageId(savedImage.getId());
                imageDto.setImageName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());

                savedImageDtos.add(imageDto);

            } catch (IOException | SQLException e) {

                throw new RuntimeException(e.getMessage());
            }
        }

        return savedImageDtos;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        // Get the existing image
        Image image = getImageById(imageId);
        // Save the new image
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

        imageRepository.save(image);
    }
}
