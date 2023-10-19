drop database if exists bird_up_test;
create database bird_up_test;
use bird_up_test;

create table app_user (
    app_user_id int primary key auto_increment,
    username varchar(50) not null unique,
    password_hash varchar(2048) not null,
    enabled boolean not null default(true)
);

create table app_role (
    app_role_id int primary key auto_increment,
    `name` varchar(50) not null unique
);

create table app_user_role (
    app_user_id int not null,
    app_role_id int not null,
    constraint pk_app_user_role
        primary key (app_user_id, app_role_id),
    constraint fk_app_user_role_user_id
        foreign key (app_user_id)
        references app_user(app_user_id),
    constraint fk_app_user_role_role_id
        foreign key (app_role_id)
        references app_role(app_role_id)
);

create table `profile` (
	app_user_id int primary key auto_increment,
    first_name varchar(50),
    last_name varchar(50),
    bio varchar(250),
    created_at timestamp default now(),
    enabled boolean default(true),
    foreign key (app_user_id)
		references app_user(app_user_id)
);

create table state (
	state_id int primary key auto_increment,
    state_name varchar(50),
    state_abbrv varchar(3)
);


create table location (
	location_id int primary key auto_increment,
    city varchar(250) not null,
    state_id int,
    postal_code varchar(5),
    constraint fk_state_id
		foreign key (state_id)
        references state(state_id)
);

create table species (
	species_id int primary key auto_increment,
    species_short_name varchar(250),
    species_long_name varchar (250)
);

create table post (
	post_id INT PRIMARY KEY auto_increment,
    location_id int not null,
	app_poster_id int not null,
	post_body VARCHAR(255),
	image BLOB,
	created_at timestamp default NOW(),
	species_id int not null,
    enabled boolean not null default(true),
	foreign key (app_poster_id)
		references app_user(app_user_id),
	foreign key (location_id)
		references location(location_id),
	foreign key (species_id)
		references species(species_id)
);

create table post_like (
	user_liker_id int not null,
    post_id int not null,
    constraint pk_liker_post
		primary key (user_liker_id, post_id),
	foreign key (user_liker_id)
        references app_user(app_user_id),
	foreign key (post_id)
        references post(post_id)
);

create table post_comment (
	comment_id int primary key auto_increment,
    comment_text varchar(250) not null,
    user_commenter_id int not null,
    post_id int not null,
    created_at timestamp default now(),
	foreign key (user_commenter_id)
		references app_user(app_user_id),
	foreign key (post_id)
		references post(post_id)
);

create table follower (
	follower_id INT NOT NULL,
    followee_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    FOREIGN KEY(follower_id) REFERENCES app_user(app_user_id),
    FOREIGN KEY(followee_id) REFERENCES app_user(app_user_id),
    PRIMARY KEY(follower_id, followee_id)
);

insert into state(state_name, state_abbrv) values
	('Alabama','AL'),
	('Alaska','AK'),
	('Arizona','AZ'),
	('Arkansas','AR'),
	('California','CA'),
	('Colorado','CO'),
	('Connecticut','CT'),
	('Delaware','DE'),
	('Florida','FL'),
	('Georgia','GA'),
	('Hawaii','HI'),
	('Idaho','ID'),
	('Illinois','IL'),
	('Indiana','IN'),
	('Iowa','IA'),
	('Kansas','KS'),
	('Kentucky','KY'),
	('Louisiana','LA'),
	('Maine','ME'),
	('Maryland','MD'),
	('Massachusetts','MA'),
	('Michigan','MI'),
	('Minnesota','MN'),
	('Mississippi','MS'),
	('Missouri','MO'),
	('Montana','MT'),
	('Nebraska','NE'),
	('Nevada','NV'),
	('New Hampshire','NH'),
	('New Jersey','NJ'),
	('New Mexico','NM'),
	('New York','NY'),
	('North Carolina','NC'),
	('North Dakota','ND'),
	('Ohio','OH'),
	('Oklahoma','OK'),
	('Oregon','OR'),
	('Pennsylvania','PA'),
	('Rhode Island','RI'),
	('South Carolina','SC'),
	('South Dakota','SD'),
	('Tennessee','TN'),
	('Texas','TX'),
	('Utah','UT'),
	('Vermont','VT'),
	('Virginia','VA'),
	('Washington','WA'),
	('West Virginia','WV'),
	('Wisconsin','WI'),
	('Wyoming','WY');
    
insert into app_role (`name`) values
    ('USER'),
    ('ADMIN');

-- passwords are set to "P@ssw0rd!"
insert into app_user (username, password_hash, enabled)
    values
    ('jane@doe.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
    ('fred@herbert.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
    ('jon@aol.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1);
;

insert into app_user_role
    values
    (1, 2),
    (2, 1);


delimiter //
create procedure set_known_good_state()
begin
    delete from follower;
	delete from `profile`;
    delete from post_like;
    delete from post_comment;
    delete from post;
	delete from location;
	delete from species;
    
	alter table `profile` auto_increment=1;
	alter table location auto_increment=1;
	alter table species auto_increment=1;
    alter table post auto_increment=1;
	alter table post_like auto_increment=1;
	alter table post_comment auto_increment=1;
	alter table follower auto_increment=1;
    
    insert into `profile` (app_user_id, first_name, last_name, bio, created_at)
		values
        (1, 'Jane', 'Doe', 'I like birds.', timestamp("2020-01-01", "02:02:02")),
        (2, 'Fred', 'Herbert', 'Birds R cool.', timestamp("2010-10-10", "01:01:01")),
        (3, 'Jon', 'Jonson', 'Testing 123', timestamp("2021-05-05", "05:05:05"));
        
	insert into location (city, postal_code, state_id) values
		('Las Vegas', '04468', 28),
		('Hilton Head Island', '29926', 40),
        ("Kennebunk", "04043", 19);
    
    insert into follower (follower_id, followee_id, created_at) values 
    (1, 2, timestamp("2022-01-01", "02:02:02")),
    (2, 1, timestamp("2023-01-01", "04:04:04")),
    (3, 1, timestamp("2023-04-04", "05:05:05"));
    
    insert into species(species_short_name, species_long_name) values
    ("Duck", "Reginald Ducksworth"),
    ("Pigeon", "Pigeus Maximus"),
    ("Seagull", "Flying Beach Rat");
    
    insert into post(location_id, app_poster_id, post_body, created_at, species_id, enabled) values
    (1, 1, "Test text", timestamp("2023-09-09", "02:02:02"), 1, false),
    (2, 1, "Test text", timestamp("2023-09-10", "07:07:07"), 2, true),
    (2, 2, "Test text", timestamp("2023-09-10", "08:08:08"), 3, true);
    
    insert into post_like (user_liker_id, post_id) values
    (1, 1),
    (1, 3),
    (2, 1);
    
    insert into post_comment (comment_text, user_commenter_id, post_id, created_at) values
    ("comment", 1, 2, timestamp("2023-10-01", "09:09:09")),
	("comment", 2, 2, timestamp("2023-10-01", "09:09:10")),
    ("comment", 2, 1, timestamp("2023-10-05", "10:10:10"));
    
end//
delimiter ;


