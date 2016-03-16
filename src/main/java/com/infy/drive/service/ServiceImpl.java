/**
 * 
 */
package com.infy.drive.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.infy.drive.common.Coordinates;
import com.infy.drive.common.User;

/**
 * @author schennia
 *
 */
@Service("serviceProxy")
public class ServiceImpl {
	static Map<String, User> users = new HashMap<String, User>();
	static Map<String, List<String>> routeDriver = new HashMap<String, List<String>>();
	static Map<String, List<Coordinates>> routeBusStop = new HashMap<String, List<Coordinates>>();
	@GET
	@Path("")
	public void home(@Context HttpServletRequest request,@Context HttpServletResponse response) {
		try {

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/*
	 * POST Service Proxy
	 */
	@POST
	@Path("/updateLocation/{url:.+}")
	public Response updateLocation( @Context HttpServletRequest request,@Context HttpServletResponse response, @PathParam("url") String userid, String postBody) {
		ResponseBuilder rBuild = null;
		System.out.println("User ID String : "+userid +"   postBody : "+postBody);
		JsonParser parser = new JsonParser();
		JsonObject msg = (JsonObject) parser.parse(postBody);
		String latt =msg.get("latt").getAsString();
		String longt =msg.get("longt").getAsString();
		if (userid != null && latt != null && longt != null) {
//			DOMUtil.properties.setProperty(task, postBody);
			User currUser = null;
			if (users.containsKey(userid)) {
				currUser =(User)users.get(userid);
			} else {
				currUser = new User();
				users.put(userid, currUser);
			}
			currUser.setLatitude(Double.valueOf(latt));
			currUser.setLongitude(Double.valueOf(longt));
			rBuild = Response.status(Response.Status.OK);
			rBuild.type(MediaType.APPLICATION_JSON)
					.entity("{\"success\":\"Data Updated\"}");
		} else {
			rBuild = Response.status(Response.Status.OK);
			rBuild.type(MediaType.APPLICATION_JSON)
					.entity("{\"error\":\"error in data\"}");
		}
		return rBuild.build();
	}
	
	/*
	 * Get Service Proxy
	 */
	@GET
	@Path("/getLocation/{url:.+}")
	public Response getLocation( @Context HttpServletRequest request,@Context HttpServletResponse response, @PathParam("url") String userid, String postBody) {
		ResponseBuilder rBuild = null;
		System.out.println("User ID String : "+userid +"   postBody : "+postBody);
		/*JsonParser parser = new JsonParser();
		JsonObject msg = (JsonObject) parser.parse(postBody);*/
		rBuild = Response.status(Response.Status.OK);
		rBuild.type(MediaType.APPLICATION_JSON)
				.entity("{\"latt\":\"12.8399389\",\"longt\":\"77.67700309999999\"}");
		return rBuild.build();
	}
	
	private void getBusStops() {
		if(routeBusStop.size() <=0) {
			List<Coordinates> coords = new ArrayList<Coordinates>();
			Coordinates coordinate1 =  new Coordinates();
			coordinate1.setLatt(1.333);
			coordinate1.setLongt(71.444);
			coords.add(coordinate1);
			Coordinates coordinate2 =  new Coordinates();
			coordinate2.setLatt(1.345);
			coordinate2.setLongt(72.444);
			coords.add(coordinate2);
			Coordinates coordinate3 =  new Coordinates();
			coordinate3.setLatt(2.543);
			coordinate3.setLongt(72.123);
			coords.add(coordinate3);
			Coordinates coordinate4 =  new Coordinates();
			coordinate4.setLatt(2.789);
			coordinate4.setLongt(73.566);
			coords.add(coordinate4);
			routeBusStop.put("route1", coords);
		}
	}

}
