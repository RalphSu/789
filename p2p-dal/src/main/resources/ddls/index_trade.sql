use yr789; 
DROP TABLE IF EXISTS index_trade; 
CREATE TABLE index_trade (
	trade_id BIGINT,
	demand_id BIGINT,
	trade_name VARCHAR(400),
	trade_amount BIGINT,
	interest_rate DECIMAL,
	time_limit INT,
	time_limit_unit VARCHAR(400),
	trade_create_date DATETIME,
	deadline DATETIME,
	trade_effective_date DATETIME,
	trade_finish_date DATETIME,
	loaned_amount BIGINT,
	least_invest_amount BIGINT,
	invest_avl_date DATETIME,
	trade_status TINYINT,
	insure_way VARCHAR(400),
	area_nb_no VARCHAR(400),
	area_name VARCHAR(400),
	trade_note VARCHAR(400),
	paymentBankName VARCHAR(400)
);