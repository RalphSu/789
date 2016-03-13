use yr789; 
DROP TABLE IF EXISTS activity_detail; 
CREATE TABLE activity_detail (
	tbl_base_id BIGINT,
	activity_id BIGINT,
	activity_name VARCHAR(400),
	user_id BIGINT,
	user_name VARCHAR(400),
	real_name VARCHAR(400),
	type tinyint,
	relation_id BIGINT,
	related_user_name VARCHAR(400),
	related_real_name VARCHAR(400),
	gift_name VARCHAR(400),
	gift_code VARCHAR(400),
	gift_number int,
	add_time DATETIME,
	finish_time DATETIME,
	status smallint,
	rem1 VARCHAR(400)
);