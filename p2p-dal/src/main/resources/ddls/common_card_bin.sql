use yr789; 
DROP TABLE IF EXISTS common_card_bin; 
CREATE TABLE common_card_bin (
	id INT,
	bank_name VARCHAR(400),
	bank_id VARCHAR(400),
	card_name VARCHAR(400),
	card_type CHAR,
	card_length INT,
	card_num VARCHAR(400),
	card_flag_len INT,
	card_flag VARCHAR(400),
	user_id VARCHAR(400),
	raw_add_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,
	raw_update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP  ON UPDATE CURRENT_TIMESTAMP 
);