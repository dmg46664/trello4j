package org.trello4j;

import com.google.gson.reflect.TypeToken;
import org.trello4j.model.*;
import org.trello4j.model.Board.Prefs;
import org.trello4j.model.Card.Attachment;
import org.trello4j.model.Checklist.CheckItem;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * The Class TrelloImpl.
 */
public class TrelloImpl implements Trello {

    private static final String METHOD_DELETE   = "DELETE";
    private static final String METHOD_GET      = "GET";
    private static final String METHOD_POST     = "POST";
    private static final String METHOD_PUT      = "PUT";
	private static final String GZIP_ENCODING   = "gzip";

	private String apiKey = null;
	private String token = null;
	private TrelloObjectFactoryImpl trelloObjFactory = new TrelloObjectFactoryImpl();


	public TrelloImpl(String apiKey) {
		this(apiKey, null);
	}

	public TrelloImpl(String apiKey, String token) {
		this.apiKey = apiKey;
		this.token = token;

		if (this.apiKey == null) {
			throw new TrelloException(
					"API key must be set, get one here: https://trello.com/1/appKey/generate");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.trello4j.BoardService#getBoard(java.lang.String)
	 */
	@Override
	public Board getBoard(final String boardId) {
		validateObjectId(boardId);

		final String url = TrelloURL
				.create(apiKey, TrelloURL.BOARD_URL, boardId)
				.token(token)
				.build();

		return trelloObjFactory.createObject(new TypeToken<Board>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.trello4j.BoardService#getActionsByBoard(java.lang.String,
	 * java.lang.String[])
	 */
	@Override
	public List<Action> getActionsByBoard(final String boardId,
			final String... filter) {
		validateObjectId(boardId);

		final String url = TrelloURL
				.create(apiKey, TrelloURL.BOARD_ACTIONS_URL, boardId)
				.token(token)
				.filter(filter)
				.build();

		return trelloObjFactory.createObject(new TypeToken<List<Action>>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.trello4j.BoardService#getCardsByBoard(java.lang.String)
	 */
	@Override
	public List<Card> getCardsByBoard(String boardId, final String... filter) {
		validateObjectId(boardId);

		final String url = TrelloURL
				.create(apiKey, TrelloURL.BOARD_CARDS_URL, boardId)
				.token(token)
				.filter(filter)
				.build();
		return trelloObjFactory.createObject(new TypeToken<List<Card>>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.trello4j.BoardService#getChecklistByBoard(java.lang.String)
	 */
	@Override
	public List<Checklist> getChecklistByBoard(String boardId) {
		validateObjectId(boardId);

		final String url = TrelloURL
				.create(apiKey, TrelloURL.BOARD_CHECKLISTS_URL, boardId)
				.token(token)
				.build();
		return trelloObjFactory.createObject(new TypeToken<List<Checklist>>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.trello4j.BoardService#getListByBoard(java.lang.String)
	 */
	@Override
	public List<org.trello4j.model.List> getListByBoard(String boardId,
			final String... filter) {
		validateObjectId(boardId);

		final String url = TrelloURL
				.create(apiKey, TrelloURL.BOARD_LISTS_URL, boardId)
				.token(token)
				.filter(filter)
				.build();
		return trelloObjFactory.createObject(
				new TypeToken<List<org.trello4j.model.List>>() {
				},
				doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.trello4j.BoardService#getMembersByBoard(java.lang.String)
	 */
	@Override
	public List<Member> getMembersByBoard(String boardId,
			final String... filter) {
		validateObjectId(boardId);

		final String url = TrelloURL
				.create(apiKey, TrelloURL.BOARD_MEMBERS_URL, boardId)
				.token(token)
				.filter(filter)
				.build();
		return trelloObjFactory.createObject(new TypeToken<List<Member>>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.trello4j.BoardService#getMembersInvitedByBoard(java.lang.String)
	 */
	@Override
	public List<Member> getMembersInvitedByBoard(String boardId,
			final String... filter) {
		validateObjectId(boardId);

		final String url = TrelloURL
				.create(apiKey, TrelloURL.BOARD_MEMBERS_INVITED_URL, boardId)
				.token(token)
				.filter(filter)
				.build();
		return trelloObjFactory.createObject(new TypeToken<List<Member>>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.trello4j.BoardService#getPrefsByBoard(java.lang.String)
	 */
	@Override
	public Prefs getPrefsByBoard(String boardId) {
		validateObjectId(boardId);

		final String url = TrelloURL
				.create(apiKey, TrelloURL.BOARD_PREFS_URL, boardId)
				.token(token)
				.build();
		return trelloObjFactory.createObject(new TypeToken<Prefs>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.trello4j.BoardService#getOrganizationByBoard(java.lang.String)
	 */
	@Override
	public Organization getOrganizationByBoard(String boardId,
			final String... filter) {
		validateObjectId(boardId);

		final String url = TrelloURL
				.create(apiKey, TrelloURL.BOARD_ORGANIZAION_URL, boardId)
				.token(token)
				.filter(filter)
				.build();
		return trelloObjFactory.createObject(new TypeToken<Organization>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.trello4j.ActionService#getAction(java.lang.String)
	 */
	@Override
	public Action getAction(final String actionId, final String... filter) {
		validateObjectId(actionId);

		final String url = TrelloURL
				.create(apiKey, TrelloURL.ACTION_URL, actionId)
				.token(token)
				.filter(filter)
				.build();

		return trelloObjFactory.createObject(new TypeToken<Action>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.trello4j.OrganizationService#getOrganization(java.lang.String)
	 */
	@Override
	public Organization getOrganization(String organizationName,
			final String... filter) {
		final String url = TrelloURL
				.create(apiKey, TrelloURL.ORGANIZATION_URL, organizationName)
				.token(token)
				.filter(filter)
				.build();
		return trelloObjFactory.createObject(new TypeToken<Organization>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.trello4j.MemberService#getMember(java.lang.String)
	 */
	@Override
	public Member getMember(String usernameOrId, final String... filter) {
		final String url = TrelloURL
				.create(apiKey, TrelloURL.MEMBER_URL, usernameOrId)
				.token(token)
				.filter(filter)
				.build();
		return trelloObjFactory.createObject(new TypeToken<Member>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.trello4j.MemberService#getBoardsByMember(java.lang.String)
	 */
	@Override
	public List<Board> getBoardsByMember(String usernameOrId,
			final String... filter) {
		final String url = TrelloURL
				.create(apiKey, TrelloURL.MEMBER_BOARDS_URL, usernameOrId)
				.token(token)
				.filter(filter)
				.build()+"&lists=all";

		return trelloObjFactory.createObject(new TypeToken<List<Board>>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.trello4j.OrganizationService#getBoardsByOrganization(java.lang.String
	 * )
	 */
	@Override
	public List<Board> getBoardsByOrganization(String organizationName,
			final String... filter) {
		final String url = TrelloURL
				.create(
						apiKey,
						TrelloURL.ORGANIZATION_BOARDS_URL,
						organizationName)
				.token(token)
				.filter(filter)
				.build();
		return trelloObjFactory.createObject(new TypeToken<List<Board>>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.trello4j.OrganizationService#getActionsByOrganization(java.lang.String
	 * )
	 */
	@Override
	public List<Action> getActionsByOrganization(String organizationNameOrId) {
		final String url = TrelloURL
				.create(
						apiKey,
						TrelloURL.ORGANIZATION_ACTIONS_URL,
						organizationNameOrId)
				.token(token)
				.build();
		return trelloObjFactory.createObject(new TypeToken<List<Action>>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.trello4j.CardService#getCard(java.lang.String)
	 */
	@Override
	public Card getCard(final String cardId) {
		validateObjectId(cardId);

		final String url = TrelloURL
				.create(apiKey, TrelloURL.CARD_URL, cardId)
				.token(token)
				.build()
				+ "&idChecklists=all";

		return trelloObjFactory.createObject(new TypeToken<Card>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.trello4j.CardService#getActionsByCard(java.lang.String)
	 */
	@Override
	public List<Action> getActionsByCard(final String cardId) {
		validateObjectId(cardId);

		final String url = TrelloURL
				.create(apiKey, TrelloURL.CARD_ACTION_URL, cardId)
				.token(token)
				.build();

		return trelloObjFactory.createObject(new TypeToken<List<Action>>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.trello4j.CardService#getAttachmentsByCard(java.lang.String)
	 */
	@Override
	public List<Attachment> getAttachmentsByCard(final String cardId) {
		validateObjectId(cardId);

		final String url = TrelloURL
				.create(apiKey, TrelloURL.CARD_ATTACHEMENT_URL, cardId)
				.token(token)
				.build();

		return trelloObjFactory.createObject(new TypeToken<List<Attachment>>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.trello4j.CardService#getBoardByCard(java.lang.String)
	 */
	@Override
	public Board getBoardByCard(final String cardId, final String... filter) {
		validateObjectId(cardId);

		final String url = TrelloURL
				.create(apiKey, TrelloURL.CARD_BOARD_URL, cardId)
				.token(token)
				.filter(filter)
				.build();

		return trelloObjFactory.createObject(new TypeToken<Board>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.trello4j.CardService#getCheckItemStatesByCard(java.lang.String)
	 */
	@Override
	public List<CheckItem> getCheckItemStatesByCard(final String cardId) {
		validateObjectId(cardId);

		final String url = TrelloURL
				.create(apiKey, TrelloURL.CARD_CHECK_ITEM_STATES_URL, cardId)
				.token(token)
				.build();

		return trelloObjFactory.createObject(new TypeToken<List<CheckItem>>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.trello4j.CardService#getChecklistByCard(java.lang.String)
	 */
	@Override
	public List<Checklist> getChecklistByCard(final String cardId) {
		validateObjectId(cardId);

		final String url = TrelloURL
				.create(apiKey, TrelloURL.CARD_CHECKLISTS_URL, cardId)
				.token(token)
				.build();

		return trelloObjFactory.createObject(new TypeToken<List<Checklist>>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.trello4j.CardService#getListByCard(java.lang.String)
	 */
	@Override
	public org.trello4j.model.List getListByCard(final String cardId,
			final String... filter) {
		validateObjectId(cardId);

		final String url = TrelloURL
				.create(apiKey, TrelloURL.CARD_LIST_URL, cardId)
				.token(token)
				.filter(filter)
				.build();

		return trelloObjFactory.createObject(
				new TypeToken<org.trello4j.model.List>() {
				},
				doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.trello4j.CardService#getMembersByCard(java.lang.String)
	 */
	@Override
	public List<Member> getMembersByCard(final String cardId) {
		validateObjectId(cardId);

		final String url = TrelloURL
				.create(apiKey, TrelloURL.CARD_MEMBERS_URL, cardId)
				.token(token)
				.build();

		return trelloObjFactory.createObject(new TypeToken<List<Member>>() {
		}, doGet(url));
	}

	@Override
	public Card createCard(String idList, String name, Map<String, String> keyValueMap) {
		validateObjectId(idList);

		final String url = TrelloURL
				.create(apiKey, TrelloURL.CARD_POST_URL)
				.token(token)
				.build();
		if (keyValueMap == null) keyValueMap = new HashMap<String, String>();
		//if (keyValueMap.containsKey("name")) keyValueMap.remove("name");
		keyValueMap.put("name", name);
		keyValueMap.put("idList", idList);

		return trelloObjFactory.createObject(new TypeToken<Card>() {
		}, doPost(url, keyValueMap));
	}

	@Override
	public void overwriteCardDesc(String cardId, String desc)
	{
		try {
			String encodedString = URLEncoder.encode(desc, "UTF-8");
			String url = "https://api.trello.com/1/cards/" + cardId +"/desc?" + "value=" + encodedString + "&key=" + apiKey + "&token=" + token;
			doRequest(url, "PUT");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void overwriteCardName(String cardId, String name)
	{
		try {
			String encodedString = URLEncoder.encode(name, "UTF-8");
			String url = "https://api.trello.com/1/cards/" + cardId +"/name?" + "value=" + encodedString + "&key=" + apiKey + "&token=" + token;
			doRequest(url, "PUT");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void overwriteCardCheckItemName(String cardId, String itemName, String checkListId, String checkItemId)
	{
		try {
			String encodedString = URLEncoder.encode(itemName, "UTF-8");
			String url = "https://api.trello.com/1/cards/" + cardId +
					"/checklist/" + checkListId +
					"/checkItem/" + checkItemId +
					"/name?value=" + encodedString +
					"&key=" + apiKey +
					"&token=" + token;
			doRequest(url, "PUT");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void createNewCheckList(String cardId, String checkListTitle)
	{
		final String url = TrelloURL
				.create(apiKey, TrelloURL.CHECKLIST_URL, "")
				.token(token)
				.build();

		Map<String, String> keyValueMap = new HashMap<String, String>();
		keyValueMap.put("name", checkListTitle);
		keyValueMap.put("idCard", cardId);
		doPost(url, keyValueMap);
	}

	@Override
	public void changeListOfCard(String cardId, String listId) {
		validateObjectId(listId);

		String url = "https://api.trello.com/1/cards/" + cardId +"/idList?" +
				"value=" + listId +
				"&key=" + apiKey +
				"&token=" + token;

		doRequest(url, "PUT");

		//TODO: this should use the doPut method instead
	}

	@Override
	public void createNewCheckItem(String itemName, String itemState, String checkListId)
	{
		if(itemState.contains("complete") || itemState.contains("incomplete"))
		{
			final String url = TrelloURL
					.create(apiKey, TrelloURL.CHECKLIST_CHECKITEMS_URL, checkListId)
					.token(token)
					.build();

			Map<String, String> keyValueMap = new HashMap<String, String>();
			keyValueMap.put("name", itemName);
			keyValueMap.put("checked", itemState);
			doPost(url, keyValueMap);
		}
		else
			throw new RuntimeException("itemState did not contain 'complete' or 'incomplete'. Arg passed in was: " + itemState);
	}

	@Override
	public void overwriteCardCheckItemState(String cardId, String itemState, String checkListId, String checkItemId) {
		try {
			if(itemState.contains("complete") || itemState.contains("incomplete"))
			{
				String encodedString = URLEncoder.encode(itemState, "UTF-8");
				String url = "https://api.trello.com/1/cards/" + cardId +
						"/checklist/" + checkListId +
						"/checkItem/" + checkItemId +
						"/state?value=" + encodedString +
						"&key=" + apiKey +
						"&token=" + token;
				doRequest(url, "PUT");
			}
			else
				throw new RuntimeException("itemState did not contain 'complete' or 'incomplete'. Arg passed in was: " + itemState);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addMemberToCard(String cardId, String trelloKey, String memberId, String trelloToken) {
		String url = "https://api.trello.com/1/cards/" + cardId +"/idMembers?" +
				"value=" + memberId +
				"&key=" + trelloKey +
				"&token=" + trelloToken;

		doRequest(url, "PUT");

		//TODO The following didn't work last time I checked, the above did but is just for reference.
//		final String url = TrelloURL.create(apiKey, TrelloURL.PUT_CARD_MEMBER_URL, cardId)
//				.token(token).build();
//		HashMap<String, String> keyValueMap = new HashMap<String, String>();
//		keyValueMap.put("value", memberId);
//		trelloObjFactory.createObject(new TypeToken<List<Member>>() {
//		}, doPut(url, keyValueMap));

	}

	@Override
	public InputStream doTrelloRequest(String url, String httpMethod, Map<String, String> map) {
		return doRequest(url, httpMethod, map);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.trello4j.ListService#getList(java.lang.String)
	 */
	@Override
	public org.trello4j.model.List getList(final String listId) {
		validateObjectId(listId);

		final String url = TrelloURL
				.create(apiKey, TrelloURL.LIST_URL, listId)
				.token(token)
				.build();

		return trelloObjFactory.createObject(
				new TypeToken<org.trello4j.model.List>() {
				},
				doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.trello4j.NotificationService#getNotification(java.lang.String)
	 */
	@Override
	public Notification getNotification(String notificationId,
			final String... filter) {
		validateObjectId(notificationId);

		final String url = TrelloURL
				.create(apiKey, TrelloURL.NOTIFICATION_URL, notificationId)
				.token(token)
				.filter(filter)
				.build();

		return trelloObjFactory.createObject(new TypeToken<Notification>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.trello4j.ChecklistService#getChecklist(java.lang.String)
	 */
	@Override
	public Checklist getChecklist(String checklistId, final String... filter) {
		validateObjectId(checklistId);

		final String url = TrelloURL
				.create(apiKey, TrelloURL.CHECKLIST_URL, checklistId)
				.token(token)
				.filter(filter)
				.build();

		return trelloObjFactory.createObject(new TypeToken<Checklist>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.trello4j.Trello#getType(java.lang.String)
	 */
	@Override
	public Type getType(String idOrName) {
		final String url = TrelloURL
				.create(apiKey, TrelloURL.TYPE_URL, idOrName)
				.token(token)
				.build();

		return trelloObjFactory.createObject(new TypeToken<Type>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.trello4j.ActionService#getBoardByAction(java.lang.String)
	 */
	@Override
	public Board getBoardByAction(String actionId, final String... filter) {
		validateObjectId(actionId);

		final String url = TrelloURL
				.create(apiKey, TrelloURL.ACTION_BOARD_URL, actionId)
				.token(token)
				.filter(filter)
				.build();

		return trelloObjFactory.createObject(new TypeToken<Board>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.trello4j.ActionService#getCardByAction(java.lang.String)
	 */
	@Override
	public Card getCardByAction(String actionId, final String... filter) {
		validateObjectId(actionId);

		final String url = TrelloURL
				.create(apiKey, TrelloURL.ACTION_CARD_URL, actionId)
				.token(token)
				.filter(filter)
				.build();

		return trelloObjFactory.createObject(new TypeToken<Card>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.trello4j.ActionService#getMemberByAction(java.lang.String)
	 */
	@Override
	public Member getMemberByAction(String actionId, final String... filter) {
		validateObjectId(actionId);

		final String url = TrelloURL
				.create(apiKey, TrelloURL.ACTION_MEMBER_URL, actionId)
				.token(token)
				.filter(filter)
				.build();

		return trelloObjFactory.createObject(new TypeToken<Member>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.trello4j.ActionService#getListByAction(java.lang.String)
	 */
	@Override
	public org.trello4j.model.List getListByAction(String actionId,
			final String... filter) {
		validateObjectId(actionId);

		final String url = TrelloURL
				.create(apiKey, TrelloURL.ACTION_LIST_URL, actionId)
				.token(token)
				.filter(filter)
				.build();

		return trelloObjFactory.createObject(
				new TypeToken<org.trello4j.model.List>() {
				},
				doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.trello4j.ActionService#getMemberCreatorByAction(java.lang.String)
	 */
	@Override
	public Member getMemberCreatorByAction(String actionId,
			final String... filter) {
		validateObjectId(actionId);

		final String url = TrelloURL
				.create(apiKey, TrelloURL.ACTION_MEMBERCREATOR_URL, actionId)
				.token(token)
				.filter(filter)
				.build();

		return trelloObjFactory.createObject(new TypeToken<Member>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.trello4j.ActionService#getOrganizationByAction(java.lang.String)
	 */
	@Override
	public Organization getOrganizationByAction(String actionId,
			final String... filter) {
		validateObjectId(actionId);

		final String url = TrelloURL
				.create(apiKey, TrelloURL.ACTION_ORGANIZATION_URL, actionId)
				.token(token)
				.filter(filter)
				.build();

		return trelloObjFactory.createObject(new TypeToken<Organization>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.trello4j.OrganizationService#getMembersByOrganization(java.lang.String
	 * )
	 */
	@Override
	public List<Member> getMembersByOrganization(String organizationNameOrId,
			final String... filter) {

		final String url = TrelloURL
				.create(
						apiKey,
						TrelloURL.ORGANIZATION_MEMBERS_URL,
						organizationNameOrId)
				.token(token)
				.filter(filter)
				.build();

		return trelloObjFactory.createObject(new TypeToken<List<Member>>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.trello4j.NotificationService#getBoardByNotification(java.lang.String)
	 */
	@Override
	public Board getBoardByNotification(String notificationId,
			final String... filter) {
		validateObjectId(notificationId);

		final String url = TrelloURL
				.create(
						apiKey,
						TrelloURL.NOTIFICATION_BOARDS_URL,
						notificationId)
				.token(token)
				.filter(filter)
				.build();

		return trelloObjFactory.createObject(new TypeToken<Board>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.trello4j.NotificationService#getCardByNotification(java.lang.String)
	 */
	@Override
	public Card getCardByNotification(String notificationId,
			final String... filter) {
		validateObjectId(notificationId);

		final String url = TrelloURL
				.create(
						apiKey,
						TrelloURL.NOTIFICATION_CARDS_URL,
						notificationId)
				.token(token)
				.filter(filter)
				.build();

		return trelloObjFactory.createObject(new TypeToken<Card>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.trello4j.NotificationService#getListByNotification(java.lang.String)
	 */
	@Override
	public org.trello4j.model.List getListByNotification(String notificationId,
			final String... filter) {
		validateObjectId(notificationId);

		final String url = TrelloURL
				.create(apiKey, TrelloURL.NOTIFICATION_LIST_URL, notificationId)
				.token(token)
				.filter(filter)
				.build();

		return trelloObjFactory.createObject(
				new TypeToken<org.trello4j.model.List>() {
				},
				doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.trello4j.NotificationService#getMemberByNotification(java.lang.String
	 * )
	 */
	@Override
	public Member getMemberByNotification(String notificationId,
			final String... filter) {
		validateObjectId(notificationId);

		final String url = TrelloURL
				.create(
						apiKey,
						TrelloURL.NOTIFICATION_MEMBERS_URL,
						notificationId)
				.token(token)
				.filter(filter)
				.build();

		return trelloObjFactory.createObject(new TypeToken<Member>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.trello4j.NotificationService#getMemberCreatorByNotification(java.
	 * lang.String)
	 */
	@Override
	public Member getMemberCreatorByNotification(String notificationId,
			final String... filter) {
		validateObjectId(notificationId);

		final String url = TrelloURL
				.create(
						apiKey,
						TrelloURL.NOTIFICATION_MEMBER_CREATOR_URL,
						notificationId)
				.token(token)
				.filter(filter)
				.build();

		return trelloObjFactory.createObject(new TypeToken<Member>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.trello4j.NotificationService#getOrganizationCreatorByNotification
	 * (java.lang.String)
	 */
	@Override
	public Member getOrganizationCreatorByNotification(String notificationId,
			final String... filter) {
		validateObjectId(notificationId);

		final String url = TrelloURL
				.create(
						apiKey,
						TrelloURL.NOTIFICATION_ORGANIZATION_URL,
						notificationId)
				.token(token)
				.filter(filter)
				.build();

		return trelloObjFactory.createObject(new TypeToken<Member>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.trello4j.ListService#getActionsByList(java.lang.String)
	 */
	@Override
	public List<Action> getActionsByList(String listId) {
		validateObjectId(listId);

		final String url = TrelloURL
				.create(apiKey, TrelloURL.LIST_ACTIONS_URL, listId)
				.token(token)
				.build();

		return trelloObjFactory.createObject(new TypeToken<List<Action>>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.trello4j.ListService#getBoardByList(java.lang.String)
	 */
	@Override
	public Board getBoardByList(String listId, final String... filter) {
		validateObjectId(listId);

		final String url = TrelloURL
				.create(apiKey, TrelloURL.LIST_BOARD_URL, listId)
				.token(token)
				.filter(filter)
				.build();

		return trelloObjFactory.createObject(new TypeToken<Board>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.trello4j.ListService#getCardsByList(java.lang.String)
	 */
	@Override
	public List<Card> getCardsByList(String listId, final String... filter) {
		validateObjectId(listId);

		final String url = TrelloURL
				.create(apiKey, TrelloURL.LIST_CARDS_URL, listId)
				.token(token)
				.filter(filter)
				.build();

		return trelloObjFactory.createObject(new TypeToken<List<Card>>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.trello4j.MemberService#getActionsByMember(java.lang.String)
	 */
	@Override
	public List<Action> getActionsByMember(String usernameOrId) {

		final String url = TrelloURL
				.create(apiKey, TrelloURL.MEMBER_ACTIONS_URL, usernameOrId)
				.token(token)
				.build();

		return trelloObjFactory.createObject(new TypeToken<List<Action>>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.trello4j.MemberService#getCardsByMember(java.lang.String)
	 */
	@Override
	public List<Card>
	getCardsByMember(String usernameOrId,
			final String... filter) {

		final String url = TrelloURL
				.create(apiKey, TrelloURL.MEMBER_CARDS_URL, usernameOrId)
				.token(token)
				.filter(filter)
				.build()+"&checklists=all";
		


		return trelloObjFactory.createObject(new TypeToken<List<Card>>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.trello4j.MemberService#getNotificationsByMember(java.lang.String)
	 */
	@Override
	public List<Notification> getNotificationsByMember(String usernameOrId,
			final String... filter) {

		final String url = TrelloURL
				.create(
						apiKey,
						TrelloURL.MEMBER_NOTIFIACTIONS_URL,
						usernameOrId)
				.token(token)
				.filter(filter)
				.build();

		return trelloObjFactory.createObject(
				new TypeToken<List<Notification>>() {
				},
				doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.trello4j.MemberService#getOrganizationsByMember(java.lang.String)
	 */
	@Override
	public List<Organization> getOrganizationsByMember(String usernameOrId,
			final String... filter) {

		final String url = TrelloURL
				.create(apiKey, TrelloURL.MEMBER_ORGANIZATION_URL, usernameOrId)
				.token(token)
				.filter(filter)
				.build();

		return trelloObjFactory.createObject(
				new TypeToken<List<Organization>>() {
				},
				doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.trello4j.MemberService#getOrganizationsInvitedByMember(java.lang.
	 * String)
	 */
	@Override
	public List<Organization> getOrganizationsInvitedByMember(
			String usernameOrId, final String... filter) {

		final String url = TrelloURL
				.create(
						apiKey,
						TrelloURL.MEMBER_ORGANIZATION_INVITED_URL,
						usernameOrId)
				.token(token)
				.filter(filter)
				.build();

		return trelloObjFactory.createObject(
				new TypeToken<List<Organization>>() {
				},
				doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.trello4j.ChecklistService#getBoardByChecklist(java.lang.String)
	 */
	@Override
	public Board
			getBoardByChecklist(String checklistId, final String... filter) {
		validateObjectId(checklistId);

		final String url = TrelloURL
				.create(apiKey, TrelloURL.CHECKLIST_BOARD_URL, checklistId)
				.token(token)
				.filter(filter)
				.build();

		return trelloObjFactory.createObject(new TypeToken<Board>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.trello4j.ChecklistService#getCheckItemsByChecklist(java.lang.String)
	 */
	@Override
	public List<CheckItem> getCheckItemsByChecklist(String checklistId) {
		validateObjectId(checklistId);

		final String url = TrelloURL
				.create(apiKey, TrelloURL.CHECKLIST_CHECKITEMS_URL, checklistId)
				.token(token)
				.build();

		return trelloObjFactory.createObject(new TypeToken<List<CheckItem>>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.trello4j.ChecklistService#getCardByChecklist(java.lang.String)
	 */
	@Override
	public List<Card> getCardByChecklist(String checklistId,
			final String... filter) {
		validateObjectId(checklistId);

		final String url = TrelloURL
				.create(apiKey, TrelloURL.CHECKLIST_CARDS_URL, checklistId)
				.token(token)
				.filter(filter)
				.build();

		return trelloObjFactory.createObject(new TypeToken<List<Card>>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.trello4j.TokenService#getToken(java.lang.String)
	 */
	@Override
	public Token getToken(String tokenId, final String... filter) {
		// validateObjectId(tokenId);

		final String url = TrelloURL
				.create(apiKey, TrelloURL.TOKENS_URL, tokenId)
				.token(token)
				.filter(filter)
				.build();

		return trelloObjFactory.createObject(new TypeToken<Token>() {
		}, doGet(url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.trello4j.TokenService#getMemberByToken(java.lang.String)
	 */
	@Override
	public Member getMemberByToken(String tokenId, final String... filter) {
		// validateObjectId(tokenId);

		final String url = TrelloURL
				.create(apiKey, TrelloURL.TOKENS_MEMBER_URL, tokenId)
				.token(token)
				.filter(filter)
				.build();

		return trelloObjFactory.createObject(new TypeToken<Member>() {
		}, doGet(url));
	}

	private InputStream doGet(String url) {
		return doRequest(url, METHOD_GET);
	}

	private InputStream doPut(String url) {
		return doRequest(url, METHOD_PUT);
	}

	private InputStream doPut(String url, Map<String, String> map) {
		return doRequest(url, METHOD_PUT, map);
	}

	private InputStream doPost(String url, Map<String, String> map) {
		return doRequest(url, METHOD_POST, map);
	}

	private InputStream doDelete(String url) {
		return doRequest(url, METHOD_DELETE);
	}

	private InputStream doRequest(String url, String requestMethod) {
        return doRequest(url, requestMethod, null);
	}

	/**
	 * Execute a POST request with URL-encoded key-value parameter pairs.
	 * @param url Trello API URL.
	 * @param map Key-value map.
	 * @return the response input stream.
	 */
	private InputStream doRequest(String url, String requestMethod, Map<String, String> map) {
		try {
			HttpsURLConnection conn = null ;
			int responseCode = -1;

//			int attemptsLeft = 2 ;
//			while(attemptsLeft > 0 && responseCode == -1)
//			{
				conn = (HttpsURLConnection) new URL(url)
						.openConnection();
				conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
	            conn.setDoOutput(requestMethod.equals(METHOD_POST) || requestMethod.equals(METHOD_PUT));
	            conn.setRequestMethod(requestMethod);

	            if(map != null && !map.isEmpty()) {
		            StringBuilder sb = new StringBuilder();
	                for (String key : map.keySet()) {
	                    sb.append(sb.length() > 0 ? "&" : "")
	                        .append(key)
	                        .append("=")
	                        .append(URLEncoder.encode(map.get(key), "UTF-8"));
	                }
	                conn.getOutputStream().write(sb.toString().getBytes());
	                conn.getOutputStream().close();
	            }

//				try {
					responseCode = conn.getResponseCode();//so we can step debug
					if ((responseCode < 0 || responseCode > 399) ){//&& attemptsLeft == 0) {
						if (conn.getResponseMessage().contains("Unauthorized"))
							throw new TrelloUnauthorizedException("Trello token expired or insufficient permission. response code:" + responseCode + " message: " + conn.getResponseMessage());
						throw new TrelloException("Trello exception on TrelloImpl.doRequest code:" + responseCode + " message: " + conn.getResponseMessage());
					}
					else {
						return getWrappedInputStream(
								conn.getInputStream(), GZIP_ENCODING.equalsIgnoreCase(conn.getContentEncoding())
						);
					}
//				} catch (IOException ex) {
//					if (attemptsLeft == 0)
//						throw ex ;
//				}
//			}
		} catch (IOException e) {
			throw new TrelloException(e.getMessage());
		}
//		return null ;
	}

	private void validateObjectId(String id) {
		if (!TrelloUtil.isObjectIdValid(id)) {
			throw new TrelloException("Invalid object id: " + id);
		}
	}

	private InputStream getWrappedInputStream(InputStream is, boolean gzip)
			throws IOException {
		/*
		 * TODO: What about this? ---------------------- "Java clients which use
		 * java.util.zip.GZIPInputStream() and wrap it with a
		 * java.io.BufferedReader() to read streaming API data will encounter
		 * buffering on low volume streams, since GZIPInputStream's available()
		 * method is not suitable for streaming purposes. To fix this, create a
		 * subclass of GZIPInputStream() which overrides the available()
		 * method."
		 * 
		 * https://dev.twitter.com/docs/streaming-api/concepts#gzip-compression
		 */
		if (gzip) {
			return new BufferedInputStream(new GZIPInputStream(is));
		} else {
			return new BufferedInputStream(is);
		}
	}

}
