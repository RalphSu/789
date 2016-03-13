use yr789; 
DROP TABLE IF EXISTS charge_detail; 
CREATE TABLE charge_detail (
	charge_detail_id BIGINT,
	user_id BIGINT,
	charge_amount BIGINT,
	charge_status BOOLEAN,
	trade_detail_id BIGINT,
	charge_actual_date DATETIME,
	charge_date DATETIME,
	note VARCHAR(400)
);