package com.theforum.util;

/**
 * @author Uliana and David
 */
import javax.ws.rs.core.Response;
//one of ways to resolve cors (Access-Control-Allow-Origin error from differents urls) not in use now , used othe configuration in WEB.XML
public class AllowCrossResponse {
	public static Response ResponseCors(int status, Object entity) {
		// TODO Auto-generated method stub
		return Response.status(status).entity(entity).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}
}
