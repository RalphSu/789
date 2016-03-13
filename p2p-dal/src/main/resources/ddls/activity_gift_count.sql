use yr789; 
DROP TABLE IF EXISTS activity_gift_count; 
CREATE TABLE activity_gift_count (
	tbl_base_id BIGINT,
	user_id BIGINT,
	gift_name VARCHAR(400),
	gift_code VARCHAR(400),
	gift_type VARCHAR(400),
	start_time DATETIME,
	end_time DATETIME,
	status TINYINT,
	gift_count int
);