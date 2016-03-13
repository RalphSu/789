use yr789; 
DROP TABLE IF EXISTS investment; 
CREATE TABLE investment (
	trade_detail_id BIGINT,
	trade_id BIGINT,
	demand_id BIGINT,
	trade_code VARCHAR(400),
	trade_name VARCHAR(400),
	trade_status TINYINT,
	amount BIGINT,
	receive_id BIGINT,
	receive_real_name VARCHAR(400),
	receive_user_name VARCHAR(400),
	pay_id BIGINT,
	pay_real_name VARCHAR(400),
	pay_user_name VARCHAR(400),
	pay_user_mail VARCHAR(400),
	pay_date DATETIME,
	trade_create_date DATETIME,
	trade_finish_date DATETIME,
	pay_note VARCHAR(400),
	trade_note VARCHAR(400),
	charge_amount BIGINT
);