use yr789; 
DROP TABLE IF EXISTS transfer_trade; 
CREATE TABLE transfer_trade (
	user_id BIGINT,
	yjf_user_name VARCHAR(400),
	transfer_phase VARCHAR(400),
	amount BIGINT,
	trade_detail_id BIGINT
);