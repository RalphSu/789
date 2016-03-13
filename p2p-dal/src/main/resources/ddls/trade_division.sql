use yr789; 
DROP TABLE IF EXISTS trade_division; 
CREATE TABLE trade_division (
	trade_division_id BIGINT,
	division_template_id BIGINT,
	trade_id BIGINT
);