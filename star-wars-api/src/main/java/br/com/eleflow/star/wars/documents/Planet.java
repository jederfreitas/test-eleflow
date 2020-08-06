package br.com.eleflow.star.wars.documents;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document
@Getter
@Setter
@NoArgsConstructor
public class Planet {

	@Id
	private ObjectId id;
	@Indexed
	private String name;
	private String climate;
	private String terrain;
	private int filmsQuantity;
}
