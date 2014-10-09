package nl.bnc.micro.services.spotdog.riak;

import java.util.concurrent.ConcurrentMap;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.restlet.RestletConstants;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.MediaType;
import org.restlet.data.Status;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.istack.logging.Logger;

public class AccountProcessor implements Processor {

	private static final Logger LOG = Logger.getLogger(AccountProcessor.class);
	private final Gson gson = new GsonBuilder().create();

	public void process(final Exchange exchange) throws Exception {

		final Request request = exchange.getIn().getHeader(RestletConstants.RESTLET_REQUEST, Request.class);
		final ConcurrentMap<String, Object> attributes = request.getAttributes();
		// Generate response
		final Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
		response.setStatus(Status.SUCCESS_OK);
		final Account account = new Account();
		account.setEmail("marcel.koopman@gmail.com");
		account.setLastName("" + attributes.get("username"));
		final String json = gson.toJson(account);
		response.setEntity(json, MediaType.APPLICATION_JSON);
		exchange.getOut().setBody(response);
	}
}
