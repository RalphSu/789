use yr789; 
DROP TABLE IF EXISTS operation_journal; 
CREATE TABLE operation_journal (
	identity BIGINT,
	base_module_name VARCHAR(400),
	permission_name VARCHAR(400),
	operation_content VARCHAR(400),
	memo VARCHAR(400),
	operator_id BIGINT,
	operator_name VARCHAR(400),
	operator_ip VARCHAR(400),
	raw_add_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,
	raw_update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP  ON UPDATE CURRENT_TIMESTAMP 
);