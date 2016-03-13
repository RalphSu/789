use yr789; 
DROP TABLE IF EXISTS division_detail; 
CREATE TABLE division_detail (
	detail_id BIGINT,
	business_code VARCHAR(400),
	out_biz_no VARCHAR(400),
	user_id BIGINT,
	trade_id BIGINT,
	trade_detail_id BIGINT,
	amount BIGINT,
	division_status INT,
	division_date DATETIME
);