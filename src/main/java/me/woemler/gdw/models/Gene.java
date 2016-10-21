package me.woemler.gdw.models;


import javax.persistence.*;
import java.util.List;
import java.util.Map;

/**
 * @author woemler
 */
@Entity
public class Gene implements Model<Long> {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO) private Long id;
	private String geneSymbol;
	private Long entrezGeneId;
	@ElementCollection private List<String> aliases;
	@ElementCollection private Map<String, String> attributes;

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGeneSymbol() {
		return geneSymbol;
	}

	public void setGeneSymbol(String geneSymbol) {
		this.geneSymbol = geneSymbol;
	}

	public Long getEntrezGeneId() {
		return entrezGeneId;
	}

	public void setEntrezGeneId(Long entrezGeneId) {
		this.entrezGeneId = entrezGeneId;
	}

	public List<String> getAliases() {
		return aliases;
	}

	public void setAliases(List<String> aliases) {
		this.aliases = aliases;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
}
