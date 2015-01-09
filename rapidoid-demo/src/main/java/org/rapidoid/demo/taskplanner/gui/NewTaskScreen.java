package org.rapidoid.demo.taskplanner.gui;

/*
 * #%L
 * rapidoid-demo
 * %%
 * Copyright (C) 2014 - 2015 Nikolche Mihajlovski
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import org.rapidoid.annotation.Session;
import org.rapidoid.app.Screen;
import org.rapidoid.app.Users;
import org.rapidoid.db.DB;
import org.rapidoid.demo.taskplanner.model.Task;
import org.rapidoid.demo.taskplanner.model.User;
import org.rapidoid.html.FieldType;
import org.rapidoid.html.Tag;
import org.rapidoid.http.HttpExchange;
import org.rapidoid.widget.FormWidget;
import org.rapidoid.widget.GridWidget;

public class NewTaskScreen extends Screen {

	@Session
	private Task task = new Task();

	public Object content() {

		Tag caption1 = titleBox("Add new task");
		FormWidget frm = create(task).buttons(ADD, CANCEL);
		frm = frm.fieldType("description", FieldType.TEXTAREA);
		frm = frm.field("comments", h3("my custom field"));

		Tag caption2 = titleBox("Most recent tasks");
		GridWidget grid = grid(Task.class, "-id", 7, "id", "priority", "title");

		return row(col4(caption1, frm), col8(caption2, grid));
	}

	public void onAdd(HttpExchange x) {
		task.owner.set(Users.current(x, User.class));
		DB.insert(task);
		task = new Task();
	}

	public void onCancel() {
		task = new Task();
	}

}
