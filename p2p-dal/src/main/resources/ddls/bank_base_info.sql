use yr789; 
DROP TABLE IF EXISTS bank_base_info; 
CREATE TABLE bank_base_info (
	bank_code VARCHAR(400),
	bank_name VARCHAR(400),
	withholding_amount DECIMAL,
	withdraw_amount DECIMAL,
	signed_way VARCHAR(400),
	is_stop VARCHAR(400),
	logo_url VARCHAR(400),
	memo VARCHAR(400),
	raw_add_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,
	raw_update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP  ON UPDATE CURRENT_TIMESTAMP 
);