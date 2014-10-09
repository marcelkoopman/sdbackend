package nl.bnc.micro.services.spotdog.riak;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.main.Main;
import org.apache.log4j.Logger;

public class SpotDogBackend {

	private static final Logger LOG = Logger.getLogger(SpotDogBackend.class);

	private Main main;

	private final int portNum;

	public SpotDogBackend(final int portNum) {
		this.portNum = portNum;
	}

	public void boot() throws Exception {
		main = new Main();
		main.enableHangupSupport();
		main.bind("accountProcessor", new AccountProcessor());
		main.addRouteBuilder(getAccountRouteBuilder());
		final String version = main.getVersion();
		LOG.info("Starting SpotDogBackend... on Apache Camel " + version);
		main.run();
	}

	public RouteBuilder getAccountRouteBuilder() throws Exception {
		return new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("restlet:http://localhost:" + portNum + "/users/{username}?restletMethods=post,get,put").bean(AccountProcessor.class);
			}
		};
	}

	public static void main(final String[] args) throws Exception {

		final SpotDogBackend sdBackend = new SpotDogBackend(9000);
		sdBackend.boot();
	}
}
