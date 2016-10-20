package me.woemler.gdw.test;

import com.querydsl.core.types.dsl.*;
import me.woemler.gdw.Gene;
import me.woemler.gdw.GeneRepository;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;

/**
 * @author woemler
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoryTests {
	
	@Autowired private GeneRepository geneRepository;
	
	@Test
	public void geneRepositoryTest() throws Exception {
		
		Assert.notNull(geneRepository);
		Assert.isTrue(geneRepository.count() == 5);
		
		List<Gene> genes = geneRepository.findByGeneSymbol("GeneB");
		Assert.notNull(genes);
		Assert.notEmpty(genes);
		Assert.isTrue(genes.size() == 1);
		Gene gene = genes.get(0);
		Assert.notNull(gene);
		Assert.notNull(gene.getId());
		Assert.isTrue("GeneB".equals(gene.getGeneSymbol()));
		Assert.isTrue(gene.getEntrezGeneId() == 2L);

		genes = geneRepository.guessGene("GeneC");
		Assert.notNull(genes);
		Assert.notEmpty(genes);
		Assert.isTrue(genes.size() == 1);
		Assert.isTrue(genes.get(0).getGeneSymbol().equals("GeneC"));

		gene = new Gene();
		gene.setGeneSymbol("GeneX");
		gene.setEntrezGeneId(100L);
		gene.setAliases(Collections.singletonList("XYZ"));
		gene.setAttributes(Collections.singletonMap("isKinase", "Y"));
		geneRepository.save(gene);
		Assert.isTrue(geneRepository.count() == 6);
		gene = geneRepository.findOneByEntrezGeneId(100L);
		Assert.notNull(gene);
		Assert.notNull(gene.getId());
		Assert.isTrue("GeneX".equals(gene.getGeneSymbol()));
		Long id = gene.getId();

		gene.setGeneSymbol("GeneY");
		geneRepository.save(gene);
		genes = geneRepository.findByGeneSymbol("GeneX");
		Assert.notNull(genes);
		Assert.isTrue(genes.size() == 0);
		gene = geneRepository.findOne(id);
		Assert.notNull(gene);
		Assert.isTrue(gene.getId().equals(id));
		Assert.isTrue("GeneY".equals(gene.getGeneSymbol()));

		geneRepository.delete(id);
		Assert.isTrue(geneRepository.count() == 5);
		gene = geneRepository.findOne(id);
		Assert.isNull(gene);

	}

	@Test
	public void queryDslPredicateTest() throws Exception {

		Path<Gene> genePath = Expressions.path(Gene.class, "gene");
		Path<String> symbolPath = Expressions.path(String.class, genePath, "geneSymbol");
		Expression<String> symbolConstant = Expressions.constant("GeneD");
		Predicate predicate = Expressions.predicate(Ops.EQ, symbolPath, symbolConstant);
		List<Gene> genes = (List<Gene>) geneRepository.findAll(predicate);
		Assert.notNull(genes);
		Assert.notEmpty(genes);
		Assert.isTrue(genes.size() == 1);
		Assert.isTrue(genes.get(0).getGeneSymbol().equals("GeneD"));

		PathBuilder<Gene> pathBuilder = new PathBuilder<>(Gene.class, "gene");
		ListPath<String, PathBuilder<String>> aliasPath = pathBuilder.getList("aliases", String.class);
		Expression<String> aliasConstant = Expressions.constant("ABC");
		predicate = Expressions.predicate(Ops.EQ, aliasPath.any(), aliasConstant);
		genes = (List<Gene>) geneRepository.findAll(predicate);
		Assert.notNull(genes);
		Assert.notEmpty(genes);
		Assert.isTrue(genes.size() == 1);
		Assert.isTrue(genes.get(0).getGeneSymbol().equals("GeneA"));

		MapPath<String, String, PathBuilder<String>> attributePath
				= pathBuilder.getMap("attributes", String.class, String.class);
		Expression<String> attributeConstant = Expressions.constant("Y");
		predicate = Expressions.predicate(Ops.EQ, attributePath.get("isKinase"), attributeConstant);
		genes = (List<Gene>) geneRepository.findAll(predicate);
		Assert.notNull(genes);
		Assert.notEmpty(genes);
		Assert.isTrue(genes.size() == 2);
		Assert.isTrue(genes.get(0).getGeneSymbol().equals("GeneA"));

	}

	
}
