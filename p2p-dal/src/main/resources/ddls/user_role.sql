use yr789; 
DROP TABLE IF EXISTS user_role; 
CREATE TABLE user_role (
	user_role_id BIGINT,
	user_id BIGINT,
	role_id INT
);