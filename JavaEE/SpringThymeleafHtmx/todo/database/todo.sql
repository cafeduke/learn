DROP TABLE IF EXISTS todo_item;

CREATE TABLE todo_item
(
   id        INTEGER PRIMARY KEY AUTOINCREMENT,
   title     TEXT   NOT NULL,
   completed BOOLEAN
);

/**
 * insert data
 */
INSERT INTO todo_item VALUES (null, 'Buy coconut',        false);
INSERT INTO todo_item VALUES (null, 'Buy mango',          true);
INSERT INTO todo_item VALUES (null, 'Buy coriander',      true);
INSERT INTO todo_item VALUES (null, 'Buy cucumber',       false);
INSERT INTO todo_item VALUES (null, 'Buy banana',         true);
INSERT INTO todo_item VALUES (null, 'Buy carrot',         false);
INSERT INTO todo_item VALUES (null, 'Buy capsicum',       false);
INSERT INTO todo_item VALUES (null, 'Buy palaka',         false);
INSERT INTO todo_item VALUES (null, 'Buy okra',           false);
INSERT INTO todo_item VALUES (null, 'Buy beans',          false);
INSERT INTO todo_item VALUES (null, 'Buy beetroot',       false);
INSERT INTO todo_item VALUES (null, 'Buy potato',         false);
INSERT INTO todo_item VALUES (null, 'Buy onion',          false);
INSERT INTO todo_item VALUES (null, 'Buy sweet potato',   false);
INSERT INTO todo_item VALUES (null, 'Buy yellow pumpkin', false);
INSERT INTO todo_item VALUES (null, 'Buy white pumpkin',  false);
INSERT INTO todo_item VALUES (null, 'Buy cherry tomato',  true);
INSERT INTO todo_item VALUES (null, 'Buy naati tomato',   false);
INSERT INTO todo_item VALUES (null, 'Buy pear',           true);
INSERT INTO todo_item VALUES (null, 'Buy apple',          true);
