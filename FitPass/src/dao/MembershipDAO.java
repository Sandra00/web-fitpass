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
		load();
	}
	
	public Membership findById(String membershipId) {
		for(Membership membership : memberships) {
			if(membership.getId().equals(membershipId) && !membership.isDeleted()) {
				return membership;
			}
		}
		return null;
	}
	
	public boolean newMembership(Membership membership) {
		if (exists(membership.getId())) {
			return false;
		}
		membership.setDeleted(false);
		boolean isSaved = memberships.add(membership);
		save();
		return isSaved;
	}
	
	public List<Membership> findAll() {
		List<Membership> existingMemberships = new ArrayList<Membership>();
		for (Membership membership : memberships) {
			if(!membership.isDeleted()) {
				existingMemberships.add(membership);
			}
		}
		return existingMemberships;
	}
	
	private boolean exists(String id) {
		for (Membership membership : memberships) {
			if(membership.getId().equals(id) && !membership.isDeleted()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean delete(String id) {
		if(findById(id) != null) {
			findById(id).setDeleted(true);
			return true;
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
