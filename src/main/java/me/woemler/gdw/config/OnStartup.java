package me.woemler.gdw.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import me.woemler.gdw.models.Gene;
import me.woemler.gdw.repositories.GeneRepository;

/**
 * @author woemler
 */
@Component
public class OnStartup implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(OnStartup.class);
	
	@Autowired private GeneRepository geneRepository;
	
	@Override 
	public void run(String... strings) throws Exception {
		logger.info("Adding dummy data.");
		addDummyGenes();
	}
	
	private void addDummyGenes(){
		
		Map<String,String> attributes = new HashMap<>();
		Gene gene = new Gene();
		gene.setGeneSymbol("GeneA");
		gene.setEntrezGeneId(1L);
		gene.setAliases(Arrays.asList("ABC"));
		attributes.put("isKinase", "Y");
		gene.setAttributes(attributes);
		geneRepository.save(gene);
		
		gene = new Gene();
		attributes = new HashMap<>();
		gene.setGeneSymbol("GeneB");
		gene.setEntrezGeneId(2L);
		gene.setAliases(Arrays.asList("DEF"));
		attributes.put("isKinase", "N");
		gene.setAttributes(attributes);
		geneRepository.save(gene);

		gene = new Gene();
		attributes = new HashMap<>();
		gene.setGeneSymbol("GeneC");
		gene.setEntrezGeneId(3L);
		gene.setAliases(Arrays.asList("GHI"));
		attributes.put("isKinase", "Y");
		gene.setAttributes(attributes);
		geneRepository.save(gene);

		gene = new Gene();
		attributes = new HashMap<>();
		gene.setGeneSymbol("GeneD");
		gene.setEntrezGeneId(4L);
		gene.setAliases(Arrays.asList("JKL"));
		attributes.put("isKinase", "N");
		gene.setAttributes(attributes);
		geneRepository.save(gene);

		gene = new Gene();
		attributes = new HashMap<>();
		gene.setGeneSymbol("GeneE");
		gene.setEntrezGeneId(5L);
		gene.setAliases(Arrays.asList("MNO"));
		attributes.put("isKinase", "N");
		gene.setAttributes(attributes);
		geneRepository.save(gene);
		
	}
	
}
