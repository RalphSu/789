use yr789; 
DROP TABLE IF EXISTS extend_attr; 
CREATE TABLE extend_attr (
	tbl_base_id bigint,
	record_id bigint,
	attr_name varchar(400),
	attr_value varchar(400)
);