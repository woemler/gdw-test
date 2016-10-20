package me.woemler.gdw;

import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

/**
 * @author woemler
 */
@RepositoryRestResource(collectionResourceRel = "genes", path = "genes")
public interface GeneRepository extends GenericRepository<Gene, Long>, GeneOperations {

	/**
	 * Can be found at
	 * /genes/search/symbol
	 */
	@RestResource(path = "symbol")
	List<Gene> findByGeneSymbol(@Param("geneSymbol") String geneSymbol);

	/**
	 * Will not be found at /genes/search
	 */
	@RestResource(exported = false)
	Gene findOneByEntrezGeneId(@Param("entrezGeneId") Long entrezGeneId);

	@RestResource
	List<Gene> findByAliases(@Param("alias") String alias);
}
