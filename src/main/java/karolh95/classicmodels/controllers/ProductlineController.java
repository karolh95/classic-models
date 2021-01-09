package karolh95.classicmodels.controllers;

import karolh95.classicmodels.dto.ProductlineDTO;
import karolh95.classicmodels.services.ProductlineService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(ProductlineController.MAPPING)
@RequiredArgsConstructor
public class ProductlineController {

    public static final String MAPPING = "api/productlines";

    private final ProductlineService productlineService;

    @GetMapping
    public Page<ProductlineDTO> getAllProductLines(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return productlineService.findAllProductlines(page, size);
    }

    @GetMapping("{productLine}")
    public ProductlineDTO getProductline(@PathVariable String productLine) {
        return productlineService.findProductlineByProductLine(productLine);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductlineDTO saveProductline(@RequestPart ProductlineDTO productline, @RequestPart MultipartFile image) throws IOException {
        productline.setImage(image.getBytes());
        return productlineService.saveProductline(productline);
    }

    @GetMapping(value = "{productLine}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathVariable String productLine){
        return productlineService.getImageByProductLine(productLine);
    }
}
