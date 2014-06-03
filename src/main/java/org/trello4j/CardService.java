package org.trello4j;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.trello4j.model.Action;
import org.trello4j.model.Board;
import org.trello4j.model.Card;
import org.trello4j.model.Card.Attachment;
import org.trello4j.model.Checklist;
import org.trello4j.model.Checklist.CheckItem;
import org.trello4j.model.Member;

/**
 * The Interface CardService.
 * 
 * @author 
 */
public interface CardService {

	Card getCard(String cardId);

	List<Action> getActionsByCard(String cardId);

	List<Attachment> getAttachmentsByCard(String cardId);

	Board getBoardByCard(String cardId, String... filter);

	List<CheckItem> getCheckItemStatesByCard(String cardId);

	List<Checklist> getChecklistByCard(String cardId);

	org.trello4j.model.List getListByCard(String cardId, String... filter);

	List<Member> getMembersByCard(String cardId);

	/**
	 * Add a new {@link Card} with the optional keyValue pairs.
	 * @param idList Id of the {@link org.trello4j.model.List}
	 *               the card should be added to.
	 * @param name Name of the new card.
	 * @param keyValeMap Map of the optional key-value-pairs.
	 */
	Card createCard(String idList, String name, Map<String, String> keyValeMap);

	void overwriteCardDesc(String cardId, String desc);

	void overwriteCardName(String cardId, String name);

	void addMemberToCard(String cardId, String trelloKey, String memberId,  String trelloToken);

	InputStream doTrelloRequest(String url, String httpMethod, Map<String, String> map);

	void overwriteCardCheckItemName(String cardId, String itemName, String checkListId, String checkItemId);

	void createNewCheckItem(String itemName, boolean itemState, String checkListId);

	void overwriteCardCheckItemState(String cardId, String itemState, String checkListId, String checkItemId);

	Checklist createNewCheckList(String cardId, String checkListTitle);

	void changeListOfCard(String cardId, String listId);

	void deleteCheckItemFromCheckList(String checkItemId, String checkListId);
}
