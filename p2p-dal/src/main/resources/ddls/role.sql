use yr789; 
DROP TABLE IF EXISTS role; 
CREATE TABLE role (
	role_id INT,
	role_code VARCHAR(400),
	role_name VARCHAR(400),
	role_parent_id INT,
	role_note VARCHAR(400)
);