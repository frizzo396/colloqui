package com.accenture.interview.service.general;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.LobbyBypassScope;
import com.microsoft.graph.models.LobbyBypassSettings;
import com.microsoft.graph.models.OnlineMeeting;
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
	 * @return the string
	 */
	public String createTeamsMeeting(Date scheduledDate) {
		try {
			final ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
					.clientId(clientId)
					.clientSecret(clientSecret)
					.tenantId(tenantId)
					.build();
			final GraphServiceClient<?> graphClient = createClientWithAuthentication(clientSecretCredential, graphLink);			
			OnlineMeeting post = graphClient.users(userId).onlineMeetings()
				.buildRequest(new LinkedList<>(Arrays.asList(new HeaderOption("Content-Type", "application/json"))))
				.post(createOnlineMeeting(scheduledDate));
			return post.joinWebUrl;
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
	 * Creates the online meeting.
	 *
	 * @param scheduledDate the scheduled date
	 * @param candidateMail the candidate mail
	 * @param candidate the candidate
	 * @param interviewerMail the interviewer mail
	 * @param assignerMail the assigner mail
	 * @return the online meeting
	 */
	private OnlineMeeting createOnlineMeeting(Date scheduledDate) {
		OnlineMeeting meeting = new OnlineMeeting();
		meeting.allowAttendeeToEnableCamera = true;
		meeting.allowAttendeeToEnableMic = true;
		meeting.startDateTime = scheduledDate.toInstant()
				  .atOffset(ZoneOffset.UTC);
		meeting.endDateTime =scheduledDate.toInstant()
				  .atOffset(ZoneOffset.UTC).plusHours(1);				
		LobbyBypassSettings lobbySettings = new LobbyBypassSettings();
		lobbySettings.scope = LobbyBypassScope.EVERYONE;
		lobbySettings.isDialInBypassEnabled = true;		
		meeting.lobbyBypassSettings = lobbySettings;	
		return meeting;
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
