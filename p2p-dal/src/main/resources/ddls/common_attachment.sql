use yr789; 
DROP TABLE IF EXISTS common_attachment; 
CREATE TABLE common_attachment (
	attachment_id BIGINT,
	biz_no VARCHAR(400),
	module_type VARCHAR(400),
	check_status VARCHAR(400),
	file_name VARCHAR(400),
	isort BIGINT,
	file_physical_path VARCHAR(400),
	request_path VARCHAR(400),
	raw_add_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,
	raw_update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP  ON UPDATE CURRENT_TIMESTAMP 
);