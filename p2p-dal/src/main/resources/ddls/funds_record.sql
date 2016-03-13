use yr789; 
DROP TABLE IF EXISTS funds_record; 
CREATE TABLE funds_record (
	tb_id VARCHAR(400),
	user_id BIGINT,
	real_name VARCHAR(400),
	cert_no VARCHAR(400),
	user_name VARCHAR(400),
	out_biz_no VARCHAR(400),
	pay_no VARCHAR(400),
	funds_from VARCHAR(400),
	funds_to VARCHAR(400),
	amounts decimal,
	charges decimal,
	acount_name VARCHAR(400),
	acount_id VARCHAR(400),
	bank_name VARCHAR(400),
	bankAcount_No VARCHAR(400),
	bank_open_name VARCHAR(400),
	pay_time DATETIME,
	pay_type VARCHAR(400),
	status int
);