package com.company.services;

import com.company.constant.Constant;
import com.company.dtos.ProductDTO;
import com.company.exceptions.DataNotFoundException;
import com.company.exceptions.InvalidParamException;
import com.company.forms.ProductForm;
import com.company.forms.ProductImageForm;
import com.company.models.Category;
import com.company.models.Product;
import com.company.models.ProductImage;
import com.company.repositories.CategoryRepository;
import com.company.repositories.ProductImageRepository;
import com.company.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, ProductImageRepository productImageRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productImageRepository = productImageRepository;
    }

    @Override
    public Product createProduct(ProductForm productForm) throws DataNotFoundException {
        Category category = categoryRepository.findById(productForm.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Can not found category with id: " + productForm.getCategoryId()));
        Product product = Product.builder()
                .name(productForm.getName())
                .price(productForm.getPrice())
                .description(productForm.getDescription())
                .thumbnail(productForm.getThumbnail())
                .category(category)
                .build();
        return productRepository.save(product);
    }

    @Override
    public Page<ProductDTO> getAllProducts(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest).map(ProductDTO::fromProduct);
    }

    @Override
    public Product getProductById(Long id) throws DataNotFoundException {
        return productRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find product with id: " + id));
    }


    @Override
    public Product updateProduct(Long id, ProductForm productForm) throws DataNotFoundException {
        Product product = getProductById(id);
        if (product != null) {
            Category category = categoryRepository.findById(productForm.getCategoryId())
                    .orElseThrow(() -> new DataNotFoundException(
                            "Can not found category with id: " + productForm.getCategoryId()));
            product.setName(productForm.getName());
            product.setCategory(category);
            product.setPrice(productForm.getPrice());
            product.setDescription(productForm.getDescription());
            product.setThumbnail(productForm.getThumbnail());
            return productRepository.save(product);
        }
        return null;
    }

    @Override
    public void deleteProductById(Long id)throws DataNotFoundException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        optionalProduct.ifPresent(productRepository::delete);
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public ProductImage createProductImage(String fileNames, MultipartFile file, ProductImageForm productImageForm) throws DataNotFoundException, InvalidParamException, IOException {
        Product product = productRepository.findById(productImageForm.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Can not found product with id: " + productImageForm.getProductId()));
        ProductImage productImage = ProductImage.builder()
                .product(product)
                .imageUrl(productImageForm.getImageUrl())
                .build();
        // Prevent insert more 5 images for product
        int size = productImageRepository.findByProductId(productImageForm.getProductId()).size();
        if (size >= Constant.MAXIMUM_IMAGES) {
            throw new InvalidParamException("Number of image must be less than " + Constant.MAXIMUM_IMAGES);
        }
        storeFile(fileNames, file);
        return productImageRepository.save(productImage);
    }

    private void storeFile(String fileNames, MultipartFile file) throws IOException {
        // tạo ra một đối tượng đại diện folder uploads -> nơi chứa image
        Path uploadDir = Paths.get("uploads");
        // nếu chưa có folder đó thì tạo
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        // tạo đường dẫn đầy đủ từ folder tới file image mình mới generate ra
        Path destination = Paths.get(uploadDir.toString(), fileNames);
        // file.getInputStream(): Lấy dữ liệu đầu vào từ tệp được tải lên.
        // Files.copy(): Sao chép nội dung của tệp từ luồng đầu vào (InputStream) đến đường dẫn đích (destination).
        // StandardCopyOption.REPLACE_EXISTING: Nếu một tệp với cùng tên đã tồn tại, tệp mới sẽ ghi đè lên tệp cũ.
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
    }

}
