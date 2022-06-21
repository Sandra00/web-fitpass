package dao;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import beans.PromoCode;
import util.LocalDateTimeDeserializer;
import util.LocalDateTimeSerializer;
import util.PersonalConfig;

public class PromoCodeDAO {
	private List<PromoCode> promoCodes;
	private String pathToFile = PersonalConfig.PROJECT_FOLDER_PATH + "\\WebContent\\promo_codes.json";
	
	public PromoCodeDAO() {
		System.out.println("I'M HERE!");
		promoCodes = new ArrayList<PromoCode>();
		/*
		LocalDateTime expDate = LocalDateTime.of(2022, 8, 31, 0, 0);
		PromoCode prc1 = new PromoCode("PROMO1", expDate, 100, 5);
		promoCodes.add(prc1);*/
		load();
	}
	
	
	
	private void load() {
		SimpleModule localDateTimeSerialization = new SimpleModule();
		localDateTimeSerialization.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
		localDateTimeSerialization.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(localDateTimeSerialization);
		try {
			promoCodes = new ArrayList<>(Arrays.asList(mapper.readValue(Paths.get(pathToFile).toFile(), PromoCode[].class)));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void save() {
		SimpleModule localDateTimeSerialization = new SimpleModule();
		localDateTimeSerialization.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
		localDateTimeSerialization.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(localDateTimeSerialization);
		try {
			mapper.writeValue(Paths.get(pathToFile).toFile(), promoCodes);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
