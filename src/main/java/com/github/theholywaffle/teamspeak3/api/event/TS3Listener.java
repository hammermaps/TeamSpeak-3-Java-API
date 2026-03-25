package com.github.theholywaffle.teamspeak3.api.event;

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

/**
 * Interface zum Empfangen von Ereignissen eines TeamSpeak-3-Servers.
 * <p>
 * Alle Methoden haben {@code default}-Implementierungen (kein Rumpf),
 * sodass nur die gewünschten Ereignisse überschrieben werden müssen.
 * </p>
 *
 * @see com.github.theholywaffle.teamspeak3.TS3Api#addTS3Listeners(TS3Listener...)
 * @see com.github.theholywaffle.teamspeak3.TS3ApiAsync#addTS3Listeners(TS3Listener...)
 */
public interface TS3Listener {

	default void onTextMessage(TextMessageEvent e) {}

	default void onClientJoin(ClientJoinEvent e) {}

	default void onClientLeave(ClientLeaveEvent e) {}

	default void onServerEdit(ServerEditedEvent e) {}

	default void onChannelEdit(ChannelEditedEvent e) {}

	default void onChannelDescriptionChanged(ChannelDescriptionEditedEvent e) {}

	default void onClientMoved(ClientMovedEvent e) {}

	default void onChannelCreate(ChannelCreateEvent e) {}

	default void onChannelDeleted(ChannelDeletedEvent e) {}

	default void onChannelMoved(ChannelMovedEvent e) {}

	default void onChannelPasswordChanged(ChannelPasswordChangedEvent e) {}

	default void onPrivilegeKeyUsed(PrivilegeKeyUsedEvent e) {}
}
