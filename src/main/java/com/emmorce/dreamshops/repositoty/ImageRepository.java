package com.emmorce.dreamshops.repositoty;

import com.emmorce.dreamshops.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
