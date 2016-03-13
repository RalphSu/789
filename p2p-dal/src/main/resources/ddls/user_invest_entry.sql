use yr789; 
DROP TABLE IF EXISTS user_invest_entry; 
CREATE TABLE user_invest_entry (
	trade_detail_id BIGINT,
	trade_id BIGINT,
	trade_code VARCHAR(400),
	trade_flow_code VARCHAR(400),
	trade_name VARCHAR(400),
	date DATETIME,
	trade_status TINYINT,
	amount BIGINT,
	investoer_id BIGINT,
	investor_user_name VARCHAR(400),
	investor_real_name VARCHAR(400),
	loaner_id BIGINT,
	loaner_user_name VARCHAR(400),
	loaner_real_name VARCHAR(400),
	transfer_phase VARCHAR(400),
	trade_expire_date DATETIME
);