package dao;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Comment;
import util.PersonalConfig;

public class CommentDAO {
	private List<Comment> comments = new ArrayList<Comment>();
	private String pathToFile = PersonalConfig.PROJECT_FOLDER_PATH + "\\WebContent\\comments.json";
	
	public CommentDAO() {
		load();
	}
	
	public void addComment(Comment comment) {
		comments.add(comment);
		save();
	}
	
	public List<Comment> findCommentsBySportsObject(String sportsObjectName){
		List<Comment> sportsObjectComments = new ArrayList<Comment>();
		for(Comment comment : comments) {
			if(comment.getSportsObjectName().equals(sportsObjectName)) {
				sportsObjectComments.add(comment);				
			}
		}
		return sportsObjectComments;
	}
	
	public List<Comment> findApprovedCommentsBySportsObject(String sportsObjectName){
		List<Comment> sportsObjectComments = new ArrayList<Comment>();
		for(Comment comment : comments) {
			if(comment.getSportsObjectName().equals(sportsObjectName) && comment.isApproved()) {
				sportsObjectComments.add(comment);
			}
		}
		return sportsObjectComments;
	}
	
	public List<Comment> findCommentsByUser(String customerUsername){
		List<Comment> sportsObjectComments = new ArrayList<Comment>();
		for(Comment comment : comments) {
			if(comment.getCustomerUsername().equals(customerUsername)) {
				sportsObjectComments.add(comment);				
			}
		}
		return sportsObjectComments;
	}
	
	private void load() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			comments = new ArrayList<>(Arrays.asList(mapper.readValue(Paths.get(pathToFile).toFile(), Comment[].class)));
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
			mapper.writeValue(Paths.get(pathToFile).toFile(), comments);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
