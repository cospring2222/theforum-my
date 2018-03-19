package com.theforum.util;

import javax.ws.rs.core.Response;

public class AllowCrossResponse {
	public static Response ResponseCors(int status, Object entity) {
		// TODO Auto-generated method stub
		return Response.status(status).entity(entity).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}
}
