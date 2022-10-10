package com.accenture.interview.service;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;

import org.springframework.stereotype.Service;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.Attendee;
import com.microsoft.graph.models.AttendeeType;
import com.microsoft.graph.models.BodyType;
import com.microsoft.graph.models.DateTimeTimeZone;
import com.microsoft.graph.models.EmailAddress;
import com.microsoft.graph.models.Event;
import com.microsoft.graph.models.ItemBody;
import com.microsoft.graph.models.Location;
import com.microsoft.graph.models.OnlineMeetingProviderType;
import com.microsoft.graph.options.HeaderOption;
import com.microsoft.graph.requests.GraphServiceClient;

/**
 * The Class EventService.
 */
@Service
public class EventService {

	/** The Constant CLIENT_ID. */
	private static final String CLIENT_ID = "45aca14f-3d35-4f50-a3bd-a217bfbeb21b";

	/** The Constant CLIENT_SECRET. */
	private static final String CLIENT_SECRET = "BR48Q~Ws74qN_Xj-AGdOznEbSJBiANLl7TwhHari";

	/** The Constant TENANT_ID. */
	private static final String TENANT_ID = "46ba36d5-5b9f-42a1-90c7-902c4f5709df";

	/** The Constant GRAPH_LINK. */
	private static final String GRAPH_LINK = "https://graph.microsoft.com/.default";

	/**
	 * Send teams invitation.
	 *
	 * @param scheduledDate    the scheduled date
	 * @param email            the email
	 * @param candidateName    the candidate name
	 * @param candidateSurname the candidate surname
	 */
	public void sendTeamsInvitation(Date scheduledDate, String email, String candidateName, String candidateSurname) {
		final ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
				.clientId(CLIENT_ID)
				.clientSecret(CLIENT_SECRET)
				.tenantId(TENANT_ID)
				.build();

		final GraphServiceClient<?> graphClient = createClientWithAuthentication(clientSecretCredential, GRAPH_LINK);

		graphClient.users("ce39ae2e-a3c4-4517-a295-ab50c0915314")
				.events()
				.buildRequest(new LinkedList<>(Arrays.asList(new HeaderOption("Content-Type", "application/json"))))
				.post(createEvent(scheduledDate, email, candidateName, candidateSurname));
	}

	/**
	 * Creates the client with authentication.
	 *
	 * @param secret       the secret
	 * @param providerLink the provider link
	 * @return the graph service client
	 */
	private GraphServiceClient<?> createClientWithAuthentication(ClientSecretCredential secret, String providerLink) {
		final TokenCredentialAuthProvider authProvider = new TokenCredentialAuthProvider(new ArrayList<>(Arrays.asList(providerLink)), secret);
		return GraphServiceClient
				.builder()
				.authenticationProvider(authProvider)
				.buildClient();
	}

	/**
	 * Creates the event.
	 *
	 * @param scheduledDate    the scheduled date
	 * @param email            the email
	 * @param candidateName    the candidate name
	 * @param candidateSurname the candidate surname
	 * @return the event
	 */
	private Event createEvent(Date scheduledDate, String email, String candidateName, String candidateSurname) {
		Event event = new Event();
		event.subject = "Colloquio Accenture";
		ItemBody body = new ItemBody();
		body.contentType = BodyType.HTML;
		body.content = "Colloquio su Microsoft Team";
		event.body = body;
		DateTimeTimeZone start = new DateTimeTimeZone();
		start.dateTime = scheduledDate.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDateTime().toString().substring(0, 16);
		start.timeZone = "Europe/Berlin";
		event.start = start;
		DateTimeTimeZone end = new DateTimeTimeZone();
		end.dateTime = scheduledDate.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDateTime().plusHours(1).toString().substring(0, 16);
		end.timeZone = "Europe/Berlin";
		event.end = end;
		Location location = new Location();
		location.displayName = "Microsoft Teams";
		event.location = location;
		LinkedList<Attendee> attendeeLinkedList = new LinkedList<>();
		Attendee attendees = new Attendee();
		EmailAddress emailAddress = new EmailAddress();
		emailAddress.address = email;
		emailAddress.name = candidateName + " " + candidateSurname;
		attendees.emailAddress = emailAddress;
		attendees.type = AttendeeType.REQUIRED;
		attendeeLinkedList.add(attendees);
		event.attendees = attendeeLinkedList;
		event.isOnlineMeeting = true;
		event.onlineMeetingProvider = OnlineMeetingProviderType.TEAMS_FOR_BUSINESS;
		return event;
	}
}
