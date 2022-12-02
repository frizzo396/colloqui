package com.accenture.interview.service.general;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.mail.search.RecipientStringTerm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.accenture.interview.utils.enums.EventDateTypeEnum;
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
import com.microsoft.graph.models.LobbyBypassSettings;
import com.microsoft.graph.models.Location;
import com.microsoft.graph.models.OnlineMeeting;
import com.microsoft.graph.models.OnlineMeetingProviderType;
import com.microsoft.graph.models.Recipient;
import com.microsoft.graph.options.HeaderOption;
import com.microsoft.graph.requests.GraphServiceClient;

/**
 * The Class EventService.
 */
@Service
public class EventService {

	/** The client id. */
	@Value("${azure.client.id}")
	private String clientId;

	/** The client secret. */
	@Value("${azure.client.secret}")
	private String clientSecret;

	/** The tenant id. */
	@Value("${azure.tenant.id}")
	private String tenantId;

	/** The graph link. */
	@Value("${microsoft.graph.link}")
	private String graphLink;

	/** The user id. */
	@Value("${azure.user.id}")
	private String userId;

	/** The message source. */
	@Autowired
	private MessageSource messageSource;

	/**
	 * Send teams invitation.
	 *
	 * @param scheduledDate the scheduled date
	 * @param candidateMail the candidate mail
	 * @param candidate the candidate
	 * @param interviewerMail the interviewer mail
	 * @param assignerMail the assigner mail
	 * @return true, if successful
	 */
	public String sendTeamsInvitation(Date scheduledDate, String candidateMail, String candidate, String interviewerMail, String assignerMail) {
		try {
			final ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
					.clientId(clientId)
					.clientSecret(clientSecret)
					.tenantId(tenantId)
					.build();
			final GraphServiceClient<?> graphClient = createClientWithAuthentication(clientSecretCredential, graphLink);			
			Event post = graphClient.users(userId).events()
					.buildRequest(new LinkedList<>(Arrays.asList(new HeaderOption("Content-Type", "application/json"))))
					.post(createEvent(scheduledDate, candidateMail, candidate, interviewerMail, assignerMail));	
			
			return post.onlineMeeting.joinUrl;

		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Creates the client with authentication.
	 *
	 * @param secret the secret
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
	 * @param scheduledDate the scheduled date
	 * @param candidateMail the candidate mail
	 * @param candidate the candidate
	 * @param interviewerMail the interviewer mail
	 * @param assignerMail the assigner mail
	 * @return the event
	 */
	private Event createEvent(Date scheduledDate, String candidateMail, String candidate, String interviewerMail, String assignerMail) {
		Event event = new Event();
		event.subject = "Colloquio Accenture - " + candidate;		
		event.body = createBody();
		event.start = createEventDate(scheduledDate, EventDateTypeEnum.START);		
		event.end = createEventDate(scheduledDate, EventDateTypeEnum.END);
		Location location = new Location();
		location.displayName = "Microsoft Teams";
		event.location = location;
		event.attendees = addPartecipants(candidateMail, interviewerMail, assignerMail);
		event.isOnlineMeeting = true;
		event.organizer = createOrganizer(interviewerMail);
		event.onlineMeetingProvider = OnlineMeetingProviderType.TEAMS_FOR_BUSINESS;
		return event;
	} 
	
	
	/*private OnlineMeeting createOnlineMeeting(Date scheduledDate, String candidateMail, String candidate, String interviewerMail, String assignerMail) {
		OnlineMeeting meeting = new OnlineMeeting();
		meeting.allowAttendeeToEnableCamera = true;
		meeting.allowAttendeeToEnableMic = true;
		meeting.
	
		return meeting;
	} */
	
	
	
	private Recipient createOrganizer(String interviewerMail) {
		Recipient organizer = new Recipient();		
		EmailAddress organizerAdress = new EmailAddress();
		organizerAdress.address = interviewerMail;
		organizer.emailAddress = organizerAdress;
		return organizer;
	}

	/**
	 * Creates the body.
	 *
	 * @return the item body
	 */
	private ItemBody createBody() {
		ItemBody body = new ItemBody();
		body.contentType = BodyType.HTML; 
		body.content = messageSource.getMessage("teams.mail.body", null, Locale.getDefault());
		return body;
	}

	/**
	 * Creates the event date.
	 *
	 * @param scheduledDate the scheduled date
	 * @param type the type
	 * @return the date time time zone
	 */
	private DateTimeTimeZone createEventDate(Date scheduledDate, EventDateTypeEnum type) {
		DateTimeTimeZone dateTime = new DateTimeTimeZone();
		dateTime.timeZone = "Europe/Berlin";
		if(type.equals(EventDateTypeEnum.START)) {
			dateTime.dateTime = scheduledDate.toInstant()
					.atZone(ZoneId.systemDefault())
					.toLocalDateTime().toString().substring(0, 16);
		} else {
			dateTime.dateTime = scheduledDate.toInstant()
					.atZone(ZoneId.systemDefault())
					.toLocalDateTime().plusHours(1).toString().substring(0, 16);
		}
		return dateTime;
	}

	/**
	 * Adds the partecipants.
	 *
	 * @param candidateMail the candidate mail
	 * @param interviewerMail the interviewer mail
	 * @param assignerMail the assigner mail
	 * @return the list
	 */
	private List<Attendee> addPartecipants(String candidateMail, String interviewerMail, String assignerMail) {
		LinkedList<Attendee> attendeeLinkedList = new LinkedList<>();
		Attendee candidate = new Attendee();
		EmailAddress candidateAddress = new EmailAddress();
		candidateAddress.address = candidateMail;
		candidate.emailAddress = candidateAddress;
		candidate.type = AttendeeType.REQUIRED;

		Attendee interviewer = new Attendee();
		EmailAddress interviewerAddress = new EmailAddress();
		interviewerAddress.address = interviewerMail;
		interviewer.emailAddress = interviewerAddress;
		interviewer.type = AttendeeType.REQUIRED;

		Attendee assigner = new Attendee();
		EmailAddress assignerAddress = new EmailAddress();
		assignerAddress.address = assignerMail;
		assigner.emailAddress = assignerAddress;
		assigner.type = AttendeeType.OPTIONAL;

		attendeeLinkedList.add(candidate);
		attendeeLinkedList.add(interviewer);
		attendeeLinkedList.add(assigner);		
		return attendeeLinkedList;
	}

	/**
	 * Event not send error message.
	 *
	 * @return the string
	 */
	public String eventNotSendErrorMessage() {
		return messageSource.getMessage("teams.event.error", null, Locale.getDefault());
	}
}
