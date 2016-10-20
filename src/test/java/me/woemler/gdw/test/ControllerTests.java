package me.woemler.gdw.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.LinkDiscoverers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author woemler
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ControllerTests {
	
	@Autowired private WebApplicationContext context;
	@Autowired LinkDiscoverers links;
	
	private MockMvc mockMvc; 
	
	@Before
	public void setup(){
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void geneControllerTest() throws Exception {

		mockMvc.perform(get("/api/genes"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasKey("_embedded")))
				.andExpect(jsonPath("$._embedded", hasKey("genes")))
				.andExpect(jsonPath("$._embedded.genes", hasSize(5)))
				.andExpect(jsonPath("$._embedded.genes[0]", hasKey("geneSymbol")))
				.andExpect(jsonPath("$._embedded.genes[0].geneSymbol", is("GeneA")));

		mockMvc.perform(get("/api/genes?geneSymbol=GeneB"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasKey("_embedded")))
				.andExpect(jsonPath("$._embedded", hasKey("genes")))
				.andExpect(jsonPath("$._embedded.genes", hasSize(1)))
				.andExpect(jsonPath("$._embedded.genes[0]", hasKey("geneSymbol")))
				.andExpect(jsonPath("$._embedded.genes[0].geneSymbol", is("GeneB")));

		mockMvc.perform(get("/api/genes?aliases=DEF"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasKey("_embedded")))
				.andExpect(jsonPath("$._embedded", hasKey("genes")))
				.andExpect(jsonPath("$._embedded.genes", hasSize(1)))
				.andExpect(jsonPath("$._embedded.genes[0]", hasKey("geneSymbol")))
				.andExpect(jsonPath("$._embedded.genes[0].geneSymbol", is("GeneB")));

//		mockMvc.perform(get("/api/genes?attributes.isKinase=Y"))
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("$", hasKey("_embedded")))
//				.andExpect(jsonPath("$._embedded", hasKey("genes")))
//				.andExpect(jsonPath("$._embedded.genes", hasSize(2)))
//				.andExpect(jsonPath("$._embedded.genes[1]", hasKey("geneSymbol")))
//				.andExpect(jsonPath("$._embedded.genes[1].geneSymbol", is("GeneC")));

	}
	
	// TODO: Crud test
	// TODO: Dynamic querydsl queries
	// TODO: Field filtering
	// TODO: text output
	
}
