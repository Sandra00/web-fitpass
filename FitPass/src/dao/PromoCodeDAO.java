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
		promoCodes = new ArrayList<PromoCode>();
		load();
	}
	
	public boolean newPromoCode(PromoCode promoCode) {
		if (exists(promoCode.getCode())) {
			return false;
		}
		boolean isSaved = promoCodes.add(promoCode);
		save();
		return isSaved;
	}
	
	public List<PromoCode> findAll() {
		return promoCodes;
	}
	
	private boolean exists(String code) {
		for (PromoCode promoCode : promoCodes) {
			if(promoCode.getCode().equals(code)) {
				return true;
			}
		}
		return false;
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
