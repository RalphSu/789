use yr789; 
DROP TABLE IF EXISTS sys_param; 
CREATE TABLE sys_param (
	param_name VARCHAR(400),
	param_value VARCHAR(400),
	extend_attribute_one VARCHAR(400),
	extend_attribute_two VARCHAR(400),
	raw_add_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,
	raw_update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP  ON UPDATE CURRENT_TIMESTAMP 
);