package com.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.pojos.Role;
import com.app.pojos.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	@Query("select u from User u where u.email=:em and u.password=:pass")
	User authenticateUser(@Param("em")String em, @Param("pass")String pass);

	@Query("select u.id from User u where u.role=:rl")
	List<Integer> getAllDeliveryBoy(@Param("rl")Role rl);
	@Query(value = "select count(role) from users where role=\"SUPPLIER\";", nativeQuery = true)
	Integer findAllcountSupplier();
	
	@Query(value = "select count(role) from users where role=\"CUSTOMER\";", nativeQuery = true)
	Integer findAllcountCustomer();
	
	@Query(value = "select count(role) from users where role=\"DELIVERY_BOY\";", nativeQuery = true)
	Integer findAllcountDeliveryBoy();
	
	@Query(value = "select count(order_delivery_status) from orders where order_delivery_status=\"PENDING\";", nativeQuery = true)
	Integer findAllcountPendingOrders();
	
	@Query(value = "select count(order_delivery_status) from orders where order_delivery_status=\"DELIVERED\";", nativeQuery = true)
	Integer findAllcountDeliveredOrders();
	
	List<User> findByRole(Role role);
}
