-- Test
create table users (id identity,
					username varchar not null,
					password varchar not null,
					password_salt varchar not null,
					primary key (id));

create table user_roles (usernmae varchar not null,
						role_name varchar not null);
						
create table roles_permissions (role_name varchar not null,
								permission varchar not null);
