use yr789; 
DROP TABLE IF EXISTS user_password_extend; 
CREATE TABLE user_password_extend (
	tb_base_id BIGINT,
	user_base_id VARCHAR(400),
	user_id BIGINT,
	password VARCHAR(400),
	password_type VARCHAR(400),
	row_add_time DATETIME,
	row_update_time DATETIME
);