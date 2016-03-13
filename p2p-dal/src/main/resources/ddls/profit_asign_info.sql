use yr789; 
DROP TABLE IF EXISTS profit_asign_info; 
CREATE TABLE profit_asign_info (
	tbl_base_id VARCHAR(400),
	receive_id BIGINT,
	distribution_id BIGINT,
	add_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,
	modify_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP  ON UPDATE CURRENT_TIMESTAMP ,
	distribution_quota decimal,
	note VARCHAR(400),
	rem1 VARCHAR(400)
);