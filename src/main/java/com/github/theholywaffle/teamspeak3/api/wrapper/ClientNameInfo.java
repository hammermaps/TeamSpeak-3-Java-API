package com.github.theholywaffle.teamspeak3.api.wrapper;

/*
 * #%L
 * TeamSpeak 3 Java API
 * %%
 * Copyright (C) 2014 Bert De Geyter
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */

import java.util.Map;

/**
 * Wrapper for the response of {@code clientgetnamefromuid} and {@code clientgetnamefromdbid}.
 * Contains the client's unique identifier, database ID and nickname.
 *
 * @see com.github.theholywaffle.teamspeak3.TS3Api#getClientNameByUId(String)
 * @see com.github.theholywaffle.teamspeak3.TS3Api#getClientNameByDbId(int)
 */
public class ClientNameInfo extends Wrapper {

	public ClientNameInfo(Map<String, String> map) {
		super(map);
	}

	/**
	 * Gets the unique identifier of the client.
	 *
	 * @return the unique identifier
	 */
	public String getUniqueIdentifier() {
		return get("cluid");
	}

	/**
	 * Gets the database ID of the client.
	 *
	 * @return the database ID
	 */
	public int getDatabaseId() {
		return getInt("cldbid");
	}

	/**
	 * Gets the last known nickname of the client.
	 *
	 * @return the nickname
	 */
	public String getNickname() {
		return get("name");
	}
}

