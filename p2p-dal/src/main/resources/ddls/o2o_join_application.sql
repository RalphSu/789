use yr789; 
DROP TABLE IF EXISTS o2o_join_application; 
CREATE TABLE o2o_join_application (
	id BIGINT,
	company_name VARCHAR(400),
	contact_name VARCHAR(400),
	human_resources VARCHAR(400),
	contact_phone VARCHAR(400),
	industry VARCHAR(400),
	age INT,
	sales_products VARCHAR(400),
	company_address VARCHAR(400),
	status VARCHAR(400),
	row_update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP  ON UPDATE CURRENT_TIMESTAMP ,
	row_add_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,
	annex_url VARCHAR(400)
);