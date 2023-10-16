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

create table post (
  post_id INT PRIMARY KEY auto_increment,
  app_poster_id int not null,
  `name` VARCHAR(255),
  image BLOB,
  created_at timestamp default NOW(),
  constraint fk_app_poster_id
	foreign key (app_poster_id)
    references app_user(app_user_id)
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
	foreign key (user_commenter_id)
		references app_user(app_user_id),
	foreign key (post_id)
		references post(post_id)
);

CREATE TABLE messages (
	id INT PRIMARY KEY AUTO_INCREMENT,
	sender_id INT NOT NULL,
	recipient_id INT NOT NULL,
	message_body varchar(2048) NOT NULL,
	sent_at TIMESTAMP DEFAULT NOW(),
	foreign key (sender_id)
		references app_user(app_user_id),
	foreign key (recipient_id)
		references app_user(app_user_id)
);

CREATE TABLE tags (
  tag_id INT AUTO_INCREMENT PRIMARY KEY,
  tag_name VARCHAR(250) UNIQUE,
  created_at TIMESTAMP DEFAULT NOW()
);
 
CREATE TABLE photo_tags (
    post_id INT NOT NULL,
    tag_id INT NOT NULL,
    FOREIGN KEY(post_id) REFERENCES post(post_id),
    FOREIGN KEY(tag_id) REFERENCES tags(tag_id),
    PRIMARY KEY(post_id, tag_id)
);

create table followers (
	follower_id INT NOT NULL,
    followee_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    FOREIGN KEY(follower_id) REFERENCES app_user(app_user_id),
    FOREIGN KEY(followee_id) REFERENCES app_user(app_user_id),
    PRIMARY KEY(follower_id, followee_id)
);



