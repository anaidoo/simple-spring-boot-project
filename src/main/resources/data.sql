-- Books
insert into book (id, name, publish_date, isbn) values (1, 'Storm Front', to_date('yyyy-mm-dd', '2000-04-01'), '0-451-45781-1');
insert into book (id, name, publish_date, isbn) values (2, 'Fool Moon', to_date('yyyy-mm-dd', '2001-01-01'), '0-4514-5812-5');
insert into book (id, name, publish_date, isbn) values (3, 'Grave Peril', to_date('yyyy-mm-dd', '2001-09-01'), '0-4514-5844-3');
insert into book (id, name, publish_date, isbn) values (4, 'Summer Knight', to_date('yyyy-mm-dd', '2002-09-02'), '0-4514-5892-3');
insert into book (id, name, publish_date, isbn) values (5, 'Good Omens', to_date('yyyy-mm-dd', '1990-05-10'), '0-575-04800-X');

-- Authors
insert into author (id, name, date_of_birth) values (1, 'Jim Butcher', to_date('yyyy-mm-dd', '1971-10-26'));
insert into author (id, name, date_of_birth) values (2, 'Terry Pratchett', to_date('yyyy-mm-dd', '1948-04-28'));
insert into author (id, name, date_of_birth) values (3, 'Neil Gaiman', to_date('yyyy-mm-dd', '1960-11-10'));

-- Book authors
insert into book_author (book_id, author_id) values (1, 1);
insert into book_author (book_id, author_id) values (2, 1);
insert into book_author (book_id, author_id) values (3, 1);
insert into book_author (book_id, author_id) values (4, 1);
insert into book_author (book_id, author_id) values (5, 2);
insert into book_author (book_id, author_id) values (5, 3);