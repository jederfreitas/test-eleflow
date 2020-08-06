package br.com.eleflow.star.wars.api;

import org.bson.types.ObjectId;

import br.com.eleflow.star.wars.dto.PlanetDto;
import br.com.eleflow.star.wars.projections.PlanetProjection;

public class TestUtils {

	private TestUtils() {

	}

	public static PlanetProjection getPlanetProjection(PlanetDto planetDto) {
		return new PlanetProjection() {

			@Override
			public String getId() {
				return new ObjectId().toString();
			}

			@Override
			public String getName() {
				return planetDto.getName();
			}

			@Override
			public String getClimate() {
				return planetDto.getClimate();
			}

			@Override
			public String getTerrain() {
				return planetDto.getTerrain();
			}

			@Override
			public int getFilmsQuantity() {
				return planetDto.getFilmsQuantity();
			}

		};
	}

}
