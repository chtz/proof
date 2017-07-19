package ch.furthermore.gae.proof;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
@Cache
public class Response {
	@Id String methodPath;
	int code;
	String type;
	byte[] content;
}