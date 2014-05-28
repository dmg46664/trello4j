package org.trello4j;

/**
 * The Class TrelloException.
 */
public class TrelloUnauthorizedException extends RuntimeException {



	/**
	 * Instantiates a new trello exception.
	 */
	public TrelloUnauthorizedException() {
		super();
	}

	/**
	 * Instantiates a new trello exception.
	 *
	 * @param msg
	 *            the msg
	 */
	public TrelloUnauthorizedException(String msg) {
		super(msg);
		System.out.println("TrelloUnauthorizedException error message: " + this.getMessage());
	}

}
