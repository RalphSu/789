use yr789; 
DROP TABLE IF EXISTS amount_flow_trade; 
CREATE TABLE amount_flow_trade (
	base_id BIGINT,
	trade_id BIGINT,
	amount_flow_id BIGINT,
	trade_detail_id BIGINT
);