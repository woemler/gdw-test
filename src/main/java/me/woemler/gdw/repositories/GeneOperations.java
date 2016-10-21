package me.woemler.gdw.repositories;

import java.util.List;

import me.woemler.gdw.models.Gene;

/**
 * @author woemler
 */
public interface GeneOperations {
    List<Gene> guessGene(String keyword);
}
