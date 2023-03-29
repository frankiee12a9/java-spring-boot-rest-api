package com.github.frankiie.springboot.domains.collection.payload;

import javax.validation.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import com.github.frankiie.springboot.utils.JSON;

import lombok.Data;

@Data
public class CreateCollectionProps {
    @NotEmpty private String title;
    private String description;  
    private MultipartFile file;  
    
    public MultipartFile getFile() {
      return this.file;
    }

    public void setFile(MultipartFile file) {
      this.file = file;
    }

    @Override
    public String toString() {
      return JSON.stringify(this);
    }

}
