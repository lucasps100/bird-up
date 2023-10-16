drop database if exists bird_up;
create database bird_up;
use bird_up;

create table app_user (
    app_user_id int primary key auto_increment,
    username varchar(50) not null unique,
    password_hash varchar(2048) not null,
    enabled bit not null default(1)
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

create table state (
	state_id int primary key auto_increment,
    state_name varchar(50),
    state_abbrv varchar(3)
);



create table location (
	location_id int primary key auto_increment,
    city varchar(250) not null,
    state_id int,
    postal_code int,
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
	foreign key (app_poster_id)
		references app_user(app_user_id),
	foreign key (location_id)
		references location(location_id),
	foreign key (species_id)
		references species(species_id)
);

create table post_like (
	app_liker_id int not null,
    post_id int not null,
    constraint pk_liker_post
		primary key (app_liker_id, post_id),
	foreign key (app_liker_id)
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

create table followers (
	follower_id INT NOT NULL,
    followee_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    FOREIGN KEY(follower_id) REFERENCES app_user(app_user_id),
    FOREIGN KEY(followee_id) REFERENCES app_user(app_user_id),
    PRIMARY KEY(follower_id, followee_id)
);



