package com.app.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.dto.ResponseDTO;
import com.app.pojos.Address;
import com.app.pojos.Product;
import com.app.pojos.SuppliedProduct;
import com.app.service.ISupplierService;


@RestController
@RequestMapping("/supplier")
@CrossOrigin(origins = "http://localhost:3000")
public class SupplierController {
	@Value("${file.upload-dir}")
    private String FILE;
	@Autowired
	private ISupplierService supplierServices;
	
	@GetMapping("/showproducts/{supplierId}")
	public ResponseDTO<?> getAllProductsBySupplier(@PathVariable int supplierId) {
		System.out.println("in getAllProductsBySupplier " + supplierId);
		try {
			return new ResponseDTO<>(HttpStatus.OK, "Product List", supplierServices.getAllProductsBySupplier(supplierId));
		} catch (RuntimeException e) {
			System.out.println("err in getAllProductsBySupplier : "+e);
			return new ResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR, "Product List Not Added", null);
		}
	}
	
	@GetMapping("/productcategoryname/{supplierId}")
	public ResponseDTO<?> getProductCategoryName(@PathVariable int supplierId) {
		System.out.println("in getAllProductsBySupplier " + supplierId);
		try {
			return new ResponseDTO<>(HttpStatus.OK, "Product List", supplierServices.getProductCategoryName(supplierId));
		} catch (RuntimeException e) {
			System.out.println("err in getAllProductsBySupplier : "+e);
			return new ResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR, "Product List Not Added", null);
		}
	}
	
	@GetMapping("/product/{productId}")
	public ResponseDTO<?> getProductById(@PathVariable int productId){
		System.out.println("in getProductById: "+productId);
		try {		
			return new ResponseDTO<>(HttpStatus.OK, "Product Added", supplierServices.getProductById(productId));
		}catch (RuntimeException e) {
			System.out.println("err in getProductById : "+e);
			return new ResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR, "Product Not Added", null);
		}
	}
	
	@PutMapping("/updateproduct/{productId}")
	public ResponseDTO<?> updateProduct(@PathVariable int productId,@RequestBody Product product){
		System.out.println("in update product : "+product);
		try {	
			return new ResponseDTO<>(HttpStatus.OK, "Update Product Success", supplierServices.updateProduct(productId, product));
		}catch (RuntimeException e) {
			System.out.println("err in update product : "+e);
			return new ResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR, "Update  Product Failed", null);
		}
	}
	
//	@PutMapping("/addproductbysupplier/{CategoryName}")
//	public ResponseDTO<?> addProductBySupplier(@PathVariable String CategoryName, @RequestBody SuppliedProduct product) {
//		System.out.println("in add product : "+product);
//		try {
//			return new ResponseDTO<>(HttpStatus.OK, "Added Product Successfully", supplierServices.addProductBySupplier(CategoryName, product));
//		}catch (RuntimeException e) {
//			System.out.println("error in add product : "+e);
//			return new ResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR, "Add Product Failed", null);
//		}
//	}
	@PutMapping("/addproductbysupplier/{CategoryName}")
	public ResponseDTO<?> addProductBySupplier(@PathVariable String CategoryName, @RequestParam String productName ,@RequestParam String description, @RequestParam int price ,@RequestParam int discount ,@RequestParam int qty,@RequestParam String categoryName, @RequestParam MultipartFile  productImage ) 
			throws IOException{
		System.out.println("in add product : "+productImage);
		try {
			File imageFile = new File(FILE+productImage.getOriginalFilename());
	        imageFile.createNewFile();
	        FileOutputStream fos = new FileOutputStream(imageFile);
	        fos.write(productImage.getBytes());
	        fos.close();
	        //String productName, String description, int rating, double price, int discount, double finalPrice,
			//int qty, String productImage
	        double finalPrice=(price-(price*discount)/100);
	        SuppliedProduct p =new SuppliedProduct(productName,description,price,discount,finalPrice,qty,productImage.getOriginalFilename());
			return new ResponseDTO<>(HttpStatus.OK, "Added Product Successfully", supplierServices.addProductBySupplier(CategoryName, p));
		}catch (RuntimeException e) {
			System.out.println("error in add product : "+e);
			return new ResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR, "Add Product Failed", null);
		}
	}
	
	@PutMapping("/addproduct/{CategoryName}")
	public ResponseDTO<?> addProduct(@PathVariable String CategoryName, @RequestBody Product product) {
		System.out.println("in add product : "+product);
		try {
			

			return new ResponseDTO<>(HttpStatus.OK, "Added Product Successfully", supplierServices.addProduct(CategoryName, product));
		}catch (RuntimeException e) {
			System.out.println("error in add product : "+e);
			return new ResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR, "Add Product Failed", null);
		}
	}
	
	@DeleteMapping("/product/{productId}")
	public ResponseDTO<?> deleteItemFromCart(@PathVariable int productId){ 
		System.out.println("in product delete: "+productId);
		try {		
			return new ResponseDTO<>(HttpStatus.OK, "Product deleted ", supplierServices.deleteProduct(productId));
		}catch (RuntimeException e) {
			System.out.println("err in product delete : "+e);
			return new ResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR, "Product not deleted", null);
		}
	}
	
	@GetMapping("/suppliedproducts/{supplierId}")
	public ResponseDTO<?> getSuppliedProducts(@PathVariable int supplierId) {
		System.out.println("in getSuppliedProducts " + supplierId);
		try {
			return new ResponseDTO<>(HttpStatus.OK, "Product List", supplierServices.getSuppliedProducts(supplierId));
		} catch (RuntimeException e) {
			System.out.println("err in getAllProductsBySupplier : "+e);
			return new ResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR, "Product List Not Added", null);
		}
	}
	
	@DeleteMapping("/suppliedproducts/{productId}")
	public ResponseDTO<?> deleteFromSuppliedProducts(@PathVariable int productId){
		System.out.println("in Supplied product delete: "+productId);
		try {		
			return new ResponseDTO<>(HttpStatus.OK, "Product deleted ", supplierServices.deleteFromSuppliedProducts(productId));
		}catch (RuntimeException e) {
			System.out.println("err in Supplied product delete : "+e);
			return new ResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR, "Product not deleted", null);
		}
	}
	
	@PutMapping("/address/{userId}")
	public ResponseDTO<?> editAddress(@PathVariable int userId,@RequestBody Address address){
		System.out.println("in add Address: "+userId + "Address : "+address);
		try {	
			return new ResponseDTO<>(HttpStatus.OK, "Address Changed successfully", supplierServices.addAddress(userId, address));
		}catch (RuntimeException e) {
			System.out.println("err in add address : "+e);
			return new ResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR, "Address Changed Failed", null);
		}
	}
}
