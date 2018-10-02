package model;

import java.math.BigDecimal;

public class Position {
	
	private String key;
	private String nome;
	private String tipo;
	private String unidade;
	private Integer id;
	private BigDecimal value;
	private Double minValue;
	private Double maxValue;
	private BigDecimal milliTime;	 
	
	public Position() {		
	}
	
	public Position(String key, String nome, String tipo, String unidade, int id, BigDecimal value, Double minValue, Double maxValue, BigDecimal milliTime) {
		
		this.key = key;
		this.nome = nome;
		this.tipo = tipo;
		this.id = id;
		this.unidade = unidade;				
		this.value = value;
		this.maxValue = maxValue;
		this.minValue = minValue;
		this.milliTime = milliTime;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		Position other = (Position) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	public Double getMinValue() {
		return minValue;
	}
	public void setMinValue(Double minValue) {
		this.minValue = minValue;
	}
	public Double getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(Double maxValue) {
		this.maxValue = maxValue;
	}
	public String getUnidade() {
		return unidade;
	}
	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
	public BigDecimal getMilliTime() {
		return milliTime;
	}
	public void setMilliTime(BigDecimal milliTime) {
		this.milliTime = milliTime;
	}
}
