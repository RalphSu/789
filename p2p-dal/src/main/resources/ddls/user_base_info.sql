use yr789; 
DROP TABLE IF EXISTS user_base_info; 
CREATE TABLE user_base_info (
	user_base_id VARCHAR(400),
	user_id BIGINT,
	user_name VARCHAR(400),
	real_name VARCHAR(400),
	log_password VARCHAR(400),
	pay_password VARCHAR(400),
	account_id VARCHAR(400),
	account_name VARCHAR(400),
	mobile VARCHAR(400),
	mobile_binding VARCHAR(400),
	mail VARCHAR(400),
	mail_binding VARCHAR(400),
	type VARCHAR(400),
	state VARCHAR(400),
	row_add_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,
	row_update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP  ON UPDATE CURRENT_TIMESTAMP ,
	identity_name VARCHAR(400),
	identity_start_no VARCHAR(400),
	identity_end_no VARCHAR(400),
	ex_identity_no VARCHAR(400),
	real_name_authentication VARCHAR(400),
	pwd_error_count INT,
	change_lock_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
);