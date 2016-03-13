use yr789; 
DROP TABLE IF EXISTS loan_type; 
CREATE TABLE loan_type (
	id int,
	code VARCHAR(400),
	name VARCHAR(400),
	hidden VARCHAR(400)
);