package api;

import spark.Request;
import spark.Response;

public class PingController {
	public static String ping(Request request, Response response) {
		return "welcome to an app that should porbably have a token system";
	}
}
