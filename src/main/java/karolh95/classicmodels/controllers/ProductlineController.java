package karolh95.classicmodels.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import karolh95.classicmodels.dto.ProductlineDTO;
import karolh95.classicmodels.services.ProductlineService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(ProductlineController.MAPPING)
@RequiredArgsConstructor
@Tag(name = "productline", description = "the Productline API")
public class ProductlineController {

    public static final String MAPPING = "api/productlines";

    private final ProductlineService productlineService;

    @Operation(summary = "Get all product lines", description = "Uses pagination ro retrieve products lines")
    @ApiResponse(responseCode = "200", description = "Found products lines")
    @PageableAsQueryParam
    @GetMapping
    public Page<ProductlineDTO> getAllProductLines(@Parameter(hidden = true) Pageable pageable) {
        return productlineService.findAllProductlines(pageable);
    }

    @Operation(summary = "Get a product line by its id")
    @Parameter(name = "productLine", description = "Id of the product line to be obtained. Cannot be empty.")
    @ApiResponse(responseCode = "200", description = "Found product line")
    @ApiResponse(responseCode = "404", description = "Product line not found", content = @Content)
    @GetMapping("{productLine}")
    public ProductlineDTO getProductline(@PathVariable String productLine) {
        return productlineService.findProductlineByProductLine(productLine);
    }

    @Operation(summary = "Add a new product line")
    @ApiResponse(responseCode = "200", description = "Product line has been created successfully.")
    @ApiResponse(responseCode = "400", description = "Product line already exists", content = @Content)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductlineDTO saveProductline(@RequestPart ProductlineDTO productline, @RequestPart MultipartFile image) throws IOException {
        productline.setImage(image.getBytes());
        return productlineService.saveProductline(productline);
    }

    @Operation(summary = "Update an existing product line")
    @Parameter(name = "productLine", description = "Id of the product line to be update. Cannot be empty.")
    @ApiResponse(responseCode = "200", description = "Product line has been updated successfully.")
    @ApiResponse(responseCode = "404", description = "Product line not found", content = @Content)
    @PutMapping(value = "{productLine}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductlineDTO updateProductline(@PathVariable String productLine, @RequestPart ProductlineDTO productline, @RequestPart MultipartFile image) throws IOException {
        productline.setImage(image.getBytes());
        return productlineService.updateProductline(productLine, productline);
    }

    @Operation(summary = "Get an image of the product line")
    @ApiResponse(responseCode = "200", description = "Found image of the product line")
    @ApiResponse(responseCode = "404", description = "Product line not found", content = @Content)
    @Parameter(name = "productLine", description = "Id of the product line")
    @GetMapping(value = "{productLine}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathVariable String productLine) {
        return productlineService.getImageByProductLine(productLine);
    }

    @Operation(summary = "Delete a product line")
    @Parameter(name = "productLine", description = "Id of the product line to be deleted. Cannot be empty.")
    @ApiResponse(responseCode = "204", description = "Product line deleted successfully")
    @ApiResponse(responseCode = "404", description = "Product line not found")
    @DeleteMapping("{productLine}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductline(@PathVariable String productLine) {
        productlineService.deleteProductline(productLine);
    }
}
