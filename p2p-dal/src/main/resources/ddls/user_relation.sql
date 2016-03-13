use yr789; 
DROP TABLE IF EXISTS user_relation; 
CREATE TABLE user_relation (
	user_relation_id BIGINT,
	parent_id BIGINT,
	child_id BIGINT,
	member_no VARCHAR(400),
	relation_status VARCHAR(400)
);