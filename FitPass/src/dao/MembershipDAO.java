package dao;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Membership;
import util.PersonalConfig;

public class MembershipDAO {
	private List<Membership> memberships;
	private String pathToFile = PersonalConfig.PROJECT_FOLDER_PATH + "\\WebContent\\memberships.json";
	
	public MembershipDAO(){
		/*System.out.println("IM HERE!");
		LocalDateTime transactionDate = LocalDateTime.of(2022, 7, 26, 15, 0);
		LocalDateTime dueDate = LocalDateTime.of(2022, 8, 26, 15, 0);
		Membership membership1 = new Membership("asdasdsd", MembershipType.MONTHLY, transactionDate, dueDate, 1500, MembershipStatus.ACTIVE, 20);
		memberships = new ArrayList<Membership>();
		memberships.add(membership1);
		save();*/
		load();
	}
	
	public Membership findById(String membershipId) {
		for(Membership membership : memberships) {
			if(membership.getId().equals(membershipId)) {
				return membership;
			}
		}
		return null;
	}
	
	public boolean newMembership(Membership promoCode) {
		if (exists(promoCode.getId())) {
			return false;
		}
		boolean isSaved = memberships.add(promoCode);
		save();
		return isSaved;
	}
	
	public List<Membership> findAll() {
		return memberships;
	}
	
	private boolean exists(String id) {
		for (Membership membership : memberships) {
			if(membership.getId().equals(id)) {
				return true;
			}
		}
		return false;
	}
	
	private void load() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			memberships = new ArrayList<>(Arrays.asList(mapper.readValue(Paths.get(pathToFile).toFile(), Membership[].class)));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void save() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(Paths.get(pathToFile).toFile(), memberships);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
