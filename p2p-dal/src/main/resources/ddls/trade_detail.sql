use yr789; 
DROP TABLE IF EXISTS trade_detail; 
CREATE TABLE trade_detail (
	trade_detail_id BIGINT,
	order_no VARCHAR(400),
	user_id BIGINT,
	user_base_id VARCHAR(400),
	user_name VARCHAR(400),
	real_name VARCHAR(400),
	role_id INT,
	trade_id BIGINT,
	amount BIGINT,
	transfer_phase VARCHAR(400),
	date DATETIME,
	note VARCHAR(400),
	profit_type TINYINT,
	profit_rate DECIMAL,
	repay_period_no TINYINT,
	repay_period_count TINYINT,
	repay_date DATETIME,
	actual_repay_date DATETIME,
	ext_order VARCHAR(400)
);