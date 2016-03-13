use yr789; 
DROP TABLE IF EXISTS user_login_log; 
CREATE TABLE user_login_log (
	tbl_base_id VARCHAR(400),
	user_id BIGINT,
	login_ip VARCHAR(400),
	login_address VARCHAR(400),
	login_time timestamp DEFAULT CURRENT_TIMESTAMP 
);