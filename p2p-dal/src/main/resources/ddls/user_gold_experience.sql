use yr789; 
DROP TABLE IF EXISTS user_gold_experience; 
CREATE TABLE user_gold_experience (
	id BIGINT,
	user_id BIGINT,
	trade_id BIGINT,
	trade_detail_id BIGINT,
	gold_exp_id BIGINT,
	amount DECIMAL,
	status VARCHAR(400),
	create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,
	usage_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
);