drop table if exists todo_item;

create table todo_item
(
   id        int    not null,
   title     text   not null,
   completed boolean,
   primary key (id)
);

/**
 * insert data
 */
insert into todo_item values (1,  'Buy coconut',        false);
insert into todo_item values (2,  'Buy mango',          true);
insert into todo_item values (3,  'Buy coriander',      true);
insert into todo_item values (4,  'Buy cucumber',       false);
insert into todo_item values (5,  'Buy banana',         true);
insert into todo_item values (6,  'Buy carrot',         false);
insert into todo_item values (7,  'Buy capsicum',       false);
insert into todo_item values (8,  'Buy palaka',         false);
insert into todo_item values (9,  'Buy okra',           false);
insert into todo_item values (10, 'Buy beans',          false);
insert into todo_item values (11, 'Buy beetroot',       false);
insert into todo_item values (12, 'Buy potato',         false);
insert into todo_item values (13, 'Buy onion',          false);
insert into todo_item values (14, 'Buy sweet potato',   false);
insert into todo_item values (15, 'Buy yellow pumpkin', false);
insert into todo_item values (16, 'Buy white pumpkin',  false);
insert into todo_item values (17, 'Buy cherry tomato',  true);
insert into todo_item values (18, 'Buy naati tomato',   false);
insert into todo_item values (19, 'Buy pear',           true);
insert into todo_item values (20, 'Buy apple',          true);
