use yr789; 
DROP TABLE IF EXISTS common_district; 
CREATE TABLE common_district (
	id INT,
	nb_no VARCHAR(400),
	father_no VARCHAR(400),
	area_name VARCHAR(400),
	short_name VARCHAR(400),
	area_py VARCHAR(400),
	area_nagda VARCHAR(400),
	cnaps VARCHAR(400),
	user_id VARCHAR(400),
	raw_add_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,
	raw_update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP  ON UPDATE CURRENT_TIMESTAMP 
);