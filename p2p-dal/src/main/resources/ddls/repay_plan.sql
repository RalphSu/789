use yr789; 
DROP TABLE IF EXISTS repay_plan; 
CREATE TABLE repay_plan (
	repay_plan_id BIGINT,
	create_repay_no VARCHAR(400),
	repay_no VARCHAR(400),
	create_batch_no VARCHAR(400),
	period_no INT,
	period_count INT,
	trade_name VARCHAR(400),
	trade_id BIGINT,
	repay_user_id BIGINT,
	repay_user_name VARCHAR(400),
	repay_user_real_name VARCHAR(400),
	repay_division_way VARCHAR(400),
	amount BIGINT,
	original_amount BIGINT,
	status VARCHAR(400),
	repay_date DATETIME,
	actual_repay_date DATETIME,
	raw_add_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,
	raw_update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP  ON UPDATE CURRENT_TIMESTAMP ,
	note VARCHAR(400)
);