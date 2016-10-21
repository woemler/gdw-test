package me.woemler.gdw.repositories;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import me.woemler.gdw.models.Gene;

/**
 * @author woemler
 */
public class GeneRepositoryImpl implements GeneOperations {

    @Autowired
    private GeneRepository geneRepository;

    @Override
    public List<Gene> guessGene(String keyword) {
        List<Gene> genes = new ArrayList<>();
        genes.addAll(geneRepository.findByGeneSymbol(keyword));
        if (!genes.isEmpty()) return genes;
        genes.addAll(geneRepository.findByAliases(keyword));
        return genes;
    }

}
