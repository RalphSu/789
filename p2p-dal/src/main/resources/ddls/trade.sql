use yr789; 
DROP TABLE IF EXISTS trade; 
CREATE TABLE trade (
	trade_id BIGINT,
	trade_code VARCHAR(400),
	demand_id BIGINT,
	trade_name VARCHAR(400),
	trade_amount BIGINT,
	interest_rate DECIMAL,
	least_invest_amount BIGINT,
	saturation_condition_method VARCHAR(400),
	saturation_condition VARCHAR(400),
	trade_status TINYINT,
	time_limit_unit VARCHAR(400),
	time_limit INT,
	deadline DATETIME,
	loaned_amount BIGINT,
	trade_create_date DATETIME,
	trade_finish_date DATETIME,
	trade_effective_date DATETIME,
	trade_expire_date DATETIME,
	trade_note VARCHAR(400),
	trade_isNotified_loaner VARCHAR(400),
	is_join_activity VARCHAR(400),
	loan_type VARCHAR(400)
);