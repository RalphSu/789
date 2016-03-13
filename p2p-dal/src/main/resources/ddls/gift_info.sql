use yr789; 
DROP TABLE IF EXISTS gift_info; 
CREATE TABLE gift_info (
	tbl_base_id BIGINT,
	gift_name VARCHAR(400),
	gift_code VARCHAR(400),
	gift_type VARCHAR(400),
	start_time DATETIME,
	end_time DATETIME,
	gift_status TINYINT,
	description VARCHAR(400)
);