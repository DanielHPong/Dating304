package pkg304models.models;

import java.sql.*;

import pkg304data.Personality;
import pkg304models.GenericModel;

public class PersonalityModel extends GenericModel<Personality> {
	public PersonalityModel(Connection con) {
		super(con);
	}
}
