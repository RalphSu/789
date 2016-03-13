use yr789; 
DROP TABLE IF EXISTS pop_info; 
CREATE TABLE pop_info (
	base_id BIGINT,
	title VARCHAR(400),
	type TINYINT,
	status TINYINT,
	parent_id BIGINT,
	parent_name VARCHAR(400),
	content MEDIUMTEXT,
	add_time DATETIME,
	modify_time DATETIME,
	remark VARCHAR(400),
	rem1 VARCHAR(400),
	hits BIGINT
);