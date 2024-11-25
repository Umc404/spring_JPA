package com.ezen.boot_JPA;

import com.ezen.boot_JPA.dto.BoardDTO;
import com.ezen.boot_JPA.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = BootJpaApplication.class)
class BootJpaApplicationTests {

	@Autowired
	private BoardService boardService;

	@Test
	void contextLoads() {
		for(int i=0; i<300; i++) {
			BoardDTO bdto = BoardDTO.builder()
					.title("test title " + i)
					.writer("tester " + ((int)(Math.random()*50)+1))
					.content("Test Content " + i)
					.build();

//			boardService.insert(bdto);
		}
	}

}
