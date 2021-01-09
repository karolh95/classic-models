package karolh95.classicmodels.controllers;

import karolh95.classicmodels.dto.ProductlineDTO;
import karolh95.classicmodels.services.ProductlineService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(ProductlineController.MAPPING)
@RequiredArgsConstructor
public class ProductlineController {

    public static final String MAPPING = "api/productlines";

    private final ProductlineService productlineService;

    @GetMapping
    public Page<ProductlineDTO> getAllProductLines(Pageable pageable) {
        return productlineService.findAllProductlines(pageable);
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

    @PutMapping(value = "{productLine}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductlineDTO updateProductline(@PathVariable String productLine, @RequestPart ProductlineDTO productline, @RequestPart MultipartFile image) throws IOException {
        productline.setImage(image.getBytes());
        return productlineService.updateProductline(productLine, productline);
    }

    @GetMapping(value = "{productLine}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathVariable String productLine) {
        return productlineService.getImageByProductLine(productLine);
    }

    @DeleteMapping("{productLine}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductline(@PathVariable String productLine) {
        productlineService.deleteProductline(productLine);
    }
}
