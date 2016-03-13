use yr789; 
DROP TABLE IF EXISTS charge_project; 
CREATE TABLE charge_project (
	charge_project_id BIGINT,
	charge_mathod VARCHAR(400),
	project_code VARCHAR(400),
	charge_project VARCHAR(400),
	trade_status TINYINT
);