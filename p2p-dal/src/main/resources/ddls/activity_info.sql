use yr789; 
DROP TABLE IF EXISTS activity_info; 
CREATE TABLE activity_info (
	tbl_base_id BIGINT,
	activity_name VARCHAR(400),
	send_gift_code VARCHAR(400),
	description VARCHAR(400),
	properties VARCHAR(400),
	start_time DATETIME,
	end_time DATETIME,
	status TINYINT,
	rem1 VARCHAR(400)
);