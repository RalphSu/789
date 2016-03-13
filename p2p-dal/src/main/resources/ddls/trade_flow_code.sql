use yr789; 
DROP TABLE IF EXISTS trade_flow_code; 
CREATE TABLE trade_flow_code (
	tbl_base_id VARCHAR(400),
	trade_detail_id BIGINT,
	trade_flow_code VARCHAR(400),
	row_add_time DATETIME,
	note VARCHAR(400),
	state TINYINT
);