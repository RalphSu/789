use yr789; 
DROP TABLE IF EXISTS gift_use_record; 
CREATE TABLE gift_use_record (
	tbl_base_id BIGINT,
	gift_name VARCHAR(400),
	biz_number VARCHAR(400),
	user_id BIGINT,
	user_name VARCHAR(400),
	use_time DATETIME,
	use_amount int,
	note VARCHAR(400),
	status tinyint
);