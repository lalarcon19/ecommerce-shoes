create database ecommerce;
use ecommerce;

select
	u.username, r.role_name, p.name as permission_name
from
	user u

    inner join user_roles ur on u.id = ur.user_id
    inner join roles r on ur.role_id = r.id
    inner join roles_permissions rp on r.id = rp.role_id
    inner join permissions p on rp.permission_id = p.id;