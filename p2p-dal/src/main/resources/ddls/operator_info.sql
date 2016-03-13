use yr789; 
DROP TABLE IF EXISTS operator_info; 
CREATE TABLE operator_info (
	base_id BIGINT,
	user_base_id VARCHAR(400),
	parent_id BIGINT,
	operator_type TINYINT,
	add_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,
	remark VARCHAR(400),
	rem1 VARCHAR(400),
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
	real_name_authentication VARCHAR(400)
);