insert into client_role_group(client_role_group_name)
values ('CLIENT');

insert into client_role(client_role_name, client_role_group_id)
values ('COMMON_INFO_VIEWER', 1);