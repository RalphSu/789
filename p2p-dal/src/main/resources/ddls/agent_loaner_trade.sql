use yr789; 
DROP TABLE IF EXISTS agent_loaner_trade; 
CREATE TABLE agent_loaner_trade (
	trade_detail_id BIGINT,
	trade_id BIGINT,
	trade_code VARCHAR(400),
	trade_name VARCHAR(400),
	loaner_id BIGINT,
	agency_id BIGINT,
	loaner_name VARCHAR(400),
	agency_name VARCHAR(400),
	transfer_phase VARCHAR(400),
	loan_amount BIGINT,
	agency_amount BIGINT,
	loanDate DATETIME,
	effectiveDate DATETIME,
	expireDate DATETIME,
	trade_status TINYINT
);