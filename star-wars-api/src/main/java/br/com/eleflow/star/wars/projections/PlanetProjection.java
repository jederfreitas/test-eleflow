package br.com.eleflow.star.wars.projections;

public interface PlanetProjection {

	String getId();
	
	String getName();

	String getClimate();

	String getTerrain();

	int getFilmsQuantity();
}
