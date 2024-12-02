package com.company.services;

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

import java.util.Optional;

@Service
public class ProductService implements IProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;

    public ProductService(ProductRepository productRepository,CategoryRepository categoryRepository,ProductImageRepository productImageRepository){
        this.productRepository=productRepository;
        this.categoryRepository=categoryRepository;
        this.productImageRepository=productImageRepository;
    }

    @Override
    public Product createProduct(ProductForm productForm) throws DataNotFoundException {
        Category category=categoryRepository.findById(productForm.getCategoryId())
                .orElseThrow(()->new DataNotFoundException(
                        "Can not found category with id: "+productForm.getCategoryId()));
        Product product=Product.builder()
                .name(productForm.getName())
                .price(productForm.getPrice())
                .thumbnail(productForm.getThumbnail())
                .category(category)
                .build();
        return productRepository.save(product);
    }

    @Override
    public Page<Product> getAllProducts(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest);
    }


    @Override
    public Product getProductById(Long id) throws DataNotFoundException {
        return productRepository.findById(id)
                .orElseThrow(()->new DataNotFoundException("Cannot find product with id: "+id));
    }



    @Override
    public Product updateProduct(Long id, ProductForm productForm) throws DataNotFoundException {
        Product product=getProductById(id);
        if(product!=null){
            Category category=categoryRepository.findById(productForm.getCategoryId())
                    .orElseThrow(()->new DataNotFoundException(
                            "Can not found category with id: "+productForm.getCategoryId()));
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
    public void deleteProduct(Long id) {
        Optional<Product> optionalProduct=productRepository.findById(id);
        optionalProduct.ifPresent(productRepository::delete);
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public ProductImage createProductImage( ProductImageForm productImageForm) throws DataNotFoundException, InvalidParamException {
        Product product=productRepository.findById(productImageForm.getProductId())
                .orElseThrow(()-> new DataNotFoundException("Can not found product with id: "+productImageForm.getProductId()));
        ProductImage productImage=ProductImage.builder()
                .product(product)
                .imageUrl(productImageForm.getImageUrl())
                .build();
        // Prevent insert more 5 images for product
       int size= productImageRepository.findByProductId(productImageForm.getProductId()).size();
       if(size>=5){
           throw new InvalidParamException("Number of image must be less than 5");
       }
       return productImageRepository.save(productImage);
    }
}
