use yr789; 
DROP TABLE IF EXISTS user_address_info; 
CREATE TABLE user_address_info (
	base_id VARCHAR(400),
	user_id BIGINT,
	address_type VARCHAR(400),
	recipient VARCHAR(400),
	recipient_mobile VARCHAR(400),
	district_code VARCHAR(400),
	office_number VARCHAR(400),
	ext_number VARCHAR(400),
	province VARCHAR(400),
	city VARCHAR(400),
	district VARCHAR(400),
	villages VARCHAR(400),
	address_detail VARCHAR(400),
	post_code VARCHAR(400),
	is_default TINYINT,
	rem1 VARCHAR(400)
);