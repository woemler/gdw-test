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
	private static final String ROOT_URL = "/api/genes";
	
	@Before
	public void setup(){
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void findAllTest() throws Exception {

		mockMvc.perform(get(ROOT_URL))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasKey("_embedded")))
				.andExpect(jsonPath("$._embedded", hasKey("genes")))
				.andExpect(jsonPath("$._embedded.genes", hasSize(5)))
				.andExpect(jsonPath("$._embedded.genes[0]", hasKey("geneSymbol")))
				.andExpect(jsonPath("$._embedded.genes[0].geneSymbol", is("GeneA")));
	}

	@Test
	public void findByStringAttribute() throws Exception {
		mockMvc.perform(get(ROOT_URL + "?geneSymbol=GeneB"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasKey("_embedded")))
				.andExpect(jsonPath("$._embedded", hasKey("genes")))
				.andExpect(jsonPath("$._embedded.genes", hasSize(1)))
				.andExpect(jsonPath("$._embedded.genes[0]", hasKey("geneSymbol")))
				.andExpect(jsonPath("$._embedded.genes[0].geneSymbol", is("GeneB")));
	}
	
	@Test
	public void findByListAttribute() throws Exception {
		mockMvc.perform(get("/api/genes?aliases=DEF"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasKey("_embedded")))
				.andExpect(jsonPath("$._embedded", hasKey("genes")))
				.andExpect(jsonPath("$._embedded.genes", hasSize(1)))
				.andExpect(jsonPath("$._embedded.genes[0]", hasKey("geneSymbol")))
				.andExpect(jsonPath("$._embedded.genes[0].geneSymbol", is("GeneB")));
	}
	
	@Test
	public void findByMapAttribute() throws Exception {
				mockMvc.perform(get("/api/genes?attributes.isKinase=Y"))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$", hasKey("_embedded")))
						.andExpect(jsonPath("$._embedded", hasKey("genes")))
						.andExpect(jsonPath("$._embedded.genes", hasSize(2)))
						.andExpect(jsonPath("$._embedded.genes[1]", hasKey("geneSymbol")))
						.andExpect(jsonPath("$._embedded.genes[1].geneSymbol", is("GeneC")));
	}

	@Test
	public void findByStringCaseInsensitive() throws Exception {
		mockMvc.perform(get(ROOT_URL + "?geneSymbol=gened"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasKey("_embedded")))
				.andExpect(jsonPath("$._embedded", hasKey("genes")))
				.andExpect(jsonPath("$._embedded.genes", hasSize(1)))
				.andExpect(jsonPath("$._embedded.genes[0]", hasKey("geneSymbol")))
				.andExpect(jsonPath("$._embedded.genes[0].geneSymbol", is("GeneD")));
	}
	
	@Test
	public void findByStringEndsWith() throws Exception {
		mockMvc.perform(get(ROOT_URL + "?geneSymbolEndsWith=D"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasKey("_embedded")))
				.andExpect(jsonPath("$._embedded", hasKey("genes")))
				.andExpect(jsonPath("$._embedded.genes", hasSize(1)))
				.andExpect(jsonPath("$._embedded.genes[0]", hasKey("geneSymbol")))
				.andExpect(jsonPath("$._embedded.genes[0].geneSymbol", is("GeneD")));
	}

	@Test
	public void findByStringStartsWith() throws Exception {
		mockMvc.perform(get(ROOT_URL + "?geneSymbolStartsWith=Gene"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasKey("_embedded")))
				.andExpect(jsonPath("$._embedded", hasKey("genes")))
				.andExpect(jsonPath("$._embedded.genes", hasSize(5)))
				.andExpect(jsonPath("$._embedded.genes[0]", hasKey("geneSymbol")))
				.andExpect(jsonPath("$._embedded.genes[0].geneSymbol", is("GeneA")));
	}
	
	// TODO: Crud test
	// TODO: Dynamic querydsl queries
	// TODO: Field filtering
	// TODO: text output
	
}
