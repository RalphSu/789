use yr789; 
DROP TABLE IF EXISTS agent_investor_trade; 
CREATE TABLE agent_investor_trade (
	trade_detail_id BIGINT,
	invest_trade_detail_id BIGINT,
	trade_id BIGINT,
	trade_code VARCHAR(400),
	trade_name VARCHAR(400),
	investor_id BIGINT,
	broker_id BIGINT,
	investor_name VARCHAR(400),
	broker_name VARCHAR(400),
	transfer_phase VARCHAR(400),
	invest_amount BIGINT,
	broker_amount BIGINT,
	investDate DATETIME,
	effectiveDate DATETIME,
	expireDate DATETIME,
	trade_status TINYINT
);