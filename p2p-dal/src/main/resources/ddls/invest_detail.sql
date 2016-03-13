use yr789; 
DROP TABLE IF EXISTS invest_detail; 
CREATE TABLE invest_detail (
	trade_detail_id BIGINT,
	user_id BIGINT,
	account_id VARCHAR(400),
	amount BIGINT,
	interest_amount BIGINT,
	ext_order VARCHAR(400)
);