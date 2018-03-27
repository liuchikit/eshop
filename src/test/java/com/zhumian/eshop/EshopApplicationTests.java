package com.zhumian.eshop;

import com.zhumian.entity.system.User;
import com.zhumian.service.system.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EshopApplicationTests {

	@Autowired
	private UserService userService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void save(){
		User user = new User();
		user.setAccount("luffy");
		user.setPassword("123");
		userService.save(user);
	}

	@Test
	public void getById(){
		System.out.println(userService.getById(11L));
	}

}
