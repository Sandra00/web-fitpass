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
import beans.TrainingHistory;
import beans.enums.CommentStatus;
import util.PersonalConfig;

public class CommentDAO {
	private List<Comment> comments = new ArrayList<Comment>();
	private String pathToFile = PersonalConfig.PROJECT_FOLDER_PATH + "\\data\\comments.json";
	
	public CommentDAO() {
		load();
	}
	
	public void addComment(Comment comment) {
		comment.setId(findMax() + 1);
		comments.add(comment);
		save();
	}
	
	private int findMax() {
		int max = 0;
		for(Comment comment : comments) {
			if(comment.getId() > max) {
				max = comment.getId();
			}
		}
		return max;
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
			if(comment.getSportsObjectName().equals(sportsObjectName) && comment.getStatus() == CommentStatus.APPROVED) {
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
	
	public Comment findById(int id) {
		for(Comment comment : comments) {
			if(comment.getId() == id) {
				return comment;
			}
		}
		return null;
	}
	
	public void setApproved(int id) {
		Comment comment = findById(id);
		comment.setStatus(CommentStatus.APPROVED);
		System.out.println("promeni " + comment.getStatus().toString());
		save();
	}
	
	public void setRejected(int id) {
		Comment comment = findById(id);
		comment.setStatus(CommentStatus.REJECTED);
		save();
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
